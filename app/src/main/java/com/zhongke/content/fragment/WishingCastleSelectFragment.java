package com.zhongke.content.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.zhongke.content.BaseFragment;
import com.zhongke.content.R;
import com.zhongke.content.adapter.WishingCastleSelectAdapter;
import com.zhongke.content.adapter.WishingCastleSelectContentAdapter;
import com.zhongke.content.entity.GiftListBean;
import com.zhongke.content.entity.WishingCastleBean;
import com.zhongke.content.entity.event.SelectWishEvent;
import com.zhongke.content.retrofit.CommonException;
import com.zhongke.content.retrofit.ResponseResultListener;
import com.zhongke.content.rxjava.RxBus;
import com.zhongke.content.utils.DisplayUtils;
import com.zhongke.content.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

import rx.Subscription;

/**
 * Created by ${xingen} on 2017/9/26.
 * <p>
 * 许愿城堡挑选界面
 */

public class WishingCastleSelectFragment extends BaseFragment {
    private RecyclerView recyclerView;
    private WishingCastleSelectAdapter wishingCastleSelectAdapter;
    public static final String TAG = WishingCastleSelectFragment.class.getSimpleName();
    private List<GiftListBean.RecordsBean> giftList = new ArrayList<>();

    public static WishingCastleSelectFragment newInstance() {
        WishingCastleSelectFragment fragment = new WishingCastleSelectFragment();
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_wishingcastleselect;
    }

    private ViewPager viewPager;
    private WishingCastleSelectContentAdapter wishingCastleSelectContentAdapter;

    @Override
    protected void initAfterActivityCreated(View rootView, Bundle savedInstanceState) {
        getGiftList();
        recyclerView = rootView.findViewById(R.id.wishing_wishing_castle_select_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        this.wishingCastleSelectAdapter = new WishingCastleSelectAdapter(R.layout.item_wishing_castle_select, WishingCastleBean.newInstance().dataList);

        recyclerView.setAdapter(wishingCastleSelectAdapter);
        this.wishingCastleSelectAdapter.setOnItemClickListener((adapter, view, position) -> {
            if (wishingCastleSelectAdapter.getCurrentSelect() == position) {return;}
            wishingCastleSelectAdapter.notifySelectItem(position);
        });
        TextView textView = rootView.findViewById(R.id.wishing_castle_select_tip_tv);
        String[] strings = {"有没有你想要的礼物 ？你也可以  ", "去树洞许愿!", "  》》"};
        int[] colors = {Color.parseColor("#9b5231"), Color.parseColor("#db235b"), Color.parseColor("#9b5231")};
        textView.setText(setText(colors, strings));
        textView.setOnClickListener(view ->
//                ((DesireActivity) getActivity()).addOrShowOrHideFragment(WishingCastleFragment.TAG)
                ((WishingTabFragment) getParentFragment()).addOrShowOrHideFragment(WishingCastleFragment.TAG)
        );

        this.viewPager = rootView.findViewById(R.id.wishing_castle_select_viewpager);
/*
        this.wishingCastleSelectContentAdapter = new WishingCastleSelectContentAdapter(getActivity());

        this.wishingCastleSelectContentAdapter.setOnWishingItemClickListener(new WishingCastleSelectContentAdapter.OnWishingItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Log.i("llj","position--------->>>"+position);
//                ((DesireActivity) getActivity()).addOrShowOrHideFragment(WishingCastleFragment.TAG);
                ((WishingTabFragment) getParentFragment()).addOrShowOrHideFragment(WishingCastleFragment.TAG);
            }
        });

        //滚动无阴影
        this.viewPager.setOverScrollMode(View.OVER_SCROLL_NEVER);
//        this.wishingCastleSelectContentAdapter.setClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.i("llj","dddddffff");
//                ((DesireActivity) getActivity()).addOrShowOrHideFragment(WishingCastleSelectFragment.TAG);
//            }
//        });
        this.viewPager.setAdapter(wishingCastleSelectContentAdapter);
//        this.wishingCastleSelectContentAdapter.setClickListener(
//                view -> ((DesireActivity) getActivity()).addOrShowOrHideFragment(WishingCastleSelectFragment.TAG)
//        );*/
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

    public void getGiftList() {
        Subscription subscription = retrofitProvider.getGiftList(new ResponseResultListener<GiftListBean>() {
            @Override
            public void success(GiftListBean giftListBean) {
                LogUtil.e("success---");
                giftList.addAll(giftListBean.getRecords());

                wishingCastleSelectContentAdapter = new WishingCastleSelectContentAdapter(getActivity(),giftList);

                wishingCastleSelectContentAdapter.setOnWishingItemClickListener(new WishingCastleSelectContentAdapter.OnWishingItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        Log.i("llj","position--------->>>"+position);
//                ((DesireActivity) getActivity()).addOrShowOrHideFragment(WishingCastleFragment.TAG);
                        GiftListBean.RecordsBean giftBean = giftList.get(position);
                        LogUtil.e("giftBean--"+giftBean.toString());
                        ((WishingTabFragment) getParentFragment()).addOrShowOrHideFragment(WishingCastleFragment.TAG);
                        SelectWishEvent event = new SelectWishEvent(giftBean);

                        //EventBus.getDefault().postSticky(event);
                        RxBus.getInstance().post(event);
                    }
                });

                //滚动无阴影
                viewPager.setOverScrollMode(View.OVER_SCROLL_NEVER);
//        this.wishingCastleSelectContentAdapter.setClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.i("llj","dddddffff");
//                ((DesireActivity) getActivity()).addOrShowOrHideFragment(WishingCastleSelectFragment.TAG);
//            }
//        });
                viewPager.setAdapter(wishingCastleSelectContentAdapter);
//        this.wishingCastleSelectContentAdapter.setClickListener(
//                view -> ((DesireActivity) getActivity()).addOrShowOrHideFragment(WishingCastleSelectFragment.TAG)
//        );
            }

            @Override
            public void failure(CommonException e) {
                LogUtil.e("failure---"+e.getErrorMsg());
            }
        });
        compositeSubscription.add(subscription);
    }
}
