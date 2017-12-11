package com.zhongke.content.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhongke.content.R;
import com.zhongke.content.entity.ContestantsBean;
import com.zhongke.content.glide.GlideLoader;
import com.zhongke.content.utils.LogUtil;

/**
 * Created by llj on 2017/8/29.
 */

public class FocusUserIcon extends FrameLayout {
    private ImageView icon;
    private TextView like;
    private TextView integral;


    /** 参加人员信息对象*/
    private ContestantsBean.ActUserListBean mPartInBean;

    /** 被赞的总次数*/
    private int likeNum = 0;
    /** 总积分数*/
    private int integralNum = 0;
    /** 是否赞过*/
    private boolean isLiked;
    /** 是否选中*/
    private boolean isSelect = false;

    private ClickLikeListener clickLikeListener;

    public FocusUserIcon(@NonNull Context context) {
        super(context);
        init(context);
    }

    public FocusUserIcon(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public FocusUserIcon(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public FocusUserIcon(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr, @StyleRes int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }


    private void init(Context context) {
        addView(View.inflate(context, R.layout.answer_user_lay, null));
        setBackgroundResource(R.drawable.user_light_empty);
        icon = findViewById(R.id.user_item_icon);
        like = findViewById(R.id.user_item_like);
        integral = findViewById(R.id.user_item_integral);
        mPartInBean = new ContestantsBean.ActUserListBean();

        like.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                doLike();
                if (clickLikeListener != null) {
                    clickLikeListener.clickLike(mPartInBean);
                }
            }
        });
    }


    /**
     * 设置参与人员的信息
     * @param bean
     */
    public void setPartInBean(ContestantsBean.ActUserListBean bean) {
        this.mPartInBean = bean;
        GlideLoader.loadNetWorkResource(getContext(),this.mPartInBean.getHeadImageUrl(),icon,true);
        this.like.setText(String.valueOf(likeNum));
        this.integral.setText(String.valueOf(this.integralNum));
    }

//    /**
//     * 设置头像
//     * @param url
//     */
//    public void setHeaderIcon(String url){
//        GlideLoader.loadNetWorkResource(getContext(),url,icon,true);
//    }

    /**
     * 设置点赞次数
     * @param num
     */
    public void setLikeNum(int num){
        this.likeNum = num;
        this.like.setText(String.valueOf(this.likeNum));
    }

    public void setIntegralNum(int num){
        this.integralNum = num;
        this.integral.setText(String.valueOf(this.integralNum));
    }

    /**
     * 获取参与人员信息
     *
     * @return
     */
    public ContestantsBean.ActUserListBean getPartInBean() {
        return mPartInBean;
    }

    /**
     * 添加积分数量
     * @param num
     */
    public void addIntegralNum(int num){
        this.integralNum += num;
        this.integral.setText(String.valueOf(this.integralNum));
    }

    /**
     * 减去积分数量
     * @param num
     */
    public void minusIntegralNum(int num){
        this.integralNum -= num;
//        // 不能有负数
//        if(this.integralNum < 0) this.integralNum = 0;
        this.integral.setText(String.valueOf(this.integralNum));
    }

    /**
     * 点赞
     */
    private void doLike(){
        LogUtil.i("llj","点赞！！");
        if(!isLiked){
            // 未被赞过
            Drawable likedDrawable = ContextCompat.getDrawable(getContext(),R.mipmap.like);
            likedDrawable.setBounds(0, 0, likedDrawable.getMinimumWidth(), likedDrawable.getMinimumHeight());
            like.setCompoundDrawables(likedDrawable,null,null,null);
            like.setTextColor(Color.RED);
            like.setText(String.valueOf(++this.likeNum));
            integral.setBackgroundResource(R.drawable.user_icon_rect_bottom_select_bg);
            integral.setText(String.valueOf(this.integralNum));
        }
        isLiked = true;
    }

    public void setSelect(boolean select){
        if(select){
            if(isSelect) return;
            setBackgroundResource(R.drawable.anim_answer_user);
            AnimationDrawable animationDrawable = (AnimationDrawable) getBackground();
            animationDrawable.start();
        }else {
            if(!isSelect) return;
            setBackgroundResource(R.drawable.user_light_empty);
        }
        isSelect = select;
    }
    //监听点赞
    public interface ClickLikeListener{
        void clickLike(ContestantsBean.ActUserListBean bean);
    }

    private void setClickListener(ClickLikeListener clickListener) {
        this.clickLikeListener = clickListener;
    }

}
