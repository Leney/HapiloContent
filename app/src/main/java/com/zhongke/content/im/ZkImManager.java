package com.zhongke.content.im;

import android.content.Context;

import com.google.gson.Gson;
import com.zhongke.account.AccountManagerClient;
import com.zhongke.account.ClientListener;
import com.zhongke.account.ResultListener;
import com.zhongke.account.model.AccountInfo;
import com.zhongke.account.model.ZkLocalMessage;
import com.zhongke.content.HPApplication;
import com.zhongke.content.entity.SessionContent_Entity;
import com.zhongke.content.im.extra.ExtraMessage;
import com.zhongke.content.utils.ConversionObjcetUtils;

import java.util.List;


/**
 * 即时通讯管理类
 * Created by llj on 2017/8/8.
 */

public class ZkImManager {
    private static final String TAG = "ZkImManager";
    private static ZkImManager instance;
    private AccountManagerClient accountManagerClient;
    private Context appContext;
    private final Gson gson;
    private SessionContent_Entity.UserMsg userMsg;

    private ZkImManager() {
        this.gson = new Gson();
        this.appContext = HPApplication.getInstance();
        this.accountManagerClient = AccountManagerClient.getInstance();
    }

    public static ZkImManager getInstance() {
        if (instance == null) {
            synchronized (ZkImManager.class) {
                if (instance == null) {
                    instance = new ZkImManager();
                }
            }
        }
        return instance;
    }

    /**
     * 用户本身的标识
     */
    public static String userId = "";

    /**
     * 初始化即时通讯中各种配置信息
     * <p>
     * 已经在Application类onCreate()初始化。
     */
    public void init() {
        this.accountManagerClient.init(appContext);
        this.accountManagerClient.queryAccount(new ResultListener<AccountInfo>() {
            @Override
            public void result(AccountInfo accountInfo) {
                userId = accountInfo.userId;
                userMsg = SessionContent_Entity.UserMsg.newInstance(accountInfo.userId, accountInfo.nickName, accountInfo.icon);
            }

            @Override
            public void error(Exception e) {
            }
        });
    }

    /**
     * 查询消息,根据Id
     */
    public void queryMessage(String msgId, ResultListener<ZkLocalMessage> subscriber) {
        accountManagerClient.queryMessage(msgId, subscriber);
    }

    /**
     * 查询消息,根据接受者。
     * <p>
     * 这里会匹配与对方的聊天信息，返回自己发的，和对方发的信息。
     */
    public void queryMessageByReceiver(String sendUserId, String recvUserId, int pageIndex, int pageSize, ResultListener<List<ZkLocalMessage>> subscription) {
        accountManagerClient.queryMessageByReceiver(sendUserId, recvUserId, pageIndex, pageSize, subscription);
    }

    /**
     * 查询消息,根据接受者。
     * <p>
     * 这里会匹配与对方的聊天信息，返回自己发的，和对方发的信息。
     */
    public void queryMessageByGroup(String groupId, int pageIndex, int pageSize, ResultListener<List<ZkLocalMessage>> subscription) {
        accountManagerClient.queryMessageByGroup(groupId, pageIndex, pageSize, subscription);
    }

    /**
     * 根据指定条件来，查询消息
     *
     * @param where
     * @param pageIndex
     * @param pageSize
     * @return
     */
    public void queryMessageByCondition(String where, int pageIndex, int pageSize, ResultListener<List<ZkLocalMessage>> subscription) {
        accountManagerClient.queryMessageByCondition(where, pageIndex, pageSize, subscription);
    }

    /**
     * 重新发送信息
     *
     * @param entity
     */
    public void againSendMessage(SessionContent_Entity entity) {
        accountManagerClient.againSendChatMessage(entity.getMsgId());
    }

    /**
     * 发送聊天信息
     *
     * @param entity
     */
    public String sendMessage(SessionContent_Entity entity) {
        final ZkLocalMessage zkLocalMessage = ConversionObjcetUtils.conversionObject(entity);
        zkLocalMessage.setSendUserId(userId);
        entity.setMemberInfo(userMsg);
        zkLocalMessage.setChatContent(gson.toJson(entity));
        entity.setSendTime(zkLocalMessage.getTime());
        if (entity.getToUserList() != null) {
            return accountManagerClient.sendChatMessage(entity.getSendUserId(), entity.getToUserList(), entity.getContent());
        } else {
            return accountManagerClient.sendChatMessageToGroup(entity.getSendUserId(), entity.getGroupId(), entity.getContent());
        }
    }

    /**
     * 发送额外指令,特殊的抢答活动
     *
     * @param toUserList
     * @param extraMessage
     * @return
     */
    public String sendExtraMessage(List<String> toUserList, ExtraMessage extraMessage) {
        return accountManagerClient.sendExtraMessageToUser(userId, toUserList, gson.toJson(extraMessage));
    }

    /**
     * 发送额外指令到群里
     * @param groupId
     * @param extraMessage
     * @return
     */
    public String sendExtraMessageToGroup(String groupId,ExtraMessage extraMessage){
        return accountManagerClient.sendChatMessageToGroup(userId,groupId,gson.toJson(extraMessage));
    }

    /**
     * 加入群
     * @param groupId
     */
    public  void joinGroup(String groupId){
        accountManagerClient.joinGroup(groupId);
    }

    public  void addSubscriber(ClientListener clientListener){
        this.accountManagerClient.addSubscriberListener(clientListener);
    }

    public void removeSubscriber(ClientListener clientListener){
          this.accountManagerClient.removeSubscriberListener(clientListener);
    }


}
