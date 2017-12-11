package com.zhongke.content.entity;

import java.util.List;

/**
 * Created by Administrator on 2017/12/9.
 * 活动流程数据列表（行为，奖励，评价，道具） bean
 */

public class ActivityFlowDataBean {

    /**
     * behaviorAward : [{"awardId":6,"fNodeId":"1510138019893","doObject":1,"openState":null,"behaviorId":null,"uploadResUrl":null,"fNodeType":"award","userId":126,"msgContent":null,"createTime":"2017-12-09 14:59:38","awardCount":1,"judgeType":1,"actionId":109,"id":5,"runTime":343,"iconUrl":"http://zhongke.oss-cn-shenzhen.aliyuncs.com/res_pic/SnXFYR5iMDc5BjSY6EPBw84aJ1512128629724.jpg","flowId":72,"nValue":null,"toolsId":null,"fNodeName":"活动奖励"},{"awardId":null,"fNodeId":"1510137837776","doObject":1,"openState":null,"behaviorId":260,"uploadResUrl":null,"fNodeType":"behavior","userId":126,"msgContent":null,"createTime":"2017-12-09 14:58:53","awardCount":1,"judgeType":1,"actionId":109,"id":6,"runTime":60,"iconUrl":null,"flowId":72,"nValue":4,"toolsId":null,"fNodeName":"关键行为"}]
     * evaluate : {"evaluateScore":0,"createTime":"2017-12-04 09:17:24","doState":1,"actionId":109,"id":137,"beginTime":"2017-12-04 16:29:52","endTime":"2017-12-30 16:31:30","userId":126,"evaluate":null,"actionName":"测试直播活动"}
     */

    private EvaluateBean evaluate;
    private List<BehaviorAwardBean> behaviorAward;

    public EvaluateBean getEvaluate() {
        return evaluate;
    }

    public void setEvaluate(EvaluateBean evaluate) {
        this.evaluate = evaluate;
    }

    public List<BehaviorAwardBean> getBehaviorAward() {
        return behaviorAward;
    }

    public void setBehaviorAward(List<BehaviorAwardBean> behaviorAward) {
        this.behaviorAward = behaviorAward;
    }

    public static class EvaluateBean {
        /**
         * evaluateScore : 0
         * createTime : 2017-12-04 09:17:24
         * doState : 1
         * actionId : 109
         * id : 137
         * beginTime : 2017-12-04 16:29:52
         * endTime : 2017-12-30 16:31:30
         * userId : 126
         * evaluate : null
         * actionName : 测试直播活动
         */

        private int evaluateScore;
        private String createTime;
        private int doState;
        private int actionId;
        private int id;
        private String beginTime;
        private String endTime;
        private int userId;
        private Object evaluate;
        private String actionName;

        public int getEvaluateScore() {
            return evaluateScore;
        }

        public void setEvaluateScore(int evaluateScore) {
            this.evaluateScore = evaluateScore;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public int getDoState() {
            return doState;
        }

        public void setDoState(int doState) {
            this.doState = doState;
        }

        public int getActionId() {
            return actionId;
        }

        public void setActionId(int actionId) {
            this.actionId = actionId;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getBeginTime() {
            return beginTime;
        }

        public void setBeginTime(String beginTime) {
            this.beginTime = beginTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public Object getEvaluate() {
            return evaluate;
        }

        public void setEvaluate(Object evaluate) {
            this.evaluate = evaluate;
        }

        public String getActionName() {
            return actionName;
        }

        public void setActionName(String actionName) {
            this.actionName = actionName;
        }
    }

    public static class BehaviorAwardBean {
        /**
         * awardId : 6
         * fNodeId : 1510138019893
         * doObject : 1
         * openState : null
         * behaviorId : null
         * uploadResUrl : null
         * fNodeType : award
         * userId : 126
         * msgContent : null
         * createTime : 2017-12-09 14:59:38
         * awardCount : 1
         * judgeType : 1
         * actionId : 109
         * id : 5
         * runTime : 343
         * iconUrl : http://zhongke.oss-cn-shenzhen.aliyuncs.com/res_pic/SnXFYR5iMDc5BjSY6EPBw84aJ1512128629724.jpg
         * flowId : 72
         * nValue : null
         * toolsId : null
         * fNodeName : 活动奖励
         */

        private int awardId;
        private String fNodeId;
        private int doObject;
        private Object openState;
        private Object behaviorId;
        private Object uploadResUrl;
        private String fNodeType;
        private int userId;
        private Object msgContent;
        private String createTime;
        private int awardCount;
        private int judgeType;
        private int actionId;
        private int id;
        private int runTime;
        private String iconUrl;
        private int flowId;
        private Object nValue;
        private Object toolsId;
        private String fNodeName;

        public int getAwardId() {
            return awardId;
        }

        public void setAwardId(int awardId) {
            this.awardId = awardId;
        }

        public String getFNodeId() {
            return fNodeId;
        }

        public void setFNodeId(String fNodeId) {
            this.fNodeId = fNodeId;
        }

        public int getDoObject() {
            return doObject;
        }

        public void setDoObject(int doObject) {
            this.doObject = doObject;
        }

        public Object getOpenState() {
            return openState;
        }

        public void setOpenState(Object openState) {
            this.openState = openState;
        }

        public Object getBehaviorId() {
            return behaviorId;
        }

        public void setBehaviorId(Object behaviorId) {
            this.behaviorId = behaviorId;
        }

        public Object getUploadResUrl() {
            return uploadResUrl;
        }

        public void setUploadResUrl(Object uploadResUrl) {
            this.uploadResUrl = uploadResUrl;
        }

        public String getFNodeType() {
            return fNodeType;
        }

        public void setFNodeType(String fNodeType) {
            this.fNodeType = fNodeType;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public Object getMsgContent() {
            return msgContent;
        }

        public void setMsgContent(Object msgContent) {
            this.msgContent = msgContent;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public int getAwardCount() {
            return awardCount;
        }

        public void setAwardCount(int awardCount) {
            this.awardCount = awardCount;
        }

        public int getJudgeType() {
            return judgeType;
        }

        public void setJudgeType(int judgeType) {
            this.judgeType = judgeType;
        }

        public int getActionId() {
            return actionId;
        }

        public void setActionId(int actionId) {
            this.actionId = actionId;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getRunTime() {
            return runTime;
        }

        public void setRunTime(int runTime) {
            this.runTime = runTime;
        }

        public String getIconUrl() {
            return iconUrl;
        }

        public void setIconUrl(String iconUrl) {
            this.iconUrl = iconUrl;
        }

        public int getFlowId() {
            return flowId;
        }

        public void setFlowId(int flowId) {
            this.flowId = flowId;
        }

        public Object getNValue() {
            return nValue;
        }

        public void setNValue(Object nValue) {
            this.nValue = nValue;
        }

        public Object getToolsId() {
            return toolsId;
        }

        public void setToolsId(Object toolsId) {
            this.toolsId = toolsId;
        }

        public String getFNodeName() {
            return fNodeName;
        }

        public void setFNodeName(String fNodeName) {
            this.fNodeName = fNodeName;
        }
    }
}
