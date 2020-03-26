package net.social.mine.setting;

import android.view.View;
import android.widget.LinearLayout;

import com.alibaba.android.arouter.facade.annotation.Route;

import net.social.mine.R;

import cn.net.base.BaseActivity;
import cn.net.base.constant.RoutePath;
import cn.net.base.utils.ToastHelper;

@Route(path = RoutePath.ACTIVITY_MINE_SETTING_URI)
public class SettingActivity extends BaseActivity implements View.OnClickListener {

    private LinearLayout setting_about;
    private LinearLayout setting_address;
    private LinearLayout setting_logout;
    private LinearLayout setting_security;
    private LinearLayout setting_update;

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
        getTitleView().setTitle(R.string.mine_setting);

        setting_about = findViewById(R.id.setting_about);
        setting_address = findViewById(R.id.setting_address);
        setting_logout = findViewById(R.id.setting_logout);
        setting_security = findViewById(R.id.setting_security);
        setting_update = findViewById(R.id.setting_update);

        setting_about.setOnClickListener(this);
        setting_address.setOnClickListener(this);
        setting_logout.setOnClickListener(this);
        setting_security.setOnClickListener(this);
        setting_update.setOnClickListener(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (R.id.setting_about==id){
            ToastHelper.showToast("about");
        }else if (R.id.setting_update==id){
            ToastHelper.showToast("update");

        }else if (R.id.setting_security==id){
            ToastHelper.showToast("security");

        }else if (R.id.setting_logout==id){
            ToastHelper.showToast("logout");

        }else if (R.id.setting_address==id){
            ToastHelper.showToast("address");

        }
    }
}
