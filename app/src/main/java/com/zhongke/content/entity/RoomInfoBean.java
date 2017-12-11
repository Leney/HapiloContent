package com.zhongke.content.entity;

import java.io.Serializable;

/**
 * Created by ${tanlei} on 2017/8/28.
 */

public class RoomInfoBean implements Serializable {
    //房间序号
    private int serial;
    //房间详情
    private String info;
    //房间状态 1代表未开始（可加入） 0代表已开始（可观战）
    private int status;
    //房间总容纳人数
    private int total;
    //房间现有人数
    private int number;
    //房间ID
    private int id;
    //阶段
    private int stage;
    //年级
    private int grade;

    public RoomInfoBean(int serial, String info, int status, int total, int number, int id) {
        this.serial = serial;
        this.info = info;
        this.status = status;
        this.total = total;
        this.number = number;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public RoomInfoBean() {
    }

    public RoomInfoBean(int serial, String info, int status, int total, int number) {
        this.serial = serial;
        this.info = info;
        this.status = status;
        this.total = total;
        this.number = number;
    }

    public int getSerial() {
        return serial;
    }

    public void setSerial(int serial) {
        this.serial = serial;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "RoomInfoBean{" +
                "serial=" + serial +
                ", info='" + info + '\'' +
                ", status=" + status +
                ", total=" + total +
                ", number=" + number +
                '}';
    }
}
