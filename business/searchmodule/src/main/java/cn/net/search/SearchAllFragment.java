package cn.net.search;


import androidx.viewpager.widget.ViewPager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import cn.net.base.BaseFragment;
import cn.net.base.constant.RoutePath;
import cn.net.base.utils.ViewUtils;
import cn.net.search.adapter.SearchAdapter;

@Route(path = RoutePath.FRAGMENT_SEARCH_ALL_URI)
public class SearchAllFragment extends BaseFragment {

    private TabLayout search_all_tablayout;
    private ViewPager search_all_viewpager;
    private String [] mTitle = {"创作","贩售","用户"};
    private List<BaseFragment> fragments=new ArrayList<>();
    private SearchAdapter searchAdapter;

    @Override
    protected void initIntentDatas() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_search_all;
    }

    @Override
    protected void initViews() {
        search_all_tablayout = getRootView().findViewById(R.id.search_all_tablayout);
        search_all_viewpager = getRootView().findViewById(R.id.search_all_viewpager);

        for (int i = 0; i < mTitle.length; i++) {
            BaseFragment fragment;
            if (i==0){
                fragment= (BaseFragment) ARouter.getInstance().build(RoutePath.FRAGMENT_SEARCH_MAKE_URI).navigation();
            }else if (i==1){
                fragment= (BaseFragment) ARouter.getInstance().build(RoutePath.FRAGMENT_SEARCH_SALE_URI).navigation();
            }else{
                fragment= (BaseFragment) ARouter.getInstance().build(RoutePath.FRAGMENT_SEARCH_USER_URI).navigation();
            }
            fragments.add(fragment);
        }
        searchAdapter = new SearchAdapter(getChildFragmentManager(), fragments,mTitle);
        search_all_viewpager.setAdapter(searchAdapter);
        ViewUtils.clearGutterSize(search_all_viewpager);

        for (int i = 0;i<mTitle.length;i++){
            search_all_tablayout.addTab(search_all_tablayout.newTab().setText(mTitle[i]));
        }
        search_all_tablayout.setupWithViewPager(search_all_viewpager);
    }
}
