package com.zhongke.content.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.zhongke.content.R;
import com.zhongke.content.glide.GlideLoader;

import java.util.List;

/**
 * 活动趣味图片Adapter
 * Created by llj on 2017/7/5.
 */

public class FunGridViewAdapter extends BaseAdapter {
    private List<String> urlList;

    public FunGridViewAdapter(List<String> urlList) {
        this.urlList = urlList;
    }

    @Override
    public int getCount() {
        return urlList.size();
    }

    @Override
    public Object getItem(int i) {
        return urlList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        HolderView holderView;
        if (view == null) {
            view = View.inflate(viewGroup.getContext(), R.layout.adapter_fun_gridview_item, null);
            holderView = new HolderView();
            holderView.img = view.findViewById(R.id.fun_grid_adapter_img);
            view.setTag(holderView);
        } else {
            holderView = (HolderView) view.getTag();
        }


        GlideLoader.loadNetWorkResource(viewGroup.getContext(), (String) getItem(i), holderView.img);

        return view;
    }

    private class HolderView {
        ImageView img;
    }
}
