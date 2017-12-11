package com.zhongke.content.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.zhongke.content.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${tanlei} on 2017/9/11.
 * 恭喜获得奖励的弹出框
 */

public class GetPictureDialog extends Dialog {
    private Context context;
    /**
     * 缩放三次从0-1.2-1的模式常量
     */
    private static final int SCALE_3_FOR_1 = 1;
    /**
     * 缩放三次从0-0.8-1的模式常量
     */
    private static final int SCALE_3_FOR_8 = 2;
    /**
     * 缩放两次从0-1的模式常量
     */
    private static final int SCALE_2_FOR_1 = 3;
    private ImageView iv1, iv2, iv3, iv4, iv5, iv6, iv7, ivLeft, ivRight;
    /**
     * 星星控件
     */
    private ImageView ivStar1, ivStar2, ivStar3, ivStar4, ivStar5, ivStar6;
    private ArrayList<Animator> list = new ArrayList<>();

    public GetPictureDialog(@NonNull Context context) {
        this(context, 0);
    }

    public GetPictureDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, R.style.DialogTheme_no_systembar);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = LayoutInflater.from(context).inflate(R.layout.reward_dialog_layout, null);
        setContentView(view);
        initView(view);
        hideAllView();
        setAnimation();
    }

    /**
     * 初始化View
     *
     * @param view
     */
    private void initView(View view) {
        iv1 = view.findViewById(R.id.iv_bg1);
        iv2 = view.findViewById(R.id.iv_bg2);
        iv3 = view.findViewById(R.id.iv_bg3);
        iv4 = view.findViewById(R.id.iv_bg4);
        iv5 = view.findViewById(R.id.iv_bg5);
        iv6 = view.findViewById(R.id.iv_bg6);
        iv7 = view.findViewById(R.id.iv_bg9);
        ivLeft = view.findViewById(R.id.iv_bg_left);
        ivRight = view.findViewById(R.id.iv_bg_right);
        ivStar1 = view.findViewById(R.id.iv_star1);
        ivStar2 = view.findViewById(R.id.iv_star2);
        ivStar3 = view.findViewById(R.id.iv_star3);
        ivStar4 = view.findViewById(R.id.iv_star4);
        ivStar5 = view.findViewById(R.id.iv_star5);
        ivStar6 = view.findViewById(R.id.iv_star6);
    }

    /**
     * 缩放动画的方法
     *
     * @param view 需要缩放的图片
     * @param i    缩放动画的模式
     * @return
     */
    private AnimatorSet setScaleXY(View view, int i) {
        AnimatorSet scaleAnimator = new AnimatorSet();
        ObjectAnimator animatorX = null;
        ObjectAnimator animatorY = null;
        if (i == SCALE_3_FOR_1) {
            scaleAnimator = new AnimatorSet();
            animatorX = ObjectAnimator.ofFloat(view, "scaleY", 0f, 1.2f, 1f);
            animatorY = ObjectAnimator.ofFloat(view, "scaleX", 0f, 1.2f, 1f);
            animatorX.setDuration(500);
            animatorY.setDuration(500);
        } else if (i == SCALE_3_FOR_8) {
            animatorX = ObjectAnimator.ofFloat(view, "scaleY", 1.0f, 0.8f, 1f);
            animatorY = ObjectAnimator.ofFloat(view, "scaleX", 1.0f, 0.8f, 1f);
            animatorX.setRepeatCount(ValueAnimator.INFINITE);
            animatorY.setRepeatCount(ValueAnimator.INFINITE);
        } else if (i == SCALE_2_FOR_1) {
            animatorX = ObjectAnimator.ofFloat(view, "scaleY", 0f, 1f);
            animatorY = ObjectAnimator.ofFloat(view, "scaleX", 0f, 1f);
        }
        scaleAnimator.playTogether(animatorX, animatorY);
        return scaleAnimator;
    }

    /**
     * 设置动画
     */
    private void setAnimation() {
        //最底层的圆形的动画
        AnimatorSet setBg1 = setScaleXY(iv1, SCALE_3_FOR_1);
        //白色光圈的动画集
        AnimatorSet set1 = setScaleXY(iv3, SCALE_3_FOR_1);
        set1.setStartDelay(100);
        //加速运动的加值器
        set1.setInterpolator(new AccelerateDecelerateInterpolator());
        //恭喜你获得奖励的图片的缩放
        AnimatorSet set2 = setScaleXY(iv7, SCALE_3_FOR_1);
        set2.setStartDelay(250);
        //钻石的缩放动画
        AnimatorSet set6 = setScaleXY(iv6, SCALE_3_FOR_1);
        set6.setStartDelay(200);
        set6.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                startStarAnimation();
                AnimatorSet a = new AnimatorSet();
                a.playTogether(list);
                a.start();
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                //内白色光圈的缩放动画
                AnimatorSet set9 = setScaleXY(iv3, SCALE_3_FOR_8);
                set9.setStartDelay(100);
                set9.setDuration(300);
                set9.start();
            }
        });
        AnimatorSet set4 = new AnimatorSet();
        ObjectAnimator animatorXXXXX = ObjectAnimator.ofFloat(iv2, "scaleY", 0f, 1.5f);
        ObjectAnimator animatorYYYYY = ObjectAnimator.ofFloat(iv2, "scaleX", 0f, 1.5f);
        animatorXXXXX.setDuration(500);
        animatorYYYYY.setDuration(500);
        ObjectAnimator animator = ObjectAnimator.ofFloat(iv2, "rotation", 0f, 359f);
        //设置动画次数：无限重复
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setDuration(2000);
        animator.setInterpolator(new LinearInterpolator());
        set4.play(animatorXXXXX).with(animatorYYYYY).with(animator);
        set4.setStartDelay(450);


        AnimatorSet set5 = setScaleXY(iv4, SCALE_2_FOR_1);
        set5.setStartDelay(1000);
        set5.setDuration(500);

        AnimatorSet s = new AnimatorSet();
        AnimatorSet set3 = setScaleXY(iv5, SCALE_2_FOR_1);
        AnimatorSet set13 = setScaleXY(ivLeft, SCALE_2_FOR_1);
        AnimatorSet set14 = setScaleXY(ivRight, SCALE_2_FOR_1);
        s.playTogether(set3, set13, set14);
        s.setStartDelay(300);
        s.setDuration(500);

        AnimatorSet set = new AnimatorSet();
        set.playTogether(setBg1, set1, set2, set4, set5, s, set6);
        set.start();

    }

    /**
     * 设置星星的动画
     */
    private void setStarAnimation(List<Animator> list, View view) {
        //用来装星星平移和缩放和旋转的动画集合
        AnimatorSet starRotationScale = new AnimatorSet();
        //传进来的星星控件的缩放动画
        ObjectAnimator animatorX = ObjectAnimator.ofFloat(view, "scaleX", 0f, 1f);
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(view, "scaleY", 0f, 1f);
        //传递进来的星星控件的旋转动画
        ObjectAnimator rotate = ObjectAnimator.ofFloat(view, "rotation", 0f, 359f);
        //旋转动画是匀速的，此处如果不设置加值器就会出现视觉卡顿（旋转一圈后出现卡顿）
        rotate.setInterpolator(new LinearInterpolator());
        //设置星星旋转的动画是循环的
        rotate.setRepeatCount(ValueAnimator.INFINITE);
        //缩放动画0.2秒执行完成
        animatorX.setDuration(200);
        animatorY.setDuration(200);

        ObjectAnimator moveY = null;
        ObjectAnimator moveX = null;
        /**
         * 因为不同的星星需要移动至不同的位置 所以平移动画的起始坐标是不一致的
         */
        if (view.getId() == R.id.iv_star1) {
            //第一颗星星移动的位置
            moveX = ObjectAnimator.ofFloat(view, "translationX", 0f, 0f);
            moveY = ObjectAnimator.ofFloat(view, "translationY", 100f, 0f);
        } else if (view.getId() == R.id.iv_star2) {
            moveX = ObjectAnimator.ofFloat(view, "translationX", 20f, 0f);
            moveY = ObjectAnimator.ofFloat(view, "translationY", 90f, 0f);
        } else if (view.getId() == R.id.iv_star3) {
            moveX = ObjectAnimator.ofFloat(view, "translationX", 40f, 0f);
            moveY = ObjectAnimator.ofFloat(view, "translationY", 50f, 0f);
        } else if (view.getId() == R.id.iv_star4) {
            moveX = ObjectAnimator.ofFloat(view, "translationX", 80f, 0f);
            moveY = ObjectAnimator.ofFloat(view, "translationY", 20f, 0f);
        } else if (view.getId() == R.id.iv_star5) {
            moveX = ObjectAnimator.ofFloat(view, "translationX", -80f, 0f);
            moveY = ObjectAnimator.ofFloat(view, "translationY", 30f, 0f);
        } else if (view.getId() == R.id.iv_star6) {
            moveX = ObjectAnimator.ofFloat(view, "translationX", -40f, 0f);
            moveY = ObjectAnimator.ofFloat(view, "translationY", 70f, 0f);
        }
        moveY.setDuration(500);
        moveX.setDuration(500);
        starRotationScale.play(animatorX).with(animatorY).with(moveY).with(moveX).before(rotate);
        list.add(starRotationScale);
    }

    /**
     * 开启星星的动画
     */
    private void startStarAnimation() {
        setStarAnimation(list, ivStar1);
        setStarAnimation(list, ivStar2);
        setStarAnimation(list, ivStar3);
        setStarAnimation(list, ivStar4);
        setStarAnimation(list, ivStar5);
        setStarAnimation(list, ivStar6);
    }

    /**
     * 把所有控件缩放至看不见
     */
    private void hideAllView() {
        hideView(iv1);
        hideView(iv2);
        hideView(iv3);
        hideView(iv4);
        hideView(iv5);
        hideView(iv6);
        hideView(iv7);
        hideView(ivLeft);
        hideView(ivRight);
        hideView(ivStar1);
        hideView(ivStar2);
        hideView(ivStar3);
        hideView(ivStar4);
        hideView(ivStar5);
        hideView(ivStar6);
    }

    /**
     * 缩放单个控件
     *
     * @param v 需要缩放的控件
     */
    private void hideView(View v) {
        v.setScaleX(0f);
        v.setScaleY(0f);
    }
}
