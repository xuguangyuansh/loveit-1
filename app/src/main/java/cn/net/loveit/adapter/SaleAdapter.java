package cn.net.loveit.adapter;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import cn.net.base.BaseFragment;

public class SaleAdapter extends FragmentPagerAdapter {

    private String[] mTitle;
    private List<BaseFragment> fragments;

    public SaleAdapter(FragmentManager fm, List<BaseFragment> fragments, String[] mTitle) {
        super(fm);
        this.fragments=fragments;
        this.mTitle=mTitle;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
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
}
