package com.zhongke.content.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zhongke.content.R;
import com.zhongke.content.entity.WishingCastleBean;

import java.util.List;

/**
 * Created by ${xingen} on 2017/9/26.
 */

public class WishingCastleSelectAdapter extends BaseQuickAdapter<WishingCastleBean.WishingCastle, BaseViewHolder> {
    private int currentSelect = 0;
    public WishingCastleSelectAdapter(@LayoutRes int layoutResId, @Nullable List<WishingCastleBean.WishingCastle> data) {
        super(layoutResId, data);
    }

    public int getCurrentSelect() {
        return currentSelect;
    }

    /**
     * 通知被选中的Item
     * @param position
     */
    public void notifySelectItem(int position){
        int beforeSelect=currentSelect;
        notifyItemChanged(beforeSelect);
        currentSelect=position;
        notifyItemChanged(currentSelect);
    }
    @Override
    protected void convert(BaseViewHolder helper, WishingCastleBean.WishingCastle item) {
        TextView textView = helper.getView(R.id.wishing_castle_select_text_tv);
        textView.setText(item.name);
        int position = helper.getLayoutPosition();
        if (position == currentSelect) {
            textView.setBackgroundResource(R.mipmap.wishing_castle_select_check_true);
        } else {
            textView.setBackgroundResource(R.mipmap.wishing_castle_select_check_false);
        }
    }


}
