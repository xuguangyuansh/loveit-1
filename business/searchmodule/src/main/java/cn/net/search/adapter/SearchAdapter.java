package cn.net.search.adapter;


import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

import cn.net.base.BaseFragment;

public class SearchAdapter extends FragmentPagerAdapter {
    private String[] mTitle;
    private List<BaseFragment> fragments;

    public SearchAdapter(FragmentManager fm, List<BaseFragment> fragments, String[] mTitle) {
        super(fm);
        this.fragments=fragments;
        this.mTitle=mTitle;
    }

    @Override
    public Fragment getItem(int i) {

        return fragments.get(i);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitle[position];
    }


    //    @Override
//    public int getItemPosition(@NonNull Object object) {
//        return PagerAdapter.POSITION_NONE;
//    }
}
