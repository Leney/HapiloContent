package com.zhongke.content.entity;

import java.util.List;

/**
 * Created by hyx on 2017/11/16.
 * 活动列表的参与人员bean
 */

public class ActivityActorBean {

    /**
     * pageTotal : 0
     * pageIndex : 1
     * records : [{"createTime":"2017-11-16 10:06:36","nickName":"苏苏","headImageUrl":"http://zhongke.oss-cn-shenzhen.aliyuncs.com/PictureFile/8cc405d996aa4a8b8196b0899ddbb14e/1510733658085.jpg","actionId":109,"id":12,"userName":"testAdmin","userId":90},"\u2026\u2026"]
     * recordTotal : 0
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
         * createTime : 2017-11-16 10:06:36
         * nickName : 苏苏
         * headImageUrl : http://zhongke.oss-cn-shenzhen.aliyuncs.com/PictureFile/8cc405d996aa4a8b8196b0899ddbb14e/1510733658085.jpg
         * actionId : 109
         * id : 12
         * userName : testAdmin
         * userId : 90
         */

        private String createTime;
        private String nickName;
        private String headImageUrl;
        private int actionId;
        private int id;
        private String userName;
        private int userId;

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getHeadImageUrl() {
            return headImageUrl;
        }

        public void setHeadImageUrl(String headImageUrl) {
            this.headImageUrl = headImageUrl;
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

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        @Override
        public String toString() {
            return "RecordsBean{" +
                    "createTime='" + createTime + '\'' +
                    ", nickName='" + nickName + '\'' +
                    ", headImageUrl='" + headImageUrl + '\'' +
                    ", actionId=" + actionId +
                    ", id=" + id +
                    ", userName='" + userName + '\'' +
                    ", userId=" + userId +
                    '}';
        }
    }
}
