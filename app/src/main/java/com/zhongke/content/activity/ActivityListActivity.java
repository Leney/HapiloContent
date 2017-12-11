package com.zhongke.content.activity;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;

import com.zhongke.content.BaseActivity;
import com.zhongke.content.R;
import com.zhongke.content.fragment.ActivityHallFragment;
import com.zhongke.content.fragment.ActivityMineFragment;

/**
 * Created by dgg1 on 2017/10/31.
 * 活动列表界面
 */

public class ActivityListActivity extends BaseActivity implements View.OnClickListener {
    private Fragment[] fragments = new Fragment[2];
    private FragmentManager manager = getSupportFragmentManager();
    private ImageView activityHall;
    private ImageView activityMine;
    private ImageView ivBack;
    private ImageView rotate_shake_iv;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_list_activity;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        activityHall = (ImageView) findViewById(R.id.iv_activity_hall);
        rotate_shake_iv = (ImageView) findViewById(R.id.live_lobby_rorate_shake);
        activityMine = (ImageView) findViewById(R.id.iv_activity_mine);
        ivBack = (ImageView) findViewById(R.id.activity_list_back);

        activityHall.setOnClickListener(this);
        activityMine.setOnClickListener(this);
        ivBack.setOnClickListener(this);
        FragmentTransaction t = manager.beginTransaction();
        showFragment(1, t);
    }

    @Override
    protected void init() {

    }

    public static void startActivity(Context context) {
        context.startActivity(new Intent(context, ActivityListActivity.class));
    }

    /**
     * 创建Fragment
     *
     * @param position
     * @return
     */
    private Fragment createFragment(int position) {
        if (position == 1) {
            return new ActivityHallFragment();
        } else {
            return new ActivityMineFragment();
        }
    }

    private void showFragment(int position, FragmentTransaction fragmentTransaction) {
        if (fragments[position] == null) {
            fragments[position] = createFragment(position);
            fragmentTransaction.add(R.id.activity_list_layout, fragments[position]);
        } else {
            fragmentTransaction.show(fragments[position]);
        }
        fragmentTransaction.commitAllowingStateLoss();
    }

    private void hideFragment(FragmentTransaction fragmentTransaction) {
        for (Fragment fragment : fragments) {
            if (fragment != null) {
                fragmentTransaction.hide(fragment);
            }
        }
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
//            Log.i(TAG, " 计算后的角度" + endAngle + " 控件原本角度 " + rotate_shake_iv.getRotation() + " 滑动距离：" + dy + "  周长: " + perimeter);
        }
        return endAngle;
    }

    protected void startRotateAnimator(float newRotation) {
        rotate_shake_iv.setRotation(newRotation);

    }

    @Override
    public void onClick(View view) {
        FragmentTransaction t = manager.beginTransaction();
        hideFragment(t);
        if (view.getId() == R.id.iv_activity_hall) {
            activityHall.setImageResource(R.drawable.check_activity_list_hall);
            activityMine.setImageResource(R.drawable.uncheck_mine_activity_list);
            showFragment(1, t);
        } else if (view.getId() == R.id.iv_activity_mine) {
            activityHall.setImageResource(R.drawable.uncheck_activity_list_hall);
            activityMine.setImageResource(R.drawable.check_mine_activity_list);
            showFragment(0, t);
        } else if (view.getId() == R.id.activity_list_back) {
            finish();
        }
    }
}
