package com.zhongke.content.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * Created by llj on 2017/8/11.
 */

public class Tools {

    /**
     * 通过日期判断登录是否有效
     *
     * @param dateStr 上次登录时间
     * @param hour    过期时间(小时为单位)
     * @return
     */
    public static boolean isLoginValid(String dateStr, float hour) {
//        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = df.parse(dateStr);
            long s = date.getTime();
            return Math.abs(System.currentTimeMillis() - s) < hour * 60 * 60 * 1000;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }


    /**
     * 将秒的数值转换成时间格式
     *
     * @param count 秒的数值
     * @return
     */
    public static String formatTime(int count) {
        if(count >= 3600) return "超过范围了";
        StringBuilder builder = new StringBuilder();
        if (count < 60) {
            if (count < 10) {
                builder.append("00:0").append(count);
            } else {
                builder.append("00:").append(count);
            }
        } else {
            // 分钟
            int minu = (int) Math.floor(count / 60);
            // 秒
            int second = count % 60;
            if (minu < 10) {
                builder.append("0").append(minu).append(":");
            } else {
                builder.append(minu).append(":");
            }

            if(second < 10){
                builder.append("0").append(second);
            }else {
                builder.append(second);
            }
        }
        return builder.toString();
    }

    /**
     * 随机获取指定范围内的值
     * @param min
     * @param max
     * @return
     */
    public static int getRandomValue(int min,int max){
        if(min >= max) return -1;
        Random random = new Random();
        return random.nextInt(max)%(max-min+1) + min;
    }
}
