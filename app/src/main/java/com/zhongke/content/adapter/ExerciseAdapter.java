package com.zhongke.content.adapter;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhongke.content.R;
import com.zhongke.content.entity.RoomInfoBean;

import java.util.List;

/**
 * Created by ${tanlei} on 2017/8/28.
 */

public class ExerciseAdapter extends BaseAdapter {
    private List<RoomInfoBean> rooms;
    private Context context;
    private OnGoExerciseClickListeners onGoExerciseClickListeners;

    public ExerciseAdapter(List<RoomInfoBean> rooms, Context context) {
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
            view = LayoutInflater.from(context).inflate(R.layout.item_exercise_room, null);
            holder.ivStatus = view.findViewById(R.id.room_stage);
            holder.tvSerial = view.findViewById(R.id.tv_room_number);
            holder.tvInfo = view.findViewById(R.id.tv_room_info);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        RoomInfoBean rib = (RoomInfoBean) getItem(i);
        holder.tvSerial.setText(rib.getSerial() + "");
        holder.tvInfo.setText(rib.getInfo());
        holder.ivStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onGoExerciseClickListeners.goExercise(rib);
            }
        });
        return view;
    }

    public OnGoExerciseClickListeners getOnGoExerciseClickListeners() {
        return onGoExerciseClickListeners;
    }

    public void setOnGoExerciseClickListeners(OnGoExerciseClickListeners onGoExerciseClickListeners) {
        this.onGoExerciseClickListeners = onGoExerciseClickListeners;
    }

    /**
     * Fragment的回调接口
     */
    public interface OnGoExerciseClickListeners {
        void goExercise(RoomInfoBean rib);
    }

    static class ViewHolder {
        TextView tvSerial, tvInfo, tvLeft, tvRight, tv;
        ImageView ivStatus;
    }
}
