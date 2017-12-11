package com.zhongke.content.adapter;

import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.zhongke.content.entity.UserDesireListBean;
import com.zhongke.content.utils.LogUtil;
import com.zhongke.content.view.DesireLayout;

import java.util.ArrayList;
import java.util.List;

import static com.zhongke.content.utils.UIUtils.getResource;

/**
 * Created by ${xingen} on 2017/9/18.
 *
 */

public class DesireAdapter extends PagerAdapter {
    private final  String tag=DesireAdapter.class.getSimpleName();
    private List<String> list;
    private Context context;
    private List<DesireLayout> desireLayoutList;
    private List<UserDesireListBean.RecordsBean> desireList;
    private int size;
    private int currentNum = 0;
    public  DesireAdapter(Context context, List<UserDesireListBean.RecordsBean> list){
        this(context,true);
        this.desireList = list;
    }
    private boolean isAnimator;
    public  DesireAdapter(Context context,boolean isAnimator){
        this.context=context;
        this.list=new ArrayList<>();
        //this.list.add("数据1");
        //this.list.add("数据二");
        this.desireLayoutList=new ArrayList<>();
        this.isAnimator=isAnimator;
    }
    @Override
    public int getCount() {
        if (desireList.size() > 0) {
            return (desireList.size() / 3)+1;
        } else {
            return 0;
        }

    }
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        DesireLayout desireLayout=null;
        if (position<desireLayoutList.size()){
            desireLayout=desireLayoutList.get(position);
        }
        if (desireLayout==null){
            int phone_Height=getResource().getDisplayMetrics().heightPixels;
            desireLayout=new DesireLayout(container.getContext(),container.getMeasuredWidth(),(int)(phone_Height*(0.9375)),position==0?(isAnimator?true:false):false);
            List<String> dataList=new ArrayList<>();

            int count = 0;
            if (desireList != null && desireList.size() > 0) {
                LogUtil.e("desireList--"+desireList.toString());
                for (int size =desireList.size();currentNum < size;currentNum++) {
                    if (count != 3) {
                        dataList.add(desireList.get(currentNum).getWishName());
                        count++;
                    } else {
                        count = 0;
                        break;
                    }
                }
                //dataList.add("愿望愿望");
            }
//            dataList.add("愿望");
//            dataList.add("愿望愿望");
//            if (position==0){
//                dataList.add("愿望愿望愿望");
//            }
            container.addView(desireLayout, ViewPager.LayoutParams.MATCH_PARENT, ViewPager.LayoutParams.MATCH_PARENT);
            LogUtil.e("desireList size"+desireList.size());
            if (desireList != null && desireList.size() > 0) {
//                if (desireList.get(position).getWishState() == 1) {
//                    desireLayout.addChildViewData(dataList,false);
//                } else {
//                    desireLayout.addChildViewData(dataList,true);
//                }
                desireLayout.addChildViewData(dataList,false);
            }
            desireLayoutList.add(desireLayout);
            Log.i(tag," ViewPager 宽度:"+container.getMeasuredWidth()+" "+container.getMeasuredHeight()+" 手动测量的高度 "+container.getMeasuredHeightAndState()+ " " +phone_Height);
        }
        return  desireLayoutList.get(position) ;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(desireLayoutList.get(position));
    }
    public void startLayoutAnimator(AnimatorListenerAdapter animatorListenerAdapter){
        if (desireLayoutList!=null&&desireLayoutList.size()>0){
            desireLayoutList.get(0).startAnimator(animatorListenerAdapter);
        }
    }

}
