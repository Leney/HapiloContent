package com.zhongke.content.view;

import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.zhongke.content.R;
import com.zhongke.content.glide.GlideLoader;
import com.zhongke.content.utils.DisplayUtils;
import com.zhongke.content.utils.ViewUtils;


/**
 * Created by ${xingen} on 2017/9/15.
 * <p>
 * 一个台灯效果的布局
 */

public class DropDownChild extends RelativeLayout implements View.OnClickListener {
    private final String TAG = DropDownChild.class.getSimpleName();
    private ImageView line_iv, heart_iv;
    private Context context;
    /**
     * 当前的模式:
     * 下落出现，台灯效果，响应缩放
     */
    private int currentMode;
    public static final int MODE_DOWN_SELF = 10;
    public static final int MODE_LAMP = 11;
    public static final int MODE_RESPONSE = 12;
    /**
     * 当前种类:愿望，许愿,返回
     */
    private int currentKind;
    public static final int KIND_DESIRE = 1;
    public static final int KIND_WISHING = 2;
    public static final int KIND_BACK = 3;
    /**
     * 美工设计图上的尺寸
     */
    private int width_heart_large, width_heart_small, height_heart_large, height_heart_small, width_heart_middle, height_heart_middle;
    private int width_line, height_line_large, height_line_small;

    public DropDownChild(Context context, int kind) {
        super(context);
        this.currentKind = kind;
        initConfig(context);
        addChildView();
    }

    //默认布局
    public DropDownChild(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.currentKind = KIND_BACK;
        initConfig(context);
        addChildView();
    }

    private void initConfig(Context context) {
        this.context = context;
        this.height_heart_small = DisplayUtils.dip2px(context, 63.3f);
        this.width_heart_small = DisplayUtils.dip2px(context, 81.3f);
        this.height_heart_large = DisplayUtils.dip2px(context, 100);
        this.width_heart_large = DisplayUtils.dip2px(context, 128);
        this.width_line = DisplayUtils.dip2px(context, 2.7f);
        this.height_line_large = DisplayUtils.dip2px(context, 249.3f);
        this.height_line_small = DisplayUtils.dip2px(context, 70);
        this.height_heart_middle = DisplayUtils.dip2px(context, 42.7f);
        this.width_heart_middle = DisplayUtils.dip2px(context, 54.7f);
    }

    private void addChildView() {
        int line_id = ViewUtils.getViewId();
        switch (currentKind) {
            case KIND_DESIRE://我的愿望
                this.line_iv = createLineView(line_id, width_line, height_line_small, R.mipmap.launcher_desire_down_line);
                this.addView(line_iv);
                this.heart_iv = createHeartView(line_id, width_heart_large, height_heart_large, R.mipmap.launcher_desire_iv);
                this.addView(this.heart_iv);
                break;
            case KIND_WISHING://许愿
                this.line_iv = createLineView(line_id, width_line, height_line_large, R.mipmap.launcher_desire_down_line);
                this.addView(line_iv);
                this.heart_iv = createHeartView(line_id, width_heart_small, height_heart_small, R.mipmap.launcher_desire_wishing_iv);
                this.addView(this.heart_iv);
                break;
            case KIND_BACK://返回
                this.line_iv = createLineView(line_id, width_line, height_line_small, R.mipmap.launcher_desire_down_line);
                this.addView(line_iv);
                this.heart_iv = createHeartView(line_id, width_heart_middle + 50, height_heart_middle + 50, R.mipmap.launcher_desire_back_iv);
                this.addView(this.heart_iv);
                break;
        }
    }

    private ImageView createLineView(int id, int width, int height, int imageId) {
        ImageView imageView = new ImageView(context);
        LayoutParams layoutParams = new LayoutParams(width, height);
        layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
        imageView.setLayoutParams(layoutParams);
        imageView.setAdjustViewBounds(true);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setId(id);
        GlideLoader.loadLocalResource(context, imageId, imageView);
        return imageView;
    }

    private int paddingTop;

    private ImageView createHeartView(int id, int width, int height, int imageId) {
        ImageView imageView = new ImageView(context);
        LayoutParams layoutParams = new LayoutParams(width, height);
        layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
        layoutParams.addRule(RelativeLayout.BELOW, id);
        imageView.setAdjustViewBounds(true);
        //向上移动-20dp
        paddingTop = DisplayUtils.dip2px(context, 20);
        layoutParams.topMargin = -paddingTop;
        imageView.setLayoutParams(layoutParams);
        GlideLoader.loadLocalResource(context, imageId, imageView);
        imageView.setOnClickListener(this);
        return imageView;
    }

    /**
     * 根据模式开启动画
     *
     * @param mode
     */
    public void startAnimator(int mode, AnimatorListenerAdapter animatorListenerAdapter) {
        this.currentMode = mode;
        switch (currentMode) {
            case MODE_DOWN_SELF:
                startDownAnimator(animatorListenerAdapter);
                break;
            case MODE_LAMP:
                startLampAnimator(animatorListenerAdapter);
                break;
            case MODE_RESPONSE:
                startResponseAnimator(animatorListenerAdapter);
                break;
        }
    }

    private void startDownAnimator(AnimatorListenerAdapter animatorListenerAdapter) {
        int translationY = -this.getHeight();
        ObjectAnimator translationXAnimator = ObjectAnimator.ofFloat(this, "translationY", translationY, 0);
        translationXAnimator.setDuration(500);
        translationXAnimator.setInterpolator(new AccelerateInterpolator());
        if (animatorListenerAdapter != null) {
            translationXAnimator.addListener(animatorListenerAdapter);
        }
        translationXAnimator.start();
    }

    private void startLampAnimator(AnimatorListenerAdapter animatorListenerAdapter) {
        float scaleY;
        float scaleX;
        float scaleY_first_value;
        if (currentKind == KIND_WISHING) {
            scaleY = (float) height_heart_large / height_heart_small;
            scaleX = (float) width_heart_large / width_heart_small;
            scaleY_first_value = 1.2f;
        } else if (currentKind == KIND_DESIRE) {
            scaleX = 1;
            scaleY = 1;
            scaleY_first_value = 1.3f;
        } else {
            scaleY = (float) height_heart_small / height_heart_middle;
            scaleX = (float) width_heart_small / width_heart_middle;
            scaleY_first_value = 1.2f;
        }
        ObjectAnimator scaleY_first = ObjectAnimator.ofFloat(line_iv, "scaleY", line_iv.getScaleY(), scaleY_first_value);
        int originHeight = line_iv.getHeight();
        scaleY_first.addUpdateListener(valueAnimator -> {
            float update = (float) valueAnimator.getAnimatedValue();
            float translation = (update - 1) * originHeight - paddingTop;
            //Log.i(TAG, " 更新的值 " + update + " " + translation);
            heart_iv.setTranslationY(translation);
        });
        ObjectAnimator scaleY_second = ObjectAnimator.ofFloat(line_iv, "scaleY", scaleY_first_value, 1f);
        scaleY_second.addUpdateListener(valueAnimator -> {
            float update = (float) valueAnimator.getAnimatedValue();
            float translation = (update - 1) * originHeight - paddingTop;
            heart_iv.setTranslationY(translation);
        });
        ObjectAnimator scaleY_four = ObjectAnimator.ofFloat(heart_iv, "scaleY", heart_iv.getScaleY(), scaleY);
        ObjectAnimator scaleY_third = ObjectAnimator.ofFloat(heart_iv, "scaleX", heart_iv.getScaleX(), scaleX);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(scaleY_second).with(scaleY_third).with(scaleY_four).after(scaleY_first);
        animatorSet.setDuration(300);
        animatorSet.setInterpolator(new AccelerateInterpolator());
        if (animatorListenerAdapter != null) {
            animatorSet.addListener(animatorListenerAdapter);
        }
        animatorSet.start();
    }

    private void startResponseAnimator(AnimatorListenerAdapter animatorListenerAdapter) {
        float scaleY;
        float scaleX;
        if (currentKind == KIND_DESIRE) {
            scaleY = (float) height_heart_small / height_heart_large;
            scaleX = (float) width_heart_small / width_heart_large;
        } else {
            scaleX = 1;
            scaleY = 1;
        }
        ObjectAnimator scaleY_four = ObjectAnimator.ofFloat(heart_iv, "scaleY", heart_iv.getScaleY(), scaleY);
        ObjectAnimator scaleY_third = ObjectAnimator.ofFloat(heart_iv, "scaleX", heart_iv.getScaleX(), scaleX);
        scaleY_third.addUpdateListener(valueAnimator -> {
            if (currentKind == KIND_DESIRE) {
                float update = (float) valueAnimator.getAnimatedValue();
                float translation = (update - 1) * line_iv.getHeight();
                heart_iv.setTranslationY(translation);
            }
        });
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setInterpolator(new AccelerateInterpolator());
        animatorSet.playTogether(scaleY_four, scaleY_third);
        animatorSet.setInterpolator(new LinearInterpolator());
        animatorSet.setDuration(300);
        animatorSet.start();
    }

    @Override
    public void onClick(View view) {
        if (this.dropDownChildClickListener != null) {
            this.dropDownChildClickListener.clickView(currentKind);
        }
    }

    public void setDropDownChildClickListener(DropDownChildClickListener dropDownChildClickListener) {
        this.dropDownChildClickListener = dropDownChildClickListener;
    }

    private DropDownChildClickListener dropDownChildClickListener;

    public interface DropDownChildClickListener {
        void clickView(int currentKind);
    }

}
