package com.zhongke.content.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by llj on 2017/8/10.
 */

public class ToastUtils {

    public static void showToast(Context context, String text) {
//        View toastRoot = LayoutInflater.from(context).inflate(R.layout.qupai_common_toast_default_layout, null, false);
//        TextView message = (TextView) toastRoot.findViewById(R.id.toast_info);
//        message.setText(text);
//        if (toast != null) {
//            toast.cancel();
//            toast = null;
//        }
//
//
//        toast = new Toast(context);
//        toast.setView(toastRoot);
//        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
//        toast.setDuration(Toast.LENGTH_LONG);
//        toast.show();
        Toast.makeText(context, text, Toast.LENGTH_LONG).show();
    }

    public static void showToast(Context context, int resID) {
        showToast(context, context.getResources().getString(resID));
    }
}
