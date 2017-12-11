package com.zhongke.content.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.zhongke.content.R;
import com.zhongke.content.view.BaseDialog;

/**
 * Created by hyx on 2017/10/28.
 *
 * 温度dialog
 */

public class TemperatureDialog extends BaseDialog implements View.OnClickListener {

    private Context mContext;
    public TemperatureDialog(@NonNull Context context) {
        super(context);
        mContext = context;
    }

    public TemperatureDialog(@NonNull Context context, int style, View.OnClickListener onClickListener) {
        super(context, style, onClickListener);
        mContext = context;
    }

    public TemperatureDialog(@NonNull Context context, View.OnClickListener onClickListener) {
        super(context, onClickListener);
        mContext = context;
    }

    @Override
    protected View getRootView() {

        return View.inflate(context, R.layout.dialog_temperature,null);
    }

    @Override
    protected void initView(View rootView) {
        TextView sure = rootView.findViewById(R.id.temperature_sure);
        TextView tip = rootView.findViewById(R.id.temperature_tip);

        String str = "小主人,<br />今天天气很好,温度<font color='#CF2200'>25°C</font>,<br />很适合出去运动呦!";
        tip.setText(Html.fromHtml(str));
        sure.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.temperature_sure:
                dismiss();
                break;
            default:
                break;
        }
    }
}
