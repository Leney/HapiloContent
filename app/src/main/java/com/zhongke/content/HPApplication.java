package com.zhongke.content;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.multidex.MultiDex;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.lqr.emoji.IImageLoader;
import com.lqr.emoji.LQREmotionKit;
import com.zhongke.account.control.AccountStateManager;
import com.zhongke.content.glide.GlideApp;
import com.zhongke.content.im.ZkImManager;
import com.zhongke.content.utils.SystemUtil;


/**
 * Created by Karma on 2017/7/29.
 * 类描述：
 */

public class HPApplication extends Application {
    private static final String TAG = "HPApplication";
    private static HPApplication instance;
    //以下属性应用于整个应用程序，合理利用资源，减少资源浪费
    private static Context mContext;//上下文
    private static Thread mMainThread;//主线程
    private static long mMainThreadId;//主线程id
    private static Looper mMainLooper;//循环队列
    private static Handler mHandler;//主线程Handler

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        //对全局属性赋值
        mContext = getApplicationContext();
        mMainThread = Thread.currentThread();
        mMainThreadId = android.os.Process.myTid();
        mHandler = new Handler();

        Log.i("llj","初始化账户信息系统！！！！");
        AccountStateManager.getInstance().init(getApplicationContext());

      initIMConfig();

        initEmojiConfig();

        // 初始化登录信息
     //   LoginManager.getInstance().init();


    }

    /**
     * 表情库的初始化
     */
    private void initEmojiConfig() {
        LQREmotionKit.init(this, new IImageLoader() {
            @Override
            public void displayImage(Context context, String path, ImageView imageView) {
                GlideApp.with(context).load(path).centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL).into(imageView);
            }
        });
    }

    /**
     *  初始化公司IM框架配置
     */
    private void initIMConfig() {
      ZkImManager.getInstance().init();
    }



    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        // 突破65535方法数限制
        MultiDex.install(base);
    }

    /**
     * 单例模式
     *
     * @return
     */

    public synchronized static HPApplication getInstance() {
        return instance;
    }

    public static Context getContext() {
        return mContext;
    }

    public static void setContext(Context mContext) {
        HPApplication.mContext = mContext;
    }

    public static Thread getMainThread() {
        return mMainThread;
    }

    public static void setMainThread(Thread mMainThread) {
        HPApplication.mMainThread = mMainThread;
    }

    public static long getMainThreadId() {
        return mMainThreadId;
    }

    public static void setMainThreadId(long mMainThreadId) {
        HPApplication.mMainThreadId = mMainThreadId;
    }

    public static Looper getMainThreadLooper() {
        return mMainLooper;
    }

    public static void setMainThreadLooper(Looper mMainLooper) {
        HPApplication.mMainLooper = mMainLooper;
    }

    public static Handler getMainHandler() {
        return mHandler;
    }

    public static void setMainHandler(Handler mHandler) {
        HPApplication.mHandler = mHandler;
    }



    /**
     *
     * @return
     */
    private boolean inMainProcess() {
        String packageName = getPackageName();
        String processName = SystemUtil.getProcessName(this);
        return packageName.equals(processName);
    }

}
