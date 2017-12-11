package com.zhongke.content.entity;

import java.io.Serializable;

/**
 * 登录成功之后的userInfo
 * Created by llj on 2017/8/10.
 */

public class LoginInfoBean implements Serializable {


    /**
     * total : 0
     * records : 0
     * pageIndex : 0
     * resCode : 1
     * model : {"loginTime":"2017-08-10","IP":"183.39.196.16","id":1284,"userId":87,"token":"qx35a4wf1502357723351"}
     * resMsg : 登录成功
     * rows : {}
     */

    private int resCode;
    private ModelBean model;
    private String resMsg;

    public int getResCode() {
        return resCode;
    }

    public LoginInfoBean setResCode(int resCode) {
        this.resCode = resCode;
        return this;
    }

    public ModelBean getModel() {
        return model;
    }

    public LoginInfoBean setModel(ModelBean model) {
        this.model = model;
        return this;
    }

    public String getResMsg() {
        return resMsg;
    }

    public LoginInfoBean setResMsg(String resMsg) {
        this.resMsg = resMsg;
        return this;
    }

    public static class ModelBean implements Serializable {
        /**
         * loginTime : 2017-08-10
         * IP : 183.39.196.16
         * id : 1284
         * userId : 87
         * token : qx35a4wf1502357723351
         */

        private String loginTime;
        private String IP;
        private int id;
        private int userId;
        private String token;

        public String getLoginTime() {
            return loginTime;
        }

        public void setLoginTime(String loginTime) {
            this.loginTime = loginTime;
        }

        public String getIP() {
            return IP;
        }

        public void setIP(String IP) {
            this.IP = IP;
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

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}
