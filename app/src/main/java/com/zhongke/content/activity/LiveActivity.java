package com.zhongke.content.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.lqr.audio.AudioRecordManager;
import com.shuyu.gsyvideoplayer.listener.LockClickListener;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;
import com.shuyu.gsyvideoplayer.video.NormalGSYVideoPlayer;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;
import com.shuyu.gsyvideoplayer.video.base.GSYVideoPlayer;
import com.zhongke.account.AccountManagerClient;
import com.zhongke.account.ClientListener;
import com.zhongke.account.model.AccountInfo;
import com.zhongke.account.model.ZkLocalMessage;
import com.zhongke.content.BaseActivity;
import com.zhongke.content.Constance;
import com.zhongke.content.HPApplication;
import com.zhongke.content.R;
import com.zhongke.content.adapter.LivePeopleAdapter;
import com.zhongke.content.entity.LivePeopleBean;
import com.zhongke.content.entity.LivePeopleBean2;
import com.zhongke.content.entity.SessionContent_Entity;
import com.zhongke.content.fragment.ChatContentFragment;
import com.zhongke.content.im.ZkImManager;
import com.zhongke.content.listener.SampleListener;
import com.zhongke.content.retrofit.CommonException;
import com.zhongke.content.retrofit.ResponseResultListener;
import com.zhongke.content.utils.BitmapUtils;
import com.zhongke.content.utils.ConversionObjcetUtils;
import com.zhongke.content.utils.IntentBuilder;
import com.zhongke.content.utils.LogUtil;
import com.zhongke.content.utils.UIUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Subscription;

import static com.zhongke.content.activity.AnswerActivity.what_net_change;
import static com.zhongke.content.utils.ConversionObjcetUtils.createInstance;

/**
 * 活动直播界面
 * Created by llj on 2017/9/8.
 */
public class LiveActivity extends BaseActivity implements View.OnClickListener, ClientListener {
    private static final String TAG = "LiveActivity";
    private TextView title;
    /**
     * 直播播放器
     */
    private NormalGSYVideoPlayer player;
    /**
     * 观众列表
     */
    private RecyclerView mRecyclerView;
    /**
     * 聊天Fragment放置部分
     */
    private FrameLayout fragmentLay;

    private OrientationUtils orientationUtils;

    private boolean isPlay, isPause, isSamll;

    private LivePeopleAdapter peopleAdapter;

    private List<LivePeopleBean> mList;

    private List<LivePeopleBean2.RecordsBean> peopleRecordList;

    private ChatContentFragment chatContentFragment;

    /**
     * 活动是否开始了
     */
    private boolean isBegin;
    private String videoUrl;
    private String liveTitle;
    private int actionId;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_act_live;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        AccountManagerClient.getInstance().addSubscriberListener(this);
        isBegin = getIntent().getBooleanExtra("isBegin", false);
        videoUrl = getIntent().getStringExtra("videoUrl");
        LogUtil.e("videoUrl---" + videoUrl);
        if (videoUrl.isEmpty()) {
            videoUrl = "http://ivi.bupt.edu.cn/hls/cctv1hd.m3u8";
            Log.e("", "videoUrl 空");
        }
        liveTitle = getIntent().getStringExtra("liveTitle");
        if (liveTitle.isEmpty()) {
            liveTitle = "测试视频";
        }
        actionId = getIntent().getIntExtra("actionId", 0);
        LogUtil.e("actionId----" + actionId);
        if (actionId == 0) {
            LogUtil.e("actionId----为0");
        }
        initView();
        //initData();
        joinLived(actionId);
        getWatchActor(actionId);
        initVideo();
        initConfig();

        addChatContentFragment();
    }

    private ResponseMessageReceiver responseMessageReceiver;

    @Override
    protected void init() {
        this.responseMessageReceiver = new ResponseMessageReceiver();
        registerReceiver(responseMessageReceiver, IntentBuilder.builderIntentFilter(IntentBuilder.ACTION_SEND_MESSAGE));
    }

    @Override
    public void accountChange(AccountInfo accountInfo) {

    }

    @Override
    public void accountDelete() {

    }

    @Override
    public void chatMessageResponse(ZkLocalMessage zkLocalMessage) {
        /**
         * 自己发送的消息，发送成功或者失败
         */
        if (zkLocalMessage.getSendUserId().equals(sendUserId)) {
            doSendMsg(zkLocalMessage);
        } else {
            // 处理接收到消息的操作
            doReceiveMsg(zkLocalMessage);
        }
    }

    @Override
    public void extraMessageResponse(ZkLocalMessage zkLocalMessage) {

    }

    @Override
    public void netChangeResult(boolean b) {
        handler.obtainMessage(what_net_change, b).sendToTarget();
    }

    /**
     * 用于处理响应信息，例如：发送聊天的结果，接收到聊天信息
     */
    public class ResponseMessageReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {

            String messageId = intent.getStringExtra(IntentBuilder.KEY_MESSAGE_ID);
            Log.i(TAG, " 响应的messageId " + messageId);
            handler.obtainMessage(response_code, messageId).sendToTarget();
        }
    }

    /**
     * 添加聊天的fragment
     */
    private void addChatContentFragment() {

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        Bundle bundle = new Bundle();
        bundle.putString(ChatContentFragment.CHAT_OBJECT_ID, String.valueOf(actionId));
        bundle.putInt(ChatContentFragment.CHAT_OBJECT_TYPE, Constance.chat_group);
        this.chatContentFragment = ChatContentFragment.newInstance(bundle);
        fragmentTransaction.add(R.id.act_live_fragment_layout, this.chatContentFragment, ChatContentFragment.TAG).commitAllowingStateLoss();
    }


    private void initConfig() {
        // 初始化语音相关
        initAudioRecordManager();
    }

    /**
     * 初始化录音管理器
     */
    private void initAudioRecordManager() {
        //设置录音时长
        AudioRecordManager.getInstance(UIUtils.getContext()).setMaxVoiceDuration(Constance.DEFAULT_MAX_AUDIO_RECORD_TIME_SECOND);
        File audioDir;
        try {
            //该库内不对文件夹是否存在进行判断
            audioDir = BitmapUtils.getDiskFile(HPApplication.getInstance(), "audio");
            if (!audioDir.exists()) {
                audioDir.mkdirs();
            }
            //设置缓存路径
            AudioRecordManager.getInstance(UIUtils.getContext()).setAudioSavePath(audioDir.getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initView() {
        // 返回按钮
        findViewById(R.id.act_live_back_img).setOnClickListener(this);
        title = (TextView) findViewById(R.id.act_live_title);
        title.setText("消息");
        findViewById(R.id.act_live_search_img).setOnClickListener(this);
        player = (NormalGSYVideoPlayer) findViewById(R.id.act_live_player);
        fragmentLay = (FrameLayout) findViewById(R.id.act_live_fragment_layout);

        mRecyclerView = (RecyclerView) findViewById(R.id.act_live_people_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setItemAnimator(null);
        mList = new ArrayList<>();
        peopleRecordList = new ArrayList<>();
        peopleAdapter = new LivePeopleAdapter(this, peopleRecordList);
        mRecyclerView.setAdapter(peopleAdapter);
    }


    /**
     * 获取直播观看人数
     */
    private void getWatchActor(int actionId) {
        Map<String, Object> map = new HashMap<>();
        map.put("actionId", actionId + "");
        Subscription subscription = retrofitProvider.getWatchActor(map, new ResponseResultListener<LivePeopleBean2>() {
            @Override
            public void success(LivePeopleBean2 livePeopleBean2) {
                peopleRecordList.clear();
                peopleRecordList.addAll(livePeopleBean2.getRecords());
                peopleAdapter.notifyDataSetChanged();
                if (livePeopleBean2.getRecords().isEmpty()) {
                    UIUtils.showToast("没有数据");
                }
                LogUtil.e("success---" + livePeopleBean2.getRecords().toString());
            }

            @Override
            public void failure(CommonException e) {
                UIUtils.showToast("加载失败");
                LogUtil.e("failure---" + e.toString());
            }
        });
        compositeSubscription.add(subscription);
    }

    /**
     * 参加直播  点击进来就让他加入
     */
    private void joinLived(int actionId) {
        Map<String, Object> map = new HashMap<>();
        map.put("actionId", actionId + "");

        Subscription subscription = retrofitProvider.joinLive(map, new ResponseResultListener() {
            @Override
            public void success(Object o) {

                LogUtil.e("加入成功");
            }

            @Override
            public void failure(CommonException e) {
                LogUtil.e("加入失败");
            }
        });
        compositeSubscription.add(subscription);
    }

    /**
     * 初始化播放器
     */
    private void initVideo() {
//        String url = "http://ivi.bupt.edu.cn/hls/cctv1hd.m3u8";
        String url = videoUrl;
        LogUtil.e("videoUrl--" + videoUrl);
        LogUtil.e("liveTitle--" + liveTitle);
        player.setUp(url, false, null, liveTitle);
        //增加封面
        ImageView imageView = new ImageView(this);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setImageResource(R.mipmap.test_live);
        player.setThumbImageView(imageView);

        resolveNormalVideoUI();

        //外部辅助的旋转，帮助全屏
        orientationUtils = new OrientationUtils(this, player);
        //初始化不打开外部的旋转
        orientationUtils.setEnable(false);

        player.setIsTouchWiget(true);
        //关闭自动旋转
        player.setRotateViewAuto(false);
        player.setLockLand(false);
        player.setShowFullAnimation(false);
        player.setNeedLockFull(true);
        //detailPlayer.setOpenPreView(true);
        player.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //直接横屏
                orientationUtils.resolveByClick();

                //第一个true是否需要隐藏actionbar，第二个true是否需要隐藏statusbar
                player.startWindowFullscreen(LiveActivity.this, true, true);
            }
        });

        player.setStandardVideoAllCallBack(new SampleListener() {
            @Override
            public void onPrepared(String url, Object... objects) {
                super.onPrepared(url, objects);
                //开始播放了才能旋转和全屏
                orientationUtils.setEnable(true);
                isPlay = true;
            }

            @Override
            public void onAutoComplete(String url, Object... objects) {
                super.onAutoComplete(url, objects);
            }

            @Override
            public void onClickStartError(String url, Object... objects) {
                super.onClickStartError(url, objects);
            }

            @Override
            public void onQuitFullscreen(String url, Object... objects) {
                super.onQuitFullscreen(url, objects);
                if (orientationUtils != null) {
                    orientationUtils.backToProtVideo();
                    /**
                     * 设置为横屏
                     */
                    if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
                        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                    }
                }
            }
        });

        player.setLockClickListener(new LockClickListener() {
            @Override
            public void onClick(View view, boolean lock) {
                if (orientationUtils != null) {
                    //配合下方的onConfigurationChanged
                    orientationUtils.setEnable(!lock);
                }
            }
        });
    }

    private void resolveNormalVideoUI() {
        //增加title
        player.getTitleTextView().setVisibility(View.GONE);
        player.getTitleTextView().setText("测试视频");
        player.getBackButton().setVisibility(View.GONE);
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //如果旋转了就全屏
        if (isPlay && !isPause && !isSamll) {
            if (newConfig.orientation == ActivityInfo.SCREEN_ORIENTATION_USER) {
                if (!player.isIfCurrentIsFullscreen()) {
                    player.startWindowFullscreen(LiveActivity.this, true, true);
                }
            } else {
                //新版本isIfCurrentIsFullscreen的标志位内部提前设置了，所以不会和手动点击冲突
                if (player.isIfCurrentIsFullscreen()) {
                    StandardGSYVideoPlayer.backFromWindowFull(this);
                }
                if (orientationUtils != null) {
                    orientationUtils.setEnable(true);
                }
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        isPause = false;
    }

    @Override
    protected void onPause() {
        super.onPause();
        isPause = true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.act_live_back_img:
                // 返回按钮
                finish();
                break;
            case R.id.act_live_search_img:
                // 搜索按钮
                break;

            default:

                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (StandardGSYVideoPlayer.backFromWindowFull(this)) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
AccountManagerClient.getInstance().removeSubscriberListener(this);
        GSYVideoPlayer.releaseAllVideos();
        if (orientationUtils != null) {
            orientationUtils.releaseListener();
        }
        unregisterReceiver(responseMessageReceiver);
    }

    public static final String sendUserId = String.valueOf(ZkImManager.userId);

    private void doSendMsg(ZkLocalMessage zkLocalMessage) {
        SessionContent_Entity entity = ConversionObjcetUtils.createInstance(Constance.type_own, zkLocalMessage);
        if (chatContentFragment != null && chatContentFragment.isVisible()) {
            chatContentFragment.updateSendChatMessage(entity);
        }
    }

    private void doReceiveMsg(ZkLocalMessage zkLocalMessage) {
        SessionContent_Entity entity1 = createInstance(Constance.type_other, zkLocalMessage);
        if (chatContentFragment != null && chatContentFragment.isVisible()) {

            chatContentFragment.updateReceiverChatMessage(entity1);
        }
    }

    /**
     * 接受到的信息
     */
    public static final int response_code = 12;
    private final Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            switch (message.what) {
                case what_net_change:
                    boolean isConnected = (boolean) message.obj;
                    showNetError(isConnected);
                    break;
                default:
                    break;
            }

            return false;
        }
    });


    private void showNetError(boolean isConnected) {
        if (chatContentFragment != null) {
            chatContentFragment.setNetErrorShow(isConnected == true ? View.GONE : View.VISIBLE);
        }

    }

    public static void startActivity(Context context, boolean isBegin) {
        Intent intent = new Intent(context, LiveActivity.class);
        intent.putExtra("isBegin", isBegin);
        context.startActivity(intent);
    }

    public static void startActivity(Context context, boolean isBegin, int actionId, String videoUrl, String liveTitle) {
        Intent intent = new Intent(context, LiveActivity.class);
        intent.putExtra("isBegin", isBegin);
        intent.putExtra("actionId", actionId);
        intent.putExtra("videoUrl", videoUrl);
        intent.putExtra("liveTitle", liveTitle);
        context.startActivity(intent);
    }
}
