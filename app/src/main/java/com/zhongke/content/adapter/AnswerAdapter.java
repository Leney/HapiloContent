package com.zhongke.content.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhongke.content.R;
import com.zhongke.content.entity.ExaminationQuestion;
import com.zhongke.content.recyclerview.BaseRecyclerViewAdapter;
import com.zhongke.content.recyclerview.RecyclerViewItemClickListener;

import java.util.List;

/**
 * Created by ${xingen} on 2017/12/9.
 */

public class AnswerAdapter extends BaseRecyclerViewAdapter<  ExaminationQuestion.QutionBean,AnswerAdapter.ViewHolder> implements RecyclerViewItemClickListener {
    private ExaminationQuestion.QutionBean qutionBean;
    private List<ExaminationQuestion.QutionBean.AnswerBean> answerBeanList;
    private int currentPosition=-1;
    public AnswerAdapter(ExaminationQuestion.QutionBean qutionBean) {
       this. changeData(qutionBean);
        this.setItemClickListener(this);
    }

    /**
     * 改变数据源
     * @param qutionBean
     */
    private void changeData(ExaminationQuestion.QutionBean qutionBean){
        this.currentPosition=-1;
        this.qutionBean=qutionBean;
        this.answerBeanList=this.qutionBean.getAnswer();
    }
    @Override
    public void addData(ExaminationQuestion.QutionBean qutionBean) {
         this.changeData(qutionBean);
        this.notifyDataSetChanged();
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_answer,parent,false);
        ViewHolder viewHolder= new ViewHolder(rootView);
        setItemClick(viewHolder);
        return viewHolder ;
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ExaminationQuestion.QutionBean.AnswerBean answerBean=answerBeanList.get(position);
        holder.textView.setText(answerBean.getTitle());
         if (currentPosition==position){
             holder.textView.setBackgroundResource(R.drawable.option_select_bg);
         }else{
             holder.textView.setBackgroundResource(R.drawable.option_nothing_bg);
         }
    }
    @Override
    public int getItemCount() {
        return this.answerBeanList.size();
    }
    public int getCurrentPosition() {
        return currentPosition;
    }
    @Override
    public void onItemClick(View view, int position) {
        if (currentPosition==position){
            return;
        }
        if (currentPosition>=0){
           notifyItemChanged(currentPosition);
        }
        currentPosition=position;
        notifyItemChanged(currentPosition);
    }

    public static class  ViewHolder extends RecyclerView.ViewHolder{
        public TextView textView;
        public ViewHolder(View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.item_answer_tv);
        }
    }
}
