package com.zhongke.content.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;

import com.zhongke.content.R;
import com.zhongke.content.utils.DisplayUtils;
import com.zhongke.content.utils.ViewUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${xinGen} on 2017/9/29.
 * <p>
 * <p>
 * 一个描边的TextView，点击切换的布局
 */

public class StrokeTextViewLayout extends ConstraintLayout implements View.OnClickListener {
    private static final String TAG=StrokeTextViewLayout.class.getSimpleName();
    public StrokeTextViewLayout(Context context) {
        super(context);
    }
    public StrokeTextViewLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        addData();
        addChildView();
    }
    private List<TextMessage> msgList = new ArrayList<>();
    private List<StrokeChildTextView> childViewList = new ArrayList<>();

    /**
     * 初始数据
     */
    private void addData() {
        for (int i = 0; i < 7; ++i) {
            TextMessage message = new TextMessage();
            String name = null;
            boolean state = false;
            switch (i) {
                case 0:
                    name = "绘画";
                    state = true;
                    break;
                case 1:
                    name = "历史";
                    break;
                case 2:
                    name = "书法";
                    break;
                case 3:
                    name = "国学";
                    break;
                case 4:
                    name = "音乐";
                    break;
                case 5:
                    name = "舞蹈";
                    break;
                case 6:
                    name = "游戏";
                    break;
            }
            message.name = name;
            message.state = state;
            msgList.add(message);
            //添加子类View
            StrokeChildTextView textView = createTextView(message.state);
            textView.setId(ViewUtils.getViewId());
            textView.setText(message.name);
            textView.setTag(i);
            textView.setOnClickListener(this);
            childViewList.add(textView);
        }
    }

    /**
     * 添加子类View
     */
    private void addChildView() {
        for (int i = 0; i < msgList.size(); ++i) {
            StrokeChildTextView textView = childViewList.get(i);
            ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            layoutParams.bottomToBottom = LayoutParams.PARENT_ID;
            switch (i) {
                case 0:
                    layoutParams.leftToLeft = LayoutParams.PARENT_ID;
                    layoutParams.rightToLeft = childViewList.get(i + 1).getId();
                    break;
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                    layoutParams.leftToRight = childViewList.get(i - 1).getId();
                    layoutParams.rightToLeft = childViewList.get(i + 1).getId();
                case 6:
                    layoutParams.leftToRight = childViewList.get(i - 1).getId();
                    layoutParams.rightToRight = LayoutParams.PARENT_ID;
                    break;
            }
            textView.setLayoutParams(layoutParams);
            addView(textView);
        }
    }
    private StrokeChildTextView createTextView(boolean state) {
        return new StrokeChildTextView(getContext(), state ? StrokeChildTextView.MODE_SELECT : StrokeChildTextView.MODE_DEFAULT);
    }
    private Bitmap defaultBitmap, selectBitmap;
    private Bitmap loadBitmap(int imageId) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        return BitmapFactory.decodeResource(getResources(), imageId, options);
    }
    private int currentSelect = 0;
    @Override
    public void onClick(View view) {
        int click = (int) view.getTag();
        Log.i(TAG," 点击位置： "+click );
        if (currentSelect == click) return;
        childViewList.get(currentSelect).setModeStroke(StrokeChildTextView.MODE_DEFAULT);
        childViewList.get(click).setModeStroke(StrokeChildTextView.MODE_SELECT);
        this.currentSelect = click;
        if (getStrokeTextViewResponseListener()!=null){
            getStrokeTextViewResponseListener().response(msgList.get(this.currentSelect).name);
        }
    }
    /**
     * 描边的TextView
     */
    private class StrokeChildTextView extends AppCompatTextView {
        private int currentMode = MODE_DEFAULT;
        //默认模式,选中模式
        public static final int MODE_SELECT = 2;
        public static final int MODE_DEFAULT = 1;
        private Paint strokePaint;
        private int strokeColor;
        private int strokeWidth = 5;
        private int stroke_color_default, stroke_color_select;
        private int textColor_default, textColor_select;

        public StrokeChildTextView(Context context, int mode) {
            super(context);
            initConfig();
            setModeStroke(mode);
        }
        /**
         * 默认属性配置
         */
        private void initConfig() {
            textColor_default = Color.parseColor("#be5d0e");
            textColor_select = Color.parseColor("#a33f18");
            stroke_color_default = Color.parseColor("#f2ca5c");
            stroke_color_select = Color.parseColor("#ee9881");
            this.strokeWidth = 8;
            setGravity(Gravity.CENTER);
            setPadding(0, DisplayUtils.dip2px(getContext(), 3), 0, 0);
            getPaint().setFakeBoldText(true);
            setTextSize(28);
        }
        private Drawable loadImageBitmap(int mode) {
            Bitmap bitmap;
            if (mode == MODE_DEFAULT) {
                if (defaultBitmap == null) {
                    defaultBitmap = loadBitmap(R.mipmap.live_lobby_select_true);
                }
                bitmap = defaultBitmap;
            } else {
                if (selectBitmap == null) {
                    selectBitmap = loadBitmap(R.mipmap.live_lobby_select_false);
                }
                bitmap = selectBitmap;
            }
            return new BitmapDrawable(getResources(), bitmap);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            drawStrokeText(canvas);
            super.onDraw(canvas);
        }

        /**
         * 绘制描边色的字体
         *
         * @param canvas
         */
        private void drawStrokeText(Canvas canvas) {
            if (this.strokePaint == null) {
                this.strokePaint = new Paint();
            }
            //拷贝原本TextView中Paint一些属性
            Paint defaultPaint = getPaint();
            this.strokePaint.set(defaultPaint);
            //根据需求，定义描边色的画笔
            this.strokePaint.setStyle(Paint.Style.STROKE);
            this.strokePaint.setColor(strokeColor);
            this.strokePaint.setStrokeWidth(strokeWidth);
            //绘制描边色的文本
            String text = getText().toString();
            canvas.drawText(text, (getWidth() - strokePaint.measureText(text)) / 2, getBaseline(), strokePaint);
        }

        /**
         * 设置描边的特征，颜色，描边的宽度
         *
         * @param mode
         */
        public void setModeStroke(int mode) {
            this.currentMode = mode;
            if (currentMode == MODE_DEFAULT) {
                strokeColor = stroke_color_default;
                setTextColor(textColor_default);
                setBackground(loadImageBitmap(currentMode));
            } else {
                strokeColor = stroke_color_select;
                setTextColor(textColor_select);
                setBackground(loadImageBitmap(currentMode));
            }
        }
    }

    private StrokeTextViewResponseListener  strokeTextViewResponseListener;
    public StrokeTextViewResponseListener getStrokeTextViewResponseListener() {
        return strokeTextViewResponseListener;
    }
    public void setStrokeTextViewResponseListener(StrokeTextViewResponseListener strokeTextViewResponseListener) {
        this.strokeTextViewResponseListener = strokeTextViewResponseListener;
    }

    /**
     * 响应监听器
     */
    public interface  StrokeTextViewResponseListener{
        void response(String name);
    }
    /**
     * 信息实体类
     */
    private static class TextMessage {
        public String name;
        public boolean state;
    }
}
