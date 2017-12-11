package com.zhongke.content.view;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhongke.content.R;
import com.zhongke.content.entity.ExaminationQuestion;
import com.zhongke.content.utils.DisplayUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 选择题选项控件
 * Created by llj on 2017/8/26.
 */

public class OptionsLayout2 extends LinearLayout {

    /**
     * 具体选项的文本视图
     */
    private TextView[] optionTexts;

    private Context mContext;

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

    /**
     * 答案选项集合
     */
    private List<ExaminationQuestion.QutionBean.AnswerBean> optionList;

    /**
     * 当前问题的对象
     */
    private ExaminationQuestion.QutionBean curQutionBean;

    /**
     * 行的布局数组
     */
    private LinearLayout[] lineLayouts;

    public OptionsLayout2(Context context) {
        super(context);
        init(context);
    }

    public OptionsLayout2(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public OptionsLayout2(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.mContext = context;
        this.optionList = new ArrayList<>();
        setOrientation(VERTICAL);
    }

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
     * @param qutionBean 问题对象
     */
    public void setOptions(ExaminationQuestion.QutionBean qutionBean) {
        if (qutionBean == null) return;
        this.curQutionBean = qutionBean;
        List<ExaminationQuestion.QutionBean.AnswerBean> options = qutionBean.getAnswer();
        if (options == null) return;
        removeAllViews();
        this.isSure = false;
        this.isCanClick = false;
        this.selectPosition = -1;
        this.optionList.clear();
        this.optionList.addAll(options);
        double length = options.size();

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
            optionText.setText(options.get(i).getTitle());
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

    public ExaminationQuestion.QutionBean getCurQutionBean() {
        return curQutionBean;
    }

    /**
     * 处理item点击
     *
     * @param position
     */
    private void doItemClick(int position) {
        if (position == selectPosition || isSure || !isCanClick) return;
        if (selectPosition >= 0) {
            // 还原之前未选中的状态
            optionTexts[selectPosition].setBackgroundResource(R.drawable.option_nothing_bg);
        }
        // 设置新的选择项的背景
        optionTexts[position].setBackgroundResource(R.drawable.option_select_bg);
        selectPosition = position;
        ExaminationQuestion.QutionBean.AnswerBean answerBean = curQutionBean.getAnswer().get(position);
        sure(answerBean.getId());
    }

    /**
     * 确定答案
     */
    private void sure(int answerId) {
        if (isSure) return;
        // 设置正确选项的背景
        int length = optionList.size();
        for (int i = 0; i < length; i++) {
            ExaminationQuestion.QutionBean.AnswerBean bean = optionList.get(i);
            if (bean.getIsRight() == 1) {
                // 不管答对与否，都要设置正确答案的背景
                optionTexts[i].setBackgroundResource(R.drawable.option_right_bg);
            }
            if (answerId == bean.getId()) {
                if (bean.getIsRight() != 1) {
                    // 答案错误
                    optionTexts[i].setBackgroundResource(R.drawable.option_wrong_bg);
                }
                if (mListener != null) {
                    // 回调结果
                    mListener.onResult(bean.getIsRight() == 1);
                }
            }
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
