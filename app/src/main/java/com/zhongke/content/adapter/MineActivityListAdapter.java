package com.zhongke.content.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhongke.content.R;
import com.zhongke.content.entity.ActivityListBean2;
import com.zhongke.content.entity.MyJoinActivityBean;
import com.zhongke.content.glide.GlideLoader;

import java.util.List;

/**
 * Created by dgg1 on 2017/10/31.
 * 我的活动列表adapter
 */

public class MineActivityListAdapter extends RecyclerView.Adapter<MineActivityListAdapter.MyViewHolder> {
    private List<MyJoinActivityBean.RecordsBean> list;
    private Context context;
    private OnItemClickListener onItemClickListener;

    public MineActivityListAdapter(List<MyJoinActivityBean.RecordsBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    public void setListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_activity_list, null);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        MyJoinActivityBean.RecordsBean bean = list.get(position);
        holder.tvName.setText(bean.getTitle());
        holder.tvTime.setText(bean.getBeginTime().substring(0,10));
        holder.tvLocation.setText(bean.getAddress());
        holder.tvNumber.setText(bean.getJoinCount() + "人");
        GlideLoader.loadNetWorkResource(context, bean.getCoverUrl(), holder.ivPhoto);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvLocation, tvTime, tvNumber;
        ImageView ivPhoto;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            tvLocation = itemView.findViewById(R.id.tv_list_location);
            tvTime = itemView.findViewById(R.id.tv_list_time);
            ivPhoto = itemView.findViewById(R.id.iv_photo);
            tvNumber = itemView.findViewById(R.id.participate_number);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onItemClick(view, getPosition());
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
}
