package com.zhongke.content.view;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.zhongke.content.R;
import com.zhongke.content.glide.GlideLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${tanlei} on 2017/9/29.
 * 我的家模块中显示家庭成员头像的控件
 */

public class MyHomeTitleView extends LinearLayout {
    private final String TAG=MyHomeTitleView.class.getSimpleName();
    /**
     * 容器的最大宽度
     */
    private float maxWidth;
    /**
     * 照片的宽度
     */
    private float photoWidth;
    /**
     * 数据集合
     */
    private List<String> str;
    private Context context;
    private List<ImageView> ivList = new ArrayList<>();
    private List<Animator> setList = new ArrayList<>();

    public MyHomeTitleView(Context context) {
        this(context, null);
    }

    public MyHomeTitleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }
    public MyHomeTitleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        this.setOrientation(HORIZONTAL);

    }

    public void setStr(List<String> str) {
        //清空上次数据
        this.ivList.clear();
        this.setList.clear();
        this.removeAllViews();

        //开始
        this.str = str;
        maxWidth = this.getWidth();
        int height = this.getHeight();
        //计算合适的宽高
        photoWidth = maxWidth / 10.5f;
        photoWidth = Math.min(photoWidth, height);

        for (int i = 0; i < str.size(); i++) {
            ImageView iv = new ImageView(context);
            LinearLayout.LayoutParams photoParams = new LinearLayout.LayoutParams((int) photoWidth, (int) photoWidth);
            if (i == 0) {
                photoParams.setMargins((int) maxWidth, 0, 0, 0);
            } else {
                photoParams.setMargins(30, 0, 0, 0);
            }
            iv.setLayoutParams(photoParams);
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            GlideLoader.loadCircleNetWorkBitmap(context, str.get(i), iv, R.mipmap.ic_launcher, R.mipmap.ic_launcher);
            this.addView(iv);
            ivList.add(iv);
        }
        for (int j = 0; j < ivList.size(); j++) {
            AnimatorSet as = setAnimator(j, ivList.get(j));
            setList.add(as);
        }
        AnimatorSet set = new AnimatorSet();
        set.playTogether(setList);
        set.start();
    }

/*
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        Log.i(TAG," onLayout "+changed+" "+l+" "+t+" child view "+this.getChildCount());
    }*/

    private AnimatorSet setAnimator(int j, View v) {
        AnimatorSet set = new AnimatorSet();
        ObjectAnimator animator = ObjectAnimator.ofFloat(v, "rotation", 0f, -1080f);
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(v, "translationX", 0, -maxWidth);
        set.playTogether(animator, animator1);
        set.setStartDelay(j * 300);
        set.setDuration(3000);
        return set;
    }
}
