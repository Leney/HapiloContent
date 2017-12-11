package com.zhongke.content.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.zhongke.content.BaseFragment;
import com.zhongke.content.R;
import com.zhongke.content.entity.WishResponseBean;
import com.zhongke.content.entity.event.SelectWishEvent;
import com.zhongke.content.retrofit.CommonException;
import com.zhongke.content.retrofit.ResponseResultListener;
import com.zhongke.content.rxjava.RxBus;
import com.zhongke.content.utils.LogUtil;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by ${xingen} on 2017/9/26.
 *
 * 愿望城堡
 *
 */

public class WishingCastleFragment extends BaseFragment implements View.OnClickListener {
    public static final String TAG=WishingCastleFragment.class.getSimpleName();
    private TextView tip;

    public static WishingCastleFragment newInstance(){
        WishingCastleFragment fragment=new WishingCastleFragment();
        return fragment;
    }
    private int wishType = -1;
    private String wishInfo;
    private String wishName;
    private int awardId;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_wishingcastle;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //EventBus.getDefault().unregister(this);
        unRegisterObservable();
    }

    @Override
    protected void initAfterActivityCreated(View rootView, Bundle savedInstanceState) {
        //EventBus.getDefault().register(this);

        rootView.findViewById(R.id.wishing_castle_sure_btn).setOnClickListener(this);
        tip = rootView.findViewById(R.id.wishing_castle_tip);
        //tip.setText("你刚刚选择了"+wishName+"-1"+"作为你想要的愿望，你确定吗？每个小朋友只能许下XX愿望哦！");
        registerObservable();
    }

    private Observable<SelectWishEvent> observable;
    private void registerObservable() {
        observable = RxBus.getInstance().register(SelectWishEvent.class);
        observable.observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<SelectWishEvent>() {
            @Override
            public void call(SelectWishEvent event) {
                Log.e(TAG, "event--2-->" +event.recordsBean.toString());
                wishInfo = event.recordsBean.getAwardName();
                wishName = event.recordsBean.getAwardName();
                awardId = event.recordsBean.getId();

                tip.setText("你刚刚选择了"+wishName+"作为你想要的愿望，你确定吗？每个小朋友只能许下XX愿望哦！");
                LogUtil.e("wishInfo--"+wishInfo+"wishName--"+wishName+"awardId--"+awardId);
            }
        });
    }

    private void unRegisterObservable() {
        if (observable != null) {
            RxBus.getInstance().unregister(SelectWishEvent.class, observable);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.wishing_castle_sure_btn:
                //Toast.makeText(getActivity(), "发送愿望成功!", Toast.LENGTH_SHORT).show();
                addWish(wishName,3,wishInfo,awardId+"");
                break;
        }
    }

    //添加愿望 愿望类型（1.文字愿望2.音频愿望3.礼物）
    public void addWish (String wishName,int wishType,String wishInfo,String awardId) {
        Map<String,Object> map = new HashMap<>();
        map.put("wishName",wishName);
        map.put("wishType",wishType);
        map.put("wishInfo",wishInfo);
        map.put("awardId",awardId);

        Subscription subscription = retrofitProvider.addUeserWish(map, new ResponseResultListener<WishResponseBean>() {
            @Override
            public void success(WishResponseBean bean) {
                Toast.makeText(getActivity(), "发送愿望成功!", Toast.LENGTH_SHORT).show();
                ((WishingTabFragment)getParentFragment()).back();

            }

            @Override
            public void failure(CommonException e) {
                Toast.makeText(getActivity(), "发送愿望失败", Toast.LENGTH_SHORT).show();
                LogUtil.e("e--"+e.getErrorMsg());
            }
        });
        compositeSubscription.add(subscription);
    }

//    @Subscribe(threadMode = ThreadMode.MAIN,priority = 990,sticky = true)
//    public void onWishEvent(SelectWishEvent selectWishEvent) {
//        LogUtil.e("event recordsBean-----"+selectWishEvent.recordsBean.toString());
//        wishInfo = selectWishEvent.recordsBean.getAwardName();
//        wishName = selectWishEvent.recordsBean.getAwardName();
//        awardId = selectWishEvent.recordsBean.getId();
//
//        tip.setText("你刚刚选择了"+wishName+"作为你想要的愿望，你确定吗？每个小朋友只能许下XX愿望哦！");
//        LogUtil.e("wishInfo--"+wishInfo+"wishName--"+wishName+"awardId--"+awardId);
//    }


}
