package com.zhongke.content.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.zhongke.content.BaseFragment;
import com.zhongke.content.R;
import com.zhongke.content.activity.EventDetailActivity;
import com.zhongke.content.adapter.NotStartExerciseAdapter;
import com.zhongke.content.entity.ExerciseBean;

/**
 * Created by ${tanlei} on 2017/9/25.
 * 未开始的活动Fragment
 */

public class NotStartExerciseFragment extends BaseFragment implements AdapterView.OnItemClickListener {
    private ListView lv;
    private NotStartExerciseAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_not_start_exercise;
    }

    @Override
    protected void initAfterActivityCreated(View rootView, Bundle savedInstanceState) {
        lv = rootView.findViewById(R.id.lv);
        adapter = new NotStartExerciseAdapter(ExerciseBean.getData(), getActivity());
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        EventDetailActivity.startEventDetailActivity(getActivity());
    }
}
