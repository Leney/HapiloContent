package com.zhongke.content.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import com.zhongke.content.BaseFragment;
import com.zhongke.content.R;
import com.zhongke.content.activity.DesireActivity;

/**
 * Created by llj on 2017/11/3.
 */

public class WishingTabFragment extends BaseFragment {
    private FrameLayout fragmentLay;
//    /**
//     * 许愿主页面
//     */
//    private WishingHomeFragment homeFragment;
//    /**
//     * 愿望城堡
//     */
//    private WishingCastleFragment castleFragment;
//    /**
//     * 许愿洞
//     */
//    private WishingCaveFragment caveFragment;
//    /**
//     * 许愿城堡选择界面
//     */
//    private WishingCastleSelectFragment castleSelectFragment;

    /**
     * 当前显示的Fragment tag值
     */
    private String curTag = WishingHomeFragment.TAG;

    public static WishingTabFragment newInstance() {
        WishingTabFragment wishingTabFragment = new WishingTabFragment();
        return wishingTabFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_wishing_tab;
    }

    @Override
    protected void initAfterActivityCreated(View rootView, Bundle savedInstanceState) {
        fragmentLay = rootView.findViewById(R.id.wishing_tab_fragment_lay);
        addOrShowOrHideFragment(WishingHomeFragment.TAG);
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
//        Log.i("llj","显示了许愿整体tab");
//        if(!hidden){
//            if (WishingCastleSelectFragment.TAG.equals(curTag)){
//                ((DesireActivity) getActivity()).setWindowBG(R.mipmap.wishing_castle_select_bg);
//            }else {
//                ((DesireActivity) getActivity()).setWindowBG(R.mipmap.launcher_desire_bg);
//            }
//        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.i("llj","显示了许愿整体tab");
        if(isVisibleToUser){
            if (WishingCastleSelectFragment.TAG.equals(curTag)){
                ((DesireActivity) getActivity()).setWindowBG(R.mipmap.wishing_castle_select_bg);
            }else {
                ((DesireActivity) getActivity()).setWindowBG(R.mipmap.launcher_desire_bg);
            }
        }
    }

    /**
     * 回退栈管理
     */
    public void back(){
        if(getChildFragmentManager().getBackStackEntryCount() <= 1){
            // 最后一级了
            getActivity().finish();
        }else {
            getChildFragmentManager().popBackStack();
        }
    }

    /**
     * 添加，显示
     *
     * @param tag
     */
    public void addOrShowOrHideFragment(String tag) {
//        // 当前显示的Fragment
//        Fragment curFragment = getFragmentManager().findFragmentByTag(curTag);

        Fragment fragment = getChildFragmentManager().findFragmentByTag(tag);
        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
//        if (curFragment != null) {
//            fragmentTransaction.hide(curFragment);
//        }
//        List<Fragment> fragmentList = getFragmentManager().getFragments();
        if (fragment == null) {
            if (WishingHomeFragment.TAG.equals(tag)) {
                fragment = WishingHomeFragment.newInstance();
                curTag = WishingHomeFragment.TAG;
            } else if (WishingCastleFragment.TAG.equals(tag)) {
                fragment = WishingCastleFragment.newInstance();
                curTag = WishingCastleFragment.TAG;
            } else if (WishingCaveFragment.TAG.equals(tag)) {
                fragment = WishingCaveFragment.newInstance();
                curTag = WishingCaveFragment.TAG;
            } else if (WishingCastleSelectFragment.TAG.equals(tag)) {
                fragment = WishingCastleSelectFragment.newInstance();
                curTag = WishingCastleSelectFragment.TAG;
            }
            fragmentTransaction.add(R.id.wishing_tab_fragment_lay, fragment, tag);
            if (fragment instanceof WishingCastleSelectFragment) {
                ((DesireActivity) getActivity()).setWindowBG(R.mipmap.wishing_castle_select_bg);
            } else {
                ((DesireActivity) getActivity()).setWindowBG(R.mipmap.launcher_desire_bg);
            }

//            for (Fragment fragment1 : fragmentList) {
//                if (fragment1 != null) {
//                    fragmentTransaction.hide(fragment1);
//                    if (fragment1 instanceof WishingCastleSelectFragment){
//                        setWindowBG(R.mipmap.launcher_desire_bg);
//                    }
//                }
//            }
        } else {
            curTag = fragment.getTag();
            fragmentTransaction.replace(R.id.wishing_tab_fragment_lay,fragment);
            if (fragment instanceof WishingCastleSelectFragment) {
                ((DesireActivity) getActivity()).setWindowBG(R.mipmap.wishing_castle_select_bg);
            } else {
                ((DesireActivity) getActivity()).setWindowBG(R.mipmap.launcher_desire_bg);
            }

//            for (Fragment fragment1 : fragmentList) {
//                if (fragment1 != null) {
//                    if (fragment1 == fragment) {
//                        fragmentTransaction.show(fragment1);
//                        if (fragment instanceof WishingCastleSelectFragment) {
//                            setWindowBG(R.mipmap.wishing_castle_select_bg);
//                        }
//                    } else {
//                        fragmentTransaction.hide(fragment1);
//                        if (fragment1 instanceof WishingCastleSelectFragment) {
//                            setWindowBG(R.mipmap.launcher_desire_bg);
//                        }
//                    }
//                }
//
//            }
        }
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commitAllowingStateLoss();
    }


}
