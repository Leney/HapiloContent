package com.zhongke.content.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dgg1 on 2017/10/31.
 * 活动列表实体类
 */

public class ActivityListBean {
    private int activityId;
    private String activityName;
    private String activityTime;
    private String activityLocation;
    private String activityPhotoUrl;
    private int participateNumber;

    public ActivityListBean() {
    }

    public ActivityListBean(int activityId, String activityName, String activityTime, String activityLocation, String activityPhotoUrl, int participateNumber) {
        this.activityId = activityId;
        this.activityName = activityName;
        this.activityTime = activityTime;
        this.activityLocation = activityLocation;
        this.activityPhotoUrl = activityPhotoUrl;
        this.participateNumber = participateNumber;
    }

    public int getActivityId() {
        return activityId;
    }

    public void setActivityId(int activityId) {
        this.activityId = activityId;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getActivityTime() {
        return activityTime;
    }

    public void setActivityTime(String activityTime) {
        this.activityTime = activityTime;
    }

    public String getActivityLocation() {
        return activityLocation;
    }

    public void setActivityLocation(String activityLocation) {
        this.activityLocation = activityLocation;
    }

    public String getActivityPhotoUrl() {
        return activityPhotoUrl;
    }

    public void setActivityPhotoUrl(String activityPhotoUrl) {
        this.activityPhotoUrl = activityPhotoUrl;
    }

    public int getParticipateNumber() {
        return participateNumber;
    }

    public void setParticipateNumber(int participateNumber) {
        this.participateNumber = participateNumber;
    }

    public static List<ActivityListBean> getData() {
        List list = new ArrayList();
        list.add(new ActivityListBean(1,"唐诗竞答大赛","2017-11-01","Hapilo支持区域","https://gss3.bdstatic.com/-Po3dSag_xI4khGkpoWK1HF6hhy/baike/c0%3Dbaike92%2C5%2C5%2C92%2C30/sign=a0311575fddcd100d991f07313e22c75/562c11dfa9ec8a13f8d1e898f203918fa1ecc0fb.jpg",1895));
        list.add(new ActivityListBean(2,"莲花山爬山比赛","2017-11-01","莲花山","https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1509447270604&di=d257e33cba0969f9aec72c04878ccb74&imgtype=0&src=http%3A%2F%2Fgb.cri.cn%2Fmmsource%2Fimages%2F2011%2F02%2F02%2F2011gzhpy023.jpg",2500));
        list.add(new ActivityListBean(3,"跆拳道直播课","2017-11-01","Hapilo支持区域","https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1510042052&di=e7cfa73e3dc7cf5808d5f0d797111541&imgtype=jpg&er=1&src=http%3A%2F%2Fpic.xiudodo.com%2Ffigure%2F00%2F00%2F41%2F64%2F87%2F3355c24899274dc.jpg",5600));
        list.add(new ActivityListBean(4,"钢琴直播课","2017-11-01","Hapilo支持区域","https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1510042125&di=3e9f24dcc5b63bc500232493f5c76c30&imgtype=jpg&er=1&src=http%3A%2F%2Fimgsrc.baidu.com%2Fimgad%2Fpic%2Fitem%2F4e4a20a4462309f787b5822f790e0cf3d7cad630.jpg",3200));
        list.add(new ActivityListBean(5,"做家务比赛","2017-11-01","家里","https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1509447459204&di=85115d619432bd560f08e4260512e1de&imgtype=0&src=http%3A%2F%2Fepaper.guilinlife.com%2Fglwb%2Fres%2F1%2F20130810%2F95951376111162812.jpg",5841));
        return list;
    }
}
