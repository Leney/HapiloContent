package com.zhongke.content.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhongke.content.R;
import com.zhongke.content.entity.FunBean;
import com.zhongke.content.view.WapGridView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 活动趣味Adapter
 * Created by llj on 2017/7/5.
 */

public class FunAdapter extends BaseAdapter {
    private List<FunBean> mFunBeanList;

    private Map<Integer, LinearLayout> leftItemLayMap;

    public FunAdapter(List<FunBean> funBeanList) {
        mFunBeanList = funBeanList;
        this.leftItemLayMap = new HashMap<>();
    }

    @Override
    public int getCount() {
        return mFunBeanList.size();
    }

    @Override
    public Object getItem(int i) {
        return mFunBeanList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        HolderView holderView;
        if (view == null) {
            view = View.inflate(viewGroup.getContext(), R.layout.adapter_fun_item, null);
            holderView = new HolderView();
            holderView.content = view.findViewById(R.id.fun_adapter_content);
            holderView.gridView = view.findViewById(R.id.fun_adapter_gridView);
            holderView.itemLay = view.findViewById(R.id.fun_adapter_lay);
            view.setTag(holderView);

            // 绑定listener监听器，检测convertview的height
            holderView.update();
        } else {
            holderView = (HolderView) view.getTag();
        }


        FunBean funBean = (FunBean) getItem(i);
        holderView.content.setText(funBean.getContent());


        holderView.gridAdapter = new FunGridViewAdapter(funBean.getImgUrls());
        holderView.gridView.setAdapter(holderView.gridAdapter);


//        if (holderView.gridAdapter == null) {
//        holderView.gridAdapter = new FunGridViewAdapter(funBean.getImgUrls());
//        holderView.gridView.setAdapter(holderView.gridAdapter);
//        Log.i("llj", "没有adapter");
//        } else {
//            holderView.gridAdapter.notifyDataSetChanged();
//            Log.i("llj", "有adapter,刷新就好！！");
//        }


//        if(holderView.gridView.getAdapter() == null){
//            holderView.gridAdapter = new FunGridViewAdapter(funBean.getImgUrls());
//            holderView.gridView.setAdapter(holderView.gridAdapter);
//            Log.i("llj","没有adapter");
//        }else {
//            holderView.gridAdapter.notifyDataSetChanged();
//            Log.i("llj","有adapter,刷新就好！！");
//        }

//        FunGridViewAdapter adapter = new FunGridViewAdapter(funBean.getImgUrls());
//        holderView.gridView.setAdapter(adapter);

//        if (holderView.gridView.getAdapter() == null) {
//            // 之前没有设置过adapter
//            FunGridViewAdapter adapter = new FunGridViewAdapter(funBean.getImgUrls());
//            holderView.gridView.setAdapter(adapter);
//        } else {
//
//        }


        holderView.content.setTag(i);
        holderView.gridView.setTag(view);


        return view;
    }

    private class HolderView {
        TextView content;
        WapGridView gridView;
        FunGridViewAdapter gridAdapter;

        LinearLayout itemLay;

        void update() {
            // 精确计算GridView的item高度
            itemLay.getViewTreeObserver().addOnGlobalLayoutListener(
                    new ViewTreeObserver.OnGlobalLayoutListener() {
                        public void onGlobalLayout() {

                            // 移除掉监听
                            itemLay.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                            int position = (Integer) content.getTag();
                            // 这里是保证同一行的item高度是相同的！！也就是同一行是齐整的 height相等
                            if (position % 2 == 1) {
                                // 这是右边的item
//                                View leftView = gridView.getChildAt(position - 1);
                                View leftView = leftItemLayMap.get(position - 1);
                                View rightView = (View) gridView.getTag();
//                                View rightView = gridView.getChildAt(position);
                                int leftHeight = leftView.getHeight();
                                int rightHeight = rightView.getHeight();

//                                Log.i("llj", "左边item高度---leftHeight---->>>" + leftHeight);
//                                Log.i("llj", "右边item高度---rightHeight---->>>" + rightHeight);

                                if(leftHeight == rightHeight){
                                    return;
                                }

                                if (leftHeight > rightHeight) {
                                    // 左边的item高度比右边的item高度高
                                    // 则将右边的item高度设置成和左边item的高度一样
//                                    Log.i("llj", "左边item更高---leftHeight---->>>" + leftHeight);
//                                    Log.i("llj", "   ");
//                                    Log.i("llj", "   ");
                                    rightView.setLayoutParams(new GridView.LayoutParams(
                                            GridView.LayoutParams.MATCH_PARENT,
                                            leftHeight));
                                } else {
                                    // 右边的item高度比左边的item高度高
                                    // 则将左边的item高度设置成和右边item的高度一样
//                                    Log.i("llj", "右边item更高---rightHeight---->>>" + rightHeight);
//                                    Log.i("llj", "   ");
//                                    Log.i("llj", "   ");
                                    leftView.setLayoutParams(new GridView.LayoutParams(
                                            GridView.LayoutParams.MATCH_PARENT,
                                            rightHeight));
                                }

                            } else {
                                leftItemLayMap.put(position, itemLay);
                            }
//                            itemLay.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        }
                    });
        }
    }

    public void destory(){
        leftItemLayMap.clear();
        leftItemLayMap = null;
    }
}
