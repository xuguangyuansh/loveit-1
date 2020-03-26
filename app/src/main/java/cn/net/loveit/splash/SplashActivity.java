package cn.net.loveit.splash;


import android.os.Handler;
import android.text.TextUtils;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

import cn.net.base.BaseActivity;
import cn.net.base.constant.RoutePath;
import cn.net.base.utils.SpUtil;
import cn.net.loveit.R;

@Route(path = RoutePath.ACTIVITY_SPLASH_URI)
public class SplashActivity extends BaseActivity {


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
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                String token = SpUtil.getInstance(SplashActivity.this).getString(SpUtil.KEY_TOKEN);
                if (TextUtils.isEmpty(token)){
                    ARouter.getInstance().build(RoutePath.LOGIN_INDEX).withInt("loginType",1).navigation();
                }else{
                    ARouter.getInstance().build(RoutePath.ACTIVITY_MAIN_URI).navigation();
                }
                finish();
            }
        },3000);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
    }
}
