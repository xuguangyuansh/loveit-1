package cn.net.loveit.sale;

import androidx.viewpager.widget.ViewPager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import cn.net.base.BaseFragment;
import cn.net.base.constant.RoutePath;
import cn.net.base.utils.ViewUtils;
import cn.net.home.RecommandFragment;
import cn.net.home.adapter.HomeAdapter;
import cn.net.loveit.R;
import cn.net.loveit.adapter.SaleAdapter;
import cn.net.loveit.sale.content.SaleContentFragment;

@Route(path = RoutePath.FRAGMENT_SALE_URI)
public class SaleFragment extends BaseFragment {

    private ViewPager sale_viewpager;
    private static int PAGE_COUNT=2;
    private List<BaseFragment> fragments=new ArrayList<>();
    private SaleAdapter saleAdapter;
    private String [] mTitle = {"全新","闲置"};
    private TabLayout mtabLayout;

    @Override
    protected void initIntentDatas() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_sale;
    }

    @Override
    protected void initViews() {
        sale_viewpager=getRootView().findViewById(R.id.sale_viewpager);

        fragments.clear();
        for (int i = 0; i < PAGE_COUNT; i++) {
            BaseFragment recommand = SaleContentFragment.getFragment(i);
            fragments.add(recommand);
        }
        saleAdapter = new SaleAdapter(getChildFragmentManager(), fragments,mTitle);
        sale_viewpager.setAdapter(saleAdapter);
        ViewUtils.clearGutterSize(sale_viewpager);
        mtabLayout=getRootView().findViewById(R.id.sale_tablayout);
        for (int i = 0;i<mTitle.length;i++){
            mtabLayout.addTab(mtabLayout.newTab().setText(mTitle[i]));
        }
        mtabLayout.setupWithViewPager(sale_viewpager);
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
