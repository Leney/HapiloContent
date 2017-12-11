package com.zhongke.content.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhongke.content.R;

/**
 * Created by ${tanlei} on 2017/7/5.
 */

public class TextViewStarBatLayout extends LinearLayout {
    private TextView tv;
    private StarBar sb;
    private Context context;
    private View view;

    public TextViewStarBatLayout(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public TextViewStarBatLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    private void init() {
        view = LayoutInflater.from(context).inflate(R.layout.text_starbar_layout, this);
        tv = view.findViewById(R.id.tv);
        sb = view.findViewById(R.id.sb);
    }

    public void setTextName(String str) {
        tv.setText(str);
    }

    public void setStarNumber(float number) {
        sb.setStarMark(number);
    }
}
