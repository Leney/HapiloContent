package com.zhongke.content.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.zhongke.content.R;

/**
 * 活动详情Tab控件
 * Created by llj on 2017/10/26.
 */

public class TabView extends FrameLayout {

    /**
     * 盖在上面的图片资源id
     */
    private int topDrawableResId;
    /**
     * 显示的名称
     */
    private String name;
    /**
     * 文本颜色
     */
    private int textColor;
    /**
     * 字体大小
     */
    private float textSize;

    private Context mContext;

    /**
     * 描边文本
     */
    private StrokeTextView tabText;

    /**
     * 顶部盖子视图
     */
    private TextView topView;

    /**
     * 顶部打开动画
     */
    private Animation topOpenAnimation;
    /**
     * 底部打开动画
     */
    private Animation bottomOpenAnimation;
    /**
     * 顶部关闭动画
     */
    private Animation topCloseAnimation;
    /**
     * 底部关闭动画
     */
    private Animation bottomCloseAnimation;


    public TabView(@NonNull Context context) {
        super(context);
        init(context);
    }

    public TabView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.TabView);
        topDrawableResId = a.getResourceId(R.styleable.TabView_topDrawable, R.mipmap.activity_detail_top_tab1);
        name = a.getString(R.styleable.TabView_text);
        textColor = a.getColor(R.styleable.TabView_textColor2, 0);
        textSize = a.getDimension(R.styleable.TabView_textSize2, 14f);
        a.recycle();
        init(context);
    }

    public TabView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }


    private void init(Context context) {
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        mContext = context;
        tabText = new StrokeTextView(context);
        tabText.setBackgroundResource(R.drawable.activity_detail_tab_bg);
        tabText.setText(name);
        tabText.setTextColor(textColor);
        tabText.setTextSize(textSize);
        tabText.setGravity(Gravity.CENTER_HORIZONTAL);
        tabText.setModeStroke(StrokeTextView.MODE_STROKE, Color.parseColor("#ffebb3"), 2);


        topView = new TextView(context);
        topView.setBackgroundResource(topDrawableResId);


        addView(tabText, params);
        addView(topView, params);

        topView.setVisibility(VISIBLE);
        tabText.setVisibility(GONE);
        // 默认不可点击
        topView.setEnabled(false);

//        topView.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                open();
////                close();
//            }
//        });

//        topOpenAnimation  = new TranslateAnimation(0,0,0,-1.0f);
//        topOpenAnimation.setDuration(500);
//
//        topCloseAnimation = new TranslateAnimation(0,0,-1.0f,0);
//        topCloseAnimation.setDuration(500);
//
//        bottomOpenAnimation = new TranslateAnimation(0,0,-1.0f,0);
//        bottomOpenAnimation.setDuration(500);
//
//        bottomCloseAnimation = new TranslateAnimation(0,0,0,-1.0f);
//        bottomCloseAnimation.setDuration(500);

        topOpenAnimation = AnimationUtils.loadAnimation(context, R.anim.anim_tab_top_open);
        topCloseAnimation = AnimationUtils.loadAnimation(context, R.anim.anim_tab_top_close);
        bottomOpenAnimation = AnimationUtils.loadAnimation(context, R.anim.anim_tab_bottom_open);
        bottomCloseAnimation = AnimationUtils.loadAnimation(context, R.anim.anim_tab_bottom_close);
    }

    /**
     * 打开
     */
    public void open() {
        topView.startAnimation(topOpenAnimation);
        tabText.startAnimation(bottomOpenAnimation);
        topView.setVisibility(GONE);
        tabText.setVisibility(VISIBLE);
    }

    /**
     * 关闭
     */
    public void close() {
        topView.startAnimation(topCloseAnimation);
        tabText.startAnimation(bottomCloseAnimation);
        topView.setVisibility(VISIBLE);
        tabText.setVisibility(GONE);
    }

    /**
     * 初始化关闭
     */
    public void initClose() {
        topView.setVisibility(VISIBLE);
        tabText.setVisibility(GONE);
        topView.startAnimation(topCloseAnimation);
    }

    /**
     * 设置点击时的tag
     *
     * @param tag
     */
    public void setChildTag(Object tag) {
        topView.setTag(tag);
    }

    /**
     * 设置标题
     *
     * @param name
     */
    public void setName(String name) {
        tabText.setText(name);
    }

    /**
     * 设置打开监听
     *
     * @param clickListener
     */
    public void setOpenListener(OnClickListener clickListener) {
        topView.setEnabled(true);
        topView.setOnClickListener(clickListener);
    }
}
