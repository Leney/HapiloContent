package com.zhongke.content.utils;


import com.google.gson.Gson;
import com.zhongke.account.model.ZkLocalMessage;
import com.zhongke.content.Constance;
import com.zhongke.content.entity.SessionContent_Entity;
import com.zhongke.content.im.ZkImManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${xingen} on 2017/8/9.
 *
 * 一个专门转换对象的工具类
 */

public class ConversionObjcetUtils {
    /**
     *
     * @param entity
     * @return
     */

    public static ZkLocalMessage conversionObject(SessionContent_Entity entity){
        ZkLocalMessage zkLocalMessage=new ZkLocalMessage();
        zkLocalMessage.setToUserList(entity.getToUserList());
        zkLocalMessage.setGroupId(entity.getGroupId());
        zkLocalMessage.setChatContent(entity.getContent());
        zkLocalMessage.setSendUserId(String.valueOf(ZkImManager.userId));
        return zkLocalMessage;
    }

    /**
     * @param zkLocalMessage
     * @return
     */
    public static  SessionContent_Entity createInstance(int type, ZkLocalMessage zkLocalMessage){
        Gson gson=new Gson();
        SessionContent_Entity entity=gson.fromJson(zkLocalMessage.chatContent,SessionContent_Entity.class);
        entity.setSendStatus(zkLocalMessage.getRetCode());
        entity.setMark(type);
        entity.setMsgId(zkLocalMessage.getMsgId());
        entity.setSendTime(zkLocalMessage.getTime());
        entity.setGroupId(zkLocalMessage.getGroupId());
        entity.setSendUserId(zkLocalMessage.getSendUserId());
        entity.setToUserList(zkLocalMessage.getToUserList());
        return entity;
    }

    public static  SessionContent_Entity createInstance(int type,String content,int currentChatMark,String receiverId){
        SessionContent_Entity entity=new SessionContent_Entity();
        entity.setMark(type);
        if (Constance.chat_group==currentChatMark){//发送给群
            entity.setGroupId(receiverId);
            entity.setToUserList(null);
        }else{
            List<String> toUserList = new ArrayList<>();
            toUserList.add(receiverId);
            entity.setToUserList(toUserList);
            entity.setGroupId("");
        }
        entity.setSendUserId(String.valueOf(ZkImManager.userId));
        entity.setContent(content);
        return entity;
    }

    /**
     *
     * @param localMessageList
     * @return
     */
    public static  List<SessionContent_Entity> createInstance(List<ZkLocalMessage> localMessageList){
        List<SessionContent_Entity> list=new ArrayList<>();
        String sendUserId=String.valueOf(ZkImManager.userId);
        for (ZkLocalMessage zkLocalMessage:localMessageList){
            if (sendUserId.equals(zkLocalMessage.getSendUserId())){
                list.add(createInstance( Constance.type_own,zkLocalMessage));
            }else{
                list.add(createInstance( Constance.type_other,zkLocalMessage));
            }
        }
        return list;
    }

    private static final  String tag=ConversionObjcetUtils.class.getSimpleName();
}
