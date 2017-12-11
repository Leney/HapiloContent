package com.zhongke.content.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zhongke.content.R;
import com.zhongke.content.entity.ActivityBean;

import java.util.List;

/**
 * Created by ${tanlei} on 2017/7/5.
 */

public class ActivityAdapter extends BaseAdapter {
    private Context context;
    private List<ActivityBean> activityBeens;

    public ActivityAdapter(Context context, List<ActivityBean> activityBeens) {
        this.context = context;
        this.activityBeens = activityBeens;
    }

    @Override
    public int getCount() {
        return activityBeens.size();
    }

    @Override
    public Object getItem(int i) {
        return activityBeens.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = new ViewHolder();
        if (null == view) {
            view = LayoutInflater.from(context).inflate(R.layout.gridview_item, null);
            holder.tv = view.findViewById(R.id.tv);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.tv.setText(activityBeens.get(i).getName());
        return view;
    }

    static class ViewHolder {
        TextView tv;
    }
}
