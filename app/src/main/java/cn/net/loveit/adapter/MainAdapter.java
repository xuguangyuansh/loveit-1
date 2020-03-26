package cn.net.loveit.adapter;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import cn.net.loveit.factory.MainFragmentFactory;

import static cn.net.loveit.factory.MainFragmentFactory.HOME_TAB_COUNT;

public class MainAdapter extends FragmentPagerAdapter {
    public MainAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        return MainFragmentFactory.getInstance().buildFragment(i);
    }

    @Override
    public int getCount() {
        return HOME_TAB_COUNT;
    }
}
