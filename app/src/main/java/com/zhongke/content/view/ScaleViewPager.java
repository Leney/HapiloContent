package com.zhongke.content.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by ${xingen} on 2017/8/24.
 *
 * 双手手指缩放的时候，不做滑动处理
 */

public class ScaleViewPager extends ViewPager {
    public ScaleViewPager(Context context) {
        super(context);
    }

    public ScaleViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if ((event.getAction() & MotionEvent.ACTION_MASK)==MotionEvent.ACTION_MOVE){
            //滑动事件，若是两个手指在滑动，则传递，自身不做处理
            if (event.getPointerCount()>=2){
                return false;
            }
        }
        return super.onInterceptTouchEvent( event);
    }
}
