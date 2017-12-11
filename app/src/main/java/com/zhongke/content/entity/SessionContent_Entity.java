package com.zhongke.content.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ${xingen} on 2017/8/7.
 */

public class SessionContent_Entity implements Serializable{
    private String localUrl;
    private String content;
    //必须传递
    private UserMsg memberInfo;
    //必需传递
    private String groupId="" ;
    //视频，文本，表情，必须
    private int chatType;
    private String msgId;
    private int mark;
    //发送状态成功，失败
    private int sendStatus=-1;
    private long sendTime;
    //音频时间
    private int duraction;

    private boolean isAudioPlay;

    public boolean getIsAudioPlay() {
        return isAudioPlay;
    }

    public void setAudioPlay(boolean audioPlay) {
        isAudioPlay = audioPlay;
    }

    public int getDuraction() {
        return duraction;
    }

    public void setDuraction(int duraction) {
        this.duraction = duraction;
    }

    private String sendUserId ;
    private List<String> toUserList;

    public List<String> getToUserList() {
        return toUserList;
    }

    public void setToUserList(List<String> toUserList) {
        this.toUserList = toUserList;
    }


    public String getSendUserId() {
        return sendUserId;
    }

    public void setSendUserId(String sendUserId) {
        this.sendUserId = sendUserId;
    }


    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public int getSendStatus() {
        return sendStatus;
    }
    public void setSendStatus(int sendStatus) {
        this.sendStatus = sendStatus;
    }
    public String getMsgId() {
        return msgId;
    }
    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }
    public int getChatType() {
        return chatType;
    }
    public void setChatType(int chatType) {
        this.chatType = chatType;
    }

    public long getSendTime() {
        return sendTime;
    }

    public void setSendTime(long sendTime) {
        this.sendTime = sendTime;
    }

    public UserMsg getMemberInfo() {
        return memberInfo;
    }

    public void setMemberInfo(UserMsg memberInfo) {
        this.memberInfo = memberInfo;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    public String getLocalUrl() {
        return localUrl;
    }

    public void setLocalUrl(String localUrl) {
        this.localUrl = localUrl;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }



    /**
     * 用户信息
     */
    public static class UserMsg implements Serializable{
        private String id;
        private String name;
        private String icon;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

     public static UserMsg newInstance(String id, String name,String icon){
         UserMsg userMsg=new UserMsg();
         userMsg.setId(id);
         userMsg.setName(name);
         userMsg.setIcon(icon);
         return userMsg;
     }
    }
}
