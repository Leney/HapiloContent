package com.zhongke.content.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zhongke.content.BaseFragment;
import com.zhongke.content.R;
import com.zhongke.content.activity.ActivityDetailActivity;
import com.zhongke.content.activity.ActivityListActivity;
import com.zhongke.content.adapter.ActivityListAdapter;
import com.zhongke.content.entity.ActivityListBean2;
import com.zhongke.content.entity.event.RefreshEvent;
import com.zhongke.content.retrofit.CommonException;
import com.zhongke.content.retrofit.ResponseResultListener;
import com.zhongke.content.utils.LogUtil;
import com.zhongke.content.utils.UIUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Subscription;

/**
 * Created by dgg1 on 2017/10/31.
 * 活动大厅Fragment
 */

public class ActivityHallFragment extends BaseFragment implements ActivityListAdapter.OnItemClickListener{
    private static final String TAG = "ActivityHallFragment";
    private RecyclerView lvHall;
    private List<ActivityListBean2.RecordsBean> activityList = new ArrayList<>();
    private ActivityListAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_activity_hall;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void initAfterActivityCreated(View rootView, Bundle savedInstanceState) {
        lvHall = rootView.findViewById(R.id.hall_list);
        lvHall.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new ActivityListAdapter(activityList, getActivity());
        lvHall.setAdapter(adapter);
        adapter.setListener(this);

        lvHall.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                ((ActivityListActivity) getActivity()).notifyRotate(dy);
            }
        });

        getActivityList();
    }

    private void getActivityList() {
        Map<String,String> map = new HashMap<>();

        Subscription subscription = retrofitProvider.getActivityList(map, new ResponseResultListener<ActivityListBean2>() {
            @Override
            public void success(ActivityListBean2 activityListBean2) {
                activityList.clear();
                activityList.addAll(activityListBean2.getRecords());
                LogUtil.e(TAG,"getActivityList success--");
                adapter.notifyDataSetChanged();
            }
            @Override
            public void failure(CommonException e) {
                UIUtils.showToast(e.getErrorMsg());
                LogUtil.e(TAG,"getActivityList failure---"+e.toString());
            }
        });
        compositeSubscription.add(subscription);
    }

    @Override
    public void onItemClick(View view, int position) {
        ActivityListBean2.RecordsBean bean = activityList.get(position);
        ActivityDetailActivity.startActivity(getActivity(), bean.getId(), bean.getTitle(),bean.getBeginTime(),bean.getEndTime());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    //刷新adapter
    @Subscribe(threadMode = ThreadMode.MAIN,priority = 991)
    public void onRefreshEvent(RefreshEvent event) {
        LogUtil.e("刷新");

        for (int i = 0,size = activityList.size();i < size;i++) {
            ActivityListBean2.RecordsBean bean = activityList.get(i);
            if (bean.getId() == event.actionId) {
                activityList.remove(bean);
            }
        }
//        if (position != -1) {
//            LogUtil.e("position");
//            adapter.notifyItemChanged(position);
//        }
        adapter.notifyDataSetChanged();
    }
}
