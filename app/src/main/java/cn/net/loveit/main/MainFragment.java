package cn.net.loveit.main;

import android.view.View;
import android.widget.LinearLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

import cn.net.base.BaseFragment;
import cn.net.base.constant.RoutePath;
import cn.net.base.utils.ViewUtils;
import cn.net.loveit.R;
import cn.net.loveit.adapter.MainAdapter;
import cn.net.view.bottombar.BottomBarItem;
import cn.net.view.utils.BlurBuilder;
import cn.net.view.viewpager.NoScrollViewPager;


@Route(path = RoutePath.FRAGMENT_MAIN_URI)
public class MainFragment extends BaseFragment implements View.OnClickListener {
    private NoScrollViewPager viewpager;
    private MainAdapter mainAdapter;
    private BottomBarItem bottom_home;
    private BottomBarItem bottom_sale;
    private BottomBarItem bottom_buy;
    private BottomBarItem bottom_mine;
    private LinearLayout bottom_release;

    @Override
    protected void initIntentDatas() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_main;
    }

    @Override
    protected void initViews() {
        viewpager = getRootView().findViewById(R.id.viewpager);
        viewpager.setNoScroll(true);
        if (mainAdapter == null) {
            mainAdapter = new MainAdapter(getChildFragmentManager());
        }
        ViewUtils.clearGutterSize(viewpager);
        viewpager.setAdapter(mainAdapter);

        bottom_home = getRootView().findViewById(R.id.bottom_home);
        bottom_sale = getRootView().findViewById(R.id.bottom_sale);
        bottom_buy = getRootView().findViewById(R.id.bottom_buy);
        bottom_mine = getRootView().findViewById(R.id.bottom_mine);
        bottom_release = getRootView().findViewById(R.id.bottom_release);

        bottom_home.setOnClickListener(this);
        bottom_sale.setOnClickListener(this);
        bottom_buy.setOnClickListener(this);
        bottom_mine.setOnClickListener(this);
        bottom_release.setOnClickListener(this);

        bottom_home.setSelected(true);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bottom_home:
                viewpager.setCurrentItem(0);
                bottom_home.setSelected(true);
                bottom_sale.setSelected(false);
                bottom_buy.setSelected(false);
                bottom_mine.setSelected(false);
                break;
            case R.id.bottom_sale:
                viewpager.setCurrentItem(1);
                bottom_sale.setSelected(true);
                bottom_home.setSelected(false);
                bottom_buy.setSelected(false);
                bottom_mine.setSelected(false);
                break;
            case R.id.bottom_buy:
                viewpager.setCurrentItem(2);
                bottom_buy.setSelected(true);
                bottom_home.setSelected(false);
                bottom_sale.setSelected(false);
                bottom_mine.setSelected(false);
                break;
            case R.id.bottom_mine:
                viewpager.setCurrentItem(3);
                bottom_mine.setSelected(true);
                bottom_home.setSelected(false);
                bottom_sale.setSelected(false);
                bottom_buy.setSelected(false);
                break;
            case R.id.bottom_release:
                BlurBuilder.snapShotWithoutStatusBar(getActivity());
                ARouter.getInstance().build(RoutePath.ACTIVITY_RELEASE_SELECT_URI).navigation();
                break;
        }
    }
}
