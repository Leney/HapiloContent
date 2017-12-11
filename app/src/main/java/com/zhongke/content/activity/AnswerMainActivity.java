package com.zhongke.content.activity;

/**
 * Created by ${tanlei} on 2017/8/26.
 */

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.zhongke.content.BaseActivity;
import com.zhongke.content.R;
import com.zhongke.content.fragment.CompetitionFragment;
import com.zhongke.content.fragment.ExerciseFragment;
/**
 * Created by ${tanlei} on 2017/8/26.
 *
 *抢答活动的主界面
 */
public class AnswerMainActivity extends BaseActivity {
    public final String TAG = getClass().getSimpleName();
    /**
     * 级别常量
     */
    //一年级
    public static final int GRADE_ONE = 1;
    //二年级
    public static final int GRADE_TWO = 2;
    //三年级
    public static final int GRADE_THREE = 3;
    //四年级
    public static final int GRADE_FOUR = 4;
    //五年级
    public static final int GRADE_FIVE = 5;
    //六年级
    public static final int GRADE_SIX = 6;
    /**
     * 类型常量
     */
    public static final int STAGE_ONE = 1;
    public static final int STAGE_TWO = 2;
    public static final int STAGE_THREE = 3;
    public static final int STAGE_FOUR = 4;
    public static final int STAGE_FIVE = 5;
    public static final int STAGE_SIX = 6;
    //    private RadioGroup rgArea;
//    private RadioButton rbExercise, rbCompetition;
    private Fragment[] fragments = new Fragment[2];
    private FragmentManager fragmentManager = getSupportFragmentManager();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main_answer;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected void init() {
//        rgArea.setOnCheckedChangeListener(this);
        showFragment(0);
    }


    private void hideFragment() {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        for (int i = 0; i < fragments.length; i++) {
            if (fragments[i] != null) {
                fragmentTransaction.hide(fragments[i]);
            }
        }
        fragmentTransaction.commitAllowingStateLoss();
    }

    /**
     * 创建Fragment
     *
     * @param position
     * @return
     */
    private Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new CompetitionFragment();
            case 1:
                return new ExerciseFragment();
            default:
                break;
        }
        return null;
    }

    /**
     * 显示Fragment
     *
     * @param position
     */
    private void showFragment(int position) {
        hideFragment();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (fragments[position] == null) {
            fragments[position] = createFragment(position);
            fragmentTransaction.add(R.id.rl_fragment, fragments[position], "CompetitionFragment");
        } else {
            fragmentTransaction.show(fragments[position]);
        }
        fragmentTransaction.commitAllowingStateLoss();
    }

//    /**
//     * 左边两个控件的点击监听事件
//     *
//     * @param radioGroup
//     * @param i
//     */
//    @Override
//    public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
//        hideFragment();
//        switch (i) {
//            case R.id.rb_exercise:
//                rbExercise.setChecked(true);
//                showFragment(0);
//                break;
//            case R.id.rb_competition:
//                rbCompetition.setChecked(true);
//                showFragment(1);
//                break;
//            default:
//                break;
//        }
//    }

    public static void startActivity(Context context){
        context.startActivity(new Intent(context,AnswerMainActivity.class));
    }
}
