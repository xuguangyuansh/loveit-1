package cn.net.loveit.adapter;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import cn.net.base.BaseFragment;

import java.util.List;

public class MainActivityAdapter extends FragmentPagerAdapter {
    private List<BaseFragment> fragments;

    public MainActivityAdapter(FragmentManager fm, List<BaseFragment> fragments) {
        super(fm);
        this.fragments=fragments;
    }

    @Override
    public Fragment getItem(int i) {
        return fragments.get(i);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
