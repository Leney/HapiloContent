package com.zhongke.content.entity;

/**
 * Created by ${tanlei} on 2017/7/4.
 */

public class RewardBean {
    /**
     * 奖励类型 0=金币，1=积分，2=商城物品，3=其他物品(实物、虚拟物品等)，4=勋章
     */
    private int type;
    /**
     * 礼物名称
     */
    private String name;
    /**
     * 奖励礼物的数值
     */
    private int value;
    /**
     * 礼物icon url
     */
    private String icon;

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

    public int getType() {
        return type;
    }

    public RewardBean setType(int type) {
        this.type = type;
        return this;
    }

    public String getIcon() {
        return icon;
    }

    public RewardBean setIcon(String icon) {
        this.icon = icon;
        return this;
    }
}
