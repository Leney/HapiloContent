package com.zhongke.content.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhongke.content.R;
import com.zhongke.content.entity.LivePeopleBean;
import com.zhongke.content.entity.LivePeopleBean2;
import com.zhongke.content.glide.GlideLoader;
import com.zhongke.content.recyclerview.BaseRecyclerViewAdapter;

import java.util.List;

/**
 * 直播观众adapter
 * Created by llj on 2017/9/9.
 */
public class LivePeopleAdapter extends BaseRecyclerViewAdapter<List<LivePeopleBean>,LivePeopleAdapter.ViewHolder> {

    private Context mContext;
    private List<LivePeopleBean2.RecordsBean> mList;


    public LivePeopleAdapter(Context context, List<LivePeopleBean2.RecordsBean> list) {
        mContext = context;
        mList = list;
    }

    @Override
    public void addData(List<LivePeopleBean> list) {
//        this.mList.addAll(list);
//        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View rootView = getItemRootView(parent, R.layout.adapter_live_people);
        ViewHolder viewHolder = new ViewHolder(rootView);
        setItemClick(viewHolder);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        LivePeopleBean2.RecordsBean bean = mList.get(position);

        //GlideLoader.loadNetWorkResource(mContext,bean.icon,holder.icon,10,R.mipmap.circle_user_icon,R.mipmap.circle_user_icon);
        holder.name.setText(bean.getNickName());
        //holder.handsUp.setSelected(bean.isHandsUp);
        // 如果当前正在讲话  则将举手图标隐藏
        //holder.handsUp.setVisibility(bean.isTalking ? View.GONE:View.VISIBLE);
//        if(bean.isForbid){
//            holder.talk.setSelected(false);
//            holder.forbidTalk.setVisibility(View.VISIBLE);
//        }else {
//            holder.forbidTalk.setVisibility(View.GONE);
//            holder.talk.setSelected(bean.isTalking);
//        }
    }

//    @Override
//    public void onBindViewHolder(ViewHolder holder, int position, List<Object> payloads) {
//        super.onBindViewHolder(holder, position, payloads);
//    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView icon;
        TextView name;
        ImageView handsUp;
        ImageView talk;
        ImageView forbidTalk;

        ViewHolder(View itemView) {
            super(itemView);
            this.icon = itemView.findViewById(R.id.live_people_item_icon);
            this.name = itemView.findViewById(R.id.live_people_item_name);
            this.handsUp = itemView.findViewById(R.id.live_people_item_hands_up_img);
            this.talk = itemView.findViewById(R.id.live_people_item_talk_img);
            this.forbidTalk = itemView.findViewById(R.id.live_people_forbid_tag);

        }
    }
}
