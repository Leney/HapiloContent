package com.zhongke.content.entity;

import java.util.List;

/**
 * Created by ${xingen} on 2017/11/21.
 */

public class FamilyDetailBean {

    /**
     * familyGroup : {"gName":"gfdfsd","createUserId":1,"address":"ds","latitude":1,"hasBrother":1,"communicationStyle":2,"orgId":1,"gIconUrl":"http://zhongke.oss-cn-shenzhen.aliyuncs.com/res_pic/jnWfYAMxE7TrWpxeQHFr3XaNN1508729362137.jpg","tagList":"fds","gRule":" ","familyStructure":1,"gCoverUrl":"http://zhongke.oss-cn-shenzhen.aliyuncs.com/res_pic/Sef36t5EfDm6rCcNEzN3Ckpe61508729364445.png","id":58,"gInfo":"fdsafds","longitude":1.1}
     * memberList : [{"gTitleName":1,"noSpeech":1,"createTime":"2017-10-23 11:35:18","nickName":"小可","headImageUrl":"http://zhongke.oss-cn-shenzhen.aliyuncs.com/PictureFile/8cc405d996aa4a8b8196b0899ddbb14e/1510733658085.jpg","groupId":58,"warning":"","id":63,"userName":"testAdmin","userId":1}]
     */

    private FamilyGroupBean familyGroup;
    private List<MemberListBean> memberList;

    public FamilyGroupBean getFamilyGroup() {
        return familyGroup;
    }

    public void setFamilyGroup(FamilyGroupBean familyGroup) {
        this.familyGroup = familyGroup;
    }

    public List<MemberListBean> getMemberList() {
        return memberList;
    }

    public void setMemberList(List<MemberListBean> memberList) {
        this.memberList = memberList;
    }

    public static class FamilyGroupBean {
        /**
         * gName : gfdfsd
         * createUserId : 1
         * address : ds
         * latitude : 1
         * hasBrother : 1
         * communicationStyle : 2
         * orgId : 1
         * gIconUrl : http://zhongke.oss-cn-shenzhen.aliyuncs.com/res_pic/jnWfYAMxE7TrWpxeQHFr3XaNN1508729362137.jpg
         * tagList : fds
         * gRule :
         * familyStructure : 1
         * gCoverUrl : http://zhongke.oss-cn-shenzhen.aliyuncs.com/res_pic/Sef36t5EfDm6rCcNEzN3Ckpe61508729364445.png
         * id : 58
         * gInfo : fdsafds
         * longitude : 1.1
         */

        private String gName;
        private int createUserId;
        private String address;
        private int latitude;
        private int hasBrother;
        private int communicationStyle;
        private int orgId;
        private String gIconUrl;
        private String tagList;
        private String gRule;
        private int familyStructure;
        private String gCoverUrl;
        private int id;
        private String gInfo;
        private double longitude;

        public String getGName() {
            return gName;
        }

        public void setGName(String gName) {
            this.gName = gName;
        }

        public int getCreateUserId() {
            return createUserId;
        }

        public void setCreateUserId(int createUserId) {
            this.createUserId = createUserId;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public int getLatitude() {
            return latitude;
        }

        public void setLatitude(int latitude) {
            this.latitude = latitude;
        }

        public int getHasBrother() {
            return hasBrother;
        }

        public void setHasBrother(int hasBrother) {
            this.hasBrother = hasBrother;
        }

        public int getCommunicationStyle() {
            return communicationStyle;
        }

        public void setCommunicationStyle(int communicationStyle) {
            this.communicationStyle = communicationStyle;
        }

        public int getOrgId() {
            return orgId;
        }

        public void setOrgId(int orgId) {
            this.orgId = orgId;
        }

        public String getGIconUrl() {
            return gIconUrl;
        }

        public void setGIconUrl(String gIconUrl) {
            this.gIconUrl = gIconUrl;
        }

        public String getTagList() {
            return tagList;
        }

        public void setTagList(String tagList) {
            this.tagList = tagList;
        }

        public String getGRule() {
            return gRule;
        }

        public void setGRule(String gRule) {
            this.gRule = gRule;
        }

        public int getFamilyStructure() {
            return familyStructure;
        }

        public void setFamilyStructure(int familyStructure) {
            this.familyStructure = familyStructure;
        }

        public String getGCoverUrl() {
            return gCoverUrl;
        }

        public void setGCoverUrl(String gCoverUrl) {
            this.gCoverUrl = gCoverUrl;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getGInfo() {
            return gInfo;
        }

        public void setGInfo(String gInfo) {
            this.gInfo = gInfo;
        }

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }
    }

    public static class MemberListBean {
        /**
         * gTitleName : 1
         * noSpeech : 1
         * createTime : 2017-10-23 11:35:18
         * nickName : 小可
         * headImageUrl : http://zhongke.oss-cn-shenzhen.aliyuncs.com/PictureFile/8cc405d996aa4a8b8196b0899ddbb14e/1510733658085.jpg
         * groupId : 58
         * warning :
         * id : 63
         * userName : testAdmin
         * userId : 1
         */

        private int gTitleName;
        private int noSpeech;
        private String createTime;
        private String nickName;
        private String headImageUrl;
        private int groupId;
        private String warning;
        private int id;
        private String userName;
        private int userId;

        public int getGTitleName() {
            return gTitleName;
        }

        public void setGTitleName(int gTitleName) {
            this.gTitleName = gTitleName;
        }

        public int getNoSpeech() {
            return noSpeech;
        }

        public void setNoSpeech(int noSpeech) {
            this.noSpeech = noSpeech;
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

        public String getHeadImageUrl() {
            return headImageUrl;
        }

        public void setHeadImageUrl(String headImageUrl) {
            this.headImageUrl = headImageUrl;
        }

        public int getGroupId() {
            return groupId;
        }

        public void setGroupId(int groupId) {
            this.groupId = groupId;
        }

        public String getWarning() {
            return warning;
        }

        public void setWarning(String warning) {
            this.warning = warning;
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
    }
}
