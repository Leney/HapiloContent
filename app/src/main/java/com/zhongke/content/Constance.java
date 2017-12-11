package com.zhongke.content;

import com.zhongke.content.utils.FileUtils;

/**
 * Created by llj on 2017/8/11.
 */

public final class Constance {
    /** 默认最大录音时间 秒*/
    public static final int DEFAULT_MAX_AUDIO_RECORD_TIME_SECOND = 60;
    /** 语音存放位置*/
    public static final String AUDIO_SAVE_DIR = FileUtils.getDir("audio");
    /** 上传文件到阿里oss地址*/
    public static final String TEST_ENDPOINT = "http://oss-cn-shenzhen.aliyuncs.com/";
    /** 阿里 KEY_ID*/
    public static final String ACCES_KEYID = "LTAItE8B6L5hKhiC ";
    /** 阿里 KEY_SECRET*/
    public static final String ACCES_KEYSECRET = "3jPrS2SEQPdaMwcVcSQW8YB7nwi5FB";
    public static final String TEST_BUCKET = "zhongke";
    //加载的地址
    public static final String DOWND_PHOTO = "http://zhongke.oss-cn-shenzhen.aliyuncs.com/";



    /**
     * 消息类型
     */
    public static final int TEXT = 1;
    public static final int VOICE = 2;
    public static final int IMAGE = 3;
    public static final int VIDEO = 4;
    public static final int STICKER = 5;

    /**
     * 消息属于别人的
     */
    public static final int type_other = 1;
    /**
     * 消息属于自己的
     */
    public static final int type_own = 2;

    /**
     * 当前正在聊天的标志，可能是一个人，可能是一个群。
     */
    public static final int chat_one = 1;
    public static final int chat_group = 2;
    public static final int chat_system = 3;
}
