package com.zhongke.content.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dgg1 on 2017/10/28.
 * 活动道具对象
 */

public class PropsBean {
    private String propsName;
    private String propsPhoto;

    public PropsBean() {
    }

    public PropsBean(String propsName, String propsPhoto) {
        this.propsName = propsName;
        this.propsPhoto = propsPhoto;
    }

    public String getPropsName() {
        return propsName;
    }

    public void setPropsName(String propsName) {
        this.propsName = propsName;
    }

    public String getPropsPhoto() {
        return propsPhoto;
    }

    public void setPropsPhoto(String propsPhoto) {
        this.propsPhoto = propsPhoto;
    }

    public static List<PropsBean> getData() {
        List<PropsBean> list = new ArrayList<>();
        list.add(new PropsBean("水壶","http://img14.360buyimg.com/n0/jfs/t2431/238/1185849783/414996/be352152/5681d8d7N69300151.png"));
        list.add(new PropsBean("门票","http://img1.imgtn.bdimg.com/it/u=499725135,841838470&fm=27&gp=0.jpg"));
        list.add(new PropsBean("哑铃","http://www.chinaispo.com.cn/up/picture/201409/15/201409151119161405161799.jpg"));
        list.add(new PropsBean("扫把扫把扫把","http://pic.58pic.com/58pic/15/20/84/65i58PICKx5_1024.jpg"));
        return list;
    }
}
