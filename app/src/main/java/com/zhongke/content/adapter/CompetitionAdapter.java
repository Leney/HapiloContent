package com.zhongke.content.adapter;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhongke.content.R;
import com.zhongke.content.entity.RoomInfoBean2;

import java.util.List;

/**
 * Created by ${tanlei} on 2017/8/28.
 */

public class CompetitionAdapter extends BaseAdapter {
    private List<RoomInfoBean2.RecordsBean> rooms;
    private Context context;
    private OnGoRoomClickListeners onGoRoomClickListeners;

    public CompetitionAdapter(List<RoomInfoBean2.RecordsBean> rooms, Context context) {
        this.rooms = rooms;
        this.context = context;
    }

    @Override
    public int getCount() {
        return rooms.size();
    }

    @Override
    public Object getItem(int i) {
        return rooms.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (null == view) {
            holder = new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.item_room, null);
            holder.ivStatus = view.findViewById(R.id.room_stage);
            holder.tvSerial = view.findViewById(R.id.tv_room_number);
            holder.tvInfo = view.findViewById(R.id.tv_room_info);
            holder.tvLeft = view.findViewById(R.id.tv_left_number);
            holder.tvRight = view.findViewById(R.id.tv_right_number);
            holder.tv = view.findViewById(R.id.tv_xiegang);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        RoomInfoBean2.RecordsBean rib = (RoomInfoBean2.RecordsBean) getItem(i);
        if (rib.getJoinCount() >= rib.getPlanCount()) {//代表已经满员
            holder.ivStatus.setImageResource(R.mipmap.watch_the_war);
            holder.tvLeft.setTextColor(Color.RED);
            holder.tv.setTextColor(Color.RED);
            holder.tvRight.setTextColor(Color.RED);
        } else {//代表未满员
            holder.ivStatus.setImageResource(R.mipmap.join_room);
            holder.tvLeft.setTextColor(context.getResources().getColorStateList(R.color.cheng));
            holder.tv.setTextColor(context.getResources().getColorStateList(R.color.cheng));
            holder.tvRight.setTextColor(context.getResources().getColorStateList(R.color.cheng));
        }
        holder.tvSerial.setText((i + 1) + "");
        holder.tvInfo.setText(rib.getTitle());
        holder.tvLeft.setText(rib.getJoinCount() + "");
        holder.tvRight.setText(rib.getPlanCount() + "");
        //点击房间跳转的回调
        holder.ivStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onGoRoomClickListeners.goRoom(rib);
            }
        });
        return view;
    }

    public OnGoRoomClickListeners getOnGoRoomClickListeners() {
        return onGoRoomClickListeners;
    }

    public void setOnGoRoomClickListeners(OnGoRoomClickListeners onGoRoomClickListeners) {
        this.onGoRoomClickListeners = onGoRoomClickListeners;
    }

    /**
     * Fragment的回调接口
     */
    public interface OnGoRoomClickListeners {
        void goRoom(RoomInfoBean2.RecordsBean rib);
    }

    static class ViewHolder {
        TextView tvSerial, tvInfo, tvLeft, tvRight, tv;
        ImageView ivStatus;
    }
}
