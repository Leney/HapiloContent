package com.zhongke.content.view;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhongke.content.R;
import com.zhongke.content.utils.DisplayUtils;
import com.zhongke.content.utils.LogUtil;
import com.zhongke.content.utils.ToastUtils;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;

/**
 * 选择题选项控件
 * Created by llj on 2017/8/26.
 */

public class OptionsLayout extends LinearLayout {

    /**
     * 行的布局数组
     */
    private LinearLayout[] lineLayouts;

    /**
     * 具体选项的文本视图
     */
    private TextView[] optionTexts;

    /**
     * 选项内容
     */
    private String[] options;

    private Context mContext;

    /**
     * 正确答案的position
     */
    private int rightPosition = -1;

    /**
     * 用户选择的position,注意：不是最终确定答案
     */
    private int selectPosition = -1;

    /**
     * 标识是否已经点击确定了答案
     */
    private boolean isSure = false;

    /**
     * 标识答案选项是否可以点击(还未抢答，只是给用户看答案)
     */
    private boolean isCanClick = false;

    private OnSelectResultListener mListener;

    public OptionsLayout(Context context) {
        super(context);
        init(context);
    }

    public OptionsLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public OptionsLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public OptionsLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        this.mContext = context;
        setOrientation(VERTICAL);
    }

//    public void setRightPosition(int position) {
//        this.rightPosition = position;
//    }

    /**
     * 设置选择结果回调监听
     *
     * @param listener
     */
    public void setOnSelectResultListener(OnSelectResultListener listener) {
        this.mListener = listener;
    }

    /**
     * 设置是否可点击选择
     *
     * @param isCanClick
     */
    public void setCanClick(boolean isCanClick) {
        this.isCanClick = isCanClick;
    }

    /**
     * 设置选项数据
     *
     * @param options       选项数组值
     * @param rightPosition 正确的选答案选项
     */
    public void setOptions(String[] options, int rightPosition) {
        if (options == null || rightPosition < 0 || rightPosition >= options.length) return;
        removeAllViews();
        this.options = options;
        this.rightPosition = rightPosition;
        this.selectPosition = -1;
        this.isSure = false;
        this.isCanClick = false;
        double length = options.length;

        double lineSize;
        if (length % 2 == 0) {
            lineSize = length / 2;
        } else {
            lineSize = Math.ceil(length / 2);
        }


        lineLayouts = new LinearLayout[(int) lineSize];
        LayoutParams lineParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        lineParams.gravity = Gravity.CENTER;
        lineParams.setMargins(0, 0, 0, DisplayUtils.dip2px(mContext, 10));
        // 创建所有行布局
        for (int i = 0; i < lineSize; i++) {
            LinearLayout lineLayout = new LinearLayout(mContext);
            lineLayout.setOrientation(HORIZONTAL);
            lineLayouts[i] = lineLayout;
            addView(lineLayouts[i], lineParams);
        }


        // 创建局里的子布局,具体选项部分
        // 子选项的布局参数
        LinearLayout.LayoutParams optionParams = new LayoutParams(0, LayoutParams.WRAP_CONTENT, 1);
//        optionParams.rightMargin = DisplayUtils.dip2px(mContext, 10);
        LinearLayout.LayoutParams optionParams2 = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        // 中间占位符视图的布局参数
        LinearLayout.LayoutParams emptyParams = new LayoutParams(DisplayUtils.dip2px(mContext, 10), 1);

        // 添加行数index
        int lineIndex = 0;
        int horPadding = DisplayUtils.dip2px(mContext, 10);
        int verPadding = DisplayUtils.dip2px(mContext, 8);

        optionTexts = new TextView[(int) length];

        for (int i = 0; i < length; i++) {
            TextView optionText = new TextView(mContext);
            optionText.setTextColor(Color.parseColor("#20271e"));
            optionText.setTextSize(20);
            optionText.setText(options[i]);
            optionText.setGravity(Gravity.CENTER);
            optionText.setPadding(horPadding, verPadding, horPadding, verPadding);
            optionText.setBackgroundResource(R.drawable.option_nothing_bg);
//            optionText.setBackgroundResource(R.drawable.option_normal_bg);
            optionTexts[i] = optionText;
            if (i % 2 == 0 && i >= length - 1) {
                // 右边没有选项了,不要占满全屏宽度了，自适应就好
                lineLayouts[lineIndex].addView(optionTexts[i], optionParams2);
            } else {
                lineLayouts[lineIndex].addView(optionTexts[i], optionParams);
                if (i % 2 == 0) {
                    // 是左边的视图,设置右边的占位符视图
                    View emptyView = new View(mContext);
                    lineLayouts[lineIndex].addView(emptyView, emptyParams);
                }
            }

            optionText.setTag(i);
            optionText.setOnClickListener(view ->
                    doItemClick((Integer) view.getTag())
            );

            if (i % 2 != 0) {
                lineIndex++;
            }

        }
    }

    /**
     * 处理item点击
     *
     * @param position
     */
    private void doItemClick(int position) {
        LogUtil.i("llj", "position------>>" + position + "\nselectPosition------>>" + selectPosition + "\nisCanClick------>>" + isCanClick + "\nisSure------>>" + isSure);
        if (position == selectPosition || isSure || !isCanClick) return;
        if (selectPosition >= 0) {
            // 还原之前未选中的状态
            optionTexts[selectPosition].setBackgroundResource(R.drawable.option_nothing_bg);
        }
        // 设置新的选择项的背景
        optionTexts[position].setBackgroundResource(R.drawable.option_select_bg);
        selectPosition = position;
    }


    /**
     * 确定答案
     */
    public void sure() {
        LogUtil.i("llj", "rightPosition------->>>" + rightPosition);
        LogUtil.i("llj", "selectPosition------->>>" + selectPosition);
        if (rightPosition < 0 || isSure) return;
        if (selectPosition < 0) {
            ToastUtils.showToast(getContext(), R.string.choice_answer);
            return;
        }
        if (rightPosition < 0 || selectPosition < 0) return;
        // 设置正确选项的背景
        optionTexts[rightPosition].setBackgroundResource(R.drawable.option_right_bg);
        if (rightPosition == selectPosition) {
            // 回答正确
            if (mListener == null) return;
            mListener.onResult(true);
        } else {
            // 回答错误
            if (mListener == null) return;
            optionTexts[selectPosition].setBackgroundResource(R.drawable.option_wrong_bg);
            mListener.onResult(false);
        }
        isSure = true;
    }

    public interface OnSelectResultListener {
        /**
         * 选择结果回调
         *
         * @param isRight 是否正确 true=正确，false=错误
         */
        void onResult(boolean isRight);
    }

}
