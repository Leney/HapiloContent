package com.zhongke.content.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.zhongke.content.R;
import com.zhongke.content.entity.ActivityActorBean;
import com.zhongke.content.glide.GlideLoader;

import java.util.List;

/**
 * Created by dgg1 on 2017/10/28.
 */

public class PartcipantsAdapter extends RecyclerView.Adapter<PartcipantsAdapter.MyViewHolder> {
    private List<ActivityActorBean.RecordsBean> list;
    private Context context;

    public PartcipantsAdapter(List<ActivityActorBean.RecordsBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_activity_participants, null);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        GlideLoader.loadCircleNetWorkBitmap(context, list.get(position).getHeadImageUrl(), holder.iv, R.mipmap.ic_launcher, R.mipmap.ic_launcher);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView iv;

        public MyViewHolder(View itemView) {
            super(itemView);
            iv = itemView.findViewById(R.id.photo);
        }
    }
}
