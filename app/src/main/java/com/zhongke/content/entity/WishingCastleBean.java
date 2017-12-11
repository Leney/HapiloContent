package com.zhongke.content.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${xingen} on 2017/9/26.
 */

public class WishingCastleBean {
    public List<WishingCastle>  dataList;
    public static class  WishingCastle{
        public  String name;
        public  int type;
    }
    public static WishingCastleBean newInstance(){
        WishingCastleBean wishingCastleBean=new WishingCastleBean();
        wishingCastleBean.dataList=new ArrayList<>();
//        for (int i=0;i<5;i++){
//            WishingCastle wishingCastle=new WishingCastle();
//            wishingCastle.name="类型"+(i+1);
//            wishingCastle.type=i+1;
//            wishingCastleBean.dataList.add(wishingCastle);
//        }
        WishingCastle wishingCastle = new WishingCastle();
        wishingCastle.name = "实物";
        wishingCastle.type=1;
        WishingCastle wishingCastle2 = new WishingCastle();
        wishingCastle2.name = "金币";
        wishingCastle2.type=2;
        WishingCastle wishingCastle3 = new WishingCastle();
        wishingCastle3.name = "口头表扬";
        wishingCastle3.type=3;
//        WishingCastle wishingCastle4 = new WishingCastle();
//        wishingCastle4.name = "勋章类";
//        wishingCastle4.type=4;
//        WishingCastle wishingCastle5 = new WishingCastle();
//        wishingCastle5.name = "零食类";
//        wishingCastle5.type=5;
        wishingCastleBean.dataList.add(wishingCastle);
        wishingCastleBean.dataList.add(wishingCastle2);
        wishingCastleBean.dataList.add(wishingCastle3);
//        wishingCastleBean.dataList.add(wishingCastle4);
//        wishingCastleBean.dataList.add(wishingCastle5);
        return wishingCastleBean;
    }
}
