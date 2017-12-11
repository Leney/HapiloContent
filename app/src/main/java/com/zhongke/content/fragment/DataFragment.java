package com.zhongke.content.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhongke.content.BaseFragment;
import com.zhongke.content.R;
import com.zhongke.content.entity.ActivityDataBean;
import com.zhongke.content.glide.GlideLoader;
import com.zhongke.content.retrofit.CommonException;
import com.zhongke.content.retrofit.ResponseResultListener;
import com.zhongke.content.utils.LogUtil;
import com.zhongke.content.utils.UIUtils;

import java.util.HashMap;
import java.util.Map;

import rx.Subscription;

/**
 * Created by dgg1 on 2017/10/27.
 * 活动详情
 * 资料Fragment
 */

public class DataFragment extends BaseFragment {
    /**
     * 活动资料展示图片控件
     */
    private ImageView ivDataPhoto;
    /**
     * 活动地点
     */
    private TextView tvLocation;
    /**
     * 活动时间
     */
    private TextView tvTime;
    //活动id
    private String actionId;

    public static DataFragment newInstance(String actionId) {
        DataFragment dataFragment = new DataFragment();
        Bundle bundle = new Bundle();
        bundle.putString("actionId",actionId);
        dataFragment.setArguments(bundle);
        return dataFragment;
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
        return R.layout.fragment_data;
    }

    @Override
    protected void initAfterActivityCreated(View rootView, Bundle savedInstanceState) {
        ivDataPhoto = rootView.findViewById(R.id.iv_photo);
        tvLocation = rootView.findViewById(R.id.tv_location);
        tvTime = rootView.findViewById(R.id.tv_time);
        //GlideLoader.loadNetWorkResource(getActivity(), "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1509102507872&di=19a1762b89ef41aaadba06badb233b74&imgtype=0&src=http%3A%2F%2Fpicuser.city8.com%2Fnews%2Fimage%2F2015916m%2F635780170322481930%25B6%25AB%25BE%25A9%25B5%25CF%25CA%25BF%25C4%25E1%25C0%25D6%25D4%25B01.jpg", ivDataPhoto);
        getActivityData(actionId);
    }

    private void getActivityData(String actionId) {
        Map<String,String> map = new HashMap<>();
//        map.put("token",token);
        map.put("actionId",actionId);
        Subscription subscription = retrofitProvider.getActivityData(map, new ResponseResultListener<ActivityDataBean>() {
            @Override
            public void success(ActivityDataBean activityDataBean) {
                LogUtil.e("success---");
                getActivityDataSuccess(activityDataBean.getAction());
            }

            @Override
            public void failure(CommonException e) {
                UIUtils.showToast(e.getErrorMsg());
                LogUtil.e("failure---"+e.toString());
            }
        });
        compositeSubscription.add(subscription);
    }

    private void getActivityDataSuccess(ActivityDataBean.ActionBean bean) {
        GlideLoader.loadNetWorkResource(getActivity(),bean.getCoverUrl(),ivDataPhoto);
        tvLocation.setText(bean.getAddress());
        tvTime.setText(bean.getBeginTime().substring(0,16)+"-"+"\n"+bean.getEndTime().substring(0,16));
    }
}
