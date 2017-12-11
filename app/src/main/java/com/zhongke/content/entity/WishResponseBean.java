package com.zhongke.content.entity;

/**
 * Created by Administrator on 2017/12/9.
 * 愿望bean
 */

public class WishResponseBean {

    /**
     * wish : {"coverUrl":null,"audioUrl":"http://zhongke.oss-cn-shenzhen.aliyuncs.com/res_pic/4GZ2cRWCifd4JH7sAsXiayRYc1508119007477.mp3","createTime":"2017-12-09","wishInfo":"音频许愿","wishName":"音频许愿","wishType":2,"id":8,"userId":90,"wishState":1}
     */

    private WishBean wish;

    public WishBean getWish() {
        return wish;
    }

    public void setWish(WishBean wish) {
        this.wish = wish;
    }

    public static class WishBean {
        /**
         * coverUrl : null
         * audioUrl : http://zhongke.oss-cn-shenzhen.aliyuncs.com/res_pic/4GZ2cRWCifd4JH7sAsXiayRYc1508119007477.mp3
         * createTime : 2017-12-09
         * wishInfo : 音频许愿
         * wishName : 音频许愿
         * wishType : 2
         * id : 8
         * userId : 90
         * wishState : 1
         */

        private Object coverUrl;
        private String audioUrl;
        private String createTime;
        private String wishInfo;
        private String wishName;
        private int wishType;
        private int id;
        private int userId;
        private int wishState;

        public Object getCoverUrl() {
            return coverUrl;
        }

        public void setCoverUrl(Object coverUrl) {
            this.coverUrl = coverUrl;
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
    }
}
