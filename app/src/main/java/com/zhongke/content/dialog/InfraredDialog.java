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
 * 红外检测dialog
 */

public class InfraredDialog extends BaseDialog implements View.OnClickListener {

    private Context mContext;
    public InfraredDialog(@NonNull Context context) {
        super(context);
        mContext = context;
    }

    public InfraredDialog(@NonNull Context context, int style, View.OnClickListener onClickListener) {
        super(context, style, onClickListener);
        mContext = context;
    }

    public InfraredDialog(@NonNull Context context, View.OnClickListener onClickListener) {
        super(context, onClickListener);
        mContext = context;
    }

    @Override
    protected View getRootView() {

        return View.inflate(context, R.layout.dialog_infrared,null);
    }

    @Override
    protected void initView(View rootView) {
        TextView sure = rootView.findViewById(R.id.infrared_sure);
        TextView tip = rootView.findViewById(R.id.infrared_tip);

        String keyStr = "25cm";
        String str = "小主人,<br />欢迎来到你我的二人世界里,<br />现在让我一起带你开启愿望吧!";
        tip.setText(Html.fromHtml(str));
        sure.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.infrared_sure:
                dismiss();
                break;
            default:
                break;
        }
    }
}
