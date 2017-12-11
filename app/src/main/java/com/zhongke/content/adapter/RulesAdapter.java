package com.zhongke.content.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zhongke.content.R;

import java.util.List;

/**
 * 家规家训Adapter
 * Created by llj on 2017/9/29.
 */
public class RulesAdapter extends BaseAdapter {
    private List<String> ruleList;

    public RulesAdapter(List<String> ruleList) {
        this.ruleList = ruleList;
    }

    @Override
    public int getCount() {
        return ruleList.size();
    }

    @Override
    public Object getItem(int i) {
        return ruleList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if(view == null){
            view = View.inflate(viewGroup.getContext(), R.layout.adapter_family_rule_item,null);
            holder = new ViewHolder();
            holder.init(view);
            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }

        holder.num.setText((i + 1)+".");
        holder.detail.setText(ruleList.get(i));
        return view;
    }

    private class ViewHolder{
        TextView num,detail;
        public void init(View baseView){
            num = baseView.findViewById(R.id.family_rule_adapter_num);
            detail = baseView.findViewById(R.id.family_rule_adapter_detail);
        }
    }
}
