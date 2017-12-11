package com.zhongke.content.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhongke.content.BaseFragment;
import com.zhongke.content.R;
import com.zhongke.content.entity.ContestantsBean;
import com.zhongke.content.entity.ExaminationQuestion;
import com.zhongke.content.entity.PartInBean2;
import com.zhongke.content.im.extra.message.QuestionResult;
import com.zhongke.content.im.extra.message.QuestionScramble;
import com.zhongke.content.retrofit.CommonException;
import com.zhongke.content.retrofit.ResponseResultListener;
import com.zhongke.content.utils.LogUtil;
import com.zhongke.content.view.AnswerFailureDialog;
import com.zhongke.content.view.AnswerRewardDialog;
import com.zhongke.content.view.CountDownTextView;
import com.zhongke.content.view.FocusUserIcon;
import com.zhongke.content.view.OptionsLayout2;
import com.zhongke.content.view.PartInLayout2;
import com.zhongke.content.view.ReceiveAwardDialog;
import com.zhongke.content.view.TimeOutDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 答题Fragment
 * Created by llj on 2017/8/26.
 */

public class AnswerFragment extends BaseFragment implements View.OnClickListener, FocusUserIcon.ClickLikeListener {
    private static final String TAG = AnswerFragment.class.getSimpleName();
    private TextView questionText;
    private OptionsLayout2 mOptionsLay;
    /**
     * 抢答按钮
     */
    private ImageView quickAnswerBtn;
    /**
     * 倒计时控件
     */
    private CountDownTextView countDownClock;
    /**
     * 超时dialog
     */
    private TimeOutDialog timeOutDialog;
    /**
     * 奖励dialog
     */
    private AnswerRewardDialog rewardDialog;
    /**
     * 回答错误dialog
     */
    private AnswerFailureDialog failureDialog;
    /**
     * 参与人员列表layout
     */
    private PartInLayout2 partInLayout;

    /**
     * 当前试题显示的position
     */
    private int curQuestionPosition = -1;

    /**
     * 活动id
     */
    public String roomId;

    /**
     * 参与人员列表
     */
    private List<ContestantsBean.ActUserListBean> partInMemberList;

    /**
     * 考题实体
     */
    private ExaminationQuestion mPaper;

    /**
     * 下一题
     */
    private static final int NEXT_CODE = 1;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == NEXT_CODE) {
                // 显示下一题
                Bundle bundle = msg.getData();
                int position = bundle.getInt("nextPosition", -1);
                if (position < 0) {
                    return;
                }
                if (rewardDialog != null) {
                    rewardDialog.dismiss();
                }
                if (failureDialog != null) {
                    failureDialog.dismiss();
                }
                if (timeOutDialog != null) {
                    timeOutDialog.dismiss();
                }
                // 切换题目
                changeNewTopic(position);
            }
        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_answer;
    }

    public static AnswerFragment newInstance(String roomId) {
//        Bundle args = new Bundle();
//        args.putString("roomId",roomId);
        AnswerFragment fragment = new AnswerFragment();
        fragment.roomId = roomId;
//        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments()!=null) {
//            roomId = getArguments().getString("roomId");
//        }
    }

    @Override
    protected void initAfterActivityCreated(View rootView, Bundle savedInstanceState) {
        partInMemberList = new ArrayList<>();
//        getParticipants(roomId);
        initView(rootView);
    }

    private void initView(View rootView) {
        LogUtil.i(TAG, "抢答视图,initView()！！！");

        questionText = rootView.findViewById(R.id.answer_question);
        mOptionsLay = rootView.findViewById(R.id.answer_options_lay);

        mOptionsLay.setOnSelectResultListener(new OptionsLayout2.OnSelectResultListener() {
            @Override
            public void onResult(boolean isRight) {
                // 点击确定答案之后，答案的正确与否的回调
                LogUtil.i(TAG, "选择的答案是否正确------>>>" + isRight);
                // 停止倒计时
                countDownClock.stop();

                if (isRight) {
                    // 回答正确
                    if (rewardDialog == null) {
                        rewardDialog = new AnswerRewardDialog(getActivity());
                        rewardDialog.setCanceledOnTouchOutside(true);
                        rewardDialog.show();
                    } else {
                        rewardDialog.reStartShow();
                    }
                } else {
                    // 回答错误
                    if (failureDialog == null) {
                        failureDialog = new AnswerFailureDialog(getActivity());
                        failureDialog.setCanceledOnTouchOutside(true);
                        failureDialog.show();
                    } else {
                        failureDialog.reStartShow();
                    }
                }

                // 判断是否还有下一题
                if (curQuestionPosition >= mPaper.getQution().size() - 1) {
                    // 是最后一题了
                    // TODO 发送活动结束的消息
                } else {
                    // 还有下一题
                    // TODO 发送结果消息
                }
            }
        });


        quickAnswerBtn = rootView.findViewById(R.id.answer_quick_answer_btn);
        quickAnswerBtn.setOnClickListener(this);

        countDownClock = rootView.findViewById(R.id.answer_count_down_view);
        countDownClock.setOnDoneListener(new CountDownTextView.OnCountDownDoneListener() {
            @Override
            public void onDone() {
                // 倒计时时间到(超时)
                if (timeOutDialog == null) {
                    timeOutDialog = new TimeOutDialog(getActivity());
                    timeOutDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialogInterface) {
                            if (failureDialog == null) {
                                failureDialog = new AnswerFailureDialog(getActivity());
                                failureDialog.setCanceledOnTouchOutside(true);
                            }
                            failureDialog.show();
                        }
                    });
                }
                timeOutDialog.show();
                // 设置选项不可点击
                mOptionsLay.setCanClick(false);
                // 设置当前没有答题人
                partInLayout.setAnswerPosition(-1);
            }
        });

        partInLayout = rootView.findViewById(R.id.part_in_list_lay);
    }

    /**
     * 点击了点赞 userId 被点赞人 thumbUpObjectId 因为什么被点赞的对象ID thumbUpType 点赞类型，因为什么被点赞（1.竞赛2.心声）
     */
    @Override
    public void clickLike(ContestantsBean.ActUserListBean bean) {
        Map<String, String> map = new HashMap<>();
        map.put("userId", bean.getUserId() + "");
        map.put("thumbUpObjectId", bean.getActionId() + "");
        map.put("thumbUpType", 1 + "");
        retrofitProvider.clickLike(map, new ResponseResultListener<PartInBean2>() {
            @Override
            public void success(PartInBean2 partInBean2) {
                LogUtil.e("success");
            }

            @Override
            public void failure(CommonException e) {
                LogUtil.e("failure---" + e.getErrorMsg());
            }
        });
    }

    /**
     * 设置试题对象数据
     *
     * @param paper
     */
    public void setPaper(ExaminationQuestion paper) {
        this.mPaper = paper;
    }


    /**
     * 设置参与人员列表
     *
     * @param memberList
     */
    public void setJoinMemberList(List<ContestantsBean.ActUserListBean> memberList) {
        if (partInMemberList == null) {
            partInMemberList = new ArrayList<>();
        }
        partInMemberList.clear();
        partInMemberList.addAll(memberList);
        // 添加视图
        partInLayout.addViews(partInMemberList);
    }

    /**
     * 切换答题题目
     *
     * @param position
     */
    public void changeNewTopic(int position) {
        curQuestionPosition = position;
        if(position < 0){
            // 还没有开始
            // 设置题目名称
            questionText.setText("请稍候...");
        }else {
            ExaminationQuestion.QutionBean qutionBean = mPaper.getQution().get(position);
            // 设置题目名称
            questionText.setText(qutionBean.getName());
            // 设置题目选项
            mOptionsLay.setOptions(qutionBean);
        }
        // 倒计时控件暂时隐藏
        countDownClock.setVisibility(View.INVISIBLE);
        // 设置没有答题人员
        partInLayout.setAnswerPosition(-1);
        // 设置抢答按钮是否显示
        quickAnswerBtn.setVisibility(position >= 0 ? View.VISIBLE:View.GONE);
    }

    /**
     * 处理有人抢到答题权的消息
     *
     * @param scramble
     */
    public void raceResult(QuestionScramble scramble) {
        // 设置正在答题的人员
        partInLayout.setAnswerUserId(scramble.userId);
        // 设置倒计时时间
        countDownClock.start(scramble.countdownTime);
        countDownClock.setVisibility(View.VISIBLE);
    }

    /**
     * 处理答题结果的消息
     *
     * @param result
     */
    public void answerResult(QuestionResult result) {
        //显示相关答题结果展示界面，并且界面展示多久时间，判断是否还有下一题，处理活动结束相关逻辑
        // 拿到对方所答题目的对象
        ExaminationQuestion.QutionBean qutionBean = mPaper.getQution().get(result.currentQuestionId);
        // 当前所显示的题目对象
        ExaminationQuestion.QutionBean curQutionBean = mOptionsLay.getCurQutionBean();
        // 对比两个题目对象是否是同一题
        countDownClock.stop();
        if (qutionBean.getId() == curQutionBean.getId()) {
            // 是同一题目
            LogUtil.i("llj", "是同一题目!!!");
            if (result.answerResult < 0) {
                // 答题超时
                if (timeOutDialog == null) {
                    timeOutDialog = new TimeOutDialog(getActivity());
//                    timeOutDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
//                        @Override
//                        public void onCancel(DialogInterface dialogInterface) {
//                            if (failureDialog == null) {
//                                failureDialog = new AnswerFailureDialog(AnswerActivity.this);
//                                failureDialog.setCanceledOnTouchOutside(true);
//                            }
//                            failureDialog.show();
//                        }
//                    });
                }
                timeOutDialog.show();
            } else {
                // 获得选项答案对象
                ExaminationQuestion.QutionBean.AnswerBean answerBean = curQutionBean.getAnswer().get(result.answerResult);
                if (answerBean.getIsRight() == 1) {
                    // 回答正确
                    if (rewardDialog == null) {
                        rewardDialog = new AnswerRewardDialog(getActivity());
                        rewardDialog.setCanceledOnTouchOutside(true);
                        rewardDialog.show();
                    } else {
                        rewardDialog.reStartShow();
                    }
                } else {
                    // 回答错误
                    if (failureDialog == null) {
                        failureDialog = new AnswerFailureDialog(getActivity());
                        failureDialog.setCanceledOnTouchOutside(true);
                        failureDialog.show();
                    } else {
                        failureDialog.reStartShow();
                    }
                }
            }
        } else {
            // 不是同一题目
            LogUtil.i("llj", "不是同一题目!!!");
        }

        if (result.coutdownTime <= 0) {
            // 没有下一题了，答题结束
            LogUtil.i("llj", "没有下一题了，答题结束!!!");
            if (rewardDialog != null) {
                rewardDialog.dismiss();
            }
            if (failureDialog != null) {
                failureDialog.dismiss();
            }
            if (timeOutDialog != null) {
                timeOutDialog.dismiss();
            }
            // 弹出排名dialog
            ReceiveAwardDialog receiveAwardDialog = new ReceiveAwardDialog(getActivity());
            receiveAwardDialog.show();
        } else {
            // 还有下一题，延迟显示下一题视图
            LogUtil.i("llj", "还有下一题，延迟显示下一题视图,延迟时间----->>>" + result.coutdownTime + "秒");
            Message message = new Message();
            message.what = NEXT_CODE;
            Bundle bundle = new Bundle();
            bundle.putInt("nextPosition", result.nextQuestionId);
            message.setData(bundle);
            mHandler.sendMessageDelayed(message, result.coutdownTime * 1000);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        countDownClock.stop();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.answer_quick_answer_btn:
                // 抢答按钮
                mOptionsLay.setCanClick(true);
                view.setVisibility(View.GONE);
//                countDownClock.start(30);
//                // 随机设置一个抢答到的人
//                partInLayout.setAnswerPosition(Tools.getRandomValue(0, 4));
                break;
            default:
                break;
        }
    }
}
