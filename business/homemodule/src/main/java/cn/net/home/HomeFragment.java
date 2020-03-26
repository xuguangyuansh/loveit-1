package cn.net.home;

import android.view.View;
import android.widget.LinearLayout;

import androidx.viewpager.widget.ViewPager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.google.android.material.tabs.TabLayout;

import cn.net.base.BaseFragment;
import cn.net.base.constant.RoutePath;
import cn.net.base.utils.ViewUtils;
import cn.net.home.adapter.HomeAdapter;

import java.util.ArrayList;
import java.util.List;

@Route(path = RoutePath.FRAGMENT_HOME_URI)
public class HomeFragment extends BaseFragment implements View.OnClickListener {

    private ViewPager home_viewpager;
    private static int PAGE_COUNT=2;
    private List<BaseFragment> fragments=new ArrayList<>();
    private HomeAdapter homeAdapter;
    private String [] mTitle = {"推荐","大大"};
    private TabLayout mtabLayout;
    private LinearLayout home_notification;
    private LinearLayout home_search;

    @Override
    protected void initIntentDatas() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initViews() {
        home_viewpager = getRootView().findViewById(R.id.home_viewpager);
        fragments.clear();
        for (int i = 0; i < PAGE_COUNT; i++) {
            BaseFragment recommand = RecommandFragment.getFragment(i);
            fragments.add(recommand);
        }
        homeAdapter = new HomeAdapter(getChildFragmentManager(), fragments,mTitle);
        home_viewpager.setAdapter(homeAdapter);
        ViewUtils.clearGutterSize(home_viewpager);
        mtabLayout=getRootView().findViewById(R.id.home_tablayout);
        for (int i = 0;i<mTitle.length;i++){
            mtabLayout.addTab(mtabLayout.newTab().setText(mTitle[i]));
        }
        mtabLayout.setupWithViewPager(home_viewpager);
        mtabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        home_notification = getRootView().findViewById(R.id.home_notification);
        home_notification.setOnClickListener(this);

        home_search = getRootView().findViewById(R.id.home_search);
        home_search.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (R.id.home_notification==id){
            ARouter.getInstance().build(RoutePath.ACTIVITY_MESSAGE_URI).navigation();
        }else if (R.id.home_search==id){
            ARouter.getInstance().build(RoutePath.ACTIVITY_SEARCH_URI).navigation();
        }
    }
}
