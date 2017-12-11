package com.zhongke.content.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhongke.content.R;
import com.zhongke.content.entity.RewardBean;
import com.zhongke.content.glide.GlideLoader;

import java.util.List;

/**
 * Created by ${tanlei} on 2017/7/4.
 */

public class RewardAdapter extends BaseAdapter {
    private List<RewardBean> rewardBeens;

    public RewardAdapter(List<RewardBean> rewardBeens) {
        this.rewardBeens = rewardBeens;
    }

    @Override
    public int getCount() {
        return rewardBeens.size();
    }

    @Override
    public Object getItem(int i) {
        return rewardBeens.get(i);
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
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.reward_gridview_item, null);
            holder.miv = view.findViewById(R.id.iv);
            holder.tvName = view.findViewById(R.id.tv_name);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        if (i % 6 == 0) {
//            GlideLoader.loadNetWorkResource(viewGroup.getContext(), "", holder.miv, R.mipmap.grid, true);
            GlideLoader.loadCircleNetWorkBitmap(viewGroup.getContext(),"",holder.miv,R.mipmap.grid,R.mipmap.grid);
            holder.tvName.setText(rewardBeens.get(i).getName());
        } else if (i % 6 == 1) {
//            GlideLoader.loadNetWorkResource(viewGroup.getContext(), "", holder.miv, R.mipmap.pic_one, true);
            GlideLoader.loadCircleNetWorkBitmap(viewGroup.getContext(),"",holder.miv,R.mipmap.pic_one,R.mipmap.pic_one);
            holder.tvName.setText("汉堡");
        } else if (i % 6 == 2) {
//            GlideLoader.loadNetWorkResource(viewGroup.getContext(), "", holder.miv, R.mipmap.pic_two, true);
            GlideLoader.loadCircleNetWorkBitmap(viewGroup.getContext(),"",holder.miv,R.mipmap.pic_two,R.mipmap.pic_two);
            holder.tvName.setText("游戏");
        } else if (i % 6 == 3) {
//            GlideLoader.loadNetWorkResource(viewGroup.getContext(), "", holder.miv, R.mipmap.pic_five, true);
            GlideLoader.loadCircleNetWorkBitmap(viewGroup.getContext(),"",holder.miv,R.mipmap.pic_five,R.mipmap.pic_five);
            holder.tvName.setText("勋章");
        } else if (i % 6 == 4) {
//            GlideLoader.loadNetWorkResource(viewGroup.getContext(), "", holder.miv, R.mipmap.pic_four, true);
            GlideLoader.loadCircleNetWorkBitmap(viewGroup.getContext(),"",holder.miv,R.mipmap.pic_four,R.mipmap.pic_four);
            holder.tvName.setText("徽章");
        } else if (i % 6 == 5) {
//            GlideLoader.loadNetWorkResource(viewGroup.getContext(), "", holder.miv, R.mipmap.pic_five, true);
            GlideLoader.loadCircleNetWorkBitmap(viewGroup.getContext(),"",holder.miv,R.mipmap.pic_five,R.mipmap.pic_five);
            holder.tvName.setText("勋章");
        }
        return view;
    }

    static class ViewHolder {
        ImageView miv;
        TextView tvName, tvValue;
    }
}
