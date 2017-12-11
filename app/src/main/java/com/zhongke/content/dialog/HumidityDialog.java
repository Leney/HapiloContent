package com.zhongke.content.dialog;

import android.content.Context;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.zhongke.content.R;
import com.zhongke.content.view.BaseDialog;

/**
 * Created by hyx on 2017/10/28.
 *
 * 湿度dialog
 */

public class HumidityDialog extends BaseDialog implements View.OnClickListener {

    private Context mContext;
    public HumidityDialog(@NonNull Context context) {
        super(context);
        mContext = context;
    }

    public HumidityDialog(@NonNull Context context, int style, View.OnClickListener onClickListener) {
        super(context, style, onClickListener);
        mContext = context;
    }

    public HumidityDialog(@NonNull Context context, View.OnClickListener onClickListener) {
        super(context, onClickListener);
        mContext = context;
    }

    @Override
    protected View getRootView() {

        return View.inflate(context, R.layout.dialog_humidity,null);
    }

    @Override
    protected void initView(View rootView) {
        TextView sure = rootView.findViewById(R.id.humidity_sure);
        TextView tip = rootView.findViewById(R.id.humidity_tip);

        String keyStr = "25cm";
        String str = "小主人,<br />今天空气湿度大,降水强度<font color='#CF2200'>"+keyStr+"</font><br />注意防水保暖哦!";
        tip.setText(Html.fromHtml(str));
        sure.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.humidity_sure:
                dismiss();
                break;
            default:
                break;
        }
    }
}
