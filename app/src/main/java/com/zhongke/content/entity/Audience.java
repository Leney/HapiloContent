package com.zhongke.content.entity;

import java.io.Serializable;

/**
 * Created by Karma on 2017/8/28.
 * 类描述：
 */

public class Audience implements Serializable{
    private String picUrl;
    private String name;

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Audience{" +
                "picUrl='" + picUrl + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
