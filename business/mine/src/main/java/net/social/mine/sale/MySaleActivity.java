package net.social.mine.sale;


import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;

import net.social.mine.R;

import cn.net.base.BaseActivity;
import cn.net.base.constant.RoutePath;

@Route(path = RoutePath.ACTIVITY_MINE_MY_SALE_URI)
public class MySaleActivity extends BaseActivity {

    private RecyclerView my_focus_list;

    @Override
    protected boolean hideTitle() {
        return false;
    }

    @Override
    public boolean isToolBar() {
        return false;
    }

    @Override
    protected void initPresenter() {

    }

    @Override
    protected void initIntentDatas() {

    }

    @Override
    protected void initStatusBar() {

    }

    @Override
    protected void initView() {
        getTitleView().setTitle(R.string.my_sale);
        my_focus_list = findViewById(R.id.my_focus_list);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_my_focus;
    }
}
