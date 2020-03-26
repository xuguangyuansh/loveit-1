package cn.net.loveit.sale.content;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import cn.net.base.BaseFragment;
import cn.net.base.utils.LogUtils;
import cn.net.loveit.R;
import cn.net.loveit.adapter.SaleAdapter;
import cn.net.view.viewpager.NoScrollViewPager;

public class SaleContentFragment extends BaseFragment {

    private TabLayout mtabLayout;
    private String [] mTitle = {"娃头娃体","化妆改模","头发眼睛","服饰鞋子","道具配件"};
    private NoScrollViewPager sale_list_viewpager;
    private List<BaseFragment> fragments=new ArrayList<>();

    public static BaseFragment getFragment(int i) {
        SaleContentFragment content = new SaleContentFragment();
        Bundle bundle = new Bundle();
//        bundle.putString("string", string);
        content.setArguments(bundle);
        return content;
    }

    @Override
    protected void initIntentDatas() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_sale_content;
    }

    @Override
    protected void initViews() {
        sale_list_viewpager = getRootView().findViewById(R.id.sale_list_viewpager);
//        sale_list_viewpager.setNoScroll(true);
        fragments.clear();
        for (int i = 0;i<mTitle.length;i++){
            BaseFragment fragment=SaleListFragment.getFragment(i);
            fragments.add(fragment);
        }
        SaleAdapter saleAdapter = new SaleAdapter(getChildFragmentManager(), fragments, mTitle);
        sale_list_viewpager.setAdapter(saleAdapter);

        mtabLayout = getRootView().findViewById(R.id.sale_content_tablayout);
        for (int i = 0;i<mTitle.length;i++){
            mtabLayout.addTab(mtabLayout.newTab().setText(mTitle[i]));
        }

        mtabLayout.setupWithViewPager(sale_list_viewpager);

        mtabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                LogUtils.e("tabPosition",position+"");
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
