package com.zhongke.content.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zhongke.content.BaseFragment;
import com.zhongke.content.R;
import com.zhongke.content.adapter.PropsAdapter;
import com.zhongke.content.entity.PropsBean2;
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
 * 道具Fragment
 */

public class PropsFragment extends BaseFragment {
    private RecyclerView recyclerView;
    private PropsAdapter adapter;
    private List<PropsBean2.RecordsBean> propList;
    private String actionId;

    public static PropsFragment newInstance(String actionId) {
        PropsFragment fragment = new PropsFragment();
        Bundle args = new Bundle();
        args.putString("actionId",actionId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            actionId = getArguments().getString("actionId");
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_props;
    }

    @Override
    protected void initAfterActivityCreated(View rootView, Bundle savedInstanceState) {
        propList = new ArrayList<>();
        recyclerView = rootView.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        adapter = new PropsAdapter(propList,getActivity());
        recyclerView.setAdapter(adapter);
        getActivityProps(actionId);
    }

    private void getActivityProps(String actionId) {
        Map<String,String> map = new HashMap<>();
//        map.put("token",token);
        map.put("actionId",actionId);

        Subscription subscription = retrofitProvider.getActivityProps(map, new ResponseResultListener<PropsBean2>() {
            @Override
            public void success(PropsBean2 propsBean2) {
                LogUtil.e("success---");
                propList.clear();
                propList.addAll(propsBean2.getRecords());
                adapter.notifyDataSetChanged();
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
