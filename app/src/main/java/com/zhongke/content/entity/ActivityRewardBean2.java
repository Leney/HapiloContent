package com.zhongke.content.entity;

import java.util.List;

/**
 * Created by dgg1 on 2017/10/27.
 * 活动奖励bean
 */

public class ActivityRewardBean2 {

    /**
     * pageTotal : 0
     * pageIndex : 1
     * records : [{"createUserId":90,"createTime":"2017-11-14 13:41:27","price":100,"awardInfo":"奖品","provideAwardType":1,"id":6,"iconUrl":"http://zhongke.oss-cn-shenzhen.aliyuncs.com/res_pic/seCpCf56QQJiKtsncw8QNhCSz1510310897421.jpg","awardName":"玩具车","orgId":1,"awardType":1},"\u2026\u2026"]
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
         * createUserId : 90
         * createTime : 2017-11-14 13:41:27
         * price : 100
         * awardInfo : 奖品
         * provideAwardType : 1
         * id : 6
         * iconUrl : http://zhongke.oss-cn-shenzhen.aliyuncs.com/res_pic/seCpCf56QQJiKtsncw8QNhCSz1510310897421.jpg
         * awardName : 玩具车
         * orgId : 1
         * awardType : 1
         */

        private int createUserId;
        private String createTime;
        private int price;
        private String awardInfo;
        private int provideAwardType;
        private int id;
        private String iconUrl;
        private String awardName;
        private int orgId;
        private int awardType;

        public int getCreateUserId() {
            return createUserId;
        }

        public void setCreateUserId(int createUserId) {
            this.createUserId = createUserId;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public String getAwardInfo() {
            return awardInfo;
        }

        public void setAwardInfo(String awardInfo) {
            this.awardInfo = awardInfo;
        }

        public int getProvideAwardType() {
            return provideAwardType;
        }

        public void setProvideAwardType(int provideAwardType) {
            this.provideAwardType = provideAwardType;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getIconUrl() {
            return iconUrl;
        }

        public void setIconUrl(String iconUrl) {
            this.iconUrl = iconUrl;
        }

        public String getAwardName() {
            return awardName;
        }

        public void setAwardName(String awardName) {
            this.awardName = awardName;
        }

        public int getOrgId() {
            return orgId;
        }

        public void setOrgId(int orgId) {
            this.orgId = orgId;
        }

        public int getAwardType() {
            return awardType;
        }

        public void setAwardType(int awardType) {
            this.awardType = awardType;
        }
    }
}
