package com.zhongke.content.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

import com.zhongke.content.R;
import com.zhongke.content.utils.StringUtils;

/**
 * Created by ${xingen} on 2017/10/28.
 * 打开情景灯成功的Dialog
 */

public class OpenSceneLightsSuccessDialog extends BaseDialog {
    public OpenSceneLightsSuccessDialog(@NonNull Context context, View.OnClickListener onClickListener) {
        super(context, R.style.DialogTheme_no_black, onClickListener);
        WindowManager.LayoutParams layoutParams= this.getWindow().getAttributes();
        DisplayMetrics displayMetrics= StringUtils.getPhoneMetrics(context);
        layoutParams.width= displayMetrics.widthPixels;
        layoutParams.height=displayMetrics.heightPixels;
    }

    @Override
    protected View getRootView() {
        return View.inflate(context, R.layout.dialog_open_scene_light_success,null) ;
    }

    @Override
    protected void initView(View rootView) {

    }
}
