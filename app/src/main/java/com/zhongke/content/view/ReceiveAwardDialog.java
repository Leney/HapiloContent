package com.zhongke.content.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhongke.content.R;
import com.zhongke.content.utils.DisplayUtils;
import com.zhongke.content.utils.StringUtils;
import com.zhongke.content.utils.SystemBarUtils;
import com.zhongke.content.utils.ViewUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${xingen} on 2017/8/29.
 * <p>
 * 领取奖励的弹窗
 */
public class ReceiveAwardDialog extends BaseDialog {
    private DisplayMetrics displayMetrics;

    public ReceiveAwardDialog(@NonNull Context context) {
        super(context, R.style.DialogTheme_no_systembar, null);
        initConfig();
    }

    /**
     * 初始配置
     */
    private void initConfig() {
        WindowManager.LayoutParams layoutParams = this.getWindow().getAttributes();
        layoutParams.width = this.displayMetrics.widthPixels;
        layoutParams.height = this.displayMetrics.heightPixels;
        this.setCanceledOnTouchOutside(true);
        this.setCancelable(true);
    }

    @Override
    protected View getRootView() {
        SystemBarUtils.setStickyStyle(getWindow());
        return View.inflate(context, R.layout.dialog_receiveraward, null);
    }

    private FrameLayout award_layout;
    private ImageView bg1_iv, bg2_iv, bg3_iv, bg4_iv;
    private ImageView ribbon_iv, crown_iv, gold_iv, double_star_iv, btn_iv;
    private RelativeLayout icon_layout;
    private int tip_color;


    @Override
    protected void initView(View rootView) {


        this.displayMetrics = StringUtils.getPhoneMetrics(context);
        this.tip_color = Color.parseColor("#f9d9af");
        this.imageViewList = new ArrayList<>();
        this.textViewList = new ArrayList<>();

        this.bg1_iv = rootView.findViewById(R.id.receiver_award_bg1_iv);
        this.bg2_iv = rootView.findViewById(R.id.receiver_award_bg2_iv);
        this.bg3_iv = rootView.findViewById(R.id.receiver_award_bg3_iv);
        this.bg4_iv = rootView.findViewById(R.id.receiver_award_bg4_iv);
        this.ribbon_iv = rootView.findViewById(R.id.receiver_award_ribbon);
        this.icon_layout = rootView.findViewById(R.id.receiver_award_icon_layout);
        this.award_layout = rootView.findViewById(R.id.receiver_award_layout);
        this.crown_iv = rootView.findViewById(R.id.receiver_award_crown_iv);
        this.gold_iv = rootView.findViewById(R.id.receiver_award_gold_iv);
        this.double_star_iv = rootView.findViewById(R.id.receiver_award_double_star_iv);
        this.btn_iv = rootView.findViewById(R.id.receiver_award_btn_iv);

        this.bg2_iv.getLayoutParams().height = displayMetrics.widthPixels;
        this.bg2_iv.getLayoutParams().width = displayMetrics.widthPixels;

        addAward();
        hideAllView();
        moveView();
        startAnimator();
    }


    private float translationY_icon, translationY_btn;
    private float original_y_icon, original_y_btn;

    /**
     * 控件在屏幕中的位置
     *
     * @return
     */
    private int getImageViewLocation() {
        int height_phone = displayMetrics.heightPixels;
        return height_phone / 2;
    }

    /**
     * 获取到Bitmap的高度
     *
     * @return
     */
    private int getImageViewBitmap(ImageView imageView) {
        BitmapDrawable bitmapDrawable = (BitmapDrawable) imageView.getDrawable();
        if (bitmapDrawable != null) {
            return bitmapDrawable.getBitmap().getHeight();
        } else {
            return 0;
        }
    }

    /**
     * 移动指定的view
     */
    private void moveView() {
        original_y_icon = 0;
        translationY_icon = -(getImageViewLocation() + getImageViewBitmap((ImageView) icon_layout.getChildAt(0)));
        moveView(icon_layout, translationY_icon);
        original_y_btn = 0;
        translationY_btn = getImageViewLocation() + getImageViewBitmap(btn_iv);
        moveView(btn_iv, translationY_btn);
    }

    private void moveView(View view, float translationY) {
        view.setTranslationY(translationY);
    }

    /**
     * 隐藏全部View
     */
    private void hideAllView() {
        scaleView(bg1_iv);
        scaleView(bg2_iv);
        scaleView(bg3_iv);
        scaleView(bg4_iv);
        scaleView(ribbon_iv);
        scaleView(crown_iv);
        award_layout.setScaleX(0.0f);
        scaleView(gold_iv);
        scaleView(double_star_iv);
        for (int i = 0; i < imageViewList.size(); ++i) {
            scaleView(imageViewList.get(i));
            scaleView(textViewList.get(i));
        }
    }

    /**
     * 开始动画
     */
    private void startAnimator() {

        AnimatorSet bg1_animatorSet = scaleChildViewAnimatorSet(bg1_iv, 600);
        Animator iconAnimtor = fallingAnimation(icon_layout, 400, translationY_icon, original_y_icon);
        iconAnimtor.setStartDelay(400);
        AnimatorSet bg3_animatorset = scaleChildViewAnimatorSet(bg3_iv, 400);
        bg3_animatorset.setStartDelay(500);
        AnimatorSet bg4_animatorset = scaleChildViewAnimatorSet(bg4_iv, 400);
        bg4_animatorset.setStartDelay(500);

        AnimatorSet ribbon_animatorset = scaleChildViewAnimatorSet(ribbon_iv, 600);
        ribbon_animatorset.setStartDelay(700);


        AnimatorSet crown_animatorset = scaleChildViewAnimatorSet(crown_iv, 400);
        crown_animatorset.setStartDelay(900);

        ObjectAnimator award_layout_animator = ObjectAnimator.ofFloat(award_layout, "scaleX", 0, 1f);
        award_layout_animator.setStartDelay(1000);
        award_layout_animator.setDuration(800);
        setAwardAnimatorListener(award_layout_animator);

        AnimatorSet gold_animatorset = scaleChildViewAnimatorSet(gold_iv, 300);
        gold_animatorset.setStartDelay(1200);

        //头像底部不动的星星动画
        AnimatorSet double_star_animatorset = scaleChildViewAnimatorSet(double_star_iv, 300);
        double_star_animatorset.setStartDelay(1300);

        //大光圈转动的动画
        AnimatorSet bg2_animatorSet = scaleChildViewAnimatorSet(bg2_iv, 500);
        bg2_animatorSet.setStartDelay(1400);
        setBg2AnimatorListener(bg2_animatorSet);
        //底部菜单上升动画
        Animator btnAnimator = fallingAnimation(btn_iv, 400, translationY_btn, original_y_btn);
        btnAnimator.setStartDelay(1500);

        //总动画
        AnimatorSet totalAnimatorSet = new AnimatorSet();
        totalAnimatorSet.playTogether(bg1_animatorSet
                , iconAnimtor
                , bg3_animatorset
                , bg4_animatorset
                , ribbon_animatorset
                , crown_animatorset
                , award_layout_animator
                , gold_animatorset
                , double_star_animatorset
                , bg2_animatorSet
                , btnAnimator
        );
        totalAnimatorSet.start();
    }

    private void setAwardAnimatorListener(Animator animator) {
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                scaleAlphaView();
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
    }

    /**
     * 缩放和透明度奖品的动画
     */
    private void scaleAlphaView() {
        AnimatorSet animatorSet1 = new AnimatorSet();
        List<Animator> animatorList = new ArrayList<>();
        for (int i = 0; i < imageViewList.size(); ++i) {
            ImageView imageView = imageViewList.get(i);
            ObjectAnimator scale_x = ObjectAnimator.ofFloat(imageView, "scaleX", 0, 1.3f, 1f);
            ObjectAnimator scale_y = ObjectAnimator.ofFloat(imageView, "scaleY", 0, 1.3f, 1f);
            ObjectAnimator alpha = ObjectAnimator.ofFloat(imageView, "alpha", 0f, 1f);
            AnimatorSet animatorSet2 = new AnimatorSet();
            animatorSet2.playTogether(scale_x, scale_y, alpha);
            animatorList.add(animatorSet2);
        }
        animatorSet1.playTogether(animatorList);
        animatorSet1.setDuration(600);
        animatorSet1.setInterpolator(new AccelerateInterpolator());
        animatorSet1.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                fallAnimator();
            }
        });
        animatorSet1.start();
    }

    /**
     * 文字下落
     */
    private void fallAnimator() {
        float move_y;
        List<Animator> animatorList = new ArrayList<>();
        for (TextView textView : textViewList) {
            String content = textView.getText().toString();
            Rect rect = new Rect();
            textView.getPaint().getTextBounds(content, 0, content.length(), rect);
            move_y = rect.bottom - rect.top + DisplayUtils.dip2px(context.getApplicationContext(), 5);
            textView.setTranslationY(-move_y);

            ObjectAnimator translationY = ObjectAnimator.ofFloat(textView, "TranslationY", -move_y, 0);
            animatorList.add(translationY);
        }
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animatorList);
        animatorSet.setDuration(500);
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                for (TextView textView : textViewList) {
                    textView.setScaleX(1.0f);
                    textView.setScaleY(1.0f);
                }
            }
        });
        animatorSet.setInterpolator(new LinearInterpolator());
        animatorSet.start();
    }

    private void setBg2AnimatorListener(AnimatorSet animatorSet) {
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                rotationBg2();
            }

            @Override
            public void onAnimationCancel(Animator animator) {
            }

            @Override
            public void onAnimationRepeat(Animator animator) {
            }
        });
    }

    /**
     * 不停旋转
     */
    private void rotationBg2() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(bg2_iv, "rotation", 0f, 359f);
        //设置动画次数：无限重复
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setDuration(2000);
        animator.setInterpolator(new LinearInterpolator());
        animator.start();
    }

    /**
     * 设置位置动画
     */
    private ObjectAnimator fallingAnimation(View view, long duration, float translationy, float endY) {
        ObjectAnimator translationY = ObjectAnimator.ofFloat(view, "TranslationY", translationy, endY);
        translationY.setDuration(duration);
        return translationY;
    }

    /**
     * 设置缩放动画
     *
     * @param view
     * @param duration
     * @return
     */
    private AnimatorSet scaleChildViewAnimatorSet(View view, long duration) {
        ObjectAnimator scale_x = ObjectAnimator.ofFloat(view, "scaleX", 0, 1f);
        ObjectAnimator scale_y = ObjectAnimator.ofFloat(view, "scaleY", 0, 1f);
        AnimatorSet animatorset = new AnimatorSet();
        animatorset.playTogether(scale_x, scale_y);
        animatorset.setDuration(duration);
        return animatorset;
    }

    /**
     * 压缩View,一开始不见
     *
     * @param view
     */
    private void scaleView(View view) {
        view.setScaleX(0.0f);
        view.setScaleY(0.0f);
    }

    private List<ImageView> imageViewList;
    private List<TextView> textViewList;

    /**
     * 动态添加奖品
     *
     * @param
     */

    private void addAward() {
        String[] strings = {"勋章×1", "积分×200", "金币×50"};
        LinearLayout total_layout = new LinearLayout(context);
       FrameLayout.LayoutParams total_layoutparams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        total_layoutparams.gravity=Gravity.CENTER_HORIZONTAL|Gravity.BOTTOM;
        total_layout.setLayoutParams(total_layoutparams);
        for (int i = 0; i < strings.length; ++i) {
            String s = strings[i];
            RelativeLayout relativeLayout = new RelativeLayout(context);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.gravity = Gravity.BOTTOM;
            layoutParams.bottomMargin = DisplayUtils.dip2px(context.getApplicationContext(), 8);
            layoutParams.rightMargin = layoutParams.leftMargin = DisplayUtils.dip2px(context.getApplicationContext(), 22);
            relativeLayout.setLayoutParams(layoutParams);
            // 奖品图片
            ImageView imageView = new ImageView(context);
            int imageId = ViewUtils.getViewId();
            imageView.setId(imageId);
            RelativeLayout.LayoutParams layoutParams1 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            layoutParams1.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
            imageView.setLayoutParams(layoutParams1);
            imageView.setBackgroundResource(R.mipmap.receiver_award_prize_bg);
            relativeLayout.addView(imageView);

            //奖品提示文字
            TextView textView = new TextView(context);
            RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            layoutParams2.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
            layoutParams2.addRule(RelativeLayout.BELOW, imageId);
            textView.setLayoutParams(layoutParams2);
            textView.getPaint().setFakeBoldText(true);
            textView.setText(s);
            textView.setTextColor(tip_color);
            textView.setTextSize(15.5f);
            relativeLayout.addView(textView);
            textViewList.add(textView);
            imageViewList.add(imageView);
            total_layout.addView(relativeLayout);
        }

        this.award_layout.addView(total_layout);
    }
}
