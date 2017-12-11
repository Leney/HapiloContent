package com.zhongke.content.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.zhongke.content.R;
import com.zhongke.content.entity.PropsBean;
import com.zhongke.content.entity.PropsBean2;
import com.zhongke.content.glide.GlideLoader;
import com.zhongke.content.view.StrokeTextView;

import java.util.List;
import java.util.Random;

/**
 * Created by dgg1 on 2017/10/28.
 */

public class PropsAdapter extends RecyclerView.Adapter<PropsAdapter.MyViewHolder> {
    private List<PropsBean2.RecordsBean> list;
    private Context context;
    private Random random = new Random();

    public PropsAdapter(List<PropsBean2.RecordsBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_props, null);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        PropsBean2.RecordsBean bean = list.get(position);
        int i = random.nextInt(3);
        if (i == 0) {
            holder.ivKnot.setImageResource(R.drawable.red_knot);
            holder.stv.setBackgroundResource(R.drawable.props_red);
        } else if (i == 1) {
            holder.ivKnot.setImageResource(R.drawable.biue_knot);
            holder.stv.setBackgroundResource(R.drawable.props_blue);
        } else {
            holder.ivKnot.setImageResource(R.drawable.yellow_knot);
            holder.stv.setBackgroundResource(R.drawable.props_yellow);
        }
        holder.stv.setText(bean.getToolsName());
//        holder.stv.setModeStroke(StrokeTextView.MODE_STROKE, Color.WHITE, 3);
        GlideLoader.loadNetWorkResource(context, bean.getImageUrl(), holder.ivPropsPhoto);
    }

    @Override
    public int getItemCount() {
        if (list !=null) {
            return list.size();
        }
        return 0;
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView ivPropsPhoto, ivKnot;
        StrokeTextView stv;

        public MyViewHolder(View itemView) {
            super(itemView);
            ivPropsPhoto = itemView.findViewById(R.id.props_photo);
            ivKnot = itemView.findViewById(R.id.iv_knot);
            stv = itemView.findViewById(R.id.tv_props_name);
        }
    }
}
