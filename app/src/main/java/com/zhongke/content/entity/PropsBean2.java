package com.zhongke.content.entity;

import java.util.List;

/**
 * Created by dgg1 on 2017/10/28.
 * 活动道具对象
 */

public class PropsBean2 {

    /**
     * pageTotal : 1
     * pageIndex : 1
     * records : [{"imageUrl":"http://zhongke.oss-cn-shenzhen.aliyuncs.com/res_pic/j3XjesbPnByQewkin38JHkj5X1510309422911.jpg","actionId":109,"id":70,"toolsName":"刀","info":"0.0\n"}]
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
         * imageUrl : http://zhongke.oss-cn-shenzhen.aliyuncs.com/res_pic/j3XjesbPnByQewkin38JHkj5X1510309422911.jpg
         * actionId : 109
         * id : 70
         * toolsName : 刀
         * info : 0.0

         */

        private String imageUrl;
        private int actionId;
        private int id;
        private String toolsName;
        private String info;

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
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

        public String getToolsName() {
            return toolsName;
        }

        public void setToolsName(String toolsName) {
            this.toolsName = toolsName;
        }

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }
    }
}
