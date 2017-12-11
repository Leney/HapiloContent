package com.zhongke.content.entity;

import java.util.List;

/**
 * Created by hyx on 2017/11/16.
 * 我参加的活动
 */

public class MyJoinActivityBean {

    /**
     * pageTotal : 1
     * pageIndex : 1
     * records : [{"coverUrl":"http://zhongke.oss-cn-shenzhen.aliyuncs.com/res_pic/kwJyArP85SF8sTsCXPd5DrBrc1509971467902.jpg","address":"深圳世界之窗","joinCount":2,"actionId":119,"actorState":5,"beginTime":"2017-11-09 20:31:18","endTime":"2019-02-22 20:31:20","title":"成语接龙","userId":90},{"coverUrl":"http://zhongke.oss-cn-shenzhen.aliyuncs.com/res_pic/BfxxitjFykAE4cbKSEKMcep7x1509963257395.jpg","address":"深圳","joinCount":1,"actionId":118,"actorState":5,"beginTime":"2017-11-06 18:14:52","endTime":"2017-11-06 18:14:54","title":"10.活动名","userId":90},{"coverUrl":"http://zhongke.oss-cn-shenzhen.aliyuncs.com/res_pic/BfxxitjFykAE4cbKSEKMcep7x1509963257395.jpg","address":"深圳","joinCount":2,"actionId":109,"actorState":5,"beginTime":"2017-11-09 18:14:52","endTime":"2017-11-22 18:14:54","title":"献爱心，快乐行","userId":90},{"coverUrl":"http://zhongke.oss-cn-shenzhen.aliyuncs.com/res_pic/MJ26HKhhAW2bXkp7nraNywwWk1510215585565.gif","address":"深圳","joinCount":1,"actionId":117,"actorState":5,"beginTime":"2017-11-09 18:14:52","endTime":"2017-11-30 18:14:54","title":"美食之旅","userId":90}]
     * recordTotal : 4
     */

    private int pageTotal;
    private int pageIndex;
    private int recordTotal;
    private List<RecordsBean> records;

    public int getPageTotal() {
        return pageTotal;
    }

    public void setPageTotal(int pageTotal) {
        this.pageTotal = pageTotal;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getRecordTotal() {
        return recordTotal;
    }

    public void setRecordTotal(int recordTotal) {
        this.recordTotal = recordTotal;
    }

    public List<RecordsBean> getRecords() {
        return records;
    }

    public void setRecords(List<RecordsBean> records) {
        this.records = records;
    }

    public static class RecordsBean {
        /**
         * coverUrl : http://zhongke.oss-cn-shenzhen.aliyuncs.com/res_pic/kwJyArP85SF8sTsCXPd5DrBrc1509971467902.jpg
         * address : 深圳世界之窗
         * joinCount : 2
         * actionId : 119
         * actorState : 5
         * beginTime : 2017-11-09 20:31:18
         * endTime : 2019-02-22 20:31:20
         * title : 成语接龙
         * userId : 90
         */

        private String coverUrl;
        private String address;
        private int joinCount;
        private int actionId;
        private int actorState;
        private String beginTime;
        private String endTime;
        private String title;
        private int userId;

        public String getCoverUrl() {
            return coverUrl;
        }

        public void setCoverUrl(String coverUrl) {
            this.coverUrl = coverUrl;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public int getJoinCount() {
            return joinCount;
        }

        public void setJoinCount(int joinCount) {
            this.joinCount = joinCount;
        }

        public int getActionId() {
            return actionId;
        }

        public void setActionId(int actionId) {
            this.actionId = actionId;
        }

        public int getActorState() {
            return actorState;
        }

        public void setActorState(int actorState) {
            this.actorState = actorState;
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

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }
    }
}
