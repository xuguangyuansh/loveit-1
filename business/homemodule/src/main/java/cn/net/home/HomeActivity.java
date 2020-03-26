package cn.net.home;

import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

import cn.net.base.BaseActivity;

@Route(path = "/home/index")
public class HomeActivity extends BaseActivity {
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
        findViewById(R.id.tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build("/login/index").navigation();
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_home;
    }
}
