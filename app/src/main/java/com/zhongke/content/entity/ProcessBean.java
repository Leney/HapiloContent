package com.zhongke.content.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dgg1 on 2017/10/28.
 */

public class ProcessBean {
    private int serial;
    private String title;
    private String text;

    public ProcessBean() {
    }

    public ProcessBean(int serial, String title, String text) {
        this.serial = serial;
        this.title = title;
        this.text = text;
    }

    public int getSerial() {
        return serial;
    }

    public void setSerial(int serial) {
        this.serial = serial;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public static List<ProcessBean> getData() {
        List<ProcessBean> list = new ArrayList<>();
        list.add(new ProcessBean(1, "游玩前准备", "迪士尼门票2-3张，乐扣杯2-3个（可以装一点水，卫生间附件有直饮水），相机，DV，雨伞，餐巾纸，小零食。"));
        list.add(new ProcessBean(2, "开放时间", "早上9点半开门，但是10点钟才能去游乐区；每天下午1:30和3:30共两场迪士尼巡游表演：每晚8:00，周末晚上9:00有“星梦奇缘”烟火表演：烟火表演很好看，之后基本在晚上10点左右就闭园了。早上9点半开门，但是10点钟才能去游乐区；每天下午1:30和3:30共两场迪士尼巡游表演：每晚8:00，周末晚上9:00有“星梦奇缘”烟火表演：烟火表演很好看，之后基本在晚上10点左右就闭园了。"));
        list.add(new ProcessBean(3, "游玩日记", "开开心心的游玩了一天了，回家写一篇日记记录一下今天的快乐之旅。"));
        return list;
    }
}
