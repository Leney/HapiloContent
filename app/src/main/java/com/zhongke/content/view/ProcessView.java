package com.zhongke.content.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhongke.content.R;

/**
 * Created by dgg1 on 2017/10/28.
 */

public class ProcessView extends RelativeLayout {
    private final View view;
    /**
     * 序列
     */
    private TextView tvSerial;
    /**
     * 标题
     */
    private TextView tvTitle;
    /**
     * 流程内容
     */
    private TextView tvText;

    public ProcessView(Context context) {
        this(context, null);
    }

    public ProcessView(Context context, AttributeSet attrs) {
        super(context, attrs);
        view = LayoutInflater.from(context).inflate(R.layout.process_view, null);
        this.addView(view);
        tvSerial = view.findViewById(R.id.tv_serial);
        tvTitle = view.findViewById(R.id.text_serial);
        tvText = view.findViewById(R.id.tv_process_text);
    }

    public void setProcess(String serial, String title, String text) {
        tvSerial.setText(serial);
        if (null != title && !"".equals(title)){
            tvTitle.setText(title);
        }
        if (null != text && !"".equals(text)) tvText.setText(text);
    }
}
