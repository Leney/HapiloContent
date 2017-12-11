package com.zhongke.content.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;

import com.zhongke.content.BaseFragment;
import com.zhongke.content.R;
import com.zhongke.content.entity.ProcessBean2;
import com.zhongke.content.retrofit.CommonException;
import com.zhongke.content.retrofit.ResponseResultListener;
import com.zhongke.content.utils.LogUtil;
import com.zhongke.content.utils.UIUtils;
import com.zhongke.content.view.ProcessView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Subscription;

/**
 * Created by dgg1 on 2017/10/28.
 * 流程Fragment
 */

public class ProcessFragment extends BaseFragment {
    private LinearLayout llLayout;
    private String actionId;

    public static ProcessFragment newInstance(String actionId) {
        ProcessFragment fragment = new ProcessFragment();
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
        return R.layout.fragment_process;
    }

    @Override
    protected void initAfterActivityCreated(View rootView, Bundle savedInstanceState) {
        llLayout = rootView.findViewById(R.id.ll);
//        List<ProcessBean> list = ProcessBean.getData();
//        for (int i = 0; i < list.size(); i++) {
//            ProcessView pv = new ProcessView(getActivity());
//            pv.setProcess(list.get(i).getSerial() + "", list.get(i).getTitle(), list.get(i).getText());
//            llLayout.addView(pv);
//        }
        getActivityProcess(actionId);
    }

    //获取活动流程列表
    private void getActivityProcess(String actionId) {
        List<ProcessBean2.FlowDataBean> processBean2List = new ArrayList<>();
        Map<String,String> map = new HashMap<>();
//        map.put("token",token);
        map.put("actionId",actionId);

        Subscription subscription = retrofitProvider.getActivityProcess(map, new ResponseResultListener<ProcessBean2>() {
            @Override
            public void success(ProcessBean2 processBean2) {
                processBean2List.clear();
                processBean2List.addAll(processBean2.getFlowData());
                getProcessView(processBean2List);
                LogUtil.e("process success------");
            }

            @Override
            public void failure(CommonException e) {
                UIUtils.showToast(e.getErrorMsg());
                LogUtil.e("process failure------"+e.toString());
            }
        });
        compositeSubscription.add(subscription);
    }

    private void getProcessView(List<ProcessBean2.FlowDataBean> flowDataList) {
        for (int i =0,size = flowDataList.size();i < size;i++) {
            ProcessView pv = new ProcessView(getActivity());
            pv.setProcess((i + 1)+"",flowDataList.get(i).getBehaviorName() , flowDataList.get(i).getMsgContent());
            llLayout.addView(pv);
        }
    }
}
