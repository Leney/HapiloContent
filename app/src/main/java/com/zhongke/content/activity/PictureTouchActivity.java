package com.zhongke.content.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

import com.zhongke.content.R;
import com.zhongke.content.entity.SessionContent_Entity;
import com.zhongke.content.glide.GlideApp;
import com.zhongke.content.view.ScaleImageView;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2016/8/16.
 * 查看图片，滑动，缩放
 */
public class PictureTouchActivity extends AppCompatActivity {
    private SessionContent_Entity entity;
    private final static String TAG = PictureTouchActivity.class.getSimpleName();
    private ViewPager viewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picturetoucher);
        receiverIntent();
        intiView();
    }

    private void intiView() {
        this.viewPager = (ViewPager) findViewById(R.id.picturetoucher_viewpager);
        this.viewPager.setAdapter(new PictureListAdapter(this.entity.getToUserList(), this));

        int selectPosition = entity.getChatType();
        this.viewPager.setCurrentItem(selectPosition);
        this.findViewById(R.id.picturetoucher_return_iv).setOnClickListener(
                view -> PictureTouchActivity.this.finish()
        );
    }


    private static class PictureListAdapter extends PagerAdapter {
        private List<String> imageUrl;
        private Context context;
        private List<ScaleImageView> imageViewList;

        public PictureListAdapter(List<String> list, Context context) {
            this.imageUrl = list;
            this.context = context;
            this.imageViewList = new ArrayList<>();
            for (int i = 0; i < imageUrl.size(); ++i) {
                ScaleImageView imageView = new ScaleImageView(this.context);
                imageView.setLayoutParams(new ViewPager.LayoutParams());
                GlideApp.with(this.context).asBitmap().load(this.imageUrl.get(i)).error(R.mipmap.default_image).placeholder(R.mipmap.default_image).centerCrop().into(imageView);
                imageViewList.add(imageView);
            }
        }

        @Override
        public int getCount() {
            return this.imageUrl != null ? this.imageUrl.size() : 0;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(imageViewList.get(position));
            return imageViewList.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    /**
     * 接受到的信息
     */
    private void receiverIntent() {
        Bundle bundle = getIntent().getExtras();
        if (bundle.containsKey(TAG)) {
            this.entity = (SessionContent_Entity) bundle.getSerializable(TAG);
        }
    }

    /**
     * 开启界面
     *
     * @param context
     * @param entity
     */
    public static void openActivity(Context context, SessionContent_Entity entity) {
        Intent intent = new Intent(context, PictureTouchActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(TAG, entity);
        intent.putExtras(bundle);
        context.startActivity(intent);

    }
}
