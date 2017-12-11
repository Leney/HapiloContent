package com.zhongke.content.utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by ${xingen} on 2017/8/22.
 *
 * 网络监控
 */

public class NetConnectedUtils {
    // return true 有网，反之无网
    public static boolean isNetConnection(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            //Toast.makeText(context, "无网络状态", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    // 跳转到打开设置界面
    public static void openSet(Context context) {
        Intent intent;
        if (Integer.parseInt(android.os.Build.VERSION.SDK) > 10) {
            intent = new Intent("android.settings.WIRELESS_SETTINGS");
        } else {
            intent = new Intent("/");
            intent.setComponent(new ComponentName("com.android.settings",
                    "com.android.settings.WirelessSettings"));
            intent.setAction("android.intent.action.VIEW");
        }
        context.startActivity(intent);
    }
}
