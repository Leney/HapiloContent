package com.zhongke.content.entity;

/**
 * 直播观众info
 * Created by llj on 2017/9/9.
 */
public class LivePeopleBean {
    public String id;
    public String icon;
    public String name;
    /** 是否在发言*/
    public boolean isTalking;
    /** 是否被禁言*/
    public boolean isForbid;
    /** 是否举手了*/
    public boolean isHandsUp;
}
