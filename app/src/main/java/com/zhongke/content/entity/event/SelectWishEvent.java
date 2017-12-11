package com.zhongke.content.entity.event;

import com.zhongke.content.entity.GiftListBean;

/**
 * Created by hyx on 2017/12/1.
 */

public class SelectWishEvent {

    public GiftListBean.RecordsBean recordsBean;
    public SelectWishEvent(GiftListBean.RecordsBean bean) {
        this.recordsBean = bean;
    }
}
