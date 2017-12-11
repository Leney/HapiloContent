package com.zhongke.content.entity;

import java.util.List;

/**
 * Created by hyx on 2017/12/1.
 * 礼物列表bean
 */

public class GiftListBean {
    /**
     * pageTotal : 1
     * pageIndex : 1
     * records : [{"createUserId":90,"createTime":"2017-11-14 13:41:27","price":100,"awardInfo":"奖品","provideAwardType":1,"id":6,"iconUrl":"http://zhongke.oss-cn-shenzhen.aliyuncs.com/res_pic/seCpCf56QQJiKtsncw8QNhCSz1510310897421.jpg","awardName":"玩具车","orgId":1,"awardType":1},{"createUserId":90,"createTime":"2017-11-10 18:47:32","price":0,"awardInfo":"奖杯","provideAwardType":1,"id":7,"iconUrl":"http://zhongke.oss-cn-shenzhen.aliyuncs.com/res_pic/JTGfe543ADzywF5NCDykd53331510310852382.jpg","awardName":"花环","orgId":1,"awardType":1},{"createUserId":90,"createTime":"2017-11-10 18:46:57","price":0,"awardInfo":"奖杯","provideAwardType":1,"id":9,"iconUrl":"http://zhongke.oss-cn-shenzhen.aliyuncs.com/res_pic/D2S8hyA72atZPCtfBMe4skBpd1510310817336.jpg","awardName":"汉堡","orgId":1,"awardType":1},{"createUserId":90,"createTime":"2017-11-10 18:28:33","price":0,"awardInfo":"奖杯","provideAwardType":1,"id":10,"iconUrl":"http://zhongke.oss-cn-shenzhen.aliyuncs.com/res_pic/J7WCH2pAd6A2AK4SHz6r2JzyS1510309713175.jpg","awardName":"薯条","orgId":1,"awardType":1},{"createUserId":90,"createTime":"2017-11-10 18:47:06","price":0,"awardInfo":"奖杯","provideAwardType":1,"id":11,"iconUrl":"http://zhongke.oss-cn-shenzhen.aliyuncs.com/res_pic/skm7hGYXGMcAXcw2PxRyYCKGN1510310825937.jpg","awardName":"可乐","orgId":1,"awardType":1},{"createUserId":90,"createTime":"2017-11-10 18:28:42","price":0,"awardInfo":"金币","provideAwardType":1,"id":12,"iconUrl":"http://zhongke.oss-cn-shenzhen.aliyuncs.com/res_pic/NSWH2jQYKkeA7eEYMaeEedFPe1510309722689.jpg","awardName":"金币","orgId":1,"awardType":2},{"createUserId":90,"createTime":"2017-11-10 18:47:39","price":0,"awardInfo":"奖杯","provideAwardType":1,"id":13,"iconUrl":"http://zhongke.oss-cn-shenzhen.aliyuncs.com/res_pic/tRHEwXPf3mY6ZHr6Z3jJbJWZt1510310860437.jpg","awardName":"沙拉","orgId":1,"awardType":1},{"createUserId":1,"createTime":"2017-11-13 10:08:37","price":56.6451,"awardInfo":"奖杯","provideAwardType":1,"id":14,"iconUrl":"http://zhongke.oss-cn-shenzhen.aliyuncs.com/res_pic/6zTccFaEkQQASNC266kEtiSPG1510538643356.jpg","awardName":"奖杯","orgId":1,"awardType":1},{"createUserId":1,"createTime":"2017-11-14 13:46:56","price":0,"awardInfo":"毛绒玩具","provideAwardType":1,"id":15,"iconUrl":"http://zhongke.oss-cn-shenzhen.aliyuncs.com/res_pic/SGYHpeHc7M85D4tjaHxkEKAPa1510538939582.jpg","awardName":"大熊猫","orgId":1,"awardType":3}]
     * recordTotal : 9
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
        private Float price;
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

        public Float getPrice() {
            return price;
        }

        public void setPrice(Float price) {
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

        @Override
        public String toString() {
            return "RecordsBean{" +
                    "createUserId=" + createUserId +
                    ", createTime='" + createTime + '\'' +
                    ", price=" + price +
                    ", awardInfo='" + awardInfo + '\'' +
                    ", provideAwardType=" + provideAwardType +
                    ", id=" + id +
                    ", iconUrl='" + iconUrl + '\'' +
                    ", awardName='" + awardName + '\'' +
                    ", orgId=" + orgId +
                    ", awardType=" + awardType +
                    '}';
        }
    }
}
