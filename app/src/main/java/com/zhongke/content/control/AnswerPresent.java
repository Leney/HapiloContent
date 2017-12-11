package com.zhongke.content.control;

import android.content.Context;

import com.lqr.adapter.LQRAdapterForRecyclerView;
import com.lqr.adapter.LQRViewHolderForRecyclerView;
import com.lqr.recyclerview.LQRRecyclerView;
import com.zhongke.content.R;
import com.zhongke.content.entity.ContestantsBean;
import com.zhongke.content.entity.WatchPeopleBean;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Karma on 2017/8/28.
 * 类描述：
 */

public class AnswerPresent {
    private List<WatchPeopleBean.RecordsBean> mData = new ArrayList<>();
    private LQRAdapterForRecyclerView<WatchPeopleBean.RecordsBean> mAdapter;
    private Context mContext;
    private LQRRecyclerView mRecyclerView;

    public AnswerPresent(Context context, LQRRecyclerView recyclerView, List<WatchPeopleBean.RecordsBean> list) {
        this.mContext = context;
        this.mRecyclerView = recyclerView;
        this.mData = list;
        setAdapter();
    }

    private void setAdapter() {
        if (mAdapter == null) {
            mAdapter = new LQRAdapterForRecyclerView<WatchPeopleBean.RecordsBean>(mContext, mData, R.layout.item_audience) {
                @Override
                public void convert(LQRViewHolderForRecyclerView helper, WatchPeopleBean.RecordsBean item, int position) {
                    helper.setText(R.id.audience_name, item.getNickName());
                }
            };
            mRecyclerView.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 加载人员列表
     * @param contestantsBeanList
     */
    public void loadLookUserList(final ContestantsBean contestantsBeanList) {
        Observable.create(subscriber -> {
            List<WatchPeopleBean.RecordsBean> recordsBeenList = new ArrayList<>();
            List<ContestantsBean.LookUserListBean> lookUserList = contestantsBeanList.getLookUserList();
            for (ContestantsBean.LookUserListBean lookUserListBean : lookUserList) {
                WatchPeopleBean.RecordsBean recordsBean = new WatchPeopleBean.RecordsBean();
                recordsBean.setUserName(lookUserListBean.getUserName());
                recordsBean.setActionId(lookUserListBean.getActionId());
                recordsBean.setActorState(lookUserListBean.getActorState());
                recordsBean.setNickName(lookUserListBean.getNickName());
                recordsBean.setUserId(lookUserListBean.getUserId());
                recordsBean.icon=lookUserListBean.getHeadImageUrl();
                recordsBeenList.add(recordsBean);
            }
            subscriber.onNext(recordsBeenList);
        }).subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> setDataList((List<WatchPeopleBean.RecordsBean>) result)
                        , error -> {

                        });
    }

    public void setDataList(List<WatchPeopleBean.RecordsBean> list) {
        mData.clear();
        mData.addAll(list);
        mAdapter.notifyDataSetChanged();
    }

}
