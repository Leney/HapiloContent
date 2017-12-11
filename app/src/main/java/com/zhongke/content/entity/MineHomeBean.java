package com.zhongke.content.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${xingen} on 2017/9/29.
 */

public class MineHomeBean {
    public List<MineHome> dataList;

    public static class MineHome {
        public List<String> urlList;
    }

    public static MineHomeBean newInstance() {
        MineHomeBean mineHomeBean = new MineHomeBean();
        mineHomeBean.dataList = new ArrayList<>();
        for (int i = 0; i < 3; ++i) {
            MineHome mineHome = new MineHome();
            mineHome.urlList = new ArrayList<>();
            mineHome.urlList.add("https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=242357529,3678215601&fm=200&gp=0.jpg");
            mineHome.urlList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1506686621377&di=28822a639a02dc4eff4e35439d031f23&imgtype=0&src=http%3A%2F%2Fimgsrc.baidu.com%2Fimgad%2Fpic%2Fitem%2F03087bf40ad162d98724f1381bdfa9ec8b13cdc8.jpg");
            mineHome.urlList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1506686621376&di=b3d4f41b4b09a210c42297fa00683470&imgtype=0&src=http%3A%2F%2Fimgsrc.baidu.com%2Fimage%2Fc0%253Dshijue1%252C0%252C0%252C294%252C40%2Fsign%3D08d2b0f95ee736d14c1e844bf33925b7%2F2e2eb9389b504fc265be4e20efdde71190ef6d36.jpg");
            mineHomeBean.dataList.add(mineHome);
        }
        return mineHomeBean;
    }
}
