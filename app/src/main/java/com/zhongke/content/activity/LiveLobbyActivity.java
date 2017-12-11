package com.zhongke.content.activity;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhongke.content.BaseActivity;
import com.zhongke.content.R;
import com.zhongke.content.fragment.LiveLobbyFragment;
import com.zhongke.content.view.StrokeTextViewLayout;

/**
 * Created by ${xinGen} on 2017/9/27.
 * <p>
 * 直播大厅
 */

public class LiveLobbyActivity extends BaseActivity implements StrokeTextViewLayout.StrokeTextViewResponseListener,View.OnClickListener {
    private final String TAG = LiveLobbyActivity.class.getSimpleName();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_livelobby;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    private ImageView rotate_shake_iv;
    private TextView showSelect_tv;
    private ImageView backBtn;

    @Override
    protected void init() {
        this.rotate_shake_iv = (ImageView) findViewById(R.id.live_lobby_rorate_shake);
    /*    this.showSelect_tv = (TextView) findViewById(R.id.live_lobby_top_text_bg);*/
        this.backBtn = (ImageView) findViewById(R.id.live_lobby_back_btn);
        this.backBtn.setOnClickListener(this);
      /*  ((StrokeTextViewLayout) findViewById(R.id.live_lobby_StrokeTextViewLayout)).setStrokeTextViewResponseListener(this);*/
        getSupportFragmentManager().beginTransaction().add(R.id.live_lobby_switch_content_layout, LiveLobbyFragment.newInstance(), LiveLobbyFragment.TAG).commitAllowingStateLoss();
    }

    /**
     * 提醒滚动改变，且执行一个动画
     *
     * @param dy
     */
    public void notifyRotate(float dy) {
        if (animator != null && animator.isRunning()) {
            animator.cancel();
        }
        startRotateAnimator(calculationRotationAngle(dy));
    }

    protected ObjectAnimator animator;

    /**
     * 计算滚动角度
     * <p>
     * 圆周长： C = π ×d 或者C=2×π×r（其中d是圆的直径，r是圆的半径）。
     *
     * @param dy
     * @return
     */

    private float calculationRotationAngle(float dy) {
        float endAngle = 0;
        BitmapDrawable bitmapDrawable = (BitmapDrawable) rotate_shake_iv.getDrawable();
        if (bitmapDrawable != null) {
            Bitmap bitmap = bitmapDrawable.getBitmap();
            int width = bitmap.getWidth();
            // 周长
            double perimeter = width * Math.PI;
            // double perimeter=width;
            endAngle = (float) (rotate_shake_iv.getRotation() + (dy / perimeter * 360));
            Log.i(TAG, " 计算后的角度" + endAngle + " 控件原本角度 " + rotate_shake_iv.getRotation() + " 滑动距离：" + dy + "  周长: " + perimeter);
        }
        return endAngle;
    }

    protected void startRotateAnimator(float newRotation) {
        rotate_shake_iv.setRotation(newRotation);

    }

    @Override
    public void response(String name) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(name.substring(0, 1));
        stringBuilder.append(" ");
        stringBuilder.append(name.substring(1, name.length()));
        this.showSelect_tv.setText(stringBuilder);
    }

    /**
     * 开启活动
     *
     * @param context
     */
    public static void startActivity(Context context) {
        context.startActivity(new Intent(context, LiveLobbyActivity.class));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.live_lobby_back_btn:
                // 返回按钮
                finish();
                break;
            default:
                break;
        }
    }
}
