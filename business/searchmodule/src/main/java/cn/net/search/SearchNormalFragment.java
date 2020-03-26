package cn.net.search;

import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

import cn.net.base.BaseFragment;
import cn.net.base.constant.RoutePath;

@Route(path = RoutePath.FRAGMENT_SEARCH_URI)
public class SearchNormalFragment extends BaseFragment implements View.OnClickListener {

    private TextView search_normal_make;
    private TextView search_normal_sale;
    private TextView search_normal_user;

    @Override
    protected void initIntentDatas() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_search_normal;
    }

    @Override
    protected void initViews() {
        search_normal_make = getRootView().findViewById(R.id.search_normal_make);
        search_normal_sale = getRootView().findViewById(R.id.search_normal_sale);
        search_normal_user = getRootView().findViewById(R.id.search_normal_user);

        search_normal_make.setOnClickListener(this);
        search_normal_sale.setOnClickListener(this);
        search_normal_user.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (R.id.search_normal_make==id){
            ARouter.getInstance().build(RoutePath.ACTIVITY_SEARCH_BY_TYPE_URI).withInt("type",SearchByTypeActivity.SEARCH_TYPE_MAKE).navigation();
        }else if (R.id.search_normal_sale==id){
            ARouter.getInstance().build(RoutePath.ACTIVITY_SEARCH_BY_TYPE_URI).withInt("type",SearchByTypeActivity.SEARCH_TYPE_SALE).navigation();
        }else if (R.id.search_normal_user==id){
            ARouter.getInstance().build(RoutePath.ACTIVITY_SEARCH_BY_TYPE_URI).withInt("type",SearchByTypeActivity.SEARCH_TYPE_USER).navigation();
        }
    }
}
