package com.zhongke.content.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.zhongke.content.BaseActivity;
import com.zhongke.content.R;
import com.zhongke.content.adapter.ActivityAdapter;
import com.zhongke.content.adapter.RewardAdapter;
import com.zhongke.content.entity.ActivityBean;
import com.zhongke.content.entity.ActivityFlowDataBean;
import com.zhongke.content.entity.QualityScoreBean;
import com.zhongke.content.entity.RankBean;
import com.zhongke.content.entity.RewardBean;
import com.zhongke.content.retrofit.CommonException;
import com.zhongke.content.retrofit.ResponseResultListener;
import com.zhongke.content.utils.StringUtils;
import com.zhongke.content.view.CircleLayout;
import com.zhongke.content.view.GetPictureDialog;
import com.zhongke.content.view.MyGridView;
import com.zhongke.content.view.RankPictureLayout;
import com.zhongke.content.view.TextViewStarBatLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Subscription;

import static com.zhongke.content.R.id.grid_activitys;
import static com.zhongke.content.R.id.grid_host;

/**
 * Created by ${tanlei} on 2017/7/4.
 *
 */

public class RewardActivity extends BaseActivity implements AdapterView.OnItemClickListener, View.OnClickListener {
    //用来显示评分的线性布局
    private LinearLayout llQuality;
    private MyGridView gridActivity, gridHost, myGridView;
    private ScrollView sv;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_reward;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        initView();
    }

    @Override
    protected void init(){

    }
    protected void initView() {
        findViewById(R.id.iv1).setOnClickListener(this);
        myGridView = (MyGridView) findViewById(R.id.grid_view);
        sv = (ScrollView) findViewById(R.id.sv);
        sv.smoothScrollTo(0, 0);
        //奖品模拟数据
        List<RewardBean> rewardBeanList = new ArrayList<>();

        for (int i = 0; i < 18; i++) {
            RewardBean rewardBean = new RewardBean();
            rewardBean.setIcon("http://pic2.ooopic.com/13/52/23/17b1OOOPICd6.jpg");
            rewardBean.setName("金币" + i);
            rewardBean.setValue(100);
            rewardBeanList.add(rewardBean);
        }
        RewardAdapter rewardAdapter = new RewardAdapter(rewardBeanList);
        //评分模拟数据
        List<QualityScoreBean> qualityScoreBeans = new ArrayList<>();
        qualityScoreBeans.add(new QualityScoreBean(StringUtils.dealString("综合能力"), 5));
        qualityScoreBeans.add(new QualityScoreBean(StringUtils.dealString("观察能力"), 3));
        qualityScoreBeans.add(new QualityScoreBean(StringUtils.dealString("注意力"), 4));
        qualityScoreBeans.add(new QualityScoreBean(StringUtils.dealString("学习能力"), 2));
        qualityScoreBeans.add(new QualityScoreBean(StringUtils.dealString("力量"), 4));
        myGridView.setAdapter(rewardAdapter);
        ((ScrollView) findViewById(R.id.sv)).smoothScrollBy(0, 0);
        llQuality = (LinearLayout) findViewById(R.id.ll_quality);
        for (int i = 0; i < qualityScoreBeans.size(); i++) {
            TextViewStarBatLayout tvSb = new TextViewStarBatLayout(this);
            tvSb.setTextName(qualityScoreBeans.get(i).getName() + "：");
            tvSb.setStarNumber(qualityScoreBeans.get(i).getValue());
            llQuality.addView(tvSb);
        }
        gridActivity = (MyGridView) findViewById(grid_activitys);
        //活动标签模拟数据
        List<ActivityBean> activityBeen = new ArrayList<>();
        activityBeen.add(new ActivityBean("活波可爱"));
        activityBeen.add(new ActivityBean("善良可人"));
        activityBeen.add(new ActivityBean("勇敢"));
        activityBeen.add(new ActivityBean("讲文明"));
        activityBeen.add(new ActivityBean("非常好玩"));
        activityBeen.add(new ActivityBean("懂礼貌"));
        activityBeen.add(new ActivityBean("天真"));
        activityBeen.add(new ActivityBean("坚强"));
        activityBeen.add(new ActivityBean("懂事"));
        activityBeen.add(new ActivityBean("有爱心"));
        ActivityAdapter activityAdapter = new ActivityAdapter(this, activityBeen);
        gridActivity.setAdapter(activityAdapter);
        gridHost = (MyGridView) findViewById(grid_host);
        gridHost.setAdapter(activityAdapter);
        gridActivity.setSelector(new ColorDrawable(Color.TRANSPARENT));
        myGridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
        gridHost.setSelector(new ColorDrawable(Color.TRANSPARENT));
        gridHost.setOnItemClickListener(this);
        gridActivity.setOnItemClickListener(this);
        CircleLayout rl = (CircleLayout) findViewById(R.id.rl);
        LinearLayout ll = (LinearLayout) findViewById(R.id.ll1);
        List<RankBean> rankBeen = new ArrayList<>();
        for (int i = 1; i < 8; i++) {
            rankBeen.add(new RankBean(i));
        }
        //如果是<=3的话显示线性布局
        if (rankBeen.size() <= 3 && rankBeen.size() > 0) {
            rl.setVisibility(View.GONE);
            findViewById(R.id.iv).setVisibility(View.GONE);
            for (int i = 0; i < rankBeen.size(); i++) {
                if (i % 3 == 0) {
                    ll.addView(producePictureLayout(rankBeen.get(i).getRank(), R.mipmap.pict_one));
                } else if (i % 3 == 1) {
                    ll.addView(producePictureLayout(rankBeen.get(i).getRank(), R.mipmap.pict_two));
                } else if (i % 3 == 2) {
                    ll.addView(producePictureLayout(rankBeen.get(i).getRank(), R.mipmap.pict_three));
                }
            }
        } else {
            //如果是>3的话显示CircleLayout
            ll.setVisibility(View.GONE);
            for (int i = 0; i < rankBeen.size(); i++) {
                if (i % 8 == 0) {
                    rl.addView(producePictureLayout(rankBeen.get(i).getRank(), R.mipmap.pict_one));
                } else if (i % 8 == 1) {
                    rl.addView(producePictureLayout(rankBeen.get(i).getRank(), R.mipmap.pict_two));
                } else if (i % 8 == 2) {
                    rl.addView(producePictureLayout(rankBeen.get(i).getRank(), R.mipmap.pict_three));
                } else if (i % 8 == 3) {
                    rl.addView(producePictureLayout(rankBeen.get(i).getRank(), R.mipmap.pict_four));
                } else if (i % 8 == 4) {
                    rl.addView(producePictureLayout(rankBeen.get(i).getRank(), R.mipmap.pict_five));
                } else if (i % 8 == 5) {
                    rl.addView(producePictureLayout(rankBeen.get(i).getRank(), R.mipmap.pict_six));
                } else if (i % 8 == 6) {
                    rl.addView(producePictureLayout(rankBeen.get(i).getRank(), R.mipmap.pict_seven));
                } else if (i % 8 == 7) {
                    rl.addView(producePictureLayout(rankBeen.get(i).getRank(), R.mipmap.pict_eight));
                }
            }
            //恭喜你获得奖励的dialog
            GetPictureDialog oc = new GetPictureDialog(this);
            oc.show();
        }
    }

    public RankPictureLayout producePictureLayout(int rank, int res) {
        if (rank > 0 && rank < 9 && res != 0) {
            RankPictureLayout pictureLayout = new RankPictureLayout(this);
            pictureLayout.setChildBitmap(BitmapFactory.decodeResource(this.getResources(), res));
            pictureLayout.setRank(rank);
            return pictureLayout;
        }
        return null;
    }

    //获取活动流程数据列表
    public void getActivityFlowDataList(String actionId,String userId) {
        Map<String,String> map = new HashMap<>();
        map.put("actionId",actionId);
        map.put("userId",userId);
        Subscription subscription = retrofitProvider.getActivityComment(map, new ResponseResultListener<ActivityFlowDataBean>() {
            @Override
            public void success(ActivityFlowDataBean activityFlowDataBean) {

            }

            @Override
            public void failure(CommonException e) {

            }
        });
        compositeSubscription.add(subscription);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        switch (adapterView.getId()) {
            case grid_activitys:
                break;
            case grid_host:
                break;
            default:
                break;
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.iv1) {
            finish();
        }
    }

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, RewardActivity.class);
        if (!(context instanceof Activity)) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent);
    }
}
