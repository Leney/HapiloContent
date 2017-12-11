package com.zhongke.content.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.zhongke.content.BaseFragment;
import com.zhongke.content.R;
import com.zhongke.content.activity.AnswerActivity;
import com.zhongke.content.activity.AnswerMainActivity;
import com.zhongke.content.adapter.CompetitionAdapter;
import com.zhongke.content.entity.RoomInfoBean2;
import com.zhongke.content.retrofit.CommonException;
import com.zhongke.content.retrofit.ResponseResultListener;
import com.zhongke.content.utils.LogUtil;
import com.zhongke.content.utils.Tools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Subscription;

import static com.zhongke.content.activity.AnswerMainActivity.STAGE_ONE;

/**
 * Created by ${tanlei} on 2017/8/28.
 * <p>
 * 竞赛区的Fragment
 */

public class CompetitionFragment extends BaseFragment implements CompetitionAdapter.OnGoRoomClickListeners, View.OnClickListener {
    private ListView lvRoom;
    /**
     * 当前选中的年级
     */
    private int currentGrade;
    /**
     * 当前选中的阶段
     */
    private int currentStage;
    private ImageView tvGradeOne, tvGradeTwo, tvGradeThree, tvGradeFour, tvGradeFive, tvGradeSix;
    private ImageView ivBack, ivCreateRoom, ivFindRoom, ivFastJoin;
    private ImageButton iv1, iv2, iv3, iv4, iv5, iv6;
    CompetitionAdapter competitionAdapter;
    private List<RoomInfoBean2.RecordsBean> rooms;

    /**
     * 标识是否是新获取列表数据
     */
    private boolean isNewGet = false;

    /**
     * 当前加载的页数
     */
    private int pageIndex = 0;

    /**
     * 每页加载的条数
     */
    private static final int PAGE_SIZE = 16;
    private TextView noText;
    private TextView sure;
    private EditText homeNumber;
    private AlertDialog alertDialog;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_competition;
    }

    @Override
    protected void initAfterActivityCreated(View rootView, Bundle savedInstanceState) {
        lvRoom = rootView.findViewById(R.id.lv_room);
        tvGradeOne = rootView.findViewById(R.id.grade_one1);
        tvGradeTwo = rootView.findViewById(R.id.grade_two1);
        tvGradeThree = rootView.findViewById(R.id.grade_three1);
        tvGradeFour = rootView.findViewById(R.id.grade_four1);
        tvGradeFive = rootView.findViewById(R.id.grade_five1);
        tvGradeSix = rootView.findViewById(R.id.grade_six1);
        ivBack = rootView.findViewById(R.id.iv_back1);
        iv1 = rootView.findViewById(R.id.ib1);
        iv2 = rootView.findViewById(R.id.ib2);
        iv3 = rootView.findViewById(R.id.ib3);
        iv4 = rootView.findViewById(R.id.ib4);
        iv5 = rootView.findViewById(R.id.ib5);
        iv6 = rootView.findViewById(R.id.ib6);
        ivCreateRoom = rootView.findViewById(R.id.iv_create_room);
        ivFindRoom = rootView.findViewById(R.id.iv_find_room);
        ivFastJoin = rootView.findViewById(R.id.iv_fast_join);
        setListeners();
        //默认设置阶段一
        setStage(STAGE_ONE);
        rooms = new ArrayList<>();
        competitionAdapter = new CompetitionAdapter(rooms, getActivity());
        competitionAdapter.setOnGoRoomClickListeners(this);
        lvRoom.setAdapter(competitionAdapter);
        getData();


        getList(1, 1);
    }

    private void setListeners() {
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
        ivCreateRoom.setOnClickListener(this);
        ivFastJoin.setOnClickListener(this);
        ivFindRoom.setOnClickListener(this);
    }

    /**
     * 获取列表数据
     */
    private void getList(int stage, int grade) {
        isNewGet = stage != currentStage || grade != currentGrade;
        if (isNewGet) {
            pageIndex = 1;
        } else {
            pageIndex++;
        }
        currentGrade = grade;
        currentStage = stage;

        Map<String, Object> params = new HashMap<>();
//        params.put("token", token);
        params.put("pageIndex", pageIndex);
        params.put("pageSize", PAGE_SIZE);
        params.put("resGrade", currentGrade);
        params.put("resKind", currentStage);
        retrofitProvider.getRoomList(params, new ResponseResultListener<RoomInfoBean2>() {
            @Override
            public void success(RoomInfoBean2 roomInfoBean2) {
                Log.i("llj", "请求列表数据成功!!!");
                if (isNewGet) {
                    rooms.clear();
                    isNewGet = false;
                    if (roomInfoBean2.getRecords().isEmpty()) {
                        // TODO 显示无数据视图
                        Log.i("llj", "没有数据");
                        Toast.makeText(getActivity(), "没有相应的房间数据", Toast.LENGTH_SHORT).show();
                    }
                }
                rooms.addAll(roomInfoBean2.getRecords());
                competitionAdapter.notifyDataSetChanged();
            }

            @Override
            public void failure(CommonException e) {
                Log.e("llj", "请求列表数据失败!!!");
                if (isNewGet) {
                    //TODO 显示错误视图
                    Toast.makeText(getActivity(), e.getErrorMsg(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * 回调函数
     *
     * @param rib 拿到点击的房间对象
     */
    @Override
    public void goRoom(RoomInfoBean2.RecordsBean rib) {
        AnswerActivity.startActivity(getActivity(), rib);
    }

    private void getData() {
        Bundle arguments = getArguments();
        if (null != arguments) {

        }
    }

    /**
     * 设置年级
     *
     * @param currentGrade
     */
    private void setGrade(int currentGrade) {
        setGrade();
        switch (currentGrade) {
            case AnswerMainActivity.GRADE_ONE:
                tvGradeOne.setImageResource(R.mipmap.check1);
                break;
            case AnswerMainActivity.GRADE_TWO:
                tvGradeTwo.setImageResource(R.mipmap.check2);
                break;
            case AnswerMainActivity.GRADE_THREE:
                tvGradeThree.setImageResource(R.mipmap.check3);
                break;
            case AnswerMainActivity.GRADE_FOUR:
                tvGradeFour.setImageResource(R.mipmap.check4);
                break;
            case AnswerMainActivity.GRADE_FIVE:
                tvGradeFive.setImageResource(R.mipmap.check5);
                break;
            case AnswerMainActivity.GRADE_SIX:
                tvGradeSix.setImageResource(R.mipmap.check6);
                break;
            default:
                break;
        }
    }

    private void setGrade() {
        tvGradeOne.setImageResource(R.mipmap.uncheck1);
        tvGradeTwo.setImageResource(R.mipmap.uncheck2);
        tvGradeThree.setImageResource(R.mipmap.uncheck3);
        tvGradeFour.setImageResource(R.mipmap.uncheck4);
        tvGradeFive.setImageResource(R.mipmap.uncheck5);
        tvGradeSix.setImageResource(R.mipmap.uncheck6);
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
                iv4.setImageResource(R.drawable.stage_4_check);
                break;
            case AnswerMainActivity.STAGE_FIVE:
                iv5.setImageResource(R.drawable.stage_5_check);
                break;
            case AnswerMainActivity.STAGE_SIX:
                iv6.setImageResource(R.drawable.stage_6_check);
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
        iv4.setImageResource(R.drawable.stage_4_uncheck);
        iv5.setImageResource(R.drawable.stage_5_uncheck);
        iv6.setImageResource(R.drawable.stage_6_uncheck);
    }

    private void showQuitJoinDialog(Context context, boolean isInsert) {
        if (alertDialog == null) {
            alertDialog = new AlertDialog.Builder(
                    context).create();
        }
        alertDialog.show();
        Window window = alertDialog.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        WindowManager.LayoutParams lp = window.getAttributes();
        window.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
        window.setBackgroundDrawable(new ColorDrawable());
        lp.alpha = 1f;// 设置透明度
        // 设置不弹出软键盘
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
                | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        window.setWindowAnimations(R.style.DialogAnimation);// 设置动画效果
        window.setAttributes(lp);
        window.setContentView(R.layout.diadlog_explain_coupon);
        LinearLayout join = (LinearLayout) window.findViewById(R.id.le_join);
        LinearLayout query = (LinearLayout) window.findViewById(R.id.le_insert);
        noText = (TextView) window.findViewById(R.id.no_text);
        TextView lookLevel = (TextView) window.findViewById(R.id.look_level);
        sure = (TextView) window.findViewById(R.id.sure);
        homeNumber = (EditText) window.findViewById(R.id.home_number);
        if (isInsert) {
            query.setVisibility(View.VISIBLE);
            join.setVisibility(View.GONE);
            sure.setVisibility(View.VISIBLE);
            sure.setOnClickListener(view -> {
                String roomNumber = homeNumber.getText().toString().trim();
                queryRoom(roomNumber);
                sure.setVisibility(View.GONE);
            });
        } else {
            join.setVisibility(View.VISIBLE);
            query.setVisibility(View.GONE);
            sure.setVisibility(View.GONE);
            lookLevel.setOnClickListener(view -> {

            });
        }
        window.findViewById(R.id.cancel).setOnClickListener(view -> alertDialog.dismiss());
    }

    //查询房间
    private void queryRoom(String actionId) {
        Map<String,String> map = new HashMap<>();
//        map.put("token",token);
        map.put("actionId",actionId);
        Subscription subscription = retrofitProvider.queryRoom(map, new ResponseResultListener<RoomInfoBean2>() {
            @Override
            public void success(RoomInfoBean2 roomInfoBean2) {
//                LogUtil.e("success");
                alertDialog.dismiss();
                AnswerActivity.startActivity(getActivity(), roomInfoBean2.getRecords().get(0));

            }
            @Override
            public void failure(CommonException e) {
                LogUtil.e("failure--"+e.toString());
                homeNumber.setVisibility(View.GONE);
                noText.setText("未查找到该房间！");
                sure.setVisibility(View.GONE);
            }
        });
        compositeSubscription.add(subscription);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            //年级控件监听
            case R.id.grade_one1:
                setGrade(1);
                getList(currentStage,1);
                break;
            case R.id.grade_two1:
                setGrade(2);
                getList(currentStage,2);
                break;
            case R.id.grade_three1:
                setGrade(3);
                getList(currentStage,3);
                break;
            case R.id.grade_four1:
                setGrade(4);
                getList(currentStage,4);
                break;
            case R.id.grade_five1:
                setGrade(5);
                getList(currentStage,5);
                break;
            case R.id.grade_six1:
                setGrade(6);
                getList(currentStage,6);
                break;
            case R.id.iv_back1:
                getActivity().finish();
                break;
            //阶段控件监听
            case R.id.ib1:
                setStage(1);
                getList(1,currentGrade);
                break;
            case R.id.ib2:
                setStage(2);
                getList(2,currentGrade);
                break;
            case R.id.ib3:
                setStage(3);
                getList(3,currentGrade);
                break;
            case R.id.ib4:
                setStage(4);
                getList(4,currentGrade);
                break;
            case R.id.ib5:
                setStage(5);
                getList(5,currentGrade);
                break;
            case R.id.ib6:
                setStage(6);
                getList(6,currentGrade);
                break;
            //创建房间
            case R.id.iv_create_room:
                showQuitJoinDialog(getActivity(), false);
                break;
            //查找房间
            case R.id.iv_find_room:
                showQuitJoinDialog(getActivity(), true);
                break;
            //快速加入
            case R.id.iv_fast_join:
                AnswerActivity.startActivity(getActivity(), rooms.get(Tools.getRandomValue(0, rooms.size())));
                break;
            default:
                break;
        }
    }

}
