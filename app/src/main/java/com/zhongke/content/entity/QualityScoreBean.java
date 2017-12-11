package com.zhongke.content.entity;

/**
 * Created by ${tanlei} on 2017/7/5.
 */

public class QualityScoreBean {
    private String name;
    private int value;

    public QualityScoreBean() {
    }

    public QualityScoreBean(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
