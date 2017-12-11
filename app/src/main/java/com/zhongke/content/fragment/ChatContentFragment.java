package com.zhongke.content.fragment;

import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lqr.audio.AudioPlayManager;
import com.lqr.audio.AudioRecordManager;
import com.lqr.audio.IAudioPlayUrlListener;
import com.lqr.audio.IAudioRecordListener;
import com.lqr.emoji.EmotionLayout;
import com.zhongke.account.ResultListener;
import com.zhongke.account.model.ZkLocalMessage;
import com.zhongke.content.BaseFragment;
import com.zhongke.content.Constance;
import com.zhongke.content.HPApplication;
import com.zhongke.content.R;
import com.zhongke.content.activity.PictureTouchActivity;
import com.zhongke.content.adapter.ContentSessionAdapter;
import com.zhongke.content.entity.PartInBean2;
import com.zhongke.content.entity.SessionContent_Entity;
import com.zhongke.content.im.ZkImManager;
import com.zhongke.content.listener.CallBackListener;
import com.zhongke.content.oss.ImageOSSClient;
import com.zhongke.content.recyclerview.BaseRecyclerView;
import com.zhongke.content.retrofit.CommonException;
import com.zhongke.content.retrofit.ResponseResultListener;
import com.zhongke.content.utils.IntentBuilder;
import com.zhongke.content.utils.LogUtil;
import com.zhongke.content.utils.NetConnectedUtils;
import com.zhongke.content.utils.StringUtil;
import com.zhongke.content.utils.UIUtils;
import com.zhongke.content.view.PartInLayout;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

import static com.zhongke.content.Constance.ACCES_KEYID;
import static com.zhongke.content.Constance.DOWND_PHOTO;
import static com.zhongke.content.activity.LiveActivity.sendUserId;
import static com.zhongke.content.utils.ConversionObjcetUtils.createInstance;


/**
 * Created by ${xingen} on 2017/8/16.
 */

public class ChatContentFragment extends BaseFragment implements View.OnClickListener, View.OnTouchListener, BGARefreshLayout.BGARefreshLayoutDelegate, ContentSessionAdapter.ContentSessionCallBack{
    public static final String TAG = ChatContentFragment.class.getSimpleName();

    private ImageView emoji_iv;
    private ImageView voice_iv;
    private EmotionLayout emotionLayout;
    private RelativeLayout voice_manager_layout;
    private RelativeLayout text_manager_layout;
    private ImageView voice_keyboard_iv;
    private ImageView text_voice_change_iv;
    private BGARefreshLayout refreshLayout;
    private BaseRecyclerView recyclerView;
    private ContentSessionAdapter contentSessionAdapter;
    //列表的当前页数
    private int currentPage = 1;
    private final int pageSize = 5;
    //一个是否正在刷新的标志
    private boolean isPullRefresh = false;
    public static final String CHAT_OBJECT_ID = "chat_object_id";
    public static final String CHAT_OBJECT_TYPE = "chat_object_type";
    public static final String CHAT_OBJECT_NAME = "chat_object_name";
    private int current_chat_object_type;
    /**
     * 正在进行的动作，切换或者下拉增多。
     */
    public static final int operate_add = 3;
    public static final int operate_change = 4;
    private int currentOperate = operate_change;

    private LinearLayout chatLay_rely_parent;

    private ImageView next_show_iv;
    /**
     * 文本的输入框
     */
    private EditText input_content_edit;

    private LinearLayoutManager manager;
    //参加人员list
    private List<PartInBean2.RecordsBean> participantList;
    private PartInLayout partInLayout;
    //房间的id
    private int roomId;

    /**
     * 静态工厂创建对象
     *
     * @return
     */
    public static ChatContentFragment newInstance() {
        return newInstance(null);
    }

    public static ChatContentFragment newInstance(Bundle bundle) {
        ChatContentFragment contentFragment = new ChatContentFragment();
        if (bundle != null) {
            contentFragment.setArguments(bundle);
        }
        return contentFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            roomId = getArguments().getInt("roomId");
            LogUtil.e("chat roomId--"+roomId);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_chat_conent;
    }

    @Override
    protected void initAfterActivityCreated(View rootView, Bundle savedInstanceState) {
        participantList = new ArrayList<>();
        receiverArguments();
        initView(rootView);
        setAudioRecordListener();
        //获取参加人员列表
        getParticipants(roomId);
    }

    /**
     * 设置录音监听器
     */
    private void setAudioRecordListener() {
        AudioRecordManager.getInstance(UIUtils.getContext()).setAudioRecordListener(new IAudioRecordListener() {
            private TextView mTimerTV;
            private TextView mStateTV;
            private ImageView mStateIV;
            private PopupWindow mRecordWindow;

            /**
             * 初始化提示视图
             */
            @Override
            public void initTipView() {
                View view = View.inflate(getActivity(), R.layout.popup_audio_wi_vo, null);
                mStateIV = (ImageView) view.findViewById(R.id.rc_audio_state_image);
                mStateTV = (TextView) view.findViewById(R.id.rc_audio_state_text);
                mTimerTV = (TextView) view.findViewById(R.id.rc_audio_timer);
                //弹出一个PopupWindow,显示正在录音。
                mRecordWindow = new PopupWindow(view, -1, -1);
                mRecordWindow.showAtLocation(chatLay_rely_parent, 17, 0, 0);
                mRecordWindow.setFocusable(true);
                mRecordWindow.setOutsideTouchable(false);
                mRecordWindow.setTouchable(false);
            }

            /**
             * 设置倒计时提示视图
             *
             * @param counter 10秒倒计时
             */
            @Override
            public void setTimeoutTipView(int counter) {
                if (this.mRecordWindow != null) {
                    this.mStateIV.setVisibility(View.GONE);
                    this.mStateTV.setVisibility(View.VISIBLE);
                    this.mStateTV.setText(R.string.voice_rec);
                    this.mStateTV.setBackgroundResource(R.drawable.bg_voice_popup);
                    this.mTimerTV.setText(String.format("%s", new Object[]{Integer.valueOf(counter)}));
                    this.mTimerTV.setVisibility(View.VISIBLE);
                }
            }

            /**
             * 设置正在录制提示视图
             */
            @Override
            public void setRecordingTipView() {
                if (this.mRecordWindow != null) {
                    this.mStateIV.setVisibility(View.VISIBLE);
                    this.mStateIV.setImageResource(R.mipmap.ic_volume_1);
                    this.mStateTV.setVisibility(View.VISIBLE);
                    this.mStateTV.setText(R.string.voice_rec);
                    this.mStateTV.setBackgroundResource(R.drawable.bg_voice_popup);
                    this.mTimerTV.setVisibility(View.GONE);
                }
            }

            /**
             * 设置语音长度太短提示视图
             */
            @Override
            public void setAudioShortTipView() {
                if (this.mRecordWindow != null) {
                    mStateIV.setImageResource(R.mipmap.ic_volume_wraning);
                    mStateTV.setText(R.string.voice_short);
                }
            }

            /**
             * 设置取消提示视图
             */
            @Override
            public void setCancelTipView() {
                if (this.mRecordWindow != null) {
                    this.mTimerTV.setVisibility(View.GONE);
                    this.mStateIV.setVisibility(View.VISIBLE);
                    this.mStateIV.setImageResource(R.mipmap.ic_volume_cancel);
                    this.mStateTV.setVisibility(View.VISIBLE);
                    this.mStateTV.setText(R.string.voice_cancel);
                    this.mStateTV.setBackgroundResource(R.drawable.corner_voice_style);
                }
            }

            /**
             * 销毁提示视图
             */
            @Override
            public void destroyTipView() {
                if (this.mRecordWindow != null) {
                    this.mRecordWindow.dismiss();
                    this.mRecordWindow = null;
                    this.mStateIV = null;
                    this.mStateTV = null;
                    this.mTimerTV = null;
                }
            }

            /**
             * 开始录制
             * 如果是做IM的话，这里可以发送一个消息，如：对方正在讲话
             */
            @Override
            public void onStartRecord() {
                //RongIMClient.getInstance().sendTypingStatus(mConversationType, mSessionId, VoiceMessage.class.getAnnotation(MessageTag.class).value());
            }

            /**
             * 录制结束
             *
             * @param audioPath 语音文件路径
             * @param duration  语音文件时长
             */
            @Override
            public void onFinish(Uri audioPath, int duration) {
                //发送文件
                File file = new File(audioPath.getPath());
                if (file.exists()) {
                    LogUtil.i(TAG, "文件存在--path------>>>" + audioPath.getPath());
                    // 上传语音、 发送
                    uploadVoiceToOss(audioPath, duration, Constance.VOICE);
                }
            }

            /**
             * 分贝改变
             *
             * @param db 分贝
             */
            @Override
            public void onAudioDBChanged(int db) {
                switch (db / 5) {
                    case 0:
                        this.mStateIV.setImageResource(R.mipmap.ic_volume_1);
                        break;
                    case 1:
                        this.mStateIV.setImageResource(R.mipmap.ic_volume_2);
                        break;
                    case 2:
                        this.mStateIV.setImageResource(R.mipmap.ic_volume_3);
                        break;
                    case 3:
                        this.mStateIV.setImageResource(R.mipmap.ic_volume_4);
                        break;
                    case 4:
                        this.mStateIV.setImageResource(R.mipmap.ic_volume_5);
                        break;
                    case 5:
                        this.mStateIV.setImageResource(R.mipmap.ic_volume_6);
                        break;
                    case 6:
                        this.mStateIV.setImageResource(R.mipmap.ic_volume_7);
                        break;
                    default:
                        this.mStateIV.setImageResource(R.mipmap.ic_volume_8);
                }
            }
        });
    }

    /**
     * 上传语音到服务器
     *
     * @param uri
     * @param duration
     * @param chatType
     */
    public void uploadVoiceToOss(Uri uri, int duration, int chatType) {
        LogUtil.e(TAG, "---------->上传语音");
        String voicePath = StringUtil.getRealFilePath(HPApplication.getInstance(), uri);
        if (StringUtil.isNull(voicePath)) {
            UIUtils.showToast("请选择语音上传");
            return;
        }
        ImageOSSClient imageOSSClient = new ImageOSSClient(Constance.TEST_ENDPOINT, ACCES_KEYID, Constance.ACCES_KEYSECRET);
        imageOSSClient.initialize(getActivity());
        String uuid = StringUtil.getUUID();
        String objectKey = "session" + "/" + uuid + "/" + (uuid.substring(0, 6)) + ".mp3";
        imageOSSClient.uploadVideo(Constance.TEST_BUCKET, objectKey, voicePath, new CallBackListener<String>() {
            @Override
            public void onSuccess(int requestCode, int responseCode, String result) {
                Observable.just(result)
                        .subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<String>() {
                            @Override
                            public void onCompleted() {
                            }

                            @Override
                            public void onError(Throwable e) {
                            }

                            @Override
                            public void onNext(String s) {
                                LogUtil.i(TAG, "上传成功 url--------->>>" + s);
                                SessionContent_Entity entity = createInstance(Constance.type_own, s, current_chat_object_type, receiverId);
                                entity.setChatType(Constance.VOICE);
                                entity.setDuraction(duration);
                                String msgId = ZkImManager.getInstance().sendMessage(entity);
                                entity.setMsgId(msgId);
                                contentSessionAdapter.addNewDateData(entity);
                                scrollIndexPosition(recyclerView, contentSessionAdapter.getList().size() - 1);
                            }
                        });

            }

            @Override
            public void onFailure(int requestCode, int responseCode, String result) {
                UIUtils.showToastSafely("上传语音失败");
            }
        });
    }

    /**
     * 查询数据库
     */
    private void queryDataFromDB(int operate) {
        this.currentOperate = operate;
        Log.i(TAG, " 聊天内容开始查询  " + receiverId);
        switch (current_chat_object_type) {
            case Constance.chat_group:
                queryGroupChatMessage(receiverId);
               // getActivity().sendBroadcast(IntentBuilder.builderGroupIntent(IntentBuilder.ACTION_JOIN_GROUP,receiverId));
                break;
            case Constance.chat_one:
                queryOneChatMessage(receiverId);
                break;
            default:

                break;
        }
    }

    private String chatObjectName;

    /**
     * 接受传递的参数:聊天对象的id,type(个人还是群)
     */
    private void receiverArguments() {
        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey(CHAT_OBJECT_ID)) {
            this.receiverId = bundle.getString(CHAT_OBJECT_ID);
            this.current_chat_object_type = bundle.getInt(CHAT_OBJECT_TYPE);
            this.chatObjectName = bundle.getString(CHAT_OBJECT_NAME);
            LogUtil.i(TAG, " 聊天内容的界面 " + receiverId + " " + current_chat_object_type + " " + chatObjectName);
        }
        switch (current_chat_object_type) {
            case Constance.chat_group:
              //  queryGroupChatMessage(receiverId);
                getActivity().sendBroadcast(IntentBuilder.builderGroupIntent(IntentBuilder.ACTION_JOIN_GROUP,receiverId));
                break;
            case Constance.chat_one:
              //  queryOneChatMessage(receiverId);
                break;
            default:

                break;
        }
    }

    private TextView net_error_tv;

    /**
     * 初始化控件和监听器
     *
     * @param rootView
     */
    private void initView(View rootView) {

        this.net_error_tv = rootView.findViewById(R.id.chat_second_chat_net_error_tv);

        this.emoji_iv = rootView.findViewById(R.id.chat_second_content_emoji_iv);
        this.refreshLayout = rootView.findViewById(R.id.chat_second_refreshlayout_content);
        this.emotionLayout = rootView.findViewById(R.id.layout_elEmotion);
        this.voice_iv = rootView.findViewById(R.id.chat_second_chat_content_voice_iv);
        this.voice_manager_layout = rootView.findViewById(R.id.chat_second_chat_voice_manager_layout);
//        this.text_emoji_manager_layout = rootView.findViewById(R.id.chat_second_text_emoji_manager_layout);
        this.text_manager_layout = rootView.findViewById(R.id.chat_second_textmanager_layout);
        this.voice_keyboard_iv = rootView.findViewById(R.id.chat_second_chat_content_keyboard_iv);
        this.text_voice_change_iv = rootView.findViewById(R.id.chat_second_content_voice);
        this.recyclerView = rootView.findViewById(R.id.chat_second_content_recyclerview);
        this.input_content_edit = rootView.findViewById(R.id.chat_second_content_edit);
        this.chatLay_rely_parent = rootView.findViewById(R.id.second_not_normal_layout);
        this.next_show_iv = rootView.findViewById(R.id.chat_second_chat_content_next_iv);
        this.press_tip_iv = rootView.findViewById(R.id.chat_second_chat_content_press_iv);


        //设置RecyclerView
        this.contentSessionAdapter = new ContentSessionAdapter(getActivity());
        this.contentSessionAdapter.setSessionCallBack(this);
        manager = new LinearLayoutManager(getActivity());
        manager.setAutoMeasureEnabled(true);
        this.recyclerView.setLayoutManager(manager);
        this.recyclerView.setAdapter(this.contentSessionAdapter);
        boolean isConneted = NetConnectedUtils.isNetConnection(getContext().getApplicationContext());
        setNetErrorShow(isConneted == true ? View.GONE : View.VISIBLE);

        //设置刷新布局
        BGANormalRefreshViewHolder refreshViewHolder = new BGANormalRefreshViewHolder(getActivity(), false);
        refreshViewHolder.setRefreshingText("");
        refreshViewHolder.setPullDownRefreshText("");
        refreshViewHolder.setReleaseRefreshText("");
        this.refreshLayout.setRefreshViewHolder(refreshViewHolder);
        //设置表情布局
        this.emotionLayout.attachEditText(input_content_edit);
        this.emotionLayout.setEmotionAddVisiable(false);
        this.emotionLayout.setEmotionSettingVisiable(false);
        //设置监听器
        this.emoji_iv.setOnClickListener(this);

        this.voice_iv.setOnTouchListener(this);
        this.refreshLayout.setDelegate(this);
        this.voice_keyboard_iv.setOnClickListener(this);
        this.text_voice_change_iv.setOnClickListener(this);
        this.next_show_iv.setOnClickListener(this);
        rootView.findViewById(R.id.chat_second_content_send_btn).setOnClickListener(this);
        rootView.findViewById(R.id.chat_second_chat_content_emoji_iv).setOnClickListener(this);
        this.net_error_tv.setOnClickListener(this);
        //参加人员layout
        partInLayout = rootView.findViewById(R.id.chat_part_in_list_lay);

    }

    private ImageView press_tip_iv;

    /**
     * 滚动到当前显示布局Item的最后一个位置
     */
    private void scrollCurrentPageLastPosition(){
        LinearLayoutManager linearLayoutManager=((LinearLayoutManager) recyclerView.getLayoutManager());
        int lastPosition = (linearLayoutManager.findLastCompletelyVisibleItemPosition()+linearLayoutManager.findFirstCompletelyVisibleItemPosition())/2;
        Log.i(TAG, "lastPosition----------->>>" + lastPosition+" "+    linearLayoutManager.findLastCompletelyVisibleItemPosition());
        recyclerView.scrollToPosition(lastPosition);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.chat_second_content_emoji_iv://点击表情
               // Log.i(TAG, " RecyclerView 的高度： " + recyclerView.getHeight());
                if (this.emotionLayout.getVisibility() == View.VISIBLE){
                    this.emotionLayout.setVisibility(View.GONE );
                }else{
                    this.emotionLayout.setVisibility(View.VISIBLE);
                }
                scrollCurrentPageLastPosition();
                break;
            case R.id.chat_second_chat_content_keyboard_iv://切换成Text输入的布局。
                this.voice_manager_layout.setVisibility(View.GONE);
//                this.text_emoji_manager_layout.setVisibility(View.VISIBLE);
                this.text_manager_layout.setVisibility(View.VISIBLE);
                this.emotionLayout.setVisibility(View.GONE);

                break;
            case R.id.chat_second_content_voice://切换成语音布局
                this.voice_manager_layout.setVisibility(View.VISIBLE);
//                this.text_emoji_manager_layout.setVisibility(View.GONE);
                this.text_manager_layout.setVisibility(View.GONE);
                this.emotionLayout.setVisibility(View.GONE);

                break;
            case R.id.chat_second_content_send_btn://发送信息
                String msg = input_content_edit.getText().toString();
                if (TextUtils.isEmpty(msg)) {
                    UIUtils.showToast("请输入内容");
                } else {
                    sendMessageToChat(Constance.TEXT, msg, null);
                    input_content_edit.setText("");
                    input_content_edit.clearFocus();
                    this.emotionLayout.setVisibility(View.GONE);
                }
                break;
            case R.id.chat_second_chat_content_next_iv:
                if (voice_iv.getVisibility() == View.VISIBLE) {
                    voice_iv.setVisibility(View.GONE);
                    press_tip_iv.setVisibility(View.GONE);
                } else {
                    voice_iv.setVisibility(View.VISIBLE);
                    press_tip_iv.setVisibility(View.VISIBLE);
                    scrollCurrentPageLastPosition();
                }
                break;
            case R.id.chat_second_chat_net_error_tv:
                NetConnectedUtils.openSet(getActivity());
                break;
            case R.id.chat_second_chat_content_emoji_iv:
                this.voice_manager_layout.setVisibility(View.GONE);
//                this.text_emoji_manager_layout.setVisibility(View.VISIBLE);
                this.emotionLayout.setVisibility(View.VISIBLE);
                this.text_manager_layout.setVisibility(View.VISIBLE);
                break;
            default:

                break;
        }
    }

    public void setNetErrorShow(int visiblility) {
        this.net_error_tv.setVisibility(visiblility);
    }

    private boolean isCancelled(View view, MotionEvent event) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        if (event.getRawX() < location[0] || event.getRawX() > location[0] + view.getWidth()
                || event.getRawY() < location[1] - 40) {
            return true;
        }
        return false;
    }

    /**
     * 发送聊天信息
     */
    public void sendMessageToChat(int type, String content, String localUrl) {
        SessionContent_Entity entity = null;
        switch (type) {
            case Constance.TEXT:
                entity = createInstance(Constance.type_own, content, current_chat_object_type, receiverId);
                break;
            case Constance.IMAGE:
            case Constance.STICKER:
                entity = createInstance(Constance.type_own, content, current_chat_object_type, receiverId);
                entity.setLocalUrl(localUrl);
                break;
            default:

                break;
        }
        entity.setChatType(type);
        String msgId = ZkImManager.getInstance().sendMessage(entity);
        entity.setMsgId(msgId);
        contentSessionAdapter.addNewDateData(entity);
        scrollIndexPosition(recyclerView, contentSessionAdapter.getItemCount() - 1);
    }

    private boolean isCancelRecord = false;

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN://手指落下，开始录音
                this.voice_iv.setImageResource(R.mipmap.chat_second_voice_press);
                //开始录音
                AudioRecordManager.getInstance(getActivity()).startRecord();
                break;
            case MotionEvent.ACTION_MOVE:
                if (isCancelled(view, motionEvent)) {
                    //将要取消录音（参与微信手指上滑）
                    isCancelRecord = true;
                    AudioRecordManager.getInstance(getContext()).willCancelRecord();
                } else {
                    //继续录音（参与微信手指上滑后加下滑回到原位）
                    isCancelRecord = false;
                    AudioRecordManager.getInstance(getActivity()).continueRecord();
                }
                break;
            case MotionEvent.ACTION_UP://手指抬起，停止录音
                //停止录音
                AudioRecordManager.getInstance(getActivity()).stopRecord();
                //销毁录音
                AudioRecordManager.getInstance(getActivity()).destroyRecord();
                this.voice_iv.setImageResource(R.mipmap.chat_second_voice_normal);
                if (!isCancelRecord) {
                    voice_iv.setVisibility(View.GONE);
                    press_tip_iv.setVisibility(View.GONE);
                    scrollCurrentPageLastPosition();
                }
                break;
            case MotionEvent.ACTION_CANCEL:
                //停止录音
                AudioRecordManager.getInstance(getActivity()).stopRecord();
                //销毁录音
                AudioRecordManager.getInstance(getActivity()).destroyRecord();
                this.voice_iv.setImageResource(R.mipmap.chat_second_voice_normal);
                //   voice_iv.setVisibility(View.GONE);
                // press_tip_iv.setVisibility(View.GONE);
                break;
            default:

                break;
        }
        return true;
    }

    /**
     * 下拉刷新，响应
     *
     * @param refreshLayout
     */
    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        LogUtil.i(TAG, "onBGARefreshLayoutBeginRefreshing ");
        if (!isPullRefresh) {//开始状态
            isPullRefresh = true;
            currentPage++;
            queryDataFromDB(operate_add);
        }
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        return false;
    }

    /**
     * 查询群聊的信息
     */
    public void queryGroupChatMessage(final String id) {
        Log.i(TAG, " 开始查询个人聊天 " + currentPage);
        ZkImManager.getInstance()
                .queryMessageByGroup(id, currentPage, pageSize,
                        new ResultListener<List<ZkLocalMessage>>() {
                            @Override
                            public void result(List<ZkLocalMessage> zkLocalMessages) {
                                conversionObject(id, zkLocalMessages);
                            }

                            @Override
                            public void error(Exception e) {

                            }
                        });
    }

    /**
     * 正在聊天接受者的id,可能是一个人，可能是一个群。
     */
    public String receiverId;

    /**
     * 查询单聊的信息
     */
    public void queryOneChatMessage(final String id) {
        Log.i(TAG, " 开始查询个人聊天  当前页数： " + currentPage);
        ZkImManager.getInstance().queryMessageByReceiver(sendUserId, id, currentPage, pageSize,
                new ResultListener<List<ZkLocalMessage>>() {
                    @Override
                    public void result(List<ZkLocalMessage> zkLocalMessages) {
                        conversionObject(id,zkLocalMessages);
                    }

                    @Override
                    public void error(Exception e) {

                    }
                });
    }
    private void conversionObject(final String id, List<ZkLocalMessage> queryResult) {
        Observable<List<SessionContent_Entity>> observable = Observable.create(subscriber -> {
            List<SessionContent_Entity> list = createInstance(queryResult);
            Collections.reverse(list);
            subscriber.onNext(list);
            subscriber.onCompleted();
        });
        executeUpdateContentList(id, observable);
    }

    /**
     * 更新聊天内容
     *
     * @param id
     * @param observable
     */
    private void executeUpdateContentList(final String id, Observable<List<SessionContent_Entity>> observable) {
        Subscription subscription = observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<List<SessionContent_Entity>>() {
                    @Override
                    public void onCompleted() {
                        closeRefresh();
                    }

                    @Override
                    public void onError(Throwable e) {
                        closeRefresh();
                    }

                    @Override
                    public void onNext(List<SessionContent_Entity> list) {
                        if (!receiverId.equals(id)) { return; }
                        switch (currentOperate) {
                            case operate_add:
                                if (list.size() > 0) {
                                    int size = list.size();
                                    contentSessionAdapter.addOldDateData(list);
                                    scrollIndexPosition(recyclerView, size - 1);
                                }
                                break;
                            case operate_change:
                                contentSessionAdapter.setData(list);
                                scrollIndexPosition(recyclerView, contentSessionAdapter.getItemCount() - 1);
                                break;
                            default:
                                break;
                        }
                    }
                });
        this.compositeSubscription.add(subscription);
    }

    /**
     * 更新接收到的聊天内容信息
     *
     * @param entity
     */
    public void updateReceiverChatMessage(SessionContent_Entity entity) {
        Log.i(TAG, " 在二级页面中具体的聊天页面 updateReceiverChatMessage ");
        contentSessionAdapter.addNewDateData(entity);
        scrollIndexPosition(recyclerView, contentSessionAdapter.getList().size() - 1);
    }

    /**
     * 根据MessageIde去匹配对应的消息状态。
     *
     * @param entity_new
     */
    public void updateSendChatMessage(SessionContent_Entity entity_new) {
        Subscription subscription = Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                int size = contentSessionAdapter.getList().size();
                // Log.i(ContentSessionAdapter.tag, "更新本人发送的聊天内容" + size);
                boolean result = false;
                for (int i = 0; i < contentSessionAdapter.getList().size(); ++i) {//没加同步锁
                    SessionContent_Entity entity1 = contentSessionAdapter.getList().get(i);
                    //Log.i(ContentSessionAdapter.tag, " 循环匹配 " + i + "  Msgid " + entity_new.getMsgId() + "  原本的 " + entity1.getMsgId());
                    if (entity_new.getMsgId().equals(entity1.getMsgId())) {//筛选方法
                        entity1.setSendStatus(entity_new.getSendStatus());
                        entity1.setMsgId(entity_new.getMsgId());
                        result = true;
                        Log.i(ContentSessionAdapter.tag, " 匹配的位置 " + i + "  Msgid " + entity_new.getMsgId() + " 状态 " + entity_new.getSendStatus());
                        break;
                    }
                }
                subscriber.onNext(result);
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean result) {
                        if (result) {
                            contentSessionAdapter.notifyData();
                        }
                    }
                });
        this.compositeSubscription.add(subscription);
    }

    /**
     * 关闭刷新布局
     */
    private void closeRefresh() {
        this.isPullRefresh = false;
        this.refreshLayout.endRefreshing();
    }

    @Override
    public void click(View view, boolean isRetry, int position) {
        Log.i(TAG, " 获取当前click的位置" + position);
        SessionContent_Entity entity = contentSessionAdapter.getList().get(position);
        if (entity == null) {
            return;
        }
        if (isRetry) {
            // 重新发送消息
            ZkImManager.getInstance().againSendMessage(entity);
        } else {
            // 播放消息本身(图片、语音、视频等)
            switch (entity.getChatType()) {
                case Constance.IMAGE:
                    filterImage(position);
                    // 展示图片
                    break;
                case Constance.VIDEO:                    // 播放视频
                   /* MVideo.getInstance()
                            .start(getActivity(), (ImageView) ((RelativeLayout) view).getChildAt(0), DOWND_PHOTO + entity.getContent());*/
                    break;
                case Constance.VOICE:
                    // 播放语音
                    playVoice(entity, (ImageView) view.getTag());
                    break;
                default:
                    break;

            }
        }
    }

    /**
     * 筛选图片，传递图片路径
     *
     * @param position
     */
    private void filterImage(int position) {
        Subscription subscription = Observable.create(new Observable.OnSubscribe<SessionContent_Entity>() {
            @Override
            public void call(Subscriber<? super SessionContent_Entity> subscriber) {
                SessionContent_Entity entity = new SessionContent_Entity();
                int size = contentSessionAdapter.getList().size();
                SessionContent_Entity select_entity = contentSessionAdapter.getList().get(position);
                List<String> list = new ArrayList<>();
                for (int i = 0; i < size; ++i) {
                    SessionContent_Entity entity_1 = contentSessionAdapter.getList().get(i);
                    if (entity_1.getChatType() == Constance.IMAGE) {
                        if (entity_1.getMark() == Constance.type_own) {
                            list.add(entity_1.getLocalUrl());
                        } else {
                            list.add(DOWND_PHOTO + entity_1.getContent());
                        }
                        if (select_entity.getMsgId() == entity_1.getMsgId()) {
                            entity.setChatType(list.size() - 1);
                        }
                    }
                }
                entity.setToUserList(list);
                subscriber.onNext(entity);
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.computation()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<SessionContent_Entity>() {
            @Override
            public void call(SessionContent_Entity sessionContent_entity) {
                PictureTouchActivity.openActivity(getActivity(), sessionContent_entity);
            }
        });
        this.compositeSubscription.add(subscription);
    }

    private String currentAudioPlayUrl;
    private int currentType;
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            switch (message.what) {
                case what_start:
                    currentType = what_start;
                    currentAudioPlayUrl = (String) message.obj;
                    handler.post(task_audio_play);
                    break;
                case what_next_start:
                    currentType = what_next_start;
                    handler.post(task_audio_play);
                    break;
                default:

                    break;
            }
            return false;
        }
    });
    private Runnable task_audio_play = new Runnable() {
        String audioPlayUrl;
        ImageView imageView;
        @Override
        public void run() {
            Log.i(TAG, " Runnable 对象执行任务");
            filterAction();
            if (audioPlayUrl != null && imageView != null) {
                Log.i(TAG, " 开始播放的");
                startPlayAudio(audioPlayUrl, imageView);
                audioPlayUrl = null;
                imageView = null;
            }
        }

        /**
         * 筛选
         */
        private void filterAction() {
            boolean isBreak = false;
            for (int i = 0; i < contentSessionAdapter.getList().size(); ++i) {
                if (currentType == what_start) {
                    if (contentSessionAdapter.getList().get(i).getContent().contains(currentAudioPlayUrl)) {
                        Log.i(TAG, "第一次播放 " + i);
                        selectChild(i);
                        break;
                    }
                } else if (currentType == what_next_start) {
                    if (contentSessionAdapter.getList().get(i).getContent().contains(currentAudioPlayUrl)) {
                        Log.i(TAG, "正在筛选 ，上一次播放是" + i + "  下一次播放 ");
                        for (int j = i + 1; j < contentSessionAdapter.getList().size(); ++j) {
                            SessionContent_Entity entity = contentSessionAdapter.getList().get(j);
                            if (entity.getChatType() == Constance.VOICE && (!entity.getIsAudioPlay())) {
                                Log.i(TAG, "筛选成功，下一次播放 " + j);
                                selectChild(j);
                                break;
                            } else {
                                Log.i(TAG, "筛选工作，下一个数据 " + j + " " + entity.getChatType() + " " + entity.getIsAudioPlay() + " ");
                                continue;
                            }
                        }
                        isBreak = true;
                    }
                    if (isBreak) {
                        break;
                    }
                }
            }
        }

        /**
         * 挑选对应位置的item
         * @param position
         */
        private void selectChild(int position) {
            Log.i(TAG, " 获取当前赛选的位置" + position);
            currentAudioPlayUrl = contentSessionAdapter.getList().get(position).getContent();
            audioPlayUrl = DOWND_PHOTO + currentAudioPlayUrl;
            //获取到指定位置对应的ViewHolder.  findViewHolderForAdapterPosition()或者findViewHolderForLayoutPosition
            RecyclerView.ViewHolder viewHolder = recyclerView.findViewHolderForAdapterPosition(position);
            if (viewHolder instanceof ContentSessionAdapter.ViewHolder6) {
                imageView = ((ContentSessionAdapter.ViewHolder6) viewHolder).voiceImg;
            } else if (viewHolder instanceof ContentSessionAdapter.ViewHolder5) {
                imageView = ((ContentSessionAdapter.ViewHolder5) viewHolder).voiceImg;
            }
            Log.i(TAG, " 获取当前赛选的位置" + position + " 播放路径 " + audioPlayUrl + " 显示的控件 " + imageView);
        }
    };

    /**
     * 播放语音
     */
    private void playVoice(SessionContent_Entity entity, ImageView voiceImg) {
        LogUtil.e(TAG, "--------->播放语音");
//        final ImageView ivAudio = helper.getView(R.id.ivAudio);
        AudioPlayManager audioPlayManager = AudioPlayManager.getInstance();
        boolean isPlaying = audioPlayManager.isPlaying(HPApplication.getInstance());
        if (isPlaying) {//正在播放语音
            //停止先前的播放
            handler.removeCallbacks(task_audio_play);
            audioPlayManager.stopPlay();
            if (!currentAudioPlayUrl.contains(entity.getContent())) {
                entity.setAudioPlay(true);
                handler.obtainMessage(what_start, entity.getContent()).sendToTarget();
            }
        } else { //没有正在播放语音
            entity.setAudioPlay(true);
            handler.obtainMessage(what_start, entity.getContent()).sendToTarget();
        }
    }

    public static final int what_start = 1;
    public static final int what_next_start = 2;

    /**
     * 开始正式播放语音
     *
     * @param newPlayUrl
     * @param voiceImg
     */
    private void startPlayAudio(String newPlayUrl, ImageView voiceImg) {
        AudioPlayManager.getInstance().startPlay(getActivity(), newPlayUrl, new IAudioPlayUrlListener() {
            @Override
            public void onStart(String var1) {
                //开播（一般是开始语音消息动画）
                AnimationDrawable animation = (AnimationDrawable) voiceImg.getBackground();
                animation.start();
            }

            @Override
            public void onStop(String var1) {
                //停播（一般是停止语音消息动画）
                AnimationDrawable animation = (AnimationDrawable) voiceImg.getBackground();
                animation.stop();
                animation.selectDrawable(0);
            }

            @Override
            public void onComplete(String var1) {

                //播完（一般是停止语音消息动画）
                if (voiceImg != null && voiceImg.getBackground() instanceof AnimationDrawable) {
                    AnimationDrawable animation = (AnimationDrawable) voiceImg.getBackground();
                    animation.stop();
                    animation.selectDrawable(0);
                    Log.i(TAG, " 本次播放完毕 ，开始下一次播放 ");
                    //播放下一个
                    handler.obtainMessage(what_next_start).sendToTarget();
                }
            }
        });
    }


    @Override
    public void onDestroyView() {
        //销毁的时候，要停止播放
        AudioPlayManager.getInstance().stopPlay();
        handler.removeCallbacks(task_audio_play);
        super.onDestroyView();
    }

    /**
     * 滚动到RecyclerView的指定item
     * 或者参考：https://stackoverflow.com/questions/38416146/recyclerview-smoothscroll-to-position-in-the-center-android
     *
     * @param recyclerView
     * @param position
     */
    private void scrollIndexPosition(RecyclerView recyclerView, int position) {
        if (recyclerView.getLayoutManager() != null && recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
            LinearLayoutManager linearLayoutManager = ((LinearLayoutManager) recyclerView.getLayoutManager());
            linearLayoutManager.scrollToPosition(position);
        }
    }

    //获取参加人员
    private void getParticipants(int roomId) {
        Map<String, Object> map = new HashMap<>();
        map.put("roomId", roomId);
        map.put("pageIndex",1);
        map.put("pageSize",4);
        Subscription subscription = retrofitProvider.getParticipants(map, new ResponseResultListener<PartInBean2>() {
            @Override
            public void success(PartInBean2 partInBean2) {
                LogUtil.e("success---" + partInBean2.getRecords().toString());
                participantList.addAll(partInBean2.getRecords());
                for (int i = 0, size = participantList.size(); i < size; i++) {
                    participantList.get(i).icon = "http://www.feizl.com/upload2007/2014_08/14081117427587.jpg";
                    partInLayout.addItem(participantList.get(i));

                }
            }

            @Override
            public void failure(CommonException e) {
                LogUtil.e("failure---" + e.getErrorMsg());
                UIUtils.showToast(e.getErrorMsg());
            }
        });
        compositeSubscription.add(subscription);
    }
}
