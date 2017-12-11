package com.zhongke.content.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.zhongke.content.R;

/**
 * Created by ${tanlei} on 2017/7/5.
 */

public class RankPictureLayout extends FrameLayout {
    private MyImageView miv;
    private ImageView iv;
    private Context context;
    private View view;

    public RankPictureLayout(@NonNull Context context) {
        super(context);
        this.context = context;
        init();
    }

    public RankPictureLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public RankPictureLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    private void init() {
        view = LayoutInflater.from(context).inflate(R.layout.ranking_picture, this);
        miv = view.findViewById(R.id.miv);
        iv = view.findViewById(R.id.iv);
    }

    public void setChildBitmap(Bitmap bit) {
        miv.setImageBitmap(bit);
    }

    public void setRank(int rank) {
        switch (rank) {
            case 1:
                iv.setImageBitmap(BitmapFactory.decodeResource(this.getContext().getResources(), R.mipmap.one));
                break;
            case 2:
                iv.setImageBitmap(BitmapFactory.decodeResource(this.getContext().getResources(), R.mipmap.two));
                break;
            case 3:
                iv.setImageBitmap(BitmapFactory.decodeResource(this.getContext().getResources(), R.mipmap.three));
                break;
            case 4:
                iv.setImageBitmap(BitmapFactory.decodeResource(this.getContext().getResources(), R.mipmap.four));
                break;
            case 5:
                iv.setImageBitmap(BitmapFactory.decodeResource(this.getContext().getResources(), R.mipmap.five));
                break;
            case 6:
                iv.setImageBitmap(BitmapFactory.decodeResource(this.getContext().getResources(), R.mipmap.six));
                break;
            case 7:
                iv.setImageBitmap(BitmapFactory.decodeResource(this.getContext().getResources(), R.mipmap.seven));
                break;
            case 8:
                iv.setImageBitmap(BitmapFactory.decodeResource(this.getContext().getResources(), R.mipmap.eight));
                break;
            default:
                break;
        }
    }
}
