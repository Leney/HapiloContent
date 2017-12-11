package com.zhongke.content.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zhongke.content.BaseFragment;
import com.zhongke.content.R;
import com.zhongke.content.activity.ActivityListActivity;
import com.zhongke.content.activity.MyActivityDetailActivity;
import com.zhongke.content.adapter.MineActivityListAdapter;
import com.zhongke.content.entity.MyJoinActivityBean;
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
 */

public class ActivityMineFragment extends BaseFragment implements MineActivityListAdapter.OnItemClickListener {
    private static final String TAG = "ActivityMineFragment";

    private RecyclerView rvMine;
    private List<MyJoinActivityBean.RecordsBean> activityList;
    private MineActivityListAdapter adapter;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_activity_mine;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void initAfterActivityCreated(View rootView, Bundle savedInstanceState) {
        rvMine = rootView.findViewById(R.id.mine_list);
        rvMine.setLayoutManager(new LinearLayoutManager(getActivity()));

        activityList = new ArrayList<>();
        adapter = new MineActivityListAdapter(activityList, getActivity());
        rvMine.setAdapter(adapter);
        adapter.setListener(this);
        rvMine.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                ((ActivityListActivity) getActivity()).notifyRotate(dy);
            }
        });
        getActivityList();
    }

    //获取我的活动列表
    private void getActivityList() {
        Map<String,String> map = new HashMap<>();
//        map.put("token",token);

        Subscription subscription = retrofitProvider.getMyJoinActivity(map, new ResponseResultListener<MyJoinActivityBean>() {
            @Override
            public void success(MyJoinActivityBean myJoinActivityBean) {
                activityList.clear();
                activityList.addAll(myJoinActivityBean.getRecords());
                adapter.notifyDataSetChanged();
                LogUtil.e("success-----");
            }

            @Override
            public void failure(CommonException e) {
                UIUtils.showToast("获取数据失败");
                LogUtil.e("failure-----"+e.toString());
            }
        });
        compositeSubscription.add(subscription);
    }

    @Override
    public void onItemClick(View view, int position) {
        MyJoinActivityBean.RecordsBean bean = activityList.get(position);
        MyActivityDetailActivity.startActivity(getActivity(), bean.getActionId(), bean.getTitle(),bean.getBeginTime(),bean.getEndTime());
    }

    //重新请求数据
    @Subscribe(threadMode = ThreadMode.MAIN,priority = 990)
    public void onMineRefreshEvent(RefreshEvent event) {
        LogUtil.e("重新请求");
        getActivityList();
    }
}
