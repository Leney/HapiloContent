package com.zhongke.content.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.lqr.audio.IAudioPlayUrlListener;
import com.zhongke.content.BaseFragment;
import com.zhongke.content.R;
import com.zhongke.content.audio.AudioRecordPlayClient;
import com.zhongke.content.entity.MyInfoBean;
import com.zhongke.content.entity.WishResponseBean;
import com.zhongke.content.im.ZkImManager;
import com.zhongke.content.im.extra.ExtraMessage;
import com.zhongke.content.im.extra.message.WishBindActivityNotice;
import com.zhongke.content.oss.AliYunOssClient;
import com.zhongke.content.retrofit.CommonException;
import com.zhongke.content.retrofit.ResponseResultListener;
import com.zhongke.content.utils.LogUtil;
import com.zhongke.content.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import rx.Subscription;

/**
 * Created by ${xinGen} on 2017/9/26.
 * 许愿洞穴界面，语音许愿
 */

public class WishingCaveFragment extends BaseFragment  {
    public static final String TAG = WishingCaveFragment.class.getSimpleName();
    private ImageView voice_iv;
    private ImageView wishing_cave_bg_controller;
    private TextView wishing_cave_input_text;
    /**
     * 录音文件的地址
     */
    private String audioFilePath;
    private ExtraMessage<WishBindActivityNotice> noticeExtraMessage;
    private ExtraMessage<WishBindActivityNotice> extraMessage2;
    private ExtraMessage<WishBindActivityNotice> extraMessage4;
    private ExtraMessage<WishBindActivityNotice> extraMessage3;

    public static WishingCaveFragment newInstance() {
        WishingCaveFragment fragment = new WishingCaveFragment();
        return fragment;
    }
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_wishingcave;
    }
    @Override
    protected void initAfterActivityCreated(View rootView, Bundle savedInstanceState) {
        this.voice_iv = rootView.findViewById(R.id.wishing_cave_voice_iv);
        this.wishing_cave_bg_controller = rootView.findViewById(R.id.wishing_cave_bg2);
        this.wishing_cave_input_text = rootView.findViewById(R.id.wishing_cave_input_edit);
        this.wishing_cave_input_text.setOnClickListener(view -> playAudio());
        rootView.findViewById(R.id.wishing_cave_finish_wishing_iv).setOnClickListener(v -> upLoadFile());
        setAudioRecord();
    }
    private void setAudioRecord(){
        AudioRecordPlayClient.getInstance().startRecord(voice_iv, new AudioRecordPlayClient.AudioResponseLister() {
            @Override
            public void showAtLocation(PopupWindow popupWindow) {
                popupWindow.showAtLocation(wishing_cave_bg_controller,Gravity.CENTER,0,0);
            }
            @Override
            public void saveAudioFile(String filePath) {
                  audioFilePath=filePath;
                  wishing_cave_input_text.setText("语音录制完成，点击可进行播放");
            }
            @Override
            public void startAudio() {

            }
            @Override
            public void endAudio() {

            }
        });
    }

    /**
     * 上传语音文件到阿里云服务器
     */
    public void upLoadFile() {
        if (TextUtils.isEmpty(audioFilePath)) {
            ToastUtils.showToast(getContext(), "请先录制语音愿望");
        } else {
            AliYunOssClient.getInstance().startUpLoad(audioFilePath, new AliYunOssClient.RequestResponseListener() {
                @Override
                public void start() {
                }

                @Override
                public void onProgress(String progress) {

                }

                @Override
                public void onError(String localFilePath, String errorMSG) {
                    ToastUtils.showToast(getContext(), "语音愿望上传失败");
                }

                @Override
                public void onSuccess(String localFilePath, String upLoadFilePath) {
                    executeNetWork(upLoadFilePath);
                }
            });
        }
    }
    /**
     * 执行网络请求
     */
    private void executeNetWork(String upLoadFilePath) {
        Subscription subscription = retrofitProvider.upLoadWishingAudio(upLoadFilePath, new ResponseResultListener<WishResponseBean>() {

            @Override
            public void success(WishResponseBean bean) {
                ToastUtils.showToast(getContext(), "许愿成功，进行下一个许愿");
                wishing_cave_input_text.setText("按住开始许愿，松开完成许愿");
                audioFilePath = null;

                //todo 发im 消息通知weiduo
                noticeExtraMessage = new ExtraMessage<WishBindActivityNotice>();
                noticeExtraMessage.code = ExtraMessage.wish_bind_activiy;
                WishBindActivityNotice notice = new WishBindActivityNotice();
                LogUtil.e("wishId---");
                notice.wishId = bean.getWish().getId();
                notice.code = WishBindActivityNotice.code_start_wishi;
                noticeExtraMessage.code=ExtraMessage.wish_bind_activiy;
                noticeExtraMessage.message = notice;

                //参加活动
                extraMessage2 = new ExtraMessage<WishBindActivityNotice>();
                extraMessage2.code = ExtraMessage.wish_bind_activiy;
                WishBindActivityNotice notice2 = new WishBindActivityNotice();
                notice2.bindActivityId = 81;
                notice2.code = WishBindActivityNotice.code_accept_activity;
                extraMessage2.message = notice2;

                //活动开始
                extraMessage3 = new ExtraMessage<WishBindActivityNotice>();
                extraMessage3.code = ExtraMessage.wish_bind_activiy;
                WishBindActivityNotice notice3 = new WishBindActivityNotice();
                notice3.bindActivityId = 82;
                notice3.code = WishBindActivityNotice.code_activity_start;
                extraMessage3.message = notice3;


                extraMessage4 = new ExtraMessage<WishBindActivityNotice>();
                extraMessage4.code = ExtraMessage.wish_bind_activiy;
                WishBindActivityNotice notice4 = new WishBindActivityNotice();
                notice4.bindActivityId = 82;
                notice4.code = WishBindActivityNotice.code_activity_finish;
                extraMessage4.message = notice4;

                //获取ID 然后发送
                getMyInfo();
            }
            @Override
            public void failure(CommonException e) {
                ToastUtils.showToast(getContext(), "许愿失败");
            }
        });
        this.compositeSubscription.add(subscription);
    }

    public void getMyInfo() {

        Subscription subscription = retrofitProvider.getMyInFo(new ResponseResultListener<MyInfoBean>() {
            @Override
            public void success(MyInfoBean myInfoBean) {

                LogUtil.e("success---");
                String id = myInfoBean.getSysUser().getActiveUserId();
                sendWishMessAge(id);

                sendJoinMessage(id);

                sendStartMessage(id);

                sendEndMessage(id);
            }

            @Override
            public void failure(CommonException e) {
                LogUtil.e("failure---"+e.getErrorMsg());
            }
        });
        compositeSubscription.add(subscription);
    }

    public void sendWishMessAge(String ActiveUserId) {
        List<String> toUserList = new ArrayList<>();

        toUserList.add(ActiveUserId);
        ZkImManager.getInstance().sendExtraMessage(toUserList,noticeExtraMessage);
    }

    public void sendJoinMessage(String ActiveUserId) {
        List<String> toUserList = new ArrayList<>();
        toUserList.add(ActiveUserId);
        ZkImManager.getInstance().sendExtraMessage(toUserList,extraMessage2);
    }

    public void sendStartMessage(String ActiveUserId) {
        List<String> toUserList = new ArrayList<>();
        toUserList.add(ActiveUserId);
        ZkImManager.getInstance().sendExtraMessage(toUserList,extraMessage3);
    }

    public void sendEndMessage(String ActiveUserId) {
        List<String> toUserList = new ArrayList<>();
        toUserList.add(ActiveUserId);
        ZkImManager.getInstance().sendExtraMessage(toUserList,extraMessage4);
    }


    /**
     * 播放语音
     */
    private void playAudio() {
        if (!TextUtils.isEmpty(audioFilePath)) {
             AudioRecordPlayClient.getInstance().playRecord( audioFilePath, new IAudioPlayUrlListener() {
                @Override
                public void onStart(String var1) {
                    //开播（一般是开始语音消息动画）
                 /*   AnimationDrawable animation = (AnimationDrawable) voiceImg.getBackground();
                    animation.start();*/
                }

                @Override
                public void onStop(String var1) {
                    //停播（一般是停止语音消息动画）
            /*        AnimationDrawable animation = (AnimationDrawable) voiceImg.getBackground();
                    animation.stop();
                    animation.selectDrawable(0);*/
                }

                @Override
                public void onComplete(String var1) {

                    //播完（一般是停止语音消息动画）
             /*       if (voiceImg != null && voiceImg.getBackground() instanceof AnimationDrawable) {
                        AnimationDrawable animation = (AnimationDrawable) voiceImg.getBackground();
                        animation.stop();
                        animation.selectDrawable(0);
                        Log.i(TAG, " 本次播放完毕 ，开始下一次播放 ");
                        //播放下一个
                        handler.obtainMessage(what_next_start).sendToTarget();
                    }*/
                }
            });
        }
    }
}
