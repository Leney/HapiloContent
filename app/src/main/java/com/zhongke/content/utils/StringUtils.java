package com.zhongke.content.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * Created by ${tanlei} on 2017/7/10.
 */

public class StringUtils {
    //给string添加空格来填充，使字符串对齐。
    public static String dealString(String str) {
        if (str.equals(null) || str.equals("") || str.equals("null"))
            return null;
        StringBuffer sb = new StringBuffer();
        if (str.length() >= 4 || str.length() == 1) {
            return str;
        } else if (str.length() == 2) {
            for (int i = 0; i < str.length(); i++) {
                sb.append(str.charAt(i) + "        ");
            }
        } else if (str.length() == 3) {
            for (int i = 0; i < str.length(); i++) {
                sb.append(str.charAt(i) + "  ");
            }
        }
        return sb.toString().trim();
    }
    /**
     * 获取手机大小，px
     *
     * @param context
     * @return
     */
    public static DisplayMetrics getPhoneMetrics(Context context) {// 获取手机分辨率
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager manager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        manager.getDefaultDisplay().getMetrics(dm);
        return dm;
    }
}
