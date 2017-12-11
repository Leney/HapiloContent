package com.zhongke.content.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.zhongke.content.BaseActivity;
import com.zhongke.content.R;
import com.zhongke.content.fragment.CompletedExerciseFragment;
import com.zhongke.content.fragment.NotStartExerciseFragment;
import com.zhongke.content.fragment.OnGoingExerciseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${tanlei} on 2017/9/25.
 * 我的活动界面
 */

public class MyExerciseActivity extends BaseActivity implements View.OnClickListener {
    /**
     * 碎片管理器
     */
    private FragmentManager manager;
    /**
     * 三个按钮
     */
    private Button tvToBeCompleted, tvRunning, tvCompleted;
    /**
     * 用来装碎片的数组
     */
    private Fragment[] fragments = new Fragment[3];
    private List<Button> buts = new ArrayList<>();

    private ImageView closeBtn;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_exercise;
    }

    @Override
    protected void initView(Bundle bundle) {

        closeBtn = (ImageView) findViewById(R.id.my_exercise_close_btn);
        closeBtn.setOnClickListener(this);

        tvToBeCompleted = (Button) findViewById(R.id.tv_to_be_completed);
        tvToBeCompleted.setOnClickListener(this);
        tvRunning = (Button) findViewById(R.id.tv_processing);
        tvRunning.setOnClickListener(this);
        tvCompleted = (Button) findViewById(R.id.tv_completed);
        tvCompleted.setOnClickListener(this);
        buts.add(tvToBeCompleted);
        buts.add(tvRunning);
        buts.add(tvCompleted);
        showFragment(0);
//        checkPosition(0);
    }

    @Override
    protected void init() {
        manager = getSupportFragmentManager();
    }

    /**
     * 创建Fragment的方法
     *
     * @param position
     * @return
     */
    private Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new NotStartExerciseFragment();
            case 1:
                return new OnGoingExerciseFragment();
            case 2:
                return new CompletedExerciseFragment();
            default:
                break;
        }
        return null;
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.tv_to_be_completed:
                showFragment(0);
//                checkPosition(0);
                break;
            case R.id.tv_completed:
                showFragment(2);
//                checkPosition(2);
                break;
            case R.id.tv_processing:
                showFragment(1);
//                checkPosition(1);
                break;
            case R.id.my_exercise_close_btn:
                finish();
                break;
            default:
                break;
        }
    }

    /**
     * 显示fragment
     *
     * @param position
     */
    private void showFragment(int position) {
        FragmentTransaction fragmentTransaction = manager.beginTransaction();
        hideFragment(fragmentTransaction);

        if (fragments[position] == null) {
            fragments[position] = createFragment(position);
            fragmentTransaction.add(R.id.ll_fragment_group, fragments[position]);
        } else {
            fragmentTransaction.show(fragments[position]);
        }
        fragmentTransaction.commitAllowingStateLoss();

        checkPosition(position);
    }

    /**
     * 影藏存在的Fragment
     */
    private void hideFragment(FragmentTransaction fragmentTransaction) {
        for (Fragment fragment : fragments) {
            if (fragment != null) {
                fragmentTransaction.hide(fragment);
            }
        }
    }

    /**
     * 根据选中的背景设置背景图
     *
     * @param position
     */
    private void checkPosition(int position) {
        for (int i = 0; i < buts.size(); i++) {
            if (i == position) {
                buts.get(i).setBackgroundResource(R.drawable.exercise_check);
            } else {
                buts.get(i).setBackgroundResource(R.drawable.exercise_uncheck);
            }
        }
    }
}
