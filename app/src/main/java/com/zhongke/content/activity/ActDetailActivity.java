package com.zhongke.content.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.zhongke.content.BaseActivity;
import com.zhongke.content.R;
import com.zhongke.content.adapter.FunAdapter;
import com.zhongke.content.adapter.RewardAdapter;
import com.zhongke.content.entity.FunBean;
import com.zhongke.content.entity.RewardBean;
import com.zhongke.content.glide.GlideLoader;
import com.zhongke.content.view.CircleLayout;
import com.zhongke.content.view.MarqueeView;
import com.zhongke.content.view.WapGridView;

import java.util.ArrayList;
import java.util.List;

/**
 * 活动详情界面
 * Created by llj on 2017/7/5.
 */

public class ActDetailActivity extends BaseActivity implements View.OnClickListener {

    private ScrollView mScrollView;
    private LinearLayout scrollLay;
    /**
     * 返回按钮
     */
    private ImageView backImg;
    /**
     * 公告
     */
    private MarqueeView hornText;
    /**
     * 开始活动的图片
     */
    private ImageView startImg;
    /**
     * 开始活动按钮
     */
    private Button startActBtn;
    /**
     * 奖品奖励部分
     */
    private WapGridView awardGridView;
    /**
     * 想要更多奖励礼物按钮
     */
    private TextView getMoreGiftBtn;
//    /**
//     * 设置本次目标按钮
//     */
//    private TextView setTargetBtn;
    /**
     * 团队贡献榜按钮
     */
    private TextView teamContributeBtn;
    /**
     * 家庭成员对战信息布局
     */
    private CircleLayout familyMemberLay1, familyMemberLay2;
    /**
     * 家庭成员icon显示的ImageView
     */
    private ImageView familyMemberIcon;

    /**
     * 家庭logo
     */
    private ImageView familyLogo1, familyLogo2;

    /**
     * 活动趣味列表
     */
    private WapGridView funListView;

    /**
     * 奖励物品列表
     */
    private List<RewardBean> mRewardBeanList;

    private RewardAdapter mRewardAdapter;


    /**
     * 活动趣味列表
     */
    private List<FunBean> mFunBeanList;
    /**
     * 活动趣味Adapter
     */
    private FunAdapter mFunAdapter;

    private ViewGroup.LayoutParams memberImgParams;

    /**
     * 活动id
     */
    private int actId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        actId = getIntent().getIntExtra("actId", -1);
        if (actId < 0) {
            finish();
            return;
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_ac_detail2;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        initView();
        initData();

        mScrollView.smoothScrollTo(0, 0);
    }

    @Override
    protected void init() {

    }

    private void initView() {
        mScrollView = (ScrollView) findViewById(R.id.act_detail_scrollView);
        scrollLay = (LinearLayout) findViewById(R.id.act_detail_scroll_lay);
        backImg = (ImageView) findViewById(R.id.act_detail_back_img);
        backImg.setOnClickListener(this);
        hornText = (MarqueeView) findViewById(R.id.act_detail_marquee_text);
        startImg = (ImageView) findViewById(R.id.act_detail_start_act_img);
        startActBtn = (Button) findViewById(R.id.act_detail_start_act_btn);
        startActBtn.setOnClickListener(this);
        awardGridView = (WapGridView) findViewById(R.id.act_detail_award_item_lay);
        getMoreGiftBtn = (TextView) findViewById(R.id.act_detail_set_more_gift_btn);
        getMoreGiftBtn.setOnClickListener(this);
//        setTargetBtn = (TextView) findViewById(R.id.act_detail_set_target_btn);
//        setTargetBtn.setOnClickListener(this);
        teamContributeBtn = (TextView) findViewById(R.id.act_detail_team_contribute_btn);
        teamContributeBtn.setOnClickListener(this);
        funListView = (WapGridView) findViewById(R.id.act_detail_fun_listView);
        familyMemberLay1 = (CircleLayout) findViewById(R.id.act_detail_circle_lay_family_1);
        familyMemberLay2 = (CircleLayout) findViewById(R.id.act_detail_circle_lay_family_2);
        familyMemberIcon = (ImageView) findViewById(R.id.act_detail_member_demo_img);
        familyLogo1 = (ImageView) findViewById(R.id.act_detail_family_logo_1);
        familyLogo2 = (ImageView) findViewById(R.id.act_detail_family_logo_2);


        familyMemberLay1.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                familyMemberLay1.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                if (familyMemberLay1.getRadius() * 2 + memberImgParams.width < familyMemberLay1.getWidth()) {
                    // 够空间，则不用去动态布局了
                    return;
                }

                // 设置左边家庭成员布局宽高
                int radiusWidth = familyMemberLay1.getWidth() - memberImgParams.width;
                familyMemberLay1.setRadius(radiusWidth / 2);
                int logoWidth = radiusWidth - memberImgParams.width;
                familyLogo1.getLayoutParams().width = logoWidth;
                familyLogo1.getLayoutParams().height = logoWidth;

                // 设置右边家庭成员布局宽高
                familyMemberLay2.setRadius(radiusWidth / 2);
                familyLogo2.getLayoutParams().width = logoWidth;
                familyLogo2.getLayoutParams().height = logoWidth;
            }
        });

        scrollLay.setFocusable(true);
        scrollLay.setFocusableInTouchMode(true);
        scrollLay.requestFocus();
    }


    private void initData() {
        //奖励部分
        mRewardBeanList = new ArrayList<>();
        mRewardAdapter = new RewardAdapter(mRewardBeanList);
        for (int i = 0; i < 7; i++) {
            RewardBean rewardBean = new RewardBean();
            rewardBean.setIcon("http://pic2.ooopic.com/13/52/23/17b1OOOPICd6.jpg");
            rewardBean.setName("金币" + i);
            rewardBean.setValue(100);
            mRewardBeanList.add(rewardBean);
        }
        awardGridView.setAdapter(mRewardAdapter);

        // 家庭成员部分
        List<String> familyIcons1 = new ArrayList<>();
        familyIcons1.add("http://img3.imgtn.bdimg.com/it/u=1415297249,1485436678&fm=26&gp=0.jpg");
        familyIcons1.add("http://img.article.pchome.net/00/42/97/32/pic_lib/s960x639/QQtxtb%2013s960x639.jpg");
        familyIcons1.add("http://www.uimaker.com/uploads/allimg/140923/1-1409230U5000-L.png");
        familyIcons1.add("http://www.narutom.com/d/img/pngico/3.png");
        familyIcons1.add("http://img1.cache.netease.com/catchpic/9/9D/9D18E624D5766DA170C4C086BED88195.png");
        familyIcons1.add("http://img4.imgtn.bdimg.com/it/u=1821117598,47740062&fm=26&gp=0.jpg");
        familyIcons1.add("http://img3.cache.netease.com/photo/0031/2014-10-20/400x300_A8VC7N7V5S6B0031.jpg");
        familyIcons1.add("http://img5.imgtn.bdimg.com/it/u=374529280,1014321583&fm=26&gp=0.jpg");
        familyIcons1.add("http://img4.imgtn.bdimg.com/it/u=972742730,2648193116&fm=26&gp=0.jpg");

        List<String> familyIcons2 = new ArrayList<>();
        familyIcons2.add("http://img3.cache.netease.com/photo/0031/2014-10-20/400x300_A8VC7N7V5S6B0031.jpg");
        familyIcons2.add("http://img5.imgtn.bdimg.com/it/u=374529280,1014321583&fm=26&gp=0.jpg");
        familyIcons2.add("http://img4.imgtn.bdimg.com/it/u=972742730,2648193116&fm=26&gp=0.jpg");
        familyIcons2.add("http://imgsrc.baidu.com/imgad/pic/item/9f2f070828381f301d06a48aa3014c086e06f0f2.jpg");
        familyIcons2.add("http://img1.imgtn.bdimg.com/it/u=3196351993,965197518&fm=26&gp=0.jpg");
        familyIcons2.add("http://img.zcool.cn/community/01bcc455bee3b66ac7253f3650ef81.jpg");

        memberImgParams = familyMemberIcon.getLayoutParams();
        familyMemberLay1.removeAllViews();
        for (int i = 0; i < familyIcons1.size(); i++) {
            ImageView icon = new ImageView(this);
            icon.setPadding(2, 2, 2, 2);
            GlideLoader.loadNetWorkResource(this, familyIcons1.get(i), icon, true);
            icon.setBackgroundResource(R.drawable.image_shape);
            familyMemberLay1.addView(icon, memberImgParams);
        }

        for (int i = 0; i < familyIcons2.size(); i++) {
            ImageView icon = new ImageView(this);
            icon.setPadding(2, 2, 2, 2);
            GlideLoader.loadNetWorkResource(this, familyIcons2.get(i), icon, true);
            icon.setBackgroundResource(R.drawable.image_shape);
            familyMemberLay2.addView(icon, memberImgParams);
        }

        GlideLoader.loadNetWorkResource(this, "http://pic7.nipic.com/20100520/1024090_154329036833_2.jpg", familyLogo1, true);
        GlideLoader.loadNetWorkResource(this, "http://f.hiphotos.baidu.com/baike/pic/item/7a899e510fb30f24c775829fcf95d143ac4b03ba.jpg", familyLogo2, true);

        // 活动趣味部分
        mFunBeanList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            FunBean funBean = new FunBean();
            funBean.setContent("趣味点"+i + "：TFboys亲临现场裁判");
            funBean.setImgUrls(new ArrayList<String>());

            if (i % 2 == 0) {
                funBean.getImgUrls().add("http://img1.imgtn.bdimg.com/it/u=2290712900,2261278371&fm=26&gp=0.jpg");
                funBean.getImgUrls().add("http://img.aiimg.com/uploads/allimg/131111/263915-131111145623.jpg");
//                funBean.getImgUrls().add("http://img.bimg.126.net/photo/DcLzomZEpKREC2crixFZeQ==/4559894622729723147.jpg");
//                funBean.getImgUrls().add("http://img15.3lian.com/2015/f3/16/d/38.jpg");
//                funBean.getImgUrls().add("http://img1.imgtn.bdimg.com/it/u=2290712900,2261278371&fm=26&gp=0.jpg");
//                funBean.getImgUrls().add("http://img.aiimg.com/uploads/allimg/131111/263915-131111145623.jpg");
//                funBean.getImgUrls().add("http://img.bimg.126.net/photo/DcLzomZEpKREC2crixFZeQ==/4559894622729723147.jpg");
//                funBean.getImgUrls().add("http://img15.3lian.com/2015/f3/16/d/38.jpg");
            } else {
//                funBean.getImgUrls().add("http://img1.imgtn.bdimg.com/it/u=2290712900,2261278371&fm=26&gp=0.jpg");
//                funBean.getImgUrls().add("http://img.aiimg.com/uploads/allimg/131111/263915-131111145623.jpg");
                funBean.getImgUrls().add("http://img.bimg.126.net/photo/DcLzomZEpKREC2crixFZeQ==/4559894622729723147.jpg");
                funBean.getImgUrls().add("http://img15.3lian.com/2015/f3/16/d/38.jpg");
            }
            mFunBeanList.add(funBean);
        }

        mFunAdapter = new FunAdapter(mFunBeanList);
        funListView.setAdapter(mFunAdapter);


        hornText.setText("路漫漫其修远兮，吾将上下而求索！");
        hornText.setTextFitSize();
        hornText.startMarquee();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        hornText.stopMarquee();
        mFunAdapter.destory();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.act_detail_back_img:
                // 返回按钮
                finish();
                break;
            case R.id.act_detail_start_act_btn:
               // startActivity(new Intent(ActDetailActivity.this, SessionActivity.class));
                // 开始活动按钮
                LiveActivity.startActivity(ActDetailActivity.this,true);
                break;
            case R.id.act_detail_set_more_gift_btn:
                // 获取更多礼物按钮
                break;
//            case R.id.act_detail_set_target_btn:
//                // 设置本次目标
//                break;
            case R.id.act_detail_team_contribute_btn:
                // 本次目标、团队成员贡献榜按钮
                break;
        }
    }


    public static void startActivity(Context context, int actId) {
        Intent intent = new Intent(context, ActDetailActivity.class);
        if (!(context instanceof Activity)){
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        intent.putExtra("actId", actId);
        context.startActivity(intent);
    }
}
