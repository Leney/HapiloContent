package com.zhongke.content.entity;

import java.util.List;

/**
 * Created by dgg1 on 2017/10/31.
 * 活动列表实体类
 */

public class ActivityListBean2 {
    /**
     * pageTotal : 1
     * pageIndex : 1
     * records : [{"coverUrl":"http://zhongke.oss-cn-shenzhen.aliyuncs.com/res_pic/MJ26HKhhAW2bXkp7nraNywwWk1510215585565.gif","actionType":1,"address":"深圳","planCount":4,"joinCount":2,"aInfo":"活动描述","id":117,"beginTime":"2017-11-09 18:14:52","endTime":"2017-11-30 18:14:54","title":"美食之旅"},{"coverUrl":"http://zhongke.oss-cn-shenzhen.aliyuncs.com/res_pic/kwJyArP85SF8sTsCXPd5DrBrc1509971467902.jpg","actionType":3,"address":"深圳世界之窗","planCount":4,"joinCount":1,"aInfo":"成语接龙","id":119,"beginTime":"2017-11-09 20:31:18","endTime":"2019-02-22 20:31:20","title":"成语接龙"},{"coverUrl":"http://zhongke.oss-cn-shenzhen.aliyuncs.com/res_pic/kYFQpFytfxraTsstN4KXa8tCt1511231052705.jpg","actionType":1,"address":null,"planCount":4,"joinCount":1,"aInfo":"美女","id":145,"beginTime":"2017-11-20 10:24:26","endTime":"2019-11-21 10:24:28","title":"美女直播"}]
     * recordTotal : 3
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
         * coverUrl : http://zhongke.oss-cn-shenzhen.aliyuncs.com/res_pic/MJ26HKhhAW2bXkp7nraNywwWk1510215585565.gif
         * actionType : 1
         * address : 深圳
         * planCount : 4
         * joinCount : 2
         * aInfo : 活动描述
         * id : 117
         * beginTime : 2017-11-09 18:14:52
         * endTime : 2017-11-30 18:14:54
         * title : 美食之旅
         */

        private String coverUrl;
        private int actionType;
        private String address;
        private int planCount;
        private int joinCount;
        private String aInfo;
        private int id;
        private String beginTime;
        private String endTime;
        private String title;

        public String getCoverUrl() {
            return coverUrl;
        }

        public void setCoverUrl(String coverUrl) {
            this.coverUrl = coverUrl;
        }

        public int getActionType() {
            return actionType;
        }

        public void setActionType(int actionType) {
            this.actionType = actionType;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public int getPlanCount() {
            return planCount;
        }

        public void setPlanCount(int planCount) {
            this.planCount = planCount;
        }

        public int getJoinCount() {
            return joinCount;
        }

        public void setJoinCount(int joinCount) {
            this.joinCount = joinCount;
        }

        public String getAInfo() {
            return aInfo;
        }

        public void setAInfo(String aInfo) {
            this.aInfo = aInfo;
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

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        @Override
        public String toString() {
            return "RecordsBean{" +
                    "coverUrl='" + coverUrl + '\'' +
                    ", actionType=" + actionType +
                    ", address='" + address + '\'' +
                    ", planCount=" + planCount +
                    ", joinCount=" + joinCount +
                    ", aInfo='" + aInfo + '\'' +
                    ", id=" + id +
                    ", beginTime='" + beginTime + '\'' +
                    ", endTime='" + endTime + '\'' +
                    ", title='" + title + '\'' +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            RecordsBean that = (RecordsBean) o;

            if (actionType != that.actionType) return false;
            if (planCount != that.planCount) return false;
            if (joinCount != that.joinCount) return false;
            if (id != that.id) return false;
            if (coverUrl != null ? !coverUrl.equals(that.coverUrl) : that.coverUrl != null)
                return false;
            if (address != null ? !address.equals(that.address) : that.address != null)
                return false;
            if (aInfo != null ? !aInfo.equals(that.aInfo) : that.aInfo != null) return false;
            if (beginTime != null ? !beginTime.equals(that.beginTime) : that.beginTime != null)
                return false;
            if (endTime != null ? !endTime.equals(that.endTime) : that.endTime != null)
                return false;
            return title != null ? title.equals(that.title) : that.title == null;

        }

        @Override
        public int hashCode() {
            int result = coverUrl != null ? coverUrl.hashCode() : 0;
            result = 31 * result + actionType;
            result = 31 * result + (address != null ? address.hashCode() : 0);
            result = 31 * result + planCount;
            result = 31 * result + joinCount;
            result = 31 * result + (aInfo != null ? aInfo.hashCode() : 0);
            result = 31 * result + id;
            result = 31 * result + (beginTime != null ? beginTime.hashCode() : 0);
            result = 31 * result + (endTime != null ? endTime.hashCode() : 0);
            result = 31 * result + (title != null ? title.hashCode() : 0);
            return result;
        }
    }
}
