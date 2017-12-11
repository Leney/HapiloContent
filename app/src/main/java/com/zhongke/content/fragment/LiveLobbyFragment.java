package com.zhongke.content.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.zhongke.content.BaseFragment;
import com.zhongke.content.R;
import com.zhongke.content.activity.ActDetailActivity;
import com.zhongke.content.activity.LiveActivity;
import com.zhongke.content.activity.LiveLobbyActivity;
import com.zhongke.content.adapter.LiveLobbyAdapter;
import com.zhongke.content.entity.LiveLobbyBean;
import com.zhongke.content.entity.LiveLobbyBean2;
import com.zhongke.content.retrofit.CommonException;
import com.zhongke.content.retrofit.ResponseResultListener;
import com.zhongke.content.utils.LogUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Subscription;

/**
 * Created by ${xingen} on 2017/9/28.
 */

public class LiveLobbyFragment extends BaseFragment {
    public static final String TAG = LiveLobbyFragment.class.getSimpleName();
    private List<LiveLobbyBean2.RecordsBean> liveList;

    public static LiveLobbyFragment newInstance() {
        LiveLobbyFragment fragment = new LiveLobbyFragment();
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_live_lobby;
    }

    private RecyclerView recyclerView;
    private LiveLobbyAdapter liveLobbyAdapter;

    @Override
    protected void initAfterActivityCreated(View rootView, Bundle savedInstanceState) {
        liveList = new ArrayList<>();
        this.recyclerView = rootView.findViewById(R.id.live_lobby_recycler_view);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //this.liveLobbyAdapter = new LiveLobbyAdapter(R.layout.item_livelobby, LiveLobbyBean.newInstance().dataList);

        this.liveLobbyAdapter = new LiveLobbyAdapter(R.layout.item_livelobby, liveList);

        this.recyclerView.setAdapter(this.liveLobbyAdapter);
        this.liveLobbyAdapter.setOnItemClickListener((adapter, view, position) -> {
//            ActDetailActivity.startActivity(getActivity(), 2);
            LiveActivity.startActivity(getActivity(), position % 2 == 0,liveList.get(position).getId(),liveList.get(position).getVideoUrl(),liveList.get(position).getTitle());
        });
        this.recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                ((LiveLobbyActivity) getActivity()).notifyRotate(dy);
            }
        });
        getLiveActivity();
    }

    private void getLiveActivity() {
        Map<String ,Object> hashMap = new HashMap<>();
        //hashMap.put("token",token);
        hashMap.put("pageIndex",1);
        Subscription subscription =  retrofitProvider.getLiveActivity(hashMap, new ResponseResultListener<LiveLobbyBean2>() {
            @Override
            public void success(LiveLobbyBean2 liveLobbyBean2) {
                //成功
                if (!liveLobbyBean2.getRecords().isEmpty()) {

                    LogUtil.e("success---"+"liveLobbyBean2---"+liveLobbyBean2.getRecords().toString());
                    liveList.clear();
                    liveList.addAll(liveLobbyBean2.getRecords());
                } else {
                    Toast.makeText(getActivity(),"获取数据为空",Toast.LENGTH_SHORT).show();
                }

                liveLobbyAdapter.notifyDataSetChanged();
            }

            @Override
            public void failure(CommonException e) {
                //
                LogUtil.e("failure---"+"liveLobbyBean2---"+e.toString());
            }
        });
        compositeSubscription.add(subscription);
    }

}
