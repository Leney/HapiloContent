package com.zhongke.content.im.extra.message;

/**
 * Created by ${xingen} on 2017/12/8.
 * 活动中一个题目的抢答
 */

public class QuestionScramble {

    /**
     * 答题人员的Id
     */
    public int userId;
    /**
     * 题目Id
     */
    public int questionId;
    /**
     * 倒计时，单位 s
     */
    public int countdownTime;
    /**
     * 活动Id
     */
    public String activityId;
}
