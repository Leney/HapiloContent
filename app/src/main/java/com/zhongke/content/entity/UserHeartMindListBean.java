package com.zhongke.content.entity;

import java.util.List;

/**
 * Created by ${xinen} on 2017/11/22.
 */

public class UserHeartMindListBean {


    private List<CommentListBean> commentList;
    private List<HeartMindListBean> heartMindList;

    public List<CommentListBean> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<CommentListBean> commentList) {
        this.commentList = commentList;
    }

    public List<HeartMindListBean> getHeartMindList() {
        return heartMindList;
    }

    public void setHeartMindList(List<HeartMindListBean> heartMindList) {
        this.heartMindList = heartMindList;
    }



    public static class CommentListBean {
        /**
         * audioUrl : http://zhongke.oss-cn-shenzhen.aliyuncs.com/res_pic/eCBQ5PKxhayfswEzRGRk7nzQP1508146747376.mp3
         * createTime : 2017-11-22 18:15:21
         * nickName :
         * fTitleName : null
         * fullName : null
         * heartMindId : 6
         * id : 14
         * commentContent : 好
         * userName : 54SB
         * userId : 131
         * hmCommentType : 2
         */

        private String audioUrl;
        private String createTime;
        private String nickName;
        private Object fTitleName;
        private Object fullName;
        private int heartMindId;
        private int id;
        private String commentContent;
        private String userName;
        private int userId;
        private int hmCommentType;

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

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public Object getFTitleName() {
            return fTitleName;
        }

        public void setFTitleName(Object fTitleName) {
            this.fTitleName = fTitleName;
        }

        public Object getFullName() {
            return fullName;
        }

        public void setFullName(Object fullName) {
            this.fullName = fullName;
        }

        public int getHeartMindId() {
            return heartMindId;
        }

        public void setHeartMindId(int heartMindId) {
            this.heartMindId = heartMindId;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getCommentContent() {
            return commentContent;
        }

        public void setCommentContent(String commentContent) {
            this.commentContent = commentContent;
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

        public int getHmCommentType() {
            return hmCommentType;
        }

        public void setHmCommentType(int hmCommentType) {
            this.hmCommentType = hmCommentType;
        }
    }

    public static class HeartMindListBean {
        /**
         * audioUrl :
         * mindImageList : http://zhongke.oss-cn-shenzhen.aliyuncs.com/res_pic/XmemQyD2Fd3NaMSrHRRPFWb7y1508989285126.gif,http://zhongke.oss-cn-shenzhen.aliyuncs.com/res_pic/iZPKD7WFYyZ8G652EsfE6PBe81508989296708.png
         * createTime : 2017-11-22 18:08:23
         * fTitleName : 2
         * id : 6
         * mindContent : 参加完这个活动，就买玩具车
         * userId : 126
         */
        private String audioUrl;
        private String mindImageList;
        private String createTime;
        private int fTitleName;
        private int id;
        private String mindContent;
        private int userId;
        public String getAudioUrl() {
            return audioUrl;
        }

        public void setAudioUrl(String audioUrl) {
            this.audioUrl = audioUrl;
        }

        public String getMindImageList() {
            return mindImageList;
        }

        public void setMindImageList(String mindImageList) {
            this.mindImageList = mindImageList;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public int getFTitleName() {
            return fTitleName;
        }

        public void setFTitleName(int fTitleName) {
            this.fTitleName = fTitleName;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getMindContent() {
            return mindContent;
        }

        public void setMindContent(String mindContent) {
            this.mindContent = mindContent;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }
    }
    /**
     * 组合数据，一个心声对应的一个评论
     */
    public static class  UserHeartBean{
        public  List<CommentListBean> commentListBeanList;
        public HeartMindListBean heartMindListBean;
        // 组装好后的数据
        /**
         * 图片数据
         */
        public List<String> imageList;
        /**
         * 点赞数据
         */
        public List<String> likeList;
        /**
         * 文字评论
         */
        public List<CommentListBean> commentList;
        /**
         * 是否点赞过
         */
        public boolean isLike;

    }
}
