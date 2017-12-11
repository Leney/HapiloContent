package com.zhongke.content.okhttp;

import android.util.Log;

import com.zhongke.account.control.AccountStateManager;
import com.zhongke.content.retrofit.HttpConstance;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by ${xingen} on 2017/11/6.
 * 定义一个请求的拦截器：
 * <p>
 * 添加一些共同的header表头:
 * 例如:
 * 数据格式，token,cockie等
 */

public class HeaderInterceptor implements Interceptor {

    /**
     * 常见的表单form格式
     */
    private static final String CONTENT_TYPE = "application/x-www-form-urlencoded; charset=UTF-8";
    private final String HEADER_TOKEN = "token";

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request.Builder builder = chain.request().newBuilder()
                .addHeader("Content-Type", CONTENT_TYPE)

                // .addHeader("user-info", HpApplication.IMEI) // 添加IMEI
                .addHeader("User-Agent", "android")
                .addHeader("appId", HttpConstance.APP_ID);
//                .addHeader(HEADER_TOKEN,"7yts73rm1510121415981");
      /*          .addHeader("Accept", "application/vnd.yourapi.v1.full+json")*/
        // .addHeader("hpVersion", Utils.getVersion(mContext))//添加版本号
        // 拿到token
        Log.i("llj", "AccountStateManager.getInstance()== null ---->>>" + (AccountStateManager.getInstance() == null));
        Log.i("llj", "AccountStateManager.getInstance().getAccountInfo()== null ---->>>" + (AccountStateManager.getInstance().getAccountInfo() == null));
        String token = AccountStateManager.getInstance().getAccountInfo().getToken();
        Log.i("llj", "账户token------->>>" + token);
        builder.addHeader(HEADER_TOKEN, token);

        return chain.proceed(builder.build());
    }

//        /**
//     * 获取共享账户信息
//     */
//    public void queryAccountInfo(){
//        AccountManager.getInstance(getApplicationContext()).queryAccount(new Subscriber<AccountInfo>() {
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
//            public void onNext(AccountInfo accountInfo) {
//                Log.i("llj","获取到共享账户信息，token--->>>"+accountInfo.getToken());
//                Log.i("llj","获取到共享账户信息，name--->>>"+accountInfo.name);
//                mAccountInfo = accountInfo;
//            }
//        });
//    }
}
