package com.zhongke.content.dialog;

import android.content.Context;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.zhongke.content.R;
import com.zhongke.content.view.BaseDialog;

/**
 * Created by hyx on 2017/10/28.
 *
 * 打开台灯成功dialog
 */

public class OpenDeskLampDialog extends BaseDialog implements View.OnClickListener {

    private Context mContext;
    public OpenDeskLampDialog(@NonNull Context context) {
        super(context);
        mContext = context;
    }

    public OpenDeskLampDialog(@NonNull Context context, int style, View.OnClickListener onClickListener) {
        super(context, style, onClickListener);
        mContext = context;
    }

    public OpenDeskLampDialog(@NonNull Context context, View.OnClickListener onClickListener) {
        super(context, onClickListener);
        mContext = context;
    }

    @Override
    protected View getRootView() {

        return View.inflate(context, R.layout.dialog_open_desk_lamp,null);
    }

    @Override
    protected void initView(View rootView) {
        TextView sure = rootView.findViewById(R.id.desk_lamp_sure);
        TextView setting = rootView.findViewById(R.id.desk_lamp_setting);

        sure.setOnClickListener(this);
        setting.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.desk_lamp_sure:

                dismiss();
                break;
            case R.id.desk_lamp_setting:
                //跳转设置界面

                break;
            default:
                break;
        }
    }
}
