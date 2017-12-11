package com.zhongke.content.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.zhongke.content.glide.GlideApp;
import com.zhongke.content.utils.DisplayUtils;
import com.zhongke.content.utils.ViewUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${xinGen} on 2017/9/29.
 * <p>
 * 我的家，显示图片的布局
 */

public class ShowImageLayout extends LinearLayout implements ViewTreeObserver.OnPreDrawListener {
    private static final String TAG = ShowImageLayout.class.getSimpleName();

    public ShowImageLayout(Context context) {
        super(context);
    }

    private int margin;
    private ViewTreeObserver viewTreeObserver;

    public ShowImageLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initConfig();
    }

    /**
     * 初始化配置
     */
    private void initConfig() {
        this.setOrientation(LinearLayout.VERTICAL);
        margin = DisplayUtils.dip2px(getContext(), 12.7f);
        //添加View的树形观察者
        viewTreeObserver = getViewTreeObserver();
        viewTreeObserver.addOnPreDrawListener(this);
    }

    private List<String> urlList = new ArrayList<>();

    public void addChildData(List<String> imageList) {
        this.removeAllViews();
        this.imageViewList.clear();
        int width = getMeasuredWidth();
        if (width > 0) {//View已经测量到了宽高
            createChild(imageList);
        } else {
            this.urlList.clear();
            urlList.addAll(imageList);
        }
    }
    private List<ImageView> imageViewList = new ArrayList<>();
    private List<ImageView> createChild(List<String> imageList) {
        for (String url : imageList) {
            imageViewList.add(createImageView(url));
        }
        switch (imageViewList.size()) {
            case 1:
                addOneView(0);
                break;
            case 2:
                this.addView(addTwoView(0, 1));
                break;
            case 3:
                this.addView(addTwoView(0, 1));
                ImageView imageView = imageViewList.get(2);
                LayoutParams layoutParams = (LayoutParams) imageView.getLayoutParams();
                layoutParams.topMargin = margin;
                this.addView(imageView);
                break;
            case 4:
                this.addView(addTwoView(0, 1));
                LinearLayout linearLayout = addTwoView(2, 3);
                LayoutParams layoutParams1 = (LayoutParams) linearLayout.getLayoutParams();
                layoutParams1.topMargin = margin;
                this.addView(linearLayout);
                break;
            default:

                break;
        }
        return imageViewList;
    }

    private void addOneView(int index) {
        this.addView(imageViewList.get(index));
    }

    private LinearLayout addTwoView(int index1, int index2) {
        LinearLayout linearLayout = new LinearLayout(getContext());
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.addView(imageViewList.get(index1));
        ImageView imageView = imageViewList.get(index2);
        LayoutParams layoutParams = (LayoutParams) imageView.getLayoutParams();
        layoutParams.gravity = Gravity.RIGHT;
        linearLayout.addView(imageView);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        return linearLayout;
    }

    private ImageView createImageView(String url) {
        ImageView imageView = new ImageView(getContext());
        int width1 = (width - margin) / 2;
        int height = (int) (width1 / 1.804);
        LayoutParams layoutParams = new LayoutParams(width1, height);
    //    Log.i(TAG, " ImageView 宽高： " + width1 + " " + height + " " + getWidth() + " " + getMeasuredWidth() + " " + margin);
        imageView.setLayoutParams(layoutParams);
        imageView.setId(ViewUtils.getViewId());
        GlideApp.with(getContext()).load(url).into(imageView);
        return imageView;
    }

    private int width;
    private int height;

    @Override
    public boolean onPreDraw() {
       // Log.i(TAG, " viewTreeObserver  onPreDraw()");
        //判断ViewTreeObserver 是否alive
        if (viewTreeObserver.isAlive()) {
            //如果存活的话移除这个观察者,避免反复调用
            viewTreeObserver.removeOnPreDrawListener(this);
        }
        if (this.width == 0) {
            this.width = getMeasuredWidth();
            this.height = getMeasuredHeight();
      //      Log.i(TAG, " viewTreeObserver  " + width);
            //执行添加操作
            addChildData(this.urlList);
            this.urlList.clear();
        }
        return true;
    }
}
