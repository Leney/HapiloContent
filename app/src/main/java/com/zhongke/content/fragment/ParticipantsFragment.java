package com.zhongke.content.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zhongke.content.BaseFragment;
import com.zhongke.content.R;
import com.zhongke.content.adapter.PartcipantsAdapter;
import com.zhongke.content.entity.ActivityActorBean;
import com.zhongke.content.retrofit.CommonException;
import com.zhongke.content.retrofit.ResponseResultListener;
import com.zhongke.content.utils.LogUtil;
import com.zhongke.content.utils.UIUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Subscription;

/**
 * Created by dgg1 on 2017/10/28.
 * 参与人员Fragment
 */

public class ParticipantsFragment extends BaseFragment {
    private RecyclerView recyclerView;
    private List<ActivityActorBean.RecordsBean> actorList;
    private PartcipantsAdapter partcipantsAdapter;
    private String actionId;

    public static ParticipantsFragment newInstance(String actionId) {
        ParticipantsFragment fragment = new ParticipantsFragment();
        Bundle args = new Bundle();
        args.putString("actionId",actionId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() !=null) {
            actionId = getArguments().getString("actionId");
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_participants;
    }

    @Override
    protected void initAfterActivityCreated(View rootView, Bundle savedInstanceState) {
        recyclerView = rootView.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 5));
//        List<String> list = new ArrayList<>();
//        list.add("http://pic.qqtn.com/up/2017-6/2017061415044584326.jpg");
//        list.add("http://img5.duitang.com/uploads/item/201402/13/20140213092819_n5TBT.thumb.224_0.jpeg");
//        list.add("http://www.ld12.com/upimg358/allimg/c140921/14112D19150210-322526.jpg");
//        list.add("http://www.qqzhi.com/uploadpic/2014-05-10/021634914.jpg");
//        list.add("http://img3.imgtn.bdimg.com/it/u=3738499274,804158082&fm=214&gp=0.jpg");
//        list.add("http://www.qqzhi.com/uploadpic/2014-05-31/071814656.jpg");
//        list.add("http://img.qq1234.org/uploads/allimg/150531/8_150531112147_11.jpg");
//        list.add("http://m.qqzhi.com/upload/img_3_2719056953D921480316_23.jpg");
//        list.add("http://tx.haiqq.com/uploads/allimg/150323/151R13N6-3.jpg");
//        list.add("http://img4.imgtn.bdimg.com/it/u=218662678,2277974231&fm=27&gp=0.jpg");
//        list.add("http://img4.imgtn.bdimg.com/it/u=218662678,2277974231&fm=27&gp=0.jpg");
        actorList = new ArrayList<>();
        partcipantsAdapter = new PartcipantsAdapter(actorList, getActivity());
        recyclerView.setAdapter(partcipantsAdapter);

        getActivityActorList(actionId);
    }

    //获取活动参加人员
    private void getActivityActorList(String actionId) {
        Map<String,String> map = new HashMap<>();
//        map.put("token",token);
        map.put("actionId",actionId);

        Subscription subscription = retrofitProvider.getActivityActorList(map, new ResponseResultListener<ActivityActorBean>() {
            @Override
            public void success(ActivityActorBean activityActorBean) {
                LogUtil.e("success---");
                actorList.clear();
                actorList.addAll(activityActorBean.getRecords());
                partcipantsAdapter.notifyDataSetChanged();
            }

            @Override
            public void failure(CommonException e) {
                UIUtils.showToast(e.getErrorMsg());
                LogUtil.e("failure---"+e.toString());
            }
        });
        compositeSubscription.add(subscription);
    }

}
