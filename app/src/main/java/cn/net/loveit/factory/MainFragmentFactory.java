package cn.net.loveit.factory;


import androidx.fragment.app.Fragment;

import com.alibaba.android.arouter.launcher.ARouter;

import cn.net.base.constant.RoutePath;

import java.util.HashMap;
import java.util.Map;

public class MainFragmentFactory {
    public static int HOME_TAB_COUNT=4;
    private MainFragmentFactory(){

    }
    private static MainFragmentFactory instance;
    public static MainFragmentFactory getInstance(){
        if (instance==null){
            instance=new MainFragmentFactory();
        }
        return instance;
    }
    private Map<Integer, Fragment> fragmentMap=new HashMap<>();
    public Fragment buildFragment(int position){
        Fragment fragment = fragmentMap.get(position);
        if (fragment!=null){
            return fragment;
        }
        switch (position){
            case 0:
                fragment= (Fragment) ARouter.getInstance().build(RoutePath.FRAGMENT_HOME_URI).navigation();
                break;
            case 1:
                fragment= (Fragment) ARouter.getInstance().build(RoutePath.FRAGMENT_SALE_URI).navigation();
                break;
            case 2:
                fragment= (Fragment) ARouter.getInstance().build(RoutePath.FRAGMENT_BUY_URI).navigation();
                break;
            case 3:
                fragment= (Fragment) ARouter.getInstance().build(RoutePath.FRAGMENT_MINE_URI).navigation();
                break;
        }
        fragmentMap.put(position,fragment);
        return fragment;
    }
}
