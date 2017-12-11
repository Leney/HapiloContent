package com.zhongke.content.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhongke.content.BaseFragment;
import com.zhongke.content.R;
import com.zhongke.content.activity.AnswerMainActivity;
import com.zhongke.content.adapter.ExerciseAdapter;
import com.zhongke.content.entity.RoomInfoBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${tanlei} on 2017/8/28.
 */

/**
 * 练习区的Fragment
 */
public class ExerciseFragment extends BaseFragment implements View.OnClickListener, ExerciseAdapter.OnGoExerciseClickListeners {
    private ListView lvRoom;
    /**
     * 当前选中的年级
     */
    private int currentGrade;
    /**
     * 当前选中的阶段
     */
    private int currentStage;

    private RelativeLayout rlGrade;
    private TextView tvGradeOne, tvGradeTwo, tvGradeThree, tvGradeFour, tvGradeFive, tvGradeSix;
    private ImageView ivBack, ivFastJoin;
    private ImageButton iv1, iv2, iv3, iv4, iv5, iv6;
    ExerciseAdapter exerciseAdapter;
    private List<RoomInfoBean> rooms;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_exercise;
    }

    @Override
    protected void initAfterActivityCreated(View rootView, Bundle savedInstanceState) {
        lvRoom = rootView.findViewById(R.id.lv_room);
        rlGrade = rootView.findViewById(R.id.rl_grade);
        tvGradeOne = rootView.findViewById(R.id.grade_one);
        tvGradeTwo = rootView.findViewById(R.id.grade_two);
        tvGradeThree = rootView.findViewById(R.id.grade_three);
        tvGradeFour = rootView.findViewById(R.id.grade_four);
        tvGradeFive = rootView.findViewById(R.id.grade_five);
        tvGradeSix = rootView.findViewById(R.id.grade_six);
        ivBack = rootView.findViewById(R.id.iv_back);
        iv1 = rootView.findViewById(R.id.ib1);
        iv2 = rootView.findViewById(R.id.ib2);
        iv3 = rootView.findViewById(R.id.ib3);
        iv4 = rootView.findViewById(R.id.ib4);
        iv5 = rootView.findViewById(R.id.ib5);
        iv6 = rootView.findViewById(R.id.ib6);
        ivFastJoin = rootView.findViewById(R.id.iv_fast_join);
        tvGradeOne.setOnClickListener(this);
        tvGradeTwo.setOnClickListener(this);
        tvGradeThree.setOnClickListener(this);
        tvGradeFour.setOnClickListener(this);
        tvGradeFive.setOnClickListener(this);
        tvGradeSix.setOnClickListener(this);
        ivBack.setOnClickListener(this);
        iv1.setOnClickListener(this);
        iv2.setOnClickListener(this);
        iv3.setOnClickListener(this);
        iv4.setOnClickListener(this);
        iv5.setOnClickListener(this);
        iv6.setOnClickListener(this);
        ivFastJoin.setOnClickListener(this);
        //默认设置阶段一
        setStage(AnswerMainActivity.STAGE_ONE);
        rooms = new ArrayList<>();
        rooms.add(new RoomInfoBean(1, "巅峰对决，不服来战", 0, 15, 15));
        rooms.add(new RoomInfoBean(2, "巅峰对决，不服来战，战个痛快", 1, 19, 12));
        rooms.add(new RoomInfoBean(3, "巅峰对决，不服来战，蛮王开大五秒无敌", 1, 15, 12));
        rooms.add(new RoomInfoBean(4, "华山之巅", 0, 13, 13));
        rooms.add(new RoomInfoBean(5, "come baby", 1, 13, 10));
        rooms.add(new RoomInfoBean(6, "武林第一高手", 0, 13, 13));
        rooms.add(new RoomInfoBean(7, "巅峰对决，不服来战", 0, 15, 15));
        rooms.add(new RoomInfoBean(8, "巅峰对决，不服来战，战个痛快", 1, 19, 12));
        rooms.add(new RoomInfoBean(9, "巅峰对决，不服来战，蛮王开大五秒无敌", 1, 15, 12));
        rooms.add(new RoomInfoBean(10, "华山之巅", 0, 13, 13));
        rooms.add(new RoomInfoBean(11, "come baby", 1, 13, 10));
        rooms.add(new RoomInfoBean(12, "武林第一高手", 0, 13, 13));
        exerciseAdapter = new ExerciseAdapter(rooms, getActivity());
        lvRoom.setAdapter(exerciseAdapter);
    }

    /**
     * 设置年级
     *
     * @param currentGrade
     */
    private void setGrade(int currentGrade) {
        switch (currentGrade) {
            case AnswerMainActivity.GRADE_ONE:
                rlGrade.setBackgroundResource(R.mipmap.bg_check1);
                break;
            case AnswerMainActivity.GRADE_TWO:
                rlGrade.setBackgroundResource(R.mipmap.bg_check2);
                break;
            case AnswerMainActivity.GRADE_THREE:
                rlGrade.setBackgroundResource(R.mipmap.bg_check3);
                break;
            case AnswerMainActivity.GRADE_FOUR:
                rlGrade.setBackgroundResource(R.mipmap.bg_check4);
                break;
            case AnswerMainActivity.GRADE_FIVE:
                rlGrade.setBackgroundResource(R.mipmap.bg_check5);
                break;
            case AnswerMainActivity.GRADE_SIX:
                rlGrade.setBackgroundResource(R.mipmap.bg_check6);
                break;
            default:
                break;
        }
    }

    /**
     * 设置阶段
     *
     * @param position
     */
    private void setStage(int position) {
        setImage();
        switch (position) {
            case AnswerMainActivity.STAGE_ONE:
                iv1.setImageResource(R.drawable.stage_1_check);
                break;
            case AnswerMainActivity.STAGE_TWO:
                iv2.setImageResource(R.drawable.stage_2_check);
                break;
            case AnswerMainActivity.STAGE_THREE:
                iv3.setImageResource(R.drawable.stage_3_check);
                break;
            case AnswerMainActivity.STAGE_FOUR:
                iv4.setImageResource(R.drawable.stage_6_check);
                break;
            case AnswerMainActivity.STAGE_FIVE:
                iv5.setImageResource(R.drawable.stage_5_check);
                break;
            case AnswerMainActivity.STAGE_SIX:
                iv6.setImageResource(R.drawable.stage_4_check);
                break;
            default:
                break;
        }
    }

    /**
     * 把控件全部设置为未选中状态
     */
    private void setImage() {
        iv1.setImageResource(R.drawable.stage_1_uncheck);
        iv2.setImageResource(R.drawable.stage_2_uncheck);
        iv3.setImageResource(R.drawable.stage_3_uncheck);
        iv4.setImageResource(R.drawable.stage_6_uncheck);
        iv5.setImageResource(R.drawable.stage_5_uncheck);
        iv6.setImageResource(R.drawable.stage_4_uncheck);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            //年级控件监听
            case R.id.grade_one:
                currentGrade = 1;
                setGrade(currentGrade);
                break;
            case R.id.grade_two:
                currentGrade = 2;
                setGrade(currentGrade);
                break;
            case R.id.grade_three:
                currentGrade = 3;
                setGrade(currentGrade);
                break;
            case R.id.grade_four:
                currentGrade = 4;
                setGrade(currentGrade);
                break;
            case R.id.grade_five:
                currentGrade = 5;
                setGrade(currentGrade);
                break;
            case R.id.grade_six:
                currentGrade = 6;
                setGrade(currentGrade);
                break;
            case R.id.iv_back:
                getActivity().finish();
                break;
            //阶段控件监听
            case R.id.ib1:
                currentStage = 1;
                setStage(currentStage);
                break;
            case R.id.ib2:
                currentStage = 2;
                setStage(currentStage);
                break;
            case R.id.ib3:
                currentStage = 3;
                setStage(currentStage);
                break;
            case R.id.ib4:
                currentStage = 4;
                setStage(currentStage);
                break;
            case R.id.ib5:
                currentStage = 5;
                setStage(currentStage);
                break;
            case R.id.ib6:
                currentStage = 6;
                setStage(currentStage);
                break;
            //快速加入
            case R.id.iv_fast_join:
                // 暂时随机获取一个房间号
//                AnswerActivity.startActivity(getActivity(), rooms.get(Tools.getRandomValue(0, rooms.size())));
                break;
            default:
                break;
        }
    }

    //回调函数
    @Override
    public void goExercise(RoomInfoBean rib) {

    }
}
