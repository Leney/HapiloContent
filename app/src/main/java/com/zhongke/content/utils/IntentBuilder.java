package com.zhongke.content.utils;

import android.content.Intent;
import android.content.IntentFilter;

/**
 * Created by ${xingen} on 2017/11/24.
 */

public class IntentBuilder {
    public static final String ACTION_ACCEPT_MESSAGE = "com.zongke.hapiloimservice.service.MessageHandlerService.AcceptMessageBroadcastReceiver";
    public static final String ACTION_SEND_MESSAGE = "com.zongke.hapiloimservice.service.MessageHandlerService.SendMessageBroadcastReceiver";
    public static final String ACTION_JOIN_GROUP= "com.zongke.hapiloimservice.service.MessageHandlerService.JoinGroupMessageBroadcastReceiver ";
    public static final String KEY_MESSAGE_ID = "messageId";
    public static final String KEY_GROUP_ID="groupId";
    public static IntentFilter builderIntentFilter(String action){
       return new IntentFilter(action);
    }
    public static Intent builderMessageIntent(String action, String messageId){
        Intent intent=  new Intent(action);
        intent.putExtra(KEY_MESSAGE_ID,messageId);
        return intent;
    }
    public static Intent builderGroupIntent(String action, String groupId){
        Intent intent=  new Intent(action);
        intent.putExtra(KEY_GROUP_ID,groupId);
        return intent;
    }
}
