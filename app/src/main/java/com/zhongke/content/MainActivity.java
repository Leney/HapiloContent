package com.zhongke.content;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.zhongke.account.control.AccountStateManager;
import com.zhongke.content.activity.ActivityDetailActivity;
import com.zhongke.content.activity.ActivityListActivity;
import com.zhongke.content.activity.AnswerMainActivity;
import com.zhongke.content.activity.DesireActivity;
import com.zhongke.content.activity.LiveLobbyActivity;
import com.zhongke.content.activity.MineHomeActivity;
import com.zhongke.content.activity.MyExerciseActivity;
import com.zhongke.content.activity.RewardActivity;
import com.zhongke.content.dialog.HumidityDialog;
import com.zhongke.content.dialog.InfraredDialog;
import com.zhongke.content.dialog.LightIntensityDialog;
import com.zhongke.content.dialog.OpenDeskLampDialog;
import com.zhongke.content.dialog.TemperatureDialog;
import com.zhongke.content.utils.LogUtil;
import com.zhongke.content.utils.SystemBarUtils;
import com.zhongke.content.view.FaceCheckGongingDialog;

/**
 * Created by ${xingen} on 2017/7/31.
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        showDialog();
//        showReceiveAwardDialog();
        setSystemUIChangeListener();

        String token = AccountStateManager.getInstance().getAccountInfo().getToken();
        LogUtil.e("token--" + token);
    }

//    private ReceiveAwardDialog receiveAwardDialog;

//    private void showReceiveAwardDialog() {
//        this.receiveAwardDialog = new ReceiveAwardDialog(this);
//        this.receiveAwardDialog.show();
//    }

//    private AnswerFailureDialog answerFailureDialog;

//    private void showAnswerFailureDialog() {
//        answerFailureDialog = new AnswerFailureDialog(this);
//        answerFailureDialog.show();
//    }

//    private AnswerRewardDialog answerRewardDialog;

    //    private void showScoreDialog() {
//
//        answerRewardDialog = new AnswerRewardDialog(this);
//        if (!answerRewardDialog.isShowing()) {
//            answerRewardDialog.show();
//        }
//    }
    private void showDialog() {
        FaceCheckGongingDialog dialog = new FaceCheckGongingDialog(this, this);
        dialog.show();
    }

    private void initView() {
        this.findViewById(R.id.main_btn1).setOnClickListener(this);
        this.findViewById(R.id.main_btn2).setOnClickListener(this);
        this.findViewById(R.id.main_btn3).setOnClickListener(this);
        this.findViewById(R.id.main_btn4).setOnClickListener(this);
        this.findViewById(R.id.main_btn5).setOnClickListener(this);
        this.findViewById(R.id.main_btn6).setOnClickListener(this);
        this.findViewById(R.id.main_btn7).setOnClickListener(this);
        this.findViewById(R.id.main_btn8).setOnClickListener(this);
        this.findViewById(R.id.main_btn9).setOnClickListener(this);
        this.findViewById(R.id.main_btn10).setOnClickListener(this);
        this.findViewById(R.id.main_btn11).setOnClickListener(this);
        this.findViewById(R.id.main_btn12).setOnClickListener(this);
        this.findViewById(R.id.button).setOnClickListener(this);
    }

    /**
     * 监听系统UI的显示，进行特殊处理
     */
    private void setSystemUIChangeListener() {
        getWindow().getDecorView().setOnSystemUiVisibilityChangeListener(visibility -> {
            if ((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) {  //当系统UI显示的时候（例如输入法显示的时候），再次隐藏
                SystemBarUtils.setStickyStyle(getWindow());
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.main_btn1:
                // 直播大厅
                LiveLobbyActivity.startActivity(MainActivity.this);
//                RewardActivity.startActivity(this);
//                LivePushActivity.startActivity(this);
//                startActivity(new Intent(MainActivity.this, AnswerActivity.class));
                break;
            case R.id.main_btn2:
                // 我的活动
                startActivity(new Intent(this, MyExerciseActivity.class));
//                ActDetailActivity.startActivity(this, 1);
                break;
            case R.id.main_btn3:
                // 我的家
//                EventDetailActivity.startEventDetailActivity(this);
                MineHomeActivity.startActivity(MainActivity.this);
                break;
            case R.id.main_btn4:
                // 我的愿望
                DesireActivity.startActivity(MainActivity.this);
                break;
            case R.id.main_btn5:
                // 抢答活动
                AnswerMainActivity.startActivity(MainActivity.this);
                break;
            case R.id.main_btn6:
                // 获得奖励
                RewardActivity.startActivity(MainActivity.this);
                break;
            case R.id.main_btn7:
                // 新版活动详情
                ActivityDetailActivity.startActivity(MainActivity.this, 117, "美食之旅", "2017-11-09 18:14:52", "2017-11-30 18:14:54");
                break;
            case R.id.main_btn8:
                OpenDeskLampDialog deskLampDialog = new OpenDeskLampDialog(this, R.style.preview_dialog, null);
                deskLampDialog.show();
                break;
            case R.id.main_btn9:
                LightIntensityDialog lightIntensityDialog = new LightIntensityDialog(this, R.style.preview_dialog, null);
                lightIntensityDialog.show();
                break;
            case R.id.main_btn10:
                TemperatureDialog temperatureDialog = new TemperatureDialog(this, R.style.preview_dialog, null);
                temperatureDialog.show();
                break;
            case R.id.main_btn11:
                HumidityDialog humidityDialog = new HumidityDialog(this, R.style.preview_dialog, null);
                humidityDialog.show();
                break;
            case R.id.main_btn12:
                InfraredDialog infraredDialog = new InfraredDialog(this, R.style.preview_dialog, null);
                infraredDialog.show();
                break;
            case R.id.button:
                // 新版活动列表
                ActivityListActivity.startActivity(MainActivity.this);
                break;
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        //焦点改变的时候，当Home键退出，重新从新进入等情况的处理。
        SystemBarUtils.setStickyStyle(getWindow());
    }
}
