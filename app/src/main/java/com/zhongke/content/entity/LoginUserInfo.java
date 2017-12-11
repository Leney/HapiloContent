package com.zhongke.content.entity;

import java.io.Serializable;

/**
 * 当前用户登录后所获取的UserInfo
 * Created by llj on 2017/8/12.
 */

public class LoginUserInfo implements Serializable{
    /**
     * total : 0
     * records : 0
     * pageIndex : 0
     * resCode : 1
     * model : {"birthday":null,"nickName":"深圳小学管理员","headImageUrl":null,"roleId":3,"userPass":"E10ADC3949BA59ABBE56E057F20F883E","userPhone":"13512345678","sex":null,"majorList":null,"deviceCode":null,"registerType":null,"userName":"szxx","orgId":50,"tagList":null,"userState":1,"createTime":"2017-08-10 14:55:19","school":null,"id":87,"registerCode":null,"info":"管理机构"}
     * resMsg : 操作成功!
     * rows : {}
     */

    private int resCode;
    private ModelBean model;
    private String resMsg;

    public static class ModelBean implements Serializable {
        /**
         * birthday : null
         * nickName : 深圳小学管理员
         * headImageUrl : null
         * roleId : 3
         * userPass : E10ADC3949BA59ABBE56E057F20F883E
         * userPhone : 13512345678
         * sex : null
         * majorList : null
         * deviceCode : null
         * registerType : null
         * userName : szxx
         * orgId : 50
         * tagList : null
         * userState : 1
         * createTime : 2017-08-10 14:55:19
         * school : null
         * id : 87
         * registerCode : null
         * info : 管理机构
         */

        private Object birthday;
        private String nickName;
        private Object headImageUrl;
        private int roleId;
        private String userPass;
        private String userPhone;
        private Object sex;
        private Object majorList;
        private Object deviceCode;
        private Object registerType;
        private String userName;
        private int orgId;
        private Object tagList;
        private int userState;
        private String createTime;
        private Object school;
        private int id;
        private Object registerCode;
        private String info;

        public Object getBirthday() {
            return birthday;
        }

        public void setBirthday(Object birthday) {
            this.birthday = birthday;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public Object getHeadImageUrl() {
            return headImageUrl;
        }

        public void setHeadImageUrl(Object headImageUrl) {
            this.headImageUrl = headImageUrl;
        }

        public int getRoleId() {
            return roleId;
        }

        public void setRoleId(int roleId) {
            this.roleId = roleId;
        }

        public String getUserPass() {
            return userPass;
        }

        public void setUserPass(String userPass) {
            this.userPass = userPass;
        }

        public String getUserPhone() {
            return userPhone;
        }

        public void setUserPhone(String userPhone) {
            this.userPhone = userPhone;
        }

        public Object getSex() {
            return sex;
        }

        public void setSex(Object sex) {
            this.sex = sex;
        }

        public Object getMajorList() {
            return majorList;
        }

        public void setMajorList(Object majorList) {
            this.majorList = majorList;
        }

        public Object getDeviceCode() {
            return deviceCode;
        }

        public void setDeviceCode(Object deviceCode) {
            this.deviceCode = deviceCode;
        }

        public Object getRegisterType() {
            return registerType;
        }

        public void setRegisterType(Object registerType) {
            this.registerType = registerType;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public int getOrgId() {
            return orgId;
        }

        public void setOrgId(int orgId) {
            this.orgId = orgId;
        }

        public Object getTagList() {
            return tagList;
        }

        public void setTagList(Object tagList) {
            this.tagList = tagList;
        }

        public int getUserState() {
            return userState;
        }

        public void setUserState(int userState) {
            this.userState = userState;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public Object getSchool() {
            return school;
        }

        public void setSchool(Object school) {
            this.school = school;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public Object getRegisterCode() {
            return registerCode;
        }

        public void setRegisterCode(Object registerCode) {
            this.registerCode = registerCode;
        }

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }
    }

    public int getResCode() {
        return resCode;
    }

    public LoginUserInfo setResCode(int resCode) {
        this.resCode = resCode;
        return this;
    }

    public ModelBean getModel() {
        return model;
    }

    public LoginUserInfo setModel(ModelBean model) {
        this.model = model;
        return this;
    }

    public String getResMsg() {
        return resMsg;
    }

    public LoginUserInfo setResMsg(String resMsg) {
        this.resMsg = resMsg;
        return this;
    }
}
