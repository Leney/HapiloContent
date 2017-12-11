package com.zhongke.content.entity;

import java.util.List;

/**
 * Created by ${xingen} on 2017/11/17.
 */

public class UserDesireListBean {
    /**
     * pageTotal : 1
     * pageIndex : 1
     * records : [{"awardId":1,"audioUrl":"http://zhongke.oss-cn-shenzhen.aliyuncs.com/res_pic/XmemQyD2Fd3NaMSrHRRPFWb7y1508989285126.gif","createTime":"2017-11-03 15:43:47","wishInfo":"奖杯","wishName":"玩具车","wishType":1,"id":6,"userId":90,"wishState":1}]
     * recordTotal : 7
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
         * awardId : 1
         * audioUrl : http://zhongke.oss-cn-shenzhen.aliyuncs.com/res_pic/XmemQyD2Fd3NaMSrHRRPFWb7y1508989285126.gif
         * createTime : 2017-11-03 15:43:47
         * wishInfo : 奖杯
         * wishName : 玩具车
         * wishType : 1
         * id : 6
         * userId : 90
         * wishState : 1
         */

        private int awardId;
        private String audioUrl;
        private String createTime;
        private String wishInfo;
        private String wishName;
        private int wishType;
        private int id;
        private int userId;
        private int wishState;

        public int getAwardId() {
            return awardId;
        }

        public void setAwardId(int awardId) {
            this.awardId = awardId;
        }

        public String getAudioUrl() {
            return audioUrl;
        }

        public void setAudioUrl(String audioUrl) {
            this.audioUrl = audioUrl;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getWishInfo() {
            return wishInfo;
        }

        public void setWishInfo(String wishInfo) {
            this.wishInfo = wishInfo;
        }

        public String getWishName() {
            return wishName;
        }

        public void setWishName(String wishName) {
            this.wishName = wishName;
        }

        public int getWishType() {
            return wishType;
        }

        public void setWishType(int wishType) {
            this.wishType = wishType;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public int getWishState() {
            return wishState;
        }

        public void setWishState(int wishState) {
            this.wishState = wishState;
        }

        @Override
        public String toString() {
            return "RecordsBean{" +
                    "awardId=" + awardId +
                    ", audioUrl='" + audioUrl + '\'' +
                    ", createTime='" + createTime + '\'' +
                    ", wishInfo='" + wishInfo + '\'' +
                    ", wishName='" + wishName + '\'' +
                    ", wishType=" + wishType +
                    ", id=" + id +
                    ", userId=" + userId +
                    ", wishState=" + wishState +
                    '}';
        }
    }
}
