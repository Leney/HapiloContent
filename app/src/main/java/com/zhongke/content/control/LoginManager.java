package com.zhongke.content.control;

import com.zhongke.content.entity.LoginInfoBean;
import com.zhongke.content.entity.LoginUserInfo;
import com.zhongke.content.retrofit.RetrofitProvider;
import com.zhongke.content.utils.LogUtil;
import com.zhongke.content.utils.PreferencesUtils;
import com.zhongke.content.utils.Tools;

import rx.Subscriber;

/**
 * Created by llj on 2017/8/10.
 */

public class LoginManager {
    private static final String TAG = "LoginManager";
    /**
     * 登录信息KEY
     */
    public static final String KEY_LOGIN_INFO = "key_login_info";

    /**
     * 登录用户具体信息KEY
     */
    public static final String KEY_LOGIN_USER_INFO = "key_login_user_info";

    private static LoginManager instance;

    /**
     * 登录成功之后的信息
     */
    private LoginInfoBean mLoginInfoBean;

    /**
     * 登录成功之后获取到的用户详情信息
     */
    private LoginUserInfo mLoginUserInfo;

    private LoginManager() {
    }

    public static LoginManager getInstance() {
        if (instance == null) {
            synchronized (LoginManager.class) {
                if (instance == null) {
                    instance = new LoginManager();
                }
            }
        }
        return instance;
    }

    public void init() {
        LoginInfoBean loginInfoBean = PreferencesUtils.getObject(KEY_LOGIN_INFO);
        if (loginInfoBean != null && Tools.isLoginValid(loginInfoBean.getModel().getLoginTime(), 48f)) {
            // 上次有登录，并且登录还未过期
            LogUtil.i(TAG, "上次有登录，并且登录还未过期,userId----->>>" + loginInfoBean.getModel().getUserId() + "\nuserToken------>>>" + loginInfoBean.getModel().getToken());
            setLoginInfoBean(loginInfoBean);
            LoginUserInfo loginUserInfo = PreferencesUtils.getObject(KEY_LOGIN_USER_INFO);
            if (loginUserInfo != null) {
                setLoginUserInfo(loginUserInfo);
                // IM 初始化
                //ZkImManager.getInstance().init();
            }
        } else {
            // 未登录或者登录信息过期，重新登录
            LogUtil.i(TAG, "未登录或者登录信息过期，去登录");
            login("13512345678", "123456");
        }
    }

    /**
     * 登录
     *
     * @param account
     * @param pwd
     */
    private void login(String account, String pwd) {
//        RetrofitProvider.getInstance().login(new Subscriber<LoginInfoBean>() {
//            @Override
//            public void onCompleted() {
//
//            }
//
//            @Override
//            public void onError(Throwable e) {
//
//            }
//
//            @Override
//            public void onNext(LoginInfoBean loginInfoBean) {
//                if (loginInfoBean.getResCode() == 1) {
//                    LogUtil.i("llj", "登录成功,userId----->>>" + loginInfoBean.getModel().getUserId() + "\n" + loginInfoBean.getModel().getToken());
////                    // 登录成功
////                    setLoginInfoBean(loginInfoBean);
//                    // IM 初始化
//                    ZkImManager.getInstance().init();
//                } else {
//                    // 登录失败
//                    LogUtil.e("llj", "登录失败!!!");
//                    setLoginInfoBean(null);
//                }
//            }
//        }, account, pwd);

        RetrofitProvider.getInstance().login2(new Subscriber<LoginUserInfo>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(LoginUserInfo loginUserInfo) {
                if (loginUserInfo != null) {
                    // 获取用户信息成功
                    LogUtil.i(TAG, "登陆并获取用户信息成功返回！userId---->>>" + loginUserInfo.getModel().getId() + "\nuserName----->>>" + loginUserInfo.getModel().getUserName());
                    if (loginUserInfo.getResCode() == 1) {
                        PreferencesUtils.putObject(KEY_LOGIN_USER_INFO, loginUserInfo);
                        setLoginUserInfo(loginUserInfo);
                        // IM 初始化
                      //  ZkImManager.getInstance().init();
                    } else {
                        // 获取用户信息失败
                        // 将登录相关信息清掉
                        PreferencesUtils.putObject(KEY_LOGIN_INFO, null);
                        PreferencesUtils.putObject(KEY_LOGIN_USER_INFO, null);
                        setLoginInfoBean(null);
                        setLoginUserInfo(null);
                    }
                } else {
                    // 获取用户信息失败
                    // 将登录相关信息清掉
                    PreferencesUtils.putObject(KEY_LOGIN_INFO, null);
                    PreferencesUtils.putObject(KEY_LOGIN_USER_INFO, null);
                    setLoginInfoBean(null);
                    setLoginUserInfo(null);
                }
            }
        }, account, pwd);
    }

    /**
     * 当前是否有登录
     *
     * @return
     */
    public boolean isLogin() {
        return mLoginInfoBean != null;
    }

    public LoginInfoBean getLoginInfoBean() {
        return mLoginInfoBean;
    }

    public void setLoginInfoBean(LoginInfoBean loginInfoBean) {
        this.mLoginInfoBean = loginInfoBean;
    }

    public LoginUserInfo getLoginUserInfo() {
        return mLoginUserInfo;
    }

    public LoginManager setLoginUserInfo(LoginUserInfo loginUserInfo) {
        mLoginUserInfo = loginUserInfo;
        return this;
    }
}
