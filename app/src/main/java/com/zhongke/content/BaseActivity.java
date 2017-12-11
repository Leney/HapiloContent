package com.zhongke.content;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.zhongke.content.retrofit.RetrofitProvider;
import com.zhongke.content.sqlbrite.SQLBriteProvider;
import com.zhongke.content.utils.SystemBarUtils;

import rx.subscriptions.CompositeSubscription;

/**
 * Created by ${tanlei} on 2017/8/26.
 */

public abstract class BaseActivity extends AppCompatActivity {
    protected RetrofitProvider retrofitProvider;
    protected SQLBriteProvider sqlBriteProvider;
    protected CompositeSubscription compositeSubscription = null;
//    临时写死默认的token
//    protected String token = "7yts73rm1510121415981";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initModeConfig();
        setContentView(getLayoutId());
        setSystemUIChangeListener();
        init();
        initView(savedInstanceState);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        //焦点改变的时候，当Home键退出，重新从新进入等情况的处理。
        SystemBarUtils.setStickyStyle(getWindow());
    }

    /**
     * 初始化Model配置
     */
    void initModeConfig() {
        compositeSubscription = new CompositeSubscription();
        this.sqlBriteProvider = SQLBriteProvider.getInstance(this);
        this.retrofitProvider = RetrofitProvider.getInstance();
//        // 每次都去检测是否有登录信息
//        AccountStateManager.getInstance().init(this);
//        queryPersonMsg();
    }

    /**
     * 监听系统UI的显示，进行特殊处理
     */
    private void setSystemUIChangeListener() {
        getWindow().getDecorView().setOnSystemUiVisibilityChangeListener(visibility -> {
            //当系统UI显示的时候（例如输入法显示的时候），再次隐藏
            if ((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) {
                SystemBarUtils.setStickyStyle(getWindow());
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeSubscription.clear();
    }

    /**
     * @return
     */
    protected abstract int getLayoutId();

    protected abstract void initView(Bundle savedInstanceState);

    protected abstract void init();


}
