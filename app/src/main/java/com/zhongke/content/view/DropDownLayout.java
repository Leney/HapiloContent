package com.zhongke.content.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.zhongke.content.utils.DisplayUtils;
import com.zhongke.content.utils.ViewUtils;

/**
 * Created by ${xingen} on 2017/9/15.
 * <p>
 * 下拉布局：类似台灯下拉开启效果。
 */

public class DropDownLayout extends RelativeLayout implements DropDownChild.DropDownChildClickListener {
    private final String tag = DropDownLayout.class.getSimpleName();
    private DropDownChild desire_child, wishing_child;
    private Context context;
    private boolean isFirst;
    private int currentState;

    public DropDownLayout(Context context) {
        super(context);
    }

    public DropDownLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        isFirst = true;
        addChildView();
    }

    private void addChildView() {
        int viewId = ViewUtils.getViewId();
        //许愿
        wishing_child = new DropDownChild(context, DropDownChild.KIND_WISHING);
        wishing_child.setDropDownChildClickListener(this);
        RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(DisplayUtils.dip2px(context, 130), LayoutParams.MATCH_PARENT);

        layoutParams2.leftMargin = DisplayUtils.dip2px(context, 40);
        this.addView(wishing_child, layoutParams2);
        //我的愿望
        desire_child = new DropDownChild(context, DropDownChild.KIND_DESIRE);
        desire_child.setDropDownChildClickListener(this);
        RelativeLayout.LayoutParams layoutParams1 = new RelativeLayout.LayoutParams(DisplayUtils.dip2px(context, 130), LayoutParams.MATCH_PARENT);
        layoutParams1.leftMargin = DisplayUtils.dip2px(context, 20);
        desire_child.setId(viewId);
        layoutParams1.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
        this.addView(desire_child, layoutParams1);
        //默认愿望状态
        this.currentState = DropDownChild.KIND_DESIRE;
    }

    public void startDownAnimator(DropDownChild back_desire_child, AnimatorListenerAdapter animatorListenerAdapter) {
        if (!isFirst) return;
        this.isFirst = false;
        this.wishing_child.startAnimator(DropDownChild.MODE_DOWN_SELF, new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                back_desire_child.setTranslationY(-back_desire_child.getHeight());
                desire_child.setTranslationY(-desire_child.getHeight());
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                desire_child.startAnimator(DropDownChild.MODE_DOWN_SELF, animatorListenerAdapter);
            }
        });
    }

    @Override
    public void clickView(int currentKind) {
        switch (currentKind) {
            case DropDownChild.KIND_DESIRE:
                if (currentState != DropDownChild.KIND_DESIRE) {//切换到我的愿望
                    clickDesireAnimator();
                }
                break;
            case DropDownChild.KIND_WISHING:
                if (currentState == DropDownChild.KIND_DESIRE) {//切换到许愿
                    clickWishingAnimator();
                }
                break;
        }
        currentState = currentKind;
    }

    //点击愿望，开启动画
    private void clickDesireAnimator() {
        this.desire_child.startAnimator(DropDownChild.MODE_LAMP, new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                wishing_child.startAnimator(DropDownChild.MODE_RESPONSE, null);
                if (getDropDownChildClickListener() != null) {
                    getDropDownChildClickListener().clickView(DropDownChild.KIND_DESIRE);
                }
            }
        });
    }

    private void clickWishingAnimator() {
        this.wishing_child.startAnimator(DropDownChild.MODE_LAMP, new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                desire_child.startAnimator(DropDownChild.MODE_RESPONSE, null);
                if (getDropDownChildClickListener() != null) {
                    getDropDownChildClickListener().clickView(DropDownChild.KIND_WISHING);
                }
            }
        });
    }

    private DropDownChild.DropDownChildClickListener dropDownChildClickListener;

    public DropDownChild.DropDownChildClickListener getDropDownChildClickListener() {
        return dropDownChildClickListener;
    }

    public void setDropDownChildClickListener(DropDownChild.DropDownChildClickListener dropDownChildClickListener) {
        this.dropDownChildClickListener = dropDownChildClickListener;
    }
}
