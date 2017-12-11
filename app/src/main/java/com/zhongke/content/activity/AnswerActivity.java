package com.zhongke.content.activity;


import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.lqr.adapter.LQRAdapterForRecyclerView;
import com.lqr.audio.AudioRecordManager;
import com.lqr.recyclerview.LQRRecyclerView;
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
import com.zhongke.content.control.AnswerPresent;
import com.zhongke.content.entity.ContestantsBean;
import com.zhongke.content.entity.ExaminationQuestion;
import com.zhongke.content.entity.RoomInfoBean2;
import com.zhongke.content.entity.SessionContent_Entity;
import com.zhongke.content.entity.WatchPeopleBean;
import com.zhongke.content.fragment.AnswerFragment;
import com.zhongke.content.fragment.ChatContentFragment;
import com.zhongke.content.im.ZkImManager;
import com.zhongke.content.im.extra.ExtraMessage;
import com.zhongke.content.im.extra.message.ActivityStart;
import com.zhongke.content.im.extra.message.JoinActivity;
import com.zhongke.content.listener.SampleListener;
import com.zhongke.content.retrofit.CommonException;
import com.zhongke.content.retrofit.ResponseResultListener;
import com.zhongke.content.retrofit.RetrofitProvider;
import com.zhongke.content.utils.BitmapUtils;
import com.zhongke.content.utils.ConversionObjcetUtils;
import com.zhongke.content.utils.LogUtil;
import com.zhongke.content.utils.UIUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import rx.Subscription;

import static com.zhongke.content.utils.ConversionObjcetUtils.createInstance;

/**
 * Created by Karma on 2017/8/26.
 * 类描述：抢答页面
 */

public class AnswerActivity extends BaseActivity implements BGARefreshLayout.BGARefreshLayoutDelegate, ClientListener {
    private static final String TAG = "AnswerActivity";
    private NormalGSYVideoPlayer webPlayer;
    private LQRRecyclerView recyclerView;
    private BGARefreshLayout mRefreshLayout;
    private FrameLayout mFrameLayout;
    private RelativeLayout mBack;
    private RoomInfoBean2.RecordsBean roomInfo;
    private OrientationUtils orientationUtils;
    private boolean isPlay;
    private boolean isPause;
    private boolean isSmall;
    private AnswerPresent present;
    private FragmentManager fragmentManager;
    private AnswerFragment mAnswerFragment;
    private ChatContentFragment chatContentFragment;
    private LQRAdapterForRecyclerView<WatchPeopleBean> mAdapter;
    private List<WatchPeopleBean> mData;
    private List<WatchPeopleBean.RecordsBean> watchList;
    private int roomId;
    private int planCount;
    private String beginTime;
    private String endTime;
    private String actionName;
    private String groupId;
    /**
     * 活动的Id
     */
    public static final String ACTIVITY_ID = "activityId";
    private String activityId;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_answer;
    }

    private Gson gson;

    @Override
    protected void initView(Bundle savedInstanceState) {
        receiverIntent();
        initIMConfig();
        if (TextUtils.isEmpty(activityId)) {
            getExaminationQuestion();
            initView();
        } else {
            //加入
            join();
            //获取观战人员
            getWatchPeopleList(String.valueOf(roomId));
            initView();
            initConfig();
            loadFragment();
        }
    }

    private void initIMConfig() {
        this.gson = new Gson();
        groupId = "Answer_" + activityId;
        ZkImManager.getInstance().joinGroup(groupId);
        AccountManagerClient.getInstance().addSubscriberListener(this);
    }

    private Map<String, Object> getParams() {
        Map<String, Object> params = new HashMap<>();
        params.put("actionId", activityId);
        return params;
    }

    /**
     * 考题实体
     */
    private ExaminationQuestion examinationQuestionBean;

    public ExaminationQuestion getExaminationQuestionBean() {
        return examinationQuestionBean;
    }

    /**
     * 获取考题，然后获取参赛人员
     */
    private void getExaminationQuestion() {
        RetrofitProvider.getInstance().getExaminationQuestion(getParams(), new ResponseResultListener<ExaminationQuestion>() {
            @Override
            public void success(ExaminationQuestion examinationQuestion) {
                examinationQuestionBean = examinationQuestion;
                //若是第四个参赛者到位，则通知全部人员开始竞赛
                handler.obtainMessage(what_start_contestants_or_notify_join_group, examinationQuestion.isStart()).sendToTarget();
                getContestants();
            }

            @Override
            public void failure(CommonException e) {

            }
        });
    }

    /**
     * 提交题目答案
     */
    public void commitAnswer() {
        Map<String, Object> params = new HashMap<>();
        params.put("actionId", activityId);
        params.put("answerId", "");
        params.put("paperId", "");
        params.put("questionId", "");
        params.put("isRight", "");
        Subscription subscription = retrofitProvider.commitAnswer(params, new ResponseResultListener() {
            @Override
            public void success(Object o) {

            }

            @Override
            public void failure(CommonException e) {

            }
        });
        this.compositeSubscription.add(subscription);
    }

    private ContestantsBean contestantsBeanList;

    /**
     * 获取参赛人员和观战人员的列表
     */
    private void getContestants() {
        Subscription subscription = RetrofitProvider.getInstance().getContestants(getParams(), new ResponseResultListener<ContestantsBean>() {
            @Override
            public void success(ContestantsBean contestantsBean) {
                contestantsBeanList = contestantsBean;
                handler.obtainMessage(what_load_user_list).sendToTarget();
            }

            @Override
            public void failure(CommonException e) {
            }
        });
        compositeSubscription.add(subscription);
    }

    /**
     * 网络变化的what
     */
    public static final int what_net_change = 11;
    /**
     * 开始竞赛或者通知别人有人加入
     */
    public static final int what_start_contestants_or_notify_join_group = 12;
    /**
     * 加载人员列表
     */
    public static final int what_load_user_list = 13;
    /**
     * 处理额外信息
     */
    public static final int what_receiver_extra_message = 14;
    /**
     * 处理发送额外信息的结果
     */
    public static final int what_send_extra_message_result = 15;

    private Handler handler = new Handler(message -> {
        switch (message.what) {
            case what_net_change:
                boolean isConnected = (boolean) message.obj;
                showNetError(isConnected);
                break;
            case what_start_contestants_or_notify_join_group: {
                boolean isReady = (Boolean) message.obj;
                //已经参赛人员到位,包含通知自己
                if (isReady) {
                    ExtraMessage<ActivityStart> extraMessage = new ExtraMessage<>();
                    extraMessage.code = ExtraMessage.activity_start;
                    ActivityStart activityStart = new ActivityStart();
                    activityStart.activityId = activityId;
                    extraMessage.message = activityStart;
                    ZkImManager.getInstance().sendExtraMessageToGroup(groupId, extraMessage);
                } //告诉别人自己进来了，不通知自己。
                else {
                    ExtraMessage<JoinActivity> extraMessage = new ExtraMessage<>();
                    extraMessage.code = ExtraMessage.join_activity;
                    JoinActivity joinActivity = new JoinActivity();
                    joinActivity.activityId = activityId;
                    joinActivity.joinUserId = ZkImManager.userId;
                    extraMessage.message = joinActivity;
                    ZkImManager.getInstance().sendExtraMessageToGroup(groupId, extraMessage);
                }
            }
            break;
            //加载人员列表的处理操作
            case what_load_user_list: {
                present.loadLookUserList(contestantsBeanList);
            }
            break;
            case what_receiver_extra_message: {
                ZkLocalMessage zkLocalMessage = (ZkLocalMessage) message.obj;
                try {
                    ExtraMessage extraMessage = gson.fromJson(zkLocalMessage.getChatContent(), ExtraMessage.class);
                    switch (extraMessage.code) {
                        //开始竞赛，和其他成员同步开始
                        case ExtraMessage.activity_start:
                            addAnswerFragment();
                            break;
                        //有其他人员加入进来，需要重新刷新列表
                        case ExtraMessage.join_activity:
                            getContestants();
                            break;
                        default:
                            break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            break;
            case what_send_extra_message_result: {
                ZkLocalMessage zkLocalMessage = (ZkLocalMessage) message.obj;
                try {
                    ExtraMessage extraMessage = gson.fromJson(zkLocalMessage.getChatContent(), ExtraMessage.class);
                    switch (extraMessage.code) {
                        case ExtraMessage.activity_start:
                            //开始竞赛，和其他成员同步开始
                            if (zkLocalMessage.getRetCode() == ZkLocalMessage.SEND_MSG_SECCUSS) {
                                addAnswerFragment();
                            }
                            break;
                        case ExtraMessage.join_activity:
                            //未通知成功，需要重新通知其他人员，自己加入进来
                            if (zkLocalMessage.getRetCode() == ZkLocalMessage.SEND_MSG_FAILED) {
                                this.handler.obtainMessage(what_start_contestants_or_notify_join_group, false).sendToTarget();
                            }
                            break;
                        default:
                            break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            break;
            default:

                break;
        }
        return false;
    });

    /**
     * 加载试卷列表
     */
    public void loadPaperList() {
        //  mAnswerFragment.loadParticipants(contestantsBeanList);
    }

    public static final String sendUserId = String.valueOf(ZkImManager.userId);

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
        try {
            //自己发送额外指定的发送结果
            if (zkLocalMessage.getSendUserId().equals(sendUserId)) {
                handler.obtainMessage(what_send_extra_message_result, zkLocalMessage).sendToTarget();
            }//处理别的客户端发送的额外指令
            else {
                handler.obtainMessage(what_receiver_extra_message, zkLocalMessage).sendToTarget();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void netChangeResult(boolean connectResult) {
        handler.obtainMessage(what_net_change, connectResult).sendToTarget();
    }

    @Override
    protected void init() {

    }

    /**
     * 根据房间人数，来处理加载哪个Fragment
     */
    private void loadFragment() {
        addAnswerFragment();
//        if (roomInfo.getJoinCount() >= roomInfo.getPlanCount()) {
//            // 已经满员
//            addAnswerFragment();
//        } else {
//            // 未满员
//            addChatContentFragment();
//        }
    }

    private void initView() {
        webPlayer = (NormalGSYVideoPlayer) findViewById(R.id.web_player);
        recyclerView = (LQRRecyclerView) findViewById(R.id.rvMsg);
        mRefreshLayout = (BGARefreshLayout) findViewById(R.id.refreshLayout);
        mFrameLayout = (FrameLayout) findViewById(R.id.frame_layout);
        mBack = (RelativeLayout) findViewById(R.id.back_finsh);
        mBack.setOnClickListener(view -> {
            quitRoom(roomId);
            finish();
        });
        watchList = new ArrayList<>();
        present = new AnswerPresent(this, recyclerView, watchList);
        initVideo();
    }

    /**
     * 加载抢答试卷的界面
     */
    private void addAnswerFragment() {
        mAnswerFragment = AnswerFragment.newInstance(activityId);
        fragmentManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (chatContentFragment != null) {
            transaction.hide(chatContentFragment);
        }
        transaction.add(R.id.frame_layout, mAnswerFragment, "answer");
        transaction.commitAllowingStateLoss();
    }

    private void receiverIntent() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null && bundle.containsKey(ACTIVITY_ID)) {
            activityId = bundle.getString(ACTIVITY_ID);
        } else {
            roomInfo = (RoomInfoBean2.RecordsBean) getIntent().getSerializableExtra("room");
            if (roomInfo == null) {
                finish();
                return;
            }
            roomId = roomInfo.getId();
            activityId = roomId + "";
            planCount = roomInfo.getPlanCount();
            beginTime = roomInfo.getBeginTime();
            endTime = roomInfo.getEndTime();
            actionName = roomInfo.getTitle();
        }
    }

    private void join() {
        joinRoom(roomId + "", planCount + "", beginTime, endTime, actionName);
    }


    private void initVideo() {
        String url = "http://ivi.bupt.edu.cn/hls/cctv1hd.m3u8";
        webPlayer.setUp(url, false, null, "测试视频");
        //增加封面
        ImageView imageView = new ImageView(this);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setImageResource(R.mipmap.test_live);
        webPlayer.setThumbImageView(imageView);
        resolveNormalVideoUI();
        //外部辅助的旋转，帮助全屏
        orientationUtils = new OrientationUtils(this, webPlayer);
        //初始化不打开外部的旋转
        orientationUtils.setEnable(false);
        webPlayer.setIsTouchWiget(true);
        //关闭自动旋转
        webPlayer.setRotateViewAuto(false);
        webPlayer.setLockLand(false);
        webPlayer.setShowFullAnimation(false);
        webPlayer.setNeedLockFull(true);
        webPlayer.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //直接横屏
                orientationUtils.resolveByClick();
                //第一个true是否需要隐藏actionbar，第二个true是否需要隐藏statusbar
                webPlayer.startWindowFullscreen(AnswerActivity.this, true, true);
            }
        });
        webPlayer.setStandardVideoAllCallBack(new SampleListener() {
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

        webPlayer.setLockClickListener(new LockClickListener() {
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
        webPlayer.getTitleTextView().setVisibility(View.GONE);
        webPlayer.getTitleTextView().setText("测试视频");
        webPlayer.getBackButton().setVisibility(View.GONE);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //如果旋转了就全屏
        if (isPlay && !isPause && !isSmall) {
            if (newConfig.orientation == ActivityInfo.SCREEN_ORIENTATION_USER) {
                if (!webPlayer.isIfCurrentIsFullscreen()) {
                    webPlayer.startWindowFullscreen(AnswerActivity.this, true, true);
                }
            } else {
                //新版本isIfCurrentIsFullscreen的标志位内部提前设置了，所以不会和手动点击冲突
                if (webPlayer.isIfCurrentIsFullscreen()) {
                    StandardGSYVideoPlayer.backFromWindowFull(this);
                }
                if (orientationUtils != null) {
                    orientationUtils.setEnable(true);
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (StandardGSYVideoPlayer.backFromWindowFull(this)) {
            return;
        }
        super.onBackPressed();
        if (orientationUtils != null) {
            orientationUtils.backToProtVideo();
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
    protected void onDestroy() {
        super.onDestroy();
        AccountManagerClient.getInstance().removeSubscriberListener(this);
        GSYVideoPlayer.releaseAllVideos();
        if (orientationUtils != null) {
            orientationUtils.releaseListener();
        }
    }


    /**
     * 获取观战人员列表
     *
     * @param roomId
     */
    private void getWatchPeopleList(String roomId) {
        Map<String, Object> map = new HashMap<>();
        map.put("roomId", roomId);
        Subscription subscription = retrofitProvider.watchPeopleList(map, new ResponseResultListener<WatchPeopleBean>() {
            @Override
            public void success(WatchPeopleBean watchPeopleBean) {
                if (TextUtils.isEmpty(activityId)) {
                    LogUtil.e("获取观战人员 success----" + watchPeopleBean.getRecords().toString());
                    present.setDataList(watchPeopleBean.getRecords());
                } else {
                }
            }

            @Override
            public void failure(CommonException e) {
                LogUtil.e("failure----" + e.toString());
            }
        });
        compositeSubscription.add(subscription);
    }

    /**
     * 加入房间
     */
    private void joinRoom(String actionId, String planCount, String beginTime, String endTime, String actionName) {
        Map<String, String> map = new HashMap<>();
        map.put("actionId", actionId);
        map.put("planCount", planCount);
        map.put("beginTime", beginTime);
        map.put("endTime", endTime);
        map.put("actionName", actionName);
        Subscription subscription = retrofitProvider.joinRoom(map, new ResponseResultListener() {
            @Override
            public void success(Object o) {
                LogUtil.e("加入 success");
            }

            @Override
            public void failure(CommonException e) {
                LogUtil.e("failure---" + e.getErrorMsg());
            }
        });
        compositeSubscription.add(subscription);
    }

    /**
     * 退出房间
     *
     * @param actionId 房间id
     */
    private void quitRoom(int actionId) {
        Map<String, Object> map = new HashMap<>();
        map.put("actionId", actionId);
        Subscription subscription = retrofitProvider.quitRoom(map, new ResponseResultListener() {
            @Override
            public void success(Object o) {
                LogUtil.e("quitRoom success---");
            }

            @Override
            public void failure(CommonException e) {
                LogUtil.e("quitRoom failed---");
                UIUtils.showToast(e.getErrorMsg());
            }
        });
        compositeSubscription.add(subscription);
    }

    /**
     * 下拉刷新
     *
     * @param refreshLayout
     */
    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {

    }

    /**
     * 加载更多
     *
     * @param refreshLayout
     * @return
     */
    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        return false;
    }

    public static void startActivity(Context context, RoomInfoBean2.RecordsBean room) {
        Intent intent = new Intent(context, AnswerActivity.class);
        intent.putExtra("room", room);
        context.startActivity(intent);
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

    /**
     * 添加聊天的fragment
     */
    private void addChatContentFragment() {
        List<Fragment> fragmentList = getSupportFragmentManager().getFragments();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        for (Fragment fragment : fragmentList) {
            if (fragment != null) {
                fragmentTransaction.hide(fragment);
            }
        }
        Bundle bundle = new Bundle();
        bundle.putString(ChatContentFragment.CHAT_OBJECT_ID, activityId);
        bundle.putInt(ChatContentFragment.CHAT_OBJECT_TYPE, Constance.chat_group);
        bundle.putInt("roomId", roomId);
        this.chatContentFragment = ChatContentFragment.newInstance(bundle);
        fragmentTransaction.add(R.id.frame_layout, this.chatContentFragment, ChatContentFragment.TAG).commitAllowingStateLoss();
    }

    /**
     * 发送信息
     *
     * @param zkLocalMessage
     */
    private void doSendMsg(ZkLocalMessage zkLocalMessage) {
        SessionContent_Entity entity = ConversionObjcetUtils.createInstance(Constance.type_own, zkLocalMessage);
        if (chatContentFragment != null && chatContentFragment.isVisible()) {
            chatContentFragment.updateSendChatMessage(entity);
        }
    }

    /**
     * 处理接受的信息
     *
     * @param zkLocalMessage
     */
    private void doReceiveMsg(ZkLocalMessage zkLocalMessage) {
        SessionContent_Entity entity1 = createInstance(Constance.type_other, zkLocalMessage);
        if (chatContentFragment != null && chatContentFragment.isVisible()) {
            chatContentFragment.updateReceiverChatMessage(entity1);
        }
    }

    /**
     * 显示网络异常信息
     *
     * @param isConnected
     */
    private void showNetError(boolean isConnected) {
        if (chatContentFragment != null) {
            chatContentFragment.setNetErrorShow(isConnected == true ? View.GONE : View.VISIBLE);
        }
    }
}
