package com.zhongke.content.adapter;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zhongke.content.R;
import com.zhongke.content.entity.ExerciseBean;
import com.zhongke.content.glide.GlideLoader;
import com.zhongke.content.utils.DisplayUtils;

import java.util.List;

/**
 * Created by ${tanlei} on 2017/9/25.
 * 我的已完成活动列表适配器
 */

public class CompleteExerciseAdapter extends BaseAdapter {
    private List<ExerciseBean> list;
    private Context context;

    public CompleteExerciseAdapter(List<ExerciseBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
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
            view = LayoutInflater.from(context).inflate(R.layout.item_complete_exercise, null);
            holder.ivPhoto = view.findViewById(R.id.iv_photo_exercise);
            holder.tvLocation = view.findViewById(R.id.tv_exercise_location);
            holder.tvTime = view.findViewById(R.id.tv_exercise_time);
//            holder.tvStartTime = view.findViewById(R.id.tv_start_time);
            holder.tvText = view.findViewById(R.id.tv_exercise_text);
            holder.llPhoto = view.findViewById(R.id.ll_photo);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        ExerciseBean eb = (ExerciseBean) getItem(i);
        holder.tvLocation.setText(eb.getLocation());
        holder.tvTime.setText(eb.getTime());
        holder.tvText.setText(eb.getText());
//        holder.tvStartTime.setText("已开始3小时");
        Glide.with(context).load(eb.getPhotoUrl()).into(holder.ivPhoto);
        List<String> ls = eb.getPhotos();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(DisplayUtils.dip2px(context, 50), DisplayUtils.dip2px(context, 50));
        params.weight = 1;
        params.gravity = Gravity.CENTER_VERTICAL;
        holder.llPhoto.removeAllViews();
        for (int j = 0; j < ls.size(); j++) {
            ImageView iv = new ImageView(context);
            iv.setLayoutParams(params);
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
//            Glide.with(context).load(ls.get(j)).into(iv);
            GlideLoader.loadCircleNetWorkBitmap(context, ls.get(j), iv, R.mipmap.ic_launcher, R.mipmap.ic_launcher);
            holder.llPhoto.addView(iv);
        }
        return view;
    }

    static class ViewHolder {
        ImageView ivPhoto;
        TextView tvLocation, tvTime, tvText/*, tvStartTime*/;
        LinearLayout llPhoto;
    }
}
