package com.zhongke.content.adapter;

import android.graphics.Color;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zhongke.content.R;
import com.zhongke.content.entity.UserHeartMindListBean;
import com.zhongke.content.utils.DisplayUtils;
import com.zhongke.content.view.ShowImageLayout;

import java.util.List;

/**
 * Created by ${xinGen} on 2017/9/29.
 */

public class MineHomeAdapter extends BaseQuickAdapter<UserHeartMindListBean.UserHeartBean, BaseViewHolder> {
    private final String TAG = MineHomeAdapter.class.getSimpleName();

    public MineHomeAdapter(@LayoutRes int layoutResId, @Nullable List<UserHeartMindListBean.UserHeartBean> data) {
        super(layoutResId, data);
    }
    @Override
    protected void convert(BaseViewHolder helper, UserHeartMindListBean.UserHeartBean item) {
        helper.addOnClickListener(R.id.mine_home_comment_iv)
                .addOnClickListener(R.id.mine_home_comment_tv)
                .addOnClickListener(R.id.mine_home_controller_like_tv)
        ;

        UserHeartMindListBean.HeartMindListBean heartMindListBean = item.heartMindListBean;
        ((ShowImageLayout) helper.getView(R.id.item_mine_home_show_image_layout)).addChildData(item.imageList);
        TextView like_tv = helper.getView(R.id.item_mine_home_like_tv);
        like_tv.setText(getLikeText(item.likeList));
        ImageView icon_iv = helper.getView(R.id.item_mine_home_default_icon);

        TextView content_tv = helper.getView(R.id.item_mine_home_content);
        content_tv.setText(heartMindListBean.getMindContent());
        TextView name_tv = helper.getView(R.id.item_mine_home_name);

        TextView controller_like_tv=helper.getView(R.id.mine_home_controller_like_tv);
        controller_like_tv.setText(item.isLike?"取消":"点赞");

        LinearLayout commentLayout = helper.getView(R.id.mine_home_comment_layout_manager);
        commentLayout.removeAllViews();
        for (int i = 0; i < item.commentList.size(); ++i) {
            TextView textView = new TextView(commentLayout.getContext());
            UserHeartMindListBean.CommentListBean commentListBean = item.commentList.get(i);
            StringBuilder stringBuilder=new StringBuilder();
            stringBuilder.append(TextUtils.isEmpty(commentListBean.getNickName())?commentListBean.getUserName():commentListBean.getNickName());
            stringBuilder.append(": ");
            String[] s={stringBuilder.toString(),commentListBean.getCommentContent()};
            int[] colors={Color.parseColor("#2381a4"),Color.BLACK};
            textView.setText(setText(colors,s));
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.bottomMargin=    layoutParams.topMargin= DisplayUtils.dip2px(commentLayout.getContext(),2);
            textView.setLayoutParams(layoutParams);
            commentLayout.addView(textView);
        }
    }

    /**
     *
     * @param colors
     * @param s
     * @return
     */
    public static SpannableStringBuilder setText( int[] colors, String[] s) {
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        int length = 0;
        for (int i = 0; i < s.length; ++i) {
            if (!TextUtils.isEmpty(s[i])){
                spannableStringBuilder.append(s[i]);
                spannableStringBuilder.setSpan(new ForegroundColorSpan(colors[i]), length, spannableStringBuilder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                length = spannableStringBuilder.length();
            }
        }
        return spannableStringBuilder;
    }

    /**
     * 拼接点赞人数
     *
     * @param stringList
     * @return
     */
    private StringBuilder getLikeText(List<String> stringList) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < stringList.size(); ++i) {
            stringBuilder.append(stringList.get(i));
            if (i != stringList.size() - 1) {
                stringBuilder.append(",");
            }
        }
        return stringBuilder;
    }
}
