package com.zhongke.content.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${xingen} on 2017/9/28.
 */

public class LiveLobbyBean {
    public List<LiveLobby> dataList;

    public static class LiveLobby{

    }
    public static LiveLobbyBean newInstance(){
        LiveLobbyBean liveLobbyBean=new LiveLobbyBean();
        liveLobbyBean.dataList=new ArrayList<>();
       for (int i=0;i<5;++i){
           LiveLobby liveLobby=new LiveLobby();
           liveLobbyBean.dataList.add(liveLobby);
       }
        return liveLobbyBean;
    }
}
