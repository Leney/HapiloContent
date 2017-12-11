package com.zhongke.content.audio;

import android.content.Context;
import android.net.Uri;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.lqr.audio.AudioPlayManager;
import com.lqr.audio.AudioRecordManager;
import com.lqr.audio.IAudioPlayUrlListener;
import com.lqr.audio.IAudioRecordListener;
import com.zhongke.content.Constance;
import com.zhongke.content.HPApplication;
import com.zhongke.content.R;
import com.zhongke.content.utils.BitmapUtils;
import com.zhongke.content.utils.LogUtil;

import java.io.File;
import java.lang.ref.WeakReference;

import static com.zhongke.content.fragment.WishingCastleFragment.TAG;

/**
 * Created by ${xinGen} on 2017/11/20.
 *
 * 一个语音播放和语音录制的客户端类
 */

public class AudioRecordPlayClient {
    private static AudioRecordPlayClient instance;
    private final String DIR_NAME = "audio";
    private Context appContext;
    private AudioRecordManager audioRecordManager;
   private AudioPlayManager audioPlayManager ;
    private WeakReference<AudioResponseLister> weakReference;

    private AudioRecordPlayClient() {
        this.appContext = HPApplication.getInstance();
        this.audioRecordManager = AudioRecordManager.getInstance(appContext);
        this.audioPlayManager = AudioPlayManager.getInstance();
        initAudioRecordConfig();
    }
    public static synchronized AudioRecordPlayClient getInstance() {
        if (instance == null) {
            instance = new AudioRecordPlayClient();
        }
        return instance;
    }
    /**
     * 录制语音
     * @param imageView
     * @param audioResponseLister
     */
    public void startRecord(ImageView imageView, AudioResponseLister audioResponseLister) {
        imageView.setOnTouchListener(new ImageViewTouch(this));
        setWeakReference(audioResponseLister);
    }
    /**
     * 播放语音
     * @param audioUrl
     * @param playListenerUrl
     */
    public void playRecord( String audioUrl, IAudioPlayUrlListener playListenerUrl){
        if (audioPlayManager.isPlaying(appContext)) {
             return;
        }
        audioPlayManager.startPlay(appContext,audioUrl,playListenerUrl);
    }

    /**
     * 当某些生命周期发生改变的时候，取消播放
     */
    public  void cancelPlay(){
        audioPlayManager.stopPlay();
    }
    protected void setWeakReference(AudioResponseLister audioResponseLister) {
        this.weakReference = new WeakReference<>(audioResponseLister);
    }

    protected AudioResponseLister getAudioResponseLister() {
        return weakReference != null ? weakReference.get() : null;
    }
    /**
     * 初始化：
     * 1. 录音配置：最长录音时间，录音文件路径
     * 2. 录音过程中监听器
     */
    private void initAudioRecordConfig() {
        try {
            //设置录音时长
            audioRecordManager.setMaxVoiceDuration(Constance.DEFAULT_MAX_AUDIO_RECORD_TIME_SECOND);
            //设置缓存路径
            audioRecordManager.setAudioSavePath(Utils.createDirFile(appContext, DIR_NAME));
            this.audioRecordManager.setAudioRecordListener(new AudioRecordChangeListener(this));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 一个录音过程状态变化的监听器
     */
    public interface AudioResponseLister {
        /**
         * 显示PopupWindow
         *
         * @param popupWindow
         */
        void showAtLocation(PopupWindow popupWindow);
        /**
         * 录音文件的路径
         *
         * @param filePath
         */
        void saveAudioFile(String filePath);
        /**
         * 开始录制
         */
        void startAudio();
        /**
         * 结束录制
         */
        void endAudio();
    }

    /**
     * 音频录制变化监听器
     */
    private static class AudioRecordChangeListener implements IAudioRecordListener {
        private TextView mTimerTV;
        private TextView mStateTV;
        private ImageView mStateIV;
        private PopupWindow mRecordWindow;
        private AudioRecordPlayClient audioRecordPlayClient;
        public AudioRecordChangeListener(AudioRecordPlayClient audioRecordPlayClient) {
            this.audioRecordPlayClient = audioRecordPlayClient;
        }
        /**
         * 初始化提示视图
         */
        @Override
        public void initTipView() {
            mRecordWindow = createPopupWindow();
            if (audioRecordPlayClient.getAudioResponseLister() != null) {
                audioRecordPlayClient.getAudioResponseLister().showAtLocation(mRecordWindow);
            }
        }
        /**
         * 创建PopupWindow
         *
         * @return
         */
        private PopupWindow createPopupWindow() {
            View view = View.inflate(audioRecordPlayClient.appContext, R.layout.popup_audio_wi_vo, null);
            mStateIV = (ImageView) view.findViewById(R.id.rc_audio_state_image);
            mStateTV = (TextView) view.findViewById(R.id.rc_audio_state_text);
            mTimerTV = (TextView) view.findViewById(R.id.rc_audio_timer);
            //弹出一个PopupWindow,显示正在录音。
            mRecordWindow = new PopupWindow(view, -1, -1);
            mRecordWindow.setFocusable(true);
            mRecordWindow.setOutsideTouchable(false);
            mRecordWindow.setTouchable(false);
            return mRecordWindow;
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
                if (audioRecordPlayClient.getAudioResponseLister()!=null){
                    audioRecordPlayClient.getAudioResponseLister().saveAudioFile(file.getAbsolutePath());
                }
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
    }
    /**
     * OnTouchListener的实现类来处理逻辑
     */
    private static class ImageViewTouch implements View.OnTouchListener {
        private  AudioRecordPlayClient audioRecordPlayClient;
        private boolean isCancelRecord = false;
        public ImageViewTouch(AudioRecordPlayClient audioRecordPlayClient) {
            this.audioRecordPlayClient = audioRecordPlayClient;
        }
        @Override
        public boolean onTouch(View view, MotionEvent event) {
            switch (event.getAction()) {
                //手指落下，开始录音
                case MotionEvent.ACTION_DOWN:
                    //开始录音
                    this.audioRecordPlayClient.audioRecordManager.startRecord();
                    if (audioRecordPlayClient.getAudioResponseLister()!=null){
                        audioRecordPlayClient.getAudioResponseLister().startAudio();
                    }
                    break;
                case MotionEvent.ACTION_MOVE:
                    if (Utils.isCancelled(view, event)) {
                        //将要取消录音（参与微信手指上滑）
                        isCancelRecord = true;
                        this.audioRecordPlayClient.audioRecordManager.willCancelRecord();
                    } else {
                        //继续录音（参与微信手指上滑后加下滑回到原位）
                        isCancelRecord = false;
                        this.audioRecordPlayClient.audioRecordManager.continueRecord();
                    }
                    break;
                //手指抬起，停止录音
                case MotionEvent.ACTION_UP:
                    //停止录音
                    this.audioRecordPlayClient.audioRecordManager.stopRecord();
                    //销毁录音
                    this.audioRecordPlayClient.audioRecordManager.destroyRecord();
                    if (audioRecordPlayClient.getAudioResponseLister()!=null){
                        audioRecordPlayClient.getAudioResponseLister().endAudio();
                    }
                    if (!isCancelRecord) {

                    }
                    break;
                //取消录音的情况
                case MotionEvent.ACTION_CANCEL:
                    //停止录音
                    this.audioRecordPlayClient.audioRecordManager.stopRecord();
                    //销毁录音
                    this.audioRecordPlayClient.audioRecordManager.destroyRecord();
                    if (audioRecordPlayClient.getAudioResponseLister()!=null){
                        audioRecordPlayClient.getAudioResponseLister().endAudio();
                    }
                    break;
                default:
                    break;
            }
            return true;
        }
    }
    /**
     * 一个工具类
     */
    private static class Utils {
        public static boolean isCancelled(View view, MotionEvent event) {
            int[] location = new int[2];
            view.getLocationOnScreen(location);
            if (event.getRawX() < location[0] || event.getRawX() > location[0] + view.getWidth()
                    || event.getRawY() < location[1] - 40) {
                return true;
            }
            return false;
        }
        /**
         * 创建指定目录
         *
         * @param context
         * @param dirName
         * @return
         */
        public static String createDirFile(Context context, String dirName) {
            //该库内不对文件夹是否存在进行判断
            File audioDir = BitmapUtils.getDiskFile(context, dirName);
            try {
                if (!audioDir.exists()) {
                    audioDir.mkdirs();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return audioDir.getAbsolutePath();
        }
    }
}
