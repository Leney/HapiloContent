package com.zhongke.content.fragment;

import android.animation.AnimatorListenerAdapter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.zhongke.content.BaseFragment;
import com.zhongke.content.R;
import com.zhongke.content.adapter.DesireAdapter;
import com.zhongke.content.entity.UserDesireListBean;
import com.zhongke.content.retrofit.CommonException;
import com.zhongke.content.retrofit.ResponseResultListener;
import com.zhongke.content.utils.DisplayUtils;
import com.zhongke.content.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

import rx.Subscription;

/**
 * Created by ${xingen} on 2017/9/18.
 * 愿望界面
 */

public class DesireFragment extends BaseFragment implements View.OnClickListener {
    public static final String TAG = DesireFragment.class.getSimpleName();
    private List<UserDesireListBean.RecordsBean> recordsBeanList;
    private TextView textTip;

    public static DesireFragment newInstance() {
        DesireFragment fragment = new DesireFragment();
        return fragment;
    }

    private ViewPager viewPager;
    private DesireAdapter desireAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_desire;
    }

    @Override
    protected void initAfterActivityCreated(View rootView, Bundle savedInstanceState) {
        recordsBeanList = new ArrayList<>();
        getDesireList();
        this.viewPager = rootView.findViewById(R.id.desire_viewpager);
        textTip = rootView.findViewById(R.id.desire_fragment_text_tip);
        String[] strings = {"没有愿望哦  ", "去许愿吧", "  》》"};
        int[] colors = {Color.parseColor("#9b5231"), Color.parseColor("#db235b"), Color.parseColor("#9b5231")};
        textTip.setText(setText(colors, strings));
        textTip.setOnClickListener(this);
//        this.desireAdapter = new DesireAdapter(getActivity());
//        this.viewPager.setAdapter(this.desireAdapter);
        //滚动无阴影
        this.viewPager.setOverScrollMode(View.OVER_SCROLL_NEVER);
    }

    private SpannableStringBuilder setText(int[] colors, String[] s) {
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        int length = 0;
        int textSize_large = DisplayUtils.dip2px(getActivity(), 24);
        int textSize_small = DisplayUtils.dip2px(getActivity(), 20);
        for (int i = 0; i < s.length; ++i) {
            spannableStringBuilder.append(s[i]);
            spannableStringBuilder.setSpan(new ForegroundColorSpan(colors[i]), length, spannableStringBuilder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            if (i == 1) {
                spannableStringBuilder.setSpan(new AbsoluteSizeSpan(textSize_large), length, spannableStringBuilder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            } else {
                spannableStringBuilder.setSpan(new AbsoluteSizeSpan(textSize_small), length, spannableStringBuilder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            length = spannableStringBuilder.length();
        }
        return spannableStringBuilder;
    }

    public void startLayoutDownAnimator(AnimatorListenerAdapter animatorListenerAdapter){
        this.desireAdapter.startLayoutAnimator(animatorListenerAdapter);
        Log.i(TAG," ViewPager"+viewPager.getHeight()+" "+viewPager.getMeasuredHeight());
    }

    //获取我的愿望列表
    public void getDesireList() {
        Subscription subscription = retrofitProvider.getUserDesireList(new ResponseResultListener<UserDesireListBean>() {
            @Override
            public void success(UserDesireListBean userDesireListBean) {
                LogUtil.e("success--"+userDesireListBean.getRecords());
                recordsBeanList.addAll(userDesireListBean.getRecords());

                int size = (userDesireListBean.getRecords().size() / 3) + 1;
                LogUtil.e("size"+size);
                desireAdapter = new DesireAdapter(getActivity(),recordsBeanList);
                viewPager.setAdapter(desireAdapter);
                if (userDesireListBean.getRecords().isEmpty()) {
                    //UIUtils.showToast("没有愿望哦,记得去许愿");
                    //viewPager.setVisibility(View.GONE);
                    //textTip.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void failure(CommonException e) {
                LogUtil.e("failure--"+e.getErrorMsg());
            }
        });
       compositeSubscription.add(subscription);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.desire_fragment_text_tip:

                break;
            default:
                break;
        }
    }
}
