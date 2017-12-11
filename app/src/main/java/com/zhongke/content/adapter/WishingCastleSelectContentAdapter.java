package com.zhongke.content.adapter;

import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.zhongke.content.entity.GiftListBean;
import com.zhongke.content.view.DesireLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${xingen} on 2017/9/18.
 */

public class WishingCastleSelectContentAdapter extends PagerAdapter {
    private final String tag = WishingCastleSelectContentAdapter.class.getSimpleName();
    private List<String> list;
    private Context context;
    private List<DesireLayout> desireLayoutList;
    private OnWishingItemClickListener mOnWishingItemClickListener;
    private List<GiftListBean.RecordsBean> giftList;

    public WishingCastleSelectContentAdapter(Context context,List<GiftListBean.RecordsBean> list) {
        this(context, true);
        this.giftList = list;
    }

    private boolean isAnimator;

    public WishingCastleSelectContentAdapter(Context context, boolean isAnimator) {
        this.context = context;
        this.list = new ArrayList<>();
        this.list.add("数据1");
        this.list.add("数据二");
        this.desireLayoutList = new ArrayList<>();
        this.isAnimator = isAnimator;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        DesireLayout desireLayout = null;
        if (position < desireLayoutList.size()) {
            desireLayout = desireLayoutList.get(position);
        }
        if (desireLayout == null) {
            desireLayout = new DesireLayout(container.getContext(), container.getMeasuredWidth(), container.getMeasuredHeight());
            List<String> dataList = new ArrayList<>();
//            dataList.add("礼物");
//            dataList.add("礼物礼物");
//            if (position == 0) {
//                dataList.add("礼物礼物礼物");
//            }
            if (giftList != null&& giftList.size()>0) {
                for (int i=0,size = giftList.size();i < size;i++) {
                    dataList.add(giftList.get(i).getAwardName());
                }
            }

            container.addView(desireLayout, ViewPager.LayoutParams.MATCH_PARENT, ViewPager.LayoutParams.MATCH_PARENT);
            desireLayout.addChildViewData(dataList, false);
            desireLayout.showHook(false);
            desireLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnWishingItemClickListener != null) {
                        mOnWishingItemClickListener.onItemClick(position);
                    }
                }
            });

            desireLayoutList.add(desireLayout);
            Log.i(tag, " ViewPager 宽度:" + container.getMeasuredWidth() + " " + container.getMeasuredHeight() + " 手动测量的高度 " + container.getMeasuredHeightAndState() + " " + container.getPaddingTop() + " " + container.getPaddingBottom());
        }
        return desireLayoutList.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(desireLayoutList.get(position));
    }

    public void startLayoutAnimator(AnimatorListenerAdapter animatorListenerAdapter) {
        if (desireLayoutList != null && desireLayoutList.size() > 0) {
            desireLayoutList.get(0).startAnimator(animatorListenerAdapter);
        }
    }

    public void setClickListener(View.OnClickListener onClickListener) {
        for (DesireLayout desireLayout : desireLayoutList) {
            desireLayout.setClickListener(onClickListener);
        }
    }

    public void setOnWishingItemClickListener(OnWishingItemClickListener listener) {
        this.mOnWishingItemClickListener = listener;
    }

    public interface OnWishingItemClickListener {
        void onItemClick(int position);
    }

}
