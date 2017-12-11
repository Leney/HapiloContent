package com.zhongke.content.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.zhongke.content.BaseActivity;
import com.zhongke.content.R;
import com.zhongke.content.adapter.BadgeGridviewAdapter;
import com.zhongke.content.view.WapGridView;

/**
 * Created by Karma on 2017/9/20.
 * 活动详情页面
 */

public class EventDetailActivity extends BaseActivity implements View.OnClickListener {

    private ConstraintLayout scrollRootLay;
    private ImageView closeBt;
    private ImageView finishBtn;
    private WapGridView badgeGridview;
    private WapGridView dollGridview;
    private WapGridView headPortarit;

    private Integer[] badgeArr = {R.drawable.event_badge, R.drawable.event_badge, R.drawable.event_badge, R.drawable.event_badge, R.drawable.event_badge
            , R.drawable.event_badge, R.drawable.event_badge, R.drawable.event_badge, R.drawable.event_badge,
            R.drawable.event_badge, R.drawable.event_badge, R.drawable.event_badge, R.drawable.event_badge,};
    private Integer dollArr[] = {R.drawable.event_award_doll, R.drawable.event_award_doll,
            R.drawable.event_award_doll, R.drawable.event_award_doll,
            R.drawable.event_award_doll, R.drawable.event_award_doll,
            R.drawable.event_award_doll, R.drawable.event_award_doll,
            R.drawable.event_award_doll, R.drawable.event_award_doll,};
    private Integer headArr[] = {R.drawable.head_portarit, R.drawable.head_portarit, R.drawable.head_portarit, R.drawable.head_portarit,
            R.drawable.head_portarit, R.drawable.head_portarit, R.drawable.head_portarit, R.drawable.head_portarit,
            R.drawable.head_portarit, R.drawable.head_portarit, R.drawable.head_portarit, R.drawable.head_portarit,
            R.drawable.head_portarit, R.drawable.head_portarit, R.drawable.head_portarit, R.drawable.head_portarit,};

    @Override
    protected int getLayoutId() {
        return R.layout.activity_event_detail;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        initView();
    }

    @Override
    protected void init() {

    }

    private void initView() {

        scrollRootLay = (ConstraintLayout) findViewById(R.id.event_scroll_root_lay);
        closeBt = (ImageView) findViewById(R.id.event_close_button);
        finishBtn = (ImageView) findViewById(R.id.event_finish_btn);
        badgeGridview = (WapGridView) findViewById(R.id.badge_group);
        dollGridview = (WapGridView) findViewById(R.id.doll_group);
        headPortarit = (WapGridView) findViewById(R.id.head_portarit_group);


        closeBt.setOnClickListener(this);
        finishBtn.setOnClickListener(this);
        badgeGridview.setAdapter(new BadgeGridviewAdapter(this, badgeArr));
        dollGridview.setAdapter(new BadgeGridviewAdapter(this, dollArr));
        headPortarit.setAdapter(new BadgeGridviewAdapter(this, headArr));

        scrollRootLay.setFocusable(true);
        scrollRootLay.setFocusableInTouchMode(true);
        scrollRootLay.requestFocus();
    }

    public static void startEventDetailActivity(Context context) {
        context.startActivity(new Intent(context, EventDetailActivity.class));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.event_close_button:
                finish();
                break;
            case R.id.event_finish_btn:
                Log.i("llj", "点击完成按钮");
                break;
            default:
                break;
        }
    }
}
