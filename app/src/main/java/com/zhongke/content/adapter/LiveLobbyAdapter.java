package com.zhongke.content.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zhongke.content.R;
import com.zhongke.content.entity.LiveLobbyBean;
import com.zhongke.content.entity.LiveLobbyBean2;
import com.zhongke.content.utils.LogUtil;

import java.util.List;

/**
 * Created by ${xingen} on 2017/9/28.
 */

public class LiveLobbyAdapter extends BaseQuickAdapter<LiveLobbyBean2.RecordsBean,BaseViewHolder> {
    public LiveLobbyAdapter(@LayoutRes int layoutResId, @Nullable List<LiveLobbyBean2.RecordsBean> data) {
        super(layoutResId, data);
    }

    //数据在这里设置
    @Override
    protected void convert(BaseViewHolder helper, LiveLobbyBean2.RecordsBean item) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(item.getBeginTime().substring(5,7)).append("月")
                .append(item.getBeginTime().substring(8,10)).append("日");
        stringBuilder.append(item.getBeginTime().substring(11,item.getBeginTime().length()-2)).append("上课");
        LogUtil.e("date-------"+item.getBeginTime().substring(0,10));

        Glide.with(mContext).load(item.getCoverUrl()).into((ImageView) helper.getView(R.id.item_live_lobby_live_iv));
        helper.setText(R.id.item_live_lobby_live_actiontype,getActionType(item.getActionType()));
        helper.setText(R.id.item_live_lobby_title_tv,item.getTitle());
        helper.setText(R.id.item_live_lobby_kind_tv,item.getActionKindName()+"|");
        helper.setText(R.id.item_live_lobby_date_tv,stringBuilder.toString());
        helper.setText(R.id.item_live_lobby_sign_up_count_tv,item.getSignupCount()+"人已经报名");
    }

    private String getActionType(int actionType) {
        switch (actionType) {
            case 1:
                return "直播";
            case 2:
                return "微活动";
            case 3:
                return "竞赛";
            default:
                return "";
        }
    }
}
