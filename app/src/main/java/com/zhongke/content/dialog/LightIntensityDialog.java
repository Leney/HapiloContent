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
 * 光照强度dialog
 */

public class LightIntensityDialog extends BaseDialog implements View.OnClickListener {

    private Context mContext;
    public LightIntensityDialog(@NonNull Context context) {
        super(context);
        mContext = context;
    }

    public LightIntensityDialog(@NonNull Context context, int style, View.OnClickListener onClickListener) {
        super(context, style, onClickListener);
        mContext = context;
    }

    public LightIntensityDialog(@NonNull Context context, View.OnClickListener onClickListener) {
        super(context, onClickListener);
        mContext = context;
    }

    @Override
    protected View getRootView() {

        return View.inflate(context, R.layout.dialog_light_intensity,null);
    }

    @Override
    protected void initView(View rootView) {
        TextView sure = rootView.findViewById(R.id.light_intensity_sure);
        TextView tip = rootView.findViewById(R.id.light_intensity_tip);

        String str = "小主人,<br />当前感应室内光照强度为<font color='#CF2200'>125</font>,<br />室外光照强度为<font color='#CF2200'>1200</font>,注意<br />主人用眼强度,及时打开台灯";
        tip.setText(Html.fromHtml(str));
        sure.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.light_intensity_sure:

                dismiss();
                break;
            default:
                break;
        }
    }
}
