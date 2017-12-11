package com.zhongke.content.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhongke.content.R;
import com.zhongke.content.entity.ActivityRewardBean;
import com.zhongke.content.entity.ActivityRewardBean2;
import com.zhongke.content.glide.GlideLoader;

import java.util.List;

/**
 * Created by dgg1 on 2017/10/27.
 */

public class ActivityRewardAdapter extends BaseAdapter {
    private List<ActivityRewardBean2.RecordsBean> list;
    private Context context;

    public ActivityRewardAdapter(List<ActivityRewardBean2.RecordsBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        if (list !=null) {
            return list.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (null == view) {
            holder = new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.adapter_activity_reward, null);
            holder.tvName = view.findViewById(R.id.tv_name);
            holder.ivBg1 = view.findViewById(R.id.bg1);
            holder.ivBg2 = view.findViewById(R.id.bg2);
            holder.ivLeft = view.findViewById(R.id.left_nail);
            holder.ivRight = view.findViewById(R.id.right_nail);
            holder.ivPhoto = view.findViewById(R.id.iv_photo);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        ActivityRewardBean2.RecordsBean arb = (ActivityRewardBean2.RecordsBean) getItem(i);
        //awardType 提供奖品的类型（1.实物 2.金币 3.口头表扬）
        if (arb.getAwardType() == 1) {
            //1代表实物（背景用黄色 纽扣用红色）
            holder.ivBg1.setImageResource(R.drawable.yellow_bg1);
            holder.ivBg2.setImageResource(R.drawable.yellow_bg2);
            holder.ivLeft.setImageResource(R.drawable.red_nail);
            holder.ivRight.setImageResource(R.drawable.red_nail);
        } else if (arb.getAwardType() == 2) {
            //代表虚拟物品（背景用绿色）
            holder.ivBg1.setImageResource(R.drawable.green_bg1);
            holder.ivBg2.setImageResource(R.drawable.green_bg2);
            holder.ivLeft.setImageResource(R.drawable.green_nail);
            holder.ivRight.setImageResource(R.drawable.green_nail);
        } else {
            holder.ivBg1.setImageResource(R.drawable.green_bg1);
            holder.ivBg2.setImageResource(R.drawable.green_bg2);
            holder.ivLeft.setImageResource(R.drawable.green_nail);
            holder.ivRight.setImageResource(R.drawable.green_nail);
        }
        holder.tvName.setText(arb.getAwardName() /*+ "x" + arb.getNumber()*/);
        GlideLoader.loadNetWorkResource(context, arb.getIconUrl(), holder.ivPhoto);
        return view;
    }

    static class ViewHolder {
        TextView tvName;
        ImageView ivBg1, ivBg2, ivLeft, ivRight, ivPhoto;
    }
}
