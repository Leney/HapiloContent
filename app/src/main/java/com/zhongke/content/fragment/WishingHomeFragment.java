package com.zhongke.content.fragment;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.Guideline;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhongke.content.BaseFragment;
import com.zhongke.content.R;
import com.zhongke.content.utils.DisplayUtils;
import com.zhongke.content.utils.ViewUtils;

/**
 * Created by ${xingen} on 2017/9/25.
 * <p>
 * 许愿主界面
 */

public class WishingHomeFragment extends BaseFragment {
    public static final String TAG = WishingHomeFragment.class.getSimpleName();

    public static WishingHomeFragment newInstance() {
        WishingHomeFragment fragment = new WishingHomeFragment();
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_wishinghome;
    }

    @Override
    protected void initAfterActivityCreated(View rootView, Bundle savedInstanceState) {
        LinearLayout linearLayout = rootView.findViewById(R.id.wishing_home_layout);
        linearLayout.addView(createChild(R.mipmap.wishing_back_left, R.mipmap.wishing_home_castle, R.mipmap.wishing_home_entry, View.VISIBLE));
        linearLayout.addView(createChild(R.mipmap.wishing_back_right, R.mipmap.wishing_home_tree_hole, R.mipmap.wishing_home_wishing, View.GONE));
    }

    private View createChild(int imageId, int imageId2, int imageId3, int visible) {
        View child = View.inflate(getActivity(), R.layout.item_desire_view, null);
        TextView tv = child.findViewById(R.id.tv_icon);
        tv.setVisibility(View.GONE);
        ImageView iv = child.findViewById(R.id.iv);
        iv.setVisibility(View.GONE);
        ImageView float_iv = child.findViewById(R.id.desire_bg2);
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) float_iv.getLayoutParams();
        float_iv.setVisibility(visible);
        //添加引导线
        Guideline guideline_top = new Guideline(getActivity());
        int top_id = ViewUtils.getViewId();
        guideline_top.setId(top_id);
        ConstraintLayout.LayoutParams layoutParams1 = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
        layoutParams1.guidePercent = 0.1094f;
        layoutParams1.orientation = ConstraintLayout.LayoutParams.HORIZONTAL;
        guideline_top.setLayoutParams(layoutParams1);
        //添加引导线
        Guideline guideline_bottom = new Guideline(getActivity());
        int bottom_id = ViewUtils.getViewId();
        guideline_bottom.setId(bottom_id);
        ConstraintLayout.LayoutParams layoutParams2 = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
        layoutParams2.guidePercent = 0.8045f;
        layoutParams2.orientation = ConstraintLayout.LayoutParams.HORIZONTAL;
        guideline_bottom.setLayoutParams(layoutParams2);
        ConstraintLayout constraintLayout = child.findViewById(R.id.item_desire_constraint_layout);
        constraintLayout.addView(guideline_top);
        constraintLayout.addView(guideline_bottom);
        //修改图片资源
        ImageView imageView = child.findViewById(R.id.item_desire_icon_iv);
        ConstraintLayout.LayoutParams iv_params = (ConstraintLayout.LayoutParams) imageView.getLayoutParams();
        iv_params.topToTop = top_id;
        iv_params.bottomToBottom = bottom_id;
        layoutParams.topToTop = top_id;
        layoutParams.bottomToBottom = bottom_id;
        imageView.setBackgroundResource(imageId);

        //提示语：
        ImageView tip_iv = new ImageView(getActivity());
        tip_iv.setImageResource(imageId2);
        ConstraintLayout.LayoutParams layoutParams3 = new ConstraintLayout.LayoutParams(0, ConstraintLayout.LayoutParams.WRAP_CONTENT);
        layoutParams3.rightToRight = layoutParams3.leftToLeft = R.id.desire_bg2;

        layoutParams3.bottomToBottom = bottom_id;
        layoutParams3.bottomMargin = DisplayUtils.dip2px(getContext().getApplicationContext(), 11.3f);
        tip_iv.setLayoutParams(layoutParams3);
        constraintLayout.addView(tip_iv);


        //按钮的引导线
        Guideline guideline_bottom2 = new Guideline(getActivity());
        int bottom_id2 = ViewUtils.getViewId();
        guideline_bottom2.setId(bottom_id2);
        ConstraintLayout.LayoutParams layoutParams4 = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
        layoutParams4.guidePercent = 0.9415f;
        layoutParams4.orientation = ConstraintLayout.LayoutParams.HORIZONTAL;
        guideline_bottom2.setLayoutParams(layoutParams4);
        constraintLayout.addView(guideline_bottom2);
        //按钮：
        ImageView btn_iv = new ImageView(getActivity());
        btn_iv.setImageResource(imageId3);
        ConstraintLayout.LayoutParams layoutParams5 = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
        layoutParams5.rightToRight = layoutParams5.leftToLeft = ConstraintLayout.LayoutParams.PARENT_ID;
        layoutParams5.bottomToBottom = bottom_id2;
        btn_iv.setLayoutParams(layoutParams5);
        constraintLayout.addView(btn_iv);
        LinearLayout.LayoutParams layoutParams_total = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
        child.setLayoutParams(layoutParams_total);
        btn_iv.setOnClickListener(view ->
//                ((DesireActivity) getActivity()).addOrShowOrHideFragment(visible == View.VISIBLE ? WishingCastleSelectFragment.TAG : WishingCaveFragment.TAG));
                ((WishingTabFragment) getParentFragment()).addOrShowOrHideFragment(visible == View.VISIBLE ? WishingCastleSelectFragment.TAG : WishingCaveFragment.TAG));
        return child;
    }
}
