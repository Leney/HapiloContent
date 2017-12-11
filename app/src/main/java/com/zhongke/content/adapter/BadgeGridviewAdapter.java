package com.zhongke.content.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.zhongke.content.R;

import java.util.List;

/**
 * Created by Karma on 2017/9/22.
 */

public class BadgeGridviewAdapter extends BaseAdapter{

    private Context context;
    private Integer[] imgResArr;
    public BadgeGridviewAdapter(Context context, Integer[] imgResArr) {
        this.context = context;
        this.imgResArr = imgResArr;
    }

    @Override
    public int getCount() {
        return imgResArr.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        BadgeHolder badgeHolder;
        if (view == null){
            badgeHolder = new BadgeHolder();
            view = View.inflate(context, R.layout.item_badge_gridview,null);
            badgeHolder.img = view.findViewById(R.id.badge_show_img);
            view.setTag(badgeHolder);
        } else {
            badgeHolder = (BadgeHolder) view.getTag();
        }
        badgeHolder.img.setImageResource(imgResArr[i]);
        return view;
    }

    class BadgeHolder {
        public BadgeHolder(){

        }
        ImageView img;
    }
}
