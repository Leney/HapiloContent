package com.zhongke.content.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.zhongke.content.BaseActivity;
import com.zhongke.content.R;
import com.zhongke.content.fragment.DesireFragment;
import com.zhongke.content.fragment.WishingTabFragment;
import com.zhongke.content.glide.GlideApp;
import com.zhongke.content.view.DropDownChild;
import com.zhongke.content.view.DropDownLayout;


/**
 * Created by ${xinGen} on 2017/9/15.
 * <p>
 * 愿望页面
 */

public class DesireActivity extends BaseActivity implements DropDownChild.DropDownChildClickListener {
    private final String tag = DesireActivity.class.getSimpleName();
    private int curTab = 0;
    private Fragment[] fragments = new Fragment[2];
    @Override
    protected int getLayoutId() {
        return R.layout.activity_desire;
    }
    @Override
    protected void initView(Bundle savedInstanceState){

    }
    private DropDownLayout dropDownLayout;
    private DropDownChild back_drop_down_layout;
    private DesireFragment desireFragment;
    @Override
    protected void init() {
        this.dropDownLayout = (DropDownLayout) findViewById(R.id.desire_drop_down_layout);
        this.dropDownLayout.setDropDownChildClickListener(this);
        this.back_drop_down_layout = (DropDownChild) findViewById(R.id.desire_back_drop_down_child);
        this.back_drop_down_layout.setDropDownChildClickListener(this);
        desireFragment = DesireFragment.newInstance();
        fragments[0] = desireFragment;
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.desire_content_layout, fragments[0], tag);
        fragmentTransaction.commitAllowingStateLoss();
        curTab = 0;
    }
    /**
     * 切换Window背景
     * @param bgId
     */
    public void setWindowBG(int bgId){
        GlideApp.with(this).asBitmap().load(bgId).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                DesireActivity.this.getWindow().getDecorView().setBackground(new BitmapDrawable(getResources(),resource));
            }
        });
    }
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        this.dropDownLayout.startDownAnimator(back_drop_down_layout, new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                //startNextAnimator();
                back_drop_down_layout.startAnimator(DropDownChild.MODE_DOWN_SELF, null);
            }
        });
    }
    /**
     * 设置下个动画
     */
    private void startNextAnimator() {
        this.desireFragment.startLayoutDownAnimator(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                back_drop_down_layout.startAnimator(DropDownChild.MODE_DOWN_SELF, null);
                Log.i(tag, "this.desireFragment 动画播放完毕 ");
            }
        });
    }
    @Override
    public void clickView(int currentKind) {
        if (currentKind == DropDownChild.KIND_BACK) {
            if(curTab == 1){
                ((WishingTabFragment)fragments[1]).back();
            }else {
                DesireActivity.this.finish();
            }
        } //切换到许愿界面
        else if (currentKind == DropDownChild.KIND_WISHING) {
            changeTab(1);
        }//切换到愿望界面
        else if (currentKind == DropDownChild.KIND_DESIRE) {
            changeTab(0);
            setWindowBG(R.mipmap.launcher_desire_bg);
        }
    }
    /**
     * 改变
     * @param tab
     */
    public void changeTab(int tab){
        if(curTab == tab){
            return;
        }
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.hide(fragments[curTab]);
        curTab = tab;
        switch (tab){
            case 0:
                if(fragments[0] == null){
                    fragments[0] = DesireFragment.newInstance();
                    fragmentTransaction.add(R.id.desire_content_layout, fragments[0]);
                }else {
                    fragmentTransaction.show(fragments[0]);
                }
                break;
            case 1:
                if(fragments[1] == null){
                    fragments[1] = WishingTabFragment.newInstance();
                    fragmentTransaction.add(R.id.desire_content_layout, fragments[1]);
                }else {
                    fragmentTransaction.show(fragments[1]);
                }
                break;
            default:
                break;
        }
        fragmentTransaction.commitAllowingStateLoss();
    }
    /**
     * 开启活动
     * @param context
     */
    public static void startActivity(Context context) {
        context.startActivity(new Intent(context, DesireActivity.class));
    }
}
