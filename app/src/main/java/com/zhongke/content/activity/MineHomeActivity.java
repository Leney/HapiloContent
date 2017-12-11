package com.zhongke.content.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;
import android.widget.ListView;

import com.zhongke.account.control.AccountStateManager;
import com.zhongke.account.model.AccountInfo;
import com.zhongke.content.BaseActivity;
import com.zhongke.content.R;
import com.zhongke.content.adapter.MineHomeAdapter;
import com.zhongke.content.adapter.RulesAdapter;
import com.zhongke.content.entity.FamilyDetailBean;
import com.zhongke.content.entity.UserHeartMindListBean;
import com.zhongke.content.recyclerview.RecyclerViewItemDecoration;
import com.zhongke.content.retrofit.CommonException;
import com.zhongke.content.retrofit.ResponseResultListener;
import com.zhongke.content.retrofit.RetrofitProvider;
import com.zhongke.content.utils.ToastUtils;
import com.zhongke.content.view.MyHomeTitleView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 我的家界面
 * Created by llj on 2017/9/29.
 */

public class MineHomeActivity extends BaseActivity {
    private final String TAG = MineHomeActivity.class.getSimpleName();
    private RetrofitProvider retrofitProvider;
    /**
     * 家规家训列表
     */
    private ListView rulesListView;
    private RulesAdapter mAdapter;
    private List<String> ruleList;
    private RecyclerView recyclerView;
    /**
     * 家庭成员头像控件
     */
    private MyHomeTitleView mtv;
    /**
     * 头像集合
     */
    private List<String> list = new ArrayList<>();
    private    List<UserHeartMindListBean.UserHeartBean> userHeartBeanList = new ArrayList<>();
    private ImageView ivBack;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mine_home;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        ruleList = new ArrayList<>();
        initView();
        initData();
    }

    @Override
    protected void init() {
        this.retrofitProvider = RetrofitProvider.getInstance();
        executeNetWork();
    }

    /**
     * 执行网络请求
     */
    private void executeNetWork() {
        Subscription subscription = this.retrofitProvider.getFamilyDetail(new ResponseResultListener<FamilyDetailBean>() {
            @Override
            public void success(FamilyDetailBean familyDetailBean) {
                list.clear();
                for (FamilyDetailBean.MemberListBean memberListBean : familyDetailBean.getMemberList()) {
                    list.add(memberListBean.getHeadImageUrl());
                }
                mtv.setStr(list);
            }
            @Override
            public void failure(CommonException e) {
                ToastUtils.showToast(getApplicationContext(), e.getErrorMsg());
            }
        });
        this.compositeSubscription.add(subscription);

        Subscription subscription1 = this.retrofitProvider.getUserHeartMindList(new ResponseResultListener<UserHeartMindListBean>() {
            @Override
            public void success(UserHeartMindListBean userHeartMindListBean) {
                matchUserHeartData(userHeartMindListBean);
            }
            @Override
            public void failure(CommonException e) {
                ToastUtils.showToast(getApplicationContext(), e.getErrorMsg());
            }
        });
        compositeSubscription.add(subscription1);
    }
    private        AccountInfo accountInfo;
    /**
     * 匹配心声和对应的评论
     *
     * @param userHeartMindListBean
     */
    private void matchUserHeartData(UserHeartMindListBean userHeartMindListBean) {
        Subscription subscription = Observable
                .create(subscriber -> {
                    userHeartBeanList.clear();
                    List<UserHeartMindListBean.CommentListBean> commentList = userHeartMindListBean.getCommentList();
                    List<UserHeartMindListBean.HeartMindListBean> heartMindList = userHeartMindListBean.getHeartMindList();
                    //筛选数据
                    for (UserHeartMindListBean.HeartMindListBean heartMindListBean : heartMindList) {
                        UserHeartMindListBean.UserHeartBean userHeartBean = new UserHeartMindListBean.UserHeartBean();
                        userHeartBean.heartMindListBean = heartMindListBean;
                        userHeartBean.commentListBeanList=new ArrayList<>();
                        for (UserHeartMindListBean.CommentListBean commentListBean : commentList) {
                            if (commentListBean.getHeartMindId() == heartMindListBean.getId()) {
                                userHeartBean.commentListBeanList.add(commentListBean);
                            } else {
                                continue;
                            }
                        }
                        userHeartBeanList.add(userHeartBean);
                    }
                  accountInfo  = AccountStateManager.getInstance().getAccountInfo();
                    /**
                     * 进一步，每个心声的组装数据
                     */
                    for (UserHeartMindListBean.UserHeartBean item :userHeartBeanList){
                        UserHeartMindListBean.HeartMindListBean heartMindListBean=item.heartMindListBean;
                        List<UserHeartMindListBean.CommentListBean> commentListBeanList=item.commentListBeanList;
                        String[] strings=heartMindListBean.getMindImageList().split(",");
                        item.imageList=arrayToList(strings);
                        item.likeList=new ArrayList<>();
                        item.commentList=new ArrayList<>();
                        for (UserHeartMindListBean.CommentListBean commentListBean:commentListBeanList){
                            if (commentListBean.getHmCommentType()==1){
                                if (Integer.valueOf(accountInfo.userId)==commentListBean.getUserId()){
                                     item.isLike=true;
                                }
                                item.likeList.add(TextUtils.isEmpty(commentListBean.getNickName())?commentListBean.getUserName():commentListBean.getNickName());
                            }else{
                               item.commentList.add(commentListBean);
                            }
                        }
                    }
                    subscriber.onNext(userHeartBeanList);
                }).subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result ->
                   mineHomeAdapter.notifyDataSetChanged()
                );
        compositeSubscription.add(subscription);
    }

    /**
     * 数组转成List
     * @param strings
     * @return
     */
    private List<String> arrayToList( String[] strings){
        List<String> imageList=new ArrayList<>();
        Collections.addAll(imageList,strings);
        return imageList;
    }
    private MineHomeAdapter mineHomeAdapter;

    private void initView() {
        rulesListView = (ListView) findViewById(R.id.mine_home_family_rules_list_view);
        mtv = (MyHomeTitleView) findViewById(R.id.my_photo_title);

        mAdapter = new RulesAdapter(ruleList);
        rulesListView.setAdapter(mAdapter);
        recyclerView = (RecyclerView) findViewById(R.id.mine_home_think_show_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new RecyclerViewItemDecoration(this, getResources().getDrawable(R.drawable.recycler_view_item_divider_mine_home)));
        mineHomeAdapter = new MineHomeAdapter(R.layout.item_mine_home, userHeartBeanList);
        recyclerView.setAdapter(mineHomeAdapter);
        mineHomeAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            switch (view.getId()) {
                //控制点赞和评论的显示
                case R.id.mine_home_comment_iv:
                    View dialog_view = adapter.getViewByPosition(recyclerView, position, R.id.mine_home_comment_dialog_bg);
                    dialog_view.setVisibility(dialog_view.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
                    Log.i(TAG, "  mineHomeAdapter 修改 " + position);
                    break;
                //点赞或者取消点赞
                case R.id.mine_home_controller_like_tv:
                    executeLikeController(position);
                    break;
                default:
                    break;
            }
        });
        ivBack = (ImageView) findViewById(R.id.home_back);
        ivBack.setOnClickListener(view -> startPlayAndAnimator(view));
    }

    /**
     *  点赞或者取消点赞
     * @param position
     */
    protected  void executeLikeController(int position){
        UserHeartMindListBean.UserHeartBean userHeartBean=mineHomeAdapter.getData().get(position);
        Map<String,Object> params=new HashMap<>();
        params.put("heartMindId",userHeartBean.heartMindListBean.getId());
        if (  userHeartBean.isLike){
            Subscription subscription=retrofitProvider.cancleLikeHeart(params, new ResponseResultListener() {
                @Override
                public void success(Object o) {
                    userHeartBean.isLike=false;
                    userHeartBean.likeList.remove(TextUtils.isEmpty(accountInfo.nickName)?accountInfo.name:accountInfo.nickName);
                    View dialog_view = mineHomeAdapter.getViewByPosition(recyclerView, position, R.id.mine_home_comment_dialog_bg);
                    dialog_view.setVisibility(dialog_view.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
                    mineHomeAdapter.notifyItemChanged(position);
                }
                @Override
                public void failure(CommonException e) {
                    ToastUtils.showToast(getApplicationContext(),e.getErrorMsg());
                }
            });
            compositeSubscription.add(subscription);
        }else {
            Subscription subscription=retrofitProvider.sureLikeHeart(params, new ResponseResultListener() {
                @Override
                public void success(Object o) {
                          userHeartBean.isLike=true;
                          userHeartBean.likeList.add(TextUtils.isEmpty(accountInfo.nickName)?accountInfo.name:accountInfo.nickName);
                          View dialog_view = mineHomeAdapter.getViewByPosition(recyclerView, position, R.id.mine_home_comment_dialog_bg);
                          dialog_view.setVisibility(dialog_view.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
                          mineHomeAdapter.notifyItemChanged(position);
                }
                @Override
                public void failure(CommonException e) {
                    ToastUtils.showToast(getApplicationContext(),e.getErrorMsg());
                }
            });
            compositeSubscription.add(subscription);
        }
    }

    private MediaPlayer mediaPlayer01;

    /**
     * 一个缩放动画，且伴随音乐
     *
     * @param view
     */
    private void startPlayAndAnimator(View view) {
        //播放音乐
        try {
            mediaPlayer01 = MediaPlayer.create(MineHomeActivity.this, R.raw.my_home_back);
            mediaPlayer01.prepare();
            mediaPlayer01.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //一个缩放动画
        ObjectAnimator x = ObjectAnimator.ofFloat(view, "scaleX", 0.8f, 1.0f, 0.8f);
        ObjectAnimator y = ObjectAnimator.ofFloat(view, "scaleY", 0.8f, 1.0f, 0.8f);
        AnimatorSet set = new AnimatorSet();
        set.playTogether(x, y);
        set.setDuration(300);
        set.setInterpolator(new AccelerateInterpolator());
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (mediaPlayer01 != null && mediaPlayer01.isPlaying()) {
                    mediaPlayer01.stop();
                }
                finish();
            }
        });
        set.start();
    }

    private void initData() {
        ruleList.add("无瑕之玉，可以为国器；孝悌之子，可以为国瑞。");
        ruleList.add("赡养父母是中华民族的传统美德，从我做起代代相传。");
        ruleList.add("我们确实有如是的优点，但也要隐藏几分，这个叫做涵养。");
        ruleList.add("我今仅守读书业，汝勿轻捐少壮时。");
        ruleList.add("家，心灵的守护地；家，温暖的港湾处；唯天下间最美丽的地方！");
        ruleList.add("奉先思孝，处下思恭；倾己勤劳，以行德义。");

        mAdapter.notifyDataSetChanged();
    }
    public static void startActivity(Context context) {
        context.startActivity(new Intent(context, MineHomeActivity.class));
    }
}
