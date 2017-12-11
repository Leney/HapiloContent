package com.zhongke.content.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.GridView;

import com.zhongke.content.BaseFragment;
import com.zhongke.content.R;
import com.zhongke.content.adapter.ActivityRewardAdapter;
import com.zhongke.content.entity.ActivityRewardBean2;
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
 * Created by dgg1 on 2017/10/27.
 * 奖励Fragment
 */

public class RewardFragment extends BaseFragment {
    private GridView gv;
    private ActivityRewardAdapter adapter;
    private List<ActivityRewardBean2.RecordsBean> rewardList;
    private String actionId;

    public static RewardFragment newInstance(String actionId) {
        RewardFragment rewardFragment = new RewardFragment();
        Bundle args = new Bundle();
        args.putString("actionId",actionId);
        rewardFragment.setArguments(args);
        return rewardFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments()!=null) {
            actionId = getArguments().getString("actionId");
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_reward;
    }

    @Override
    protected void initAfterActivityCreated(View rootView, Bundle savedInstanceState) {
        rewardList = new ArrayList<>();
        gv = rootView.findViewById(R.id.grid_view);
        adapter = new ActivityRewardAdapter(rewardList, getActivity());
        gv.setAdapter(adapter);

        getActivityAward(actionId);
    }

    private void getActivityAward(String actionId) {
        Map<String,String> map = new HashMap<>();
//        map.put("token",token);
        map.put("actionId",actionId);
        Subscription subscription = retrofitProvider.getActivityRewardList(map, new ResponseResultListener<ActivityRewardBean2>() {
            @Override
            public void success(ActivityRewardBean2 activityRewardBean2) {
                LogUtil.e("success----");
                rewardList.addAll(activityRewardBean2.getRecords());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void failure(CommonException e) {
                UIUtils.showToast(e.getErrorMsg());
                LogUtil.e("failure----"+e.toString());
            }
        });
        compositeSubscription.add(subscription);
    }

}
