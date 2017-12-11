package com.zhongke.content.utils;

import android.util.DisplayMetrics;

import com.zhongke.content.HPApplication;

/**
 * Created by ${xingen} on 2017/7/13.
 *
 * 一个等量比例计算方法的操作类
 */

public class ProportionUtils {

    private  static  ProportionUtils instance;
    private  int widthPixels;
    private  int heightPixels;
    /**
     *     美工设计图上的宽高，单位dp
     */
    private  final  int width_dp=640;
    private final  int height_dp=360;
    static {
        instance=new ProportionUtils();
    }
    private ProportionUtils(){
       DisplayMetrics displayMetrics= HPApplication.getInstance(). getResources().getDisplayMetrics();
        this.widthPixels=displayMetrics.widthPixels;
        this.heightPixels=displayMetrics.heightPixels;
    }
    public  int calculationWith(float dip_w){
              return (int) (((float)dip_w/width_dp)*widthPixels);
    }
    public  int calculationHeight(float dip_h){
        return (int) (((float)dip_h/height_dp)*heightPixels);
    }
}
