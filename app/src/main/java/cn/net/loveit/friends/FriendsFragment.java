package cn.net.loveit.friends;

import com.alibaba.android.arouter.facade.annotation.Route;

import cn.net.base.BaseFragment;
import cn.net.base.constant.RoutePath;
import cn.net.loveit.R;

@Route(path = RoutePath.FRAGMENT_FRIENDS_URI)
public class FriendsFragment extends BaseFragment {
    @Override
    protected void initIntentDatas() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_friends;
    }

    @Override
    protected void initViews() {

    }
}
