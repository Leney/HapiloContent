package com.zhongke.content.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhongke.content.R;
import com.zhongke.content.entity.SessionList_Entity;
import com.zhongke.content.glide.GlideLoader;
import com.zhongke.content.recyclerview.BaseRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${xingen} on 2017/8/7.
 * 聊天列表人员
 */

public class ListSessionAdapter extends BaseRecyclerViewAdapter<List<SessionList_Entity>, ListSessionAdapter.ViewHolder> {
    private int selectPosition;
    private int whiteColor, textColor1, textColor2;
    private Context context;
    private List<SessionList_Entity> list;
    private final String tag = ListSessionAdapter.class.getSimpleName();

    public List<SessionList_Entity> getList() {
        return list;
    }

    public ListSessionAdapter(Context context) {
        this.list = new ArrayList<>();
        this.context = context;
        this.selectPosition = 0;
        this.textColor1 = Color.parseColor("#1ba8d9");
        this.textColor2 = Color.parseColor("#b06d0c");
        this.whiteColor = Color.parseColor("#ffffff");
    }
    @Override
    public void addData(List<SessionList_Entity> list) {
        this.list.clear();
        this.list.addAll(list);
        this.notifyDataSetChanged();
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = getItemRootView(parent, R.layout.session_list_item);
        ViewHolder viewHolder = new ViewHolder(rootView);
        setItemClick(viewHolder);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Log.i(tag, " 更新选中" + this.selectPosition+"   item的位置"+position);
        if (position == selectPosition) {
            this.currentMarkId=list.get(position).getMarkId();
            holder.linearLayout.setBackgroundResource(R.drawable.session_list_item_select);
            holder.textView.setTextColor(textColor2);
        } else {
            holder.linearLayout.setBackgroundColor(whiteColor);
            holder.textView.setTextColor(textColor1);
        }
        SessionList_Entity item = list.get(position);
        holder.textView.setText(item.getPersonName());
        GlideLoader.loadNetWorkResource(context, item.getIconUrl(), holder.imageView, R.mipmap.session_list_item_default_icon, true);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public int getSelectPosition() {
        return selectPosition;
    }
    private String currentMarkId;

    public String getCurrentMarkId() {
        return currentMarkId;
    }

    public void setSelectPosition(int selectPosition) {
        this.selectPosition = selectPosition;
        Log.i(tag, " 设置选中" + this.selectPosition);
        this.notifyDataSetChanged();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView textView;
        public LinearLayout linearLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            this.imageView = (ImageView) itemView.findViewById(R.id.session_list_item_iv);
            this.textView = (TextView) itemView.findViewById(R.id.session_list_item_tv);
            this.linearLayout = (LinearLayout) itemView.findViewById(R.id.session_list_item_bg);
        }
    }

}
