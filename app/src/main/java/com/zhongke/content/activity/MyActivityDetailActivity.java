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

import com.zhongke.content.BaseActivity;
import com.zhongke.content.R;
import com.zhongke.content.adapter.FragmentAdapter;
import com.zhongke.content.fragment.DataFragment;
import com.zhongke.content.fragment.ParticipantsFragment;
import com.zhongke.content.fragment.ProcessFragment;
import com.zhongke.content.fragment.PropsFragment;
import com.zhongke.content.fragment.RewardFragment;
import com.zhongke.content.retrofit.CommonException;
import com.zhongke.content.retrofit.ResponseResultListener;
import com.zhongke.content.utils.LogUtil;
import com.zhongke.content.view.TabView;

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
 * 我的活动详情界面
 * Created by llj on 2017/10/25.
 */

public class MyActivityDetailActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = MyActivityDetailActivity.class.getSimpleName();

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
    private String name;

    private RefreshListListener refreshListListener;
    private String beginTime;
    private String endTime;
    private ImageView noStartActImg;
    private SimpleDateFormat sdf;
    private Date beginDate = null;
    private Date endDate = null;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_activity_detail;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        mList = new ArrayList<>();
        mList.add(DataFragment.newInstance(actId+""));
        mList.add(RewardFragment.newInstance(actId+""));
        mList.add(ParticipantsFragment.newInstance(actId+""));
        mList.add(ProcessFragment.newInstance(actId+""));
        mList.add(PropsFragment.newInstance(actId+""));
        title = (TextView) findViewById(R.id.activity_detail_title);
        title.setText(name);

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
        //joinActImg.setOnClickListener(this);
        joinActImg.setVisibility(View.GONE);
        doingActImg = (ImageView) findViewById(R.id.activity_detail_doing_act_img);
        noStartActImg = (ImageView) findViewById(R.id.activity_detail_no_start_img);
        showActionImage();

        roteImg = (ImageView) findViewById(R.id.activity_detail_duo_img);
        changeTab(0);
    }

    private void showActionImage() {
        Date date = new Date();
        if (beginDate != null && endDate != null) {
            if (date.before(beginDate)) {
                //小于开始时间
                noStartActImg.setVisibility(View.VISIBLE);
            } else if (date.after(beginDate)&& date.before(endDate)) {
                //大于开始,小于结束
                doingActImg.setVisibility(View.GONE);
            }
        }
    }

    @Override
    protected void init() {
        receiverIntent();

        beginImgOutAnimation = new TranslateAnimation(0, 500, 0, 0);
        beginImgOutAnimation.setDuration(500);

        doingImgInAnimation = new TranslateAnimation(0, 0, 400, 0);
        doingImgInAnimation.setDuration(400);
        doingImgInAnimation.setStartOffset(300);

        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
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
        LogUtil.e("actId----"+actId);
        if (actId < 0) {
            finish();
            return;
        }
        name = getIntent().getStringExtra("name");
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
                // 开始活动
                joinActImg.startAnimation(beginImgOutAnimation);
                doingActImg.startAnimation(doingImgInAnimation);
                joinActImg.setVisibility(View.GONE);
                doingActImg.setVisibility(View.VISIBLE);

                //发送请求

                //刷新外部的view
                if (refreshListListener != null) {
                    refreshListListener.refreshActivityList(actId);

                    refreshListListener.refreshMyActivityList(actId);
                }
                break;
        }
    }

    public void joinActivity(int actionId,String actionName,String beginTime,String endTime) {
        Map<String ,Object> map = new HashMap<>();
        map.put("actionId",actionId);
        map.put("actionName",actionName);
        map.put("beginTime",beginTime);
        map.put("endTime",endTime);
        Subscription subscription = retrofitProvider.joinActivity(map, new ResponseResultListener() {
            @Override
            public void success(Object o) {
                LogUtil.e("success--");

            }

            @Override
            public void failure(CommonException e) {
                LogUtil.e("failure--"+e.getErrorMsg());
            }
        });
        compositeSubscription.add(subscription);
    }

    public static void startActivity(Context context, int actId, String name,String beginTime,String endTime) {
        Intent intent = new Intent(context, MyActivityDetailActivity.class);
        intent.putExtra("actId", actId);
        intent.putExtra("name", name);
        intent.putExtra("beginTime",beginTime);
        intent.putExtra("endTime",endTime);
        context.startActivity(intent);
    }

    public interface RefreshListListener {
        void refreshActivityList(int actionId);

        void refreshMyActivityList(int actionId);
    }

    public void setRefreshListener(RefreshListListener listener) {
        this.refreshListListener = listener;
    }
}
