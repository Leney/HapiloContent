package com.zhongke.content.entity;

import java.util.List;

/**
 * 直播观看人数bean
 * Created by llj on 2017/9/9.
 */
public class LivePeopleBean2 {

    /**
     * pageTotal : 1
     * pageIndex : 1
     * records : [{"nickName":"苏苏","actionId":118,"actorState":3,"userName":"testAdmin","userId":90}]
     * recordTotal : 1
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
         * nickName : 苏苏
         * actionId : 118
         * actorState : 3
         * userName : testAdmin
         * userId : 90
         */

        private String nickName;
        private int actionId;
        private int actorState;
        private String userName;
        private int userId;

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
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
                    "nickName='" + nickName + '\'' +
                    ", actionId=" + actionId +
                    ", actorState=" + actorState +
                    ", userName='" + userName + '\'' +
                    ", userId=" + userId +
                    '}';
        }
    }
}
