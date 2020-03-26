package cn.net.loveit.buy;

import androidx.viewpager.widget.ViewPager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import cn.net.base.BaseFragment;
import cn.net.base.constant.RoutePath;
import cn.net.base.utils.ViewUtils;
import cn.net.loveit.R;
import cn.net.loveit.adapter.SaleAdapter;
import cn.net.loveit.buy.content.BuyListFragment;
import cn.net.loveit.sale.content.SaleContentFragment;

@Route(path = RoutePath.FRAGMENT_BUY_URI)
public class BuyFragment extends BaseFragment {
    private ViewPager buy_viewpager;
    private static int PAGE_COUNT=2;
    private List<BaseFragment> fragments=new ArrayList<>();
    private SaleAdapter buyAdapter;
    private String [] mTitle = {"全新","闲置"};
    private TabLayout mtabLayout;

    @Override
    protected void initIntentDatas() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_buy;
    }

    @Override
    protected void initViews() {
        buy_viewpager=getRootView().findViewById(R.id.buy_viewpager);

        fragments.clear();
        for (int i = 0; i < PAGE_COUNT; i++) {
            BaseFragment recommand = BuyListFragment.getFragment(i);
            fragments.add(recommand);
        }
        buyAdapter = new SaleAdapter(getChildFragmentManager(), fragments,mTitle);
        buy_viewpager.setAdapter(buyAdapter);
        ViewUtils.clearGutterSize(buy_viewpager);
        mtabLayout=getRootView().findViewById(R.id.buy_tablayout);
        for (int i = 0;i<mTitle.length;i++){
            mtabLayout.addTab(mtabLayout.newTab().setText(mTitle[i]));
        }
        mtabLayout.setupWithViewPager(buy_viewpager);
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
    }
}
