package com.zhongke.content.view;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by ${xingen} on 2017/8/24.
 *
 * 定义一个缩放的ImageView
 *
 *
 * 缩放分析：
 *
 * 先记录中心点（缩放，旋转都需要用到），原本的Matrix,原本手指间距离，通过距离比率来设置
 *
 */

public class ScaleImageView extends AppCompatImageView implements View.OnTouchListener{
    //记录当前图片的Matrix
    private Matrix beforeMatrix = new Matrix();
    //改变状态后的Matrix
    private Matrix changeMatrix = new Matrix();
    //记录下一开两个手指间的距离：
    private float before_Distance;
    //记录下开始时，两个手指间的中心点
    private PointF midPointF;
    //记录当前图片处于的状态
    private int currentState = 0;//当前状态
    private final static int MOVE = 1;//拖动
    private final static int SCALE = 2;//缩放
    public ScaleImageView(Context context) {
        super(context);
        initConfig();
    }

    public ScaleImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initConfig();
    }
    private void initConfig() {
        //设置矩阵特征，适用于拖动
        this.setScaleType(ScaleType.MATRIX);
        //设置滑动事件
        this.setOnTouchListener(this);
    }
    @Override
    public boolean onTouch(View view, MotionEvent event) {
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            //手指压下屏幕
            case MotionEvent.ACTION_DOWN:
                currentState = MOVE;
                beforeMatrix.set(this.getImageMatrix());
                break;
            //手指在滑动
            case MotionEvent.ACTION_MOVE:
                if (currentState == SCALE) {//缩放状态
                    float endDistance = distance(event);
                    if (endDistance > 10f) {
                        float scale = endDistance / before_Distance;
                        changeMatrix.set(beforeMatrix);
                        changeMatrix.postScale(scale, scale, midPointF.x, midPointF.y);
                    }
                }
                break;
            //手指离开触摸
            case MotionEvent.ACTION_UP:
                currentState = 0;
                break;
            //多一个手指在触摸
            case MotionEvent.ACTION_POINTER_DOWN:
                currentState = SCALE;
                //开始时，两个手指间距离
                before_Distance = distance(event);
                if (before_Distance > 10f) {
                    midPointF = mid(event);
                    beforeMatrix.set(this.getImageMatrix());
                }
                break;
            //还有手指在触摸
            case MotionEvent.ACTION_POINTER_UP:
                currentState = 0;
                break;
        }
        this.setImageMatrix(changeMatrix);
        return true;
    }
    /**
     * 计算两个手指间的中间点
     */
    private PointF mid(MotionEvent event) {
        float midX = (event.getX(1) + event.getX(0)) / 2;
        float midY = (event.getY(1) + event.getY(0)) / 2;
        return new PointF(midX, midY);
    }
    /**
     * 计算两个手指间的距离
     */
    private float distance(MotionEvent event) {
        float dx = event.getX(1) - event.getX(0);
        float dy = event.getY(1) - event.getY(0);
        /** 使用勾股定理返回两点之间的距离 */
        return (float) Math.sqrt(dx * dx + dy * dy);
    }

}
