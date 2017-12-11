package com.zhongke.content.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhongke.content.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by ${xinGen} on 2017/9/18.
 * <p>
 * 愿望Item的布局。
 */

public class DesireLayout extends LinearLayout {
    private final String tag = DesireLayout.class.getSimpleName();
    private SparseArray<View> childView;
    private Context context;
    private boolean isAnimator;
    private int width;
    private int height;


    public DesireLayout(Context context, int width, int height) {
        this(context, width, height, false);
    }

    public DesireLayout(Context context, int width, int height, boolean isAnimator) {
        super(context);
        this.context = context;
        this.width = width;
        this.setOrientation(LinearLayout.HORIZONTAL);
        this.isAnimator = isAnimator;
        this.childView = new SparseArray<>();
        this.height = height;
    }

    public void addChildViewData(List<String> data, boolean isIcon) {
        this.childView.clear();
        this.removeAllViews();
        int averageWidth = width / 3;
        for (int i = 0; i < data.size(); ++i) {
            View child = addChild(data.get(i), isIcon);
            this.addView(child, averageWidth, LayoutParams.MATCH_PARENT);
            childView.put(i, child);
        }
        if (isAnimator && isFirst) {
            isFirst = false;
            for (int i = 0; i < childView.size(); ++i) {
                View child = childView.get(i);
                Log.i(tag, "计算出来的高度" + height);
                child.setTranslationY(-height);
            }
        }
    }

    public void showHook(boolean show) {
        for (int i = 0; i < childView.size(); ++i) {
            View view = childView.get(i);
            view.findViewById(R.id.desire_h00k_right).setVisibility(show ? VISIBLE : GONE);
            view.findViewById(R.id.desire_h00k_left).setVisibility(show ? VISIBLE : GONE);
        }
    }

    private View addChild(String name, boolean isIcon) {
        View child = View.inflate(context, R.layout.item_desire_view, null);
        TextView textView = new TextView(context);
        textView.setTextColor(Color.parseColor("#9c1210"));
        textView.setTextSize(20);
        textView.setText(name);
        // TODO: 2017/11/3  暂停先产生随机数来添加标签判断
        ImageView iv = child.findViewById(R.id.iv);
        TextView tv = child.findViewById(R.id.tv_icon);
        if (isIcon) {
            Random random = new Random();
            int i = random.nextInt();
            if (i % 2 == 0) {
                iv.setImageResource(R.drawable.props_red);
                tv.setText("进行中");
            } else {
                iv.setImageResource(R.drawable.props_blue);
                tv.setText("未实现");
            }
        } else {
            iv.setVisibility(View.GONE);
            tv.setVisibility(View.GONE);
        }
        ConstraintLayout constraintLayout = child.findViewById(R.id.item_desire_constraint_layout);
        ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.bottomToBottom = R.id.item_desire_guide_line3;
        layoutParams.leftToLeft = R.id.item_desire_bg1;
        layoutParams.rightToRight = ConstraintLayout.LayoutParams.PARENT_ID;
        textView.setLayoutParams(layoutParams);
        constraintLayout.addView(textView, layoutParams);
        return child;
    }

    private boolean isFirst = true;

    /**
     * 开启动画
     *
     * @param animatorListenerAdapter
     */
    public void startAnimator(AnimatorListenerAdapter animatorListenerAdapter) {
        AnimatorSet animatorSet = new AnimatorSet();
        List<Animator> animatorList = new ArrayList<>();
        for (int i = 0; i < childView.size(); ++i) {
            int translationY = -childView.get(i).getMeasuredHeight();
            Log.i(tag, " 动画 " + translationY + " " + childView.get(i).getMeasuredHeight());
            Animator animator = createDownAnimator(childView.get(i), translationY);
            if (i == childView.size() - 1) {
                animator.addListener(animatorListenerAdapter);
            }
            animatorList.add(animator);
        }
        animatorSet.playSequentially(animatorList);
        animatorSet.start();
        Log.i(tag, " DesireLayout startAnimator ");
    }

    private Animator createDownAnimator(View view, float translationY) {
        ObjectAnimator translationXAnimator = ObjectAnimator.ofFloat(view, "translationY", translationY, 0);
        translationXAnimator.setDuration(200);
        translationXAnimator.setInterpolator(new AccelerateInterpolator());
        return translationXAnimator;
    }

    public void setClickListener(OnClickListener onClickListener) {
        for (int i = 0; i < childView.size(); ++i) {
            View view = childView.get(i);
            view.setOnClickListener(onClickListener);
        }
    }

}
