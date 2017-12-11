package com.zhongke.content.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.zhongke.account.ClientListener;
import com.zhongke.account.model.AccountInfo;
import com.zhongke.account.model.ZkLocalMessage;
import com.zhongke.content.BaseActivity;
import com.zhongke.content.R;
import com.zhongke.content.adapter.FragmentAdapter;
import com.zhongke.content.entity.ActivityDetailBean;
import com.zhongke.content.entity.event.RefreshEvent;
import com.zhongke.content.entity.event.RefreshMineEvent;
import com.zhongke.content.fragment.DataFragment;
import com.zhongke.content.fragment.ParticipantsFragment;
import com.zhongke.content.fragment.ProcessFragment;
import com.zhongke.content.fragment.PropsFragment;
import com.zhongke.content.fragment.RewardFragment;
import com.zhongke.content.im.ZkImManager;
import com.zhongke.content.im.extra.ExtraMessage;
import com.zhongke.content.im.extra.message.WishBindActivityNotice;
import com.zhongke.content.retrofit.CommonException;
import com.zhongke.content.retrofit.ResponseResultListener;
import com.zhongke.content.utils.LogUtil;
import com.zhongke.content.utils.UIUtils;
import com.zhongke.content.view.TabView;

import org.greenrobot.eventbus.EventBus;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import rx.Subscription;

/**
 * 活动详情界面(新)
 * Created by llj on 2017/10/25.
 */

public class ActivityDetailActivity extends BaseActivity implements View.OnClickListener, ClientListener {

    private static final String TAG = ActivityDetailActivity.class.getSimpleName();

    private TabView[] tabViews = new TabView[5];

    private ViewPager mViewPager;

    private FragmentAdapter mAdapter;

    private List<Fragment> mList;

    private int curTab = -1;

    /**
     * 参加按钮
     */
    private ImageView joinActImg;

    /**
     * 正在开始按钮
     */
    private ImageView doingActImg;

    /**
     * 标题
     */
    private TextView title;

    /**
     * 开始活动按钮消失动画
     */
    private Animation beginImgOutAnimation;

    /**
     * 活动进行中图片出现动画
     */
    private Animation doingImgInAnimation;

    /**
     * 圆盘图片
     */
    private ImageView roteImg;

    /**
     * 圆盘图片的宽度
     */
    private int roteImgWidth;

    /**
     * 活动id
     */
    private int actId;

    /**
     * 活动名称
     */
    private String actionName;
    private String beginTime;
    private String endTime;
    private ImageView noStartActImg;
    private TranslateAnimation noStartImgInAnimation;
    private SimpleDateFormat sdf;
    private Date beginDate = null;
    private Date endDate = null;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_activity_detail;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        ZkImManager.getInstance().addSubscriber(this);
        mList = new ArrayList<>();
        mList.add(DataFragment.newInstance(actId + ""));
        mList.add(RewardFragment.newInstance(actId + ""));
        mList.add(ParticipantsFragment.newInstance(actId + ""));
        mList.add(ProcessFragment.newInstance(actId + ""));
        mList.add(PropsFragment.newInstance(actId + ""));
        title = (TextView) findViewById(R.id.activity_detail_title);
        title.setText(actionName);

        tabViews[0] = (TabView) findViewById(R.id.activity_detail_tab1);
        tabViews[0].setChildTag(0);
        tabViews[0].setName("资料");

        tabViews[1] = (TabView) findViewById(R.id.activity_detail_tab2);
        tabViews[1].setName("奖励");

        tabViews[2] = (TabView) findViewById(R.id.activity_detail_tab3);
        tabViews[2].setName("人员");

        tabViews[3] = (TabView) findViewById(R.id.activity_detail_tab4);
        tabViews[3].setName("流程");

        tabViews[4] = (TabView) findViewById(R.id.activity_detail_tab5);
        tabViews[4].setName("道具");

        mViewPager = (ViewPager) findViewById(R.id.activity_detail_viewpager);
        mAdapter = new FragmentAdapter(getSupportFragmentManager(), mList);
        mViewPager.setAdapter(mAdapter);
        // 默认不显示ViewPager的内容
        mViewPager.setVisibility(View.GONE);
//        mViewPager.setCurrentItem(curTab);
//        tabViews[curTab].open();

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // 旋转转盘
                roteImg.setRotation(calculationRotationAngle(positionOffsetPixels));
            }

            @Override
            public void onPageSelected(int position) {
                changeTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        int length = tabViews.length;
        for (int i = 0; i < length; i++) {
            tabViews[i].initClose();
            tabViews[i].setChildTag(i);
            tabViews[i].setOpenListener(tabClickListener);
        }

        findViewById(R.id.activity_detail_back_img).setOnClickListener(this);
        joinActImg = (ImageView) findViewById(R.id.activity_detail_join_act_img);
        joinActImg.setOnClickListener(this);
        doingActImg = (ImageView) findViewById(R.id.activity_detail_doing_act_img);
        noStartActImg = (ImageView) findViewById(R.id.activity_detail_no_start_img);

        roteImg = (ImageView) findViewById(R.id.activity_detail_duo_img);
        changeTab(0);

    }

    @Override
    protected void init() {
        receiverIntent();

        beginImgOutAnimation = new TranslateAnimation(0, 500, 0, 0);
        beginImgOutAnimation.setDuration(500);

        doingImgInAnimation = new TranslateAnimation(0, 0, 400, 0);
        doingImgInAnimation.setDuration(400);
        doingImgInAnimation.setStartOffset(300);

        noStartImgInAnimation = new TranslateAnimation(0, 0, 400, 0);
        noStartImgInAnimation.setDuration(400);
        noStartImgInAnimation.setStartOffset(300);

        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        beginDate = null;
        endDate = null;
        //时间转化为date
        try {
            beginDate = sdf.parse(beginTime);
            endDate = sdf.parse(endTime);
        } catch (ParseException e) {
            e.printStackTrace();
            LogUtil.e("beginDate,转换异常");
        }
    }

    private void receiverIntent() {
        actId = getIntent().getIntExtra("actId", -1);
        LogUtil.e("actId----" + actId);
        if (actId < 0) {
            finish();
            return;
        }
        actionName = getIntent().getStringExtra("actionName");
        beginTime = getIntent().getStringExtra("beginTime");
        endTime = getIntent().getStringExtra("endTime");
    }

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
        if (roteImgWidth <= 0) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) roteImg.getDrawable();
            if (bitmapDrawable != null) {
                Bitmap bitmap = bitmapDrawable.getBitmap();
                roteImgWidth = bitmap.getWidth();
            }
        }
        // 周长
        double perimeter = roteImgWidth * Math.PI;
        endAngle = (float) (roteImg.getRotation() + (dy / perimeter * 360));
        return endAngle;
    }

    private View.OnClickListener tabClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            changeTab((Integer) v.getTag());
        }
    };

    private void changeTab(int position) {
        if (curTab >= 0) {
            // 关闭上一个
            tabViews[curTab].close();
        } else {
            // 首次点击其中一个，显示ViewPager
            mViewPager.setVisibility(View.VISIBLE);
        }
        curTab = position;
        tabViews[curTab].open();
        // 切换ViewPager相应的Fragment页面
        mViewPager.setCurrentItem(curTab);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_detail_back_img:
                // 返回按钮
                finish();
                break;
            case R.id.activity_detail_join_act_img:
                Date date = new Date();
                if (date.after(endDate)) {
                    UIUtils.showToast("对不起,活动已经结束了");
                    joinActImg.setVisibility(View.GONE);
                } else {
                    //发送请求
                    joinActivity(actId, actionName, beginTime, endTime);
                    //showActionImage();
                }
                break;
        }
    }

    //显示活动状态图片
    private void showActionImage() {
        Date date = new Date();
        if (beginDate != null && endDate != null && beginDate != endDate) {
            if (date.after(beginDate) && date.before(endDate)) {
                //大于开始时间,小于结束时间 显示进行中
                // 开始活动
                joinActImg.startAnimation(beginImgOutAnimation);
                doingActImg.startAnimation(doingImgInAnimation);
                joinActImg.setVisibility(View.GONE);
                doingActImg.setVisibility(View.VISIBLE);

            } else if (date.before(beginDate)) {
                //小于开始时间 显示未开始
                joinActImg.startAnimation(beginImgOutAnimation);
                noStartActImg.startAnimation(noStartImgInAnimation);
                joinActImg.setVisibility(View.GONE);
                noStartActImg.setVisibility(View.VISIBLE);
            } else if (date.after(endDate)) {
                //大于结束时间
                joinActImg.setVisibility(View.GONE);
                UIUtils.showToast("对不起,活动已经结束了");
            }
        }
    }

    public void joinActivity(int actionId, String actionName, String beginTime, String endTime) {
        Map<String, Object> map = new HashMap<>();
        map.put("actionId", actionId);
        map.put("actionName", actionName);
        map.put("beginTime", beginTime);
        map.put("endTime", endTime);
        Subscription subscription = retrofitProvider.joinActivity(map, new ResponseResultListener() {
            @Override
            public void success(Object o) {
                UIUtils.showToast("参加活动成功");
                LogUtil.e("success--");
                showActionImage();
                RefreshEvent event = new RefreshEvent(actId);
                //更新ActivityHallFragment
                EventBus.getDefault().post(event);
                //ActivityMineFragment 重新请求数据
                EventBus.getDefault().post(new RefreshMineEvent());

                //发送参加活动
                ExtraMessage noticeMessage = new ExtraMessage();
                WishBindActivityNotice notice = new WishBindActivityNotice();
                //
                notice.wishId = actionId;
                notice.code = WishBindActivityNotice.code_accept_activity;
                noticeMessage.message = notice;

                //ZkImManager.getInstance().sendExtraMessage(,noticeMessage)
            }

            @Override
            public void failure(CommonException e) {
                UIUtils.showToast("参加活动失败");
                LogUtil.e("failure--" + e.getErrorMsg());
            }
        });
        compositeSubscription.add(subscription);
    }

    public static void startActivity(Context context, int actId, String actionName, String beginTime, String endTime) {
        Intent intent = new Intent(context, ActivityDetailActivity.class);
        intent.putExtra("actId", actId);
        intent.putExtra("actionName", actionName);
        intent.putExtra("beginTime", beginTime);
        intent.putExtra("endTime", endTime);
        context.startActivity(intent);
    }

    @Override
    public void accountChange(AccountInfo accountInfo) {

    }

    @Override
    public void accountDelete() {

    }

    @Override
    public void chatMessageResponse(ZkLocalMessage zkLocalMessage) {

    }

    @Override
    public void extraMessageResponse(ZkLocalMessage zkLocalMessage) {
        String content = zkLocalMessage.chatContent;
        ExtraMessage message = new Gson().fromJson(content, ExtraMessage.class);
        if (message == null) return;
        if (message.code == WishBindActivityNotice.code_wish_bind_activity) {
            WishBindActivityNotice notice = (WishBindActivityNotice) message.message;
            LogUtil.e("lee" + notice.toString());

            //请求http 获取活动数据
            getActivityDetail(notice.bindActivityId + "");
        }

    }

    //获取活动详情
    public void getActivityDetail(String actionId) {
        Map<String, String> map = new HashMap<>();
        map.put("actionId", actionId);
        Subscription subscription = retrofitProvider.getActivityDetail(map, new ResponseResultListener<ActivityDetailBean>() {
            @Override
            public void success(ActivityDetailBean activityDetailBean) {

            }

            @Override
            public void failure(CommonException e) {

            }
        });
        compositeSubscription.add(subscription);
    }

    @Override
    public void netChangeResult(boolean b) {

    }
}
