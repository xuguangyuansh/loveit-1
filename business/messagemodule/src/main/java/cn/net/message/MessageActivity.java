package cn.net.message;

import com.alibaba.android.arouter.facade.annotation.Route;

import cn.net.base.BaseActivity;
import cn.net.base.constant.RoutePath;

@Route(path=RoutePath.ACTIVITY_MESSAGE_URI)
public class MessageActivity extends BaseActivity {
    @Override
    protected boolean hideTitle() {
        return true;
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

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_message;
    }
}
