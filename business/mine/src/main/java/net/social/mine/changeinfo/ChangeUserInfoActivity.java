package net.social.mine.changeinfo;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;

import net.social.mine.R;

import cn.net.base.BaseActivity;
import cn.net.base.constant.RoutePath;

@Route(path = RoutePath.ACTIVITY_MINE_CHANGE_INFO_URI)
public class ChangeUserInfoActivity extends BaseActivity {

    private LinearLayout change_photo_ll;
    private ImageView iv_photo;
    private LinearLayout change_nickname_ll;
    private TextView change_nickname_text;
    private LinearLayout change_gender_ll;
    private TextView change_gender_text;
    private LinearLayout change_birthday_ll;
    private TextView change_birthday_text;
    private LinearLayout change_tags_ll;
    private LinearLayout tags_ll;

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
        getTitleView().setTitle(getString(R.string.mine_change_info));

        change_photo_ll = findViewById(R.id.change_photo_ll);
        iv_photo = findViewById(R.id.iv_photo);
        change_nickname_ll = findViewById(R.id.change_nickname_ll);
        change_nickname_text = findViewById(R.id.change_nickname_text);
        change_gender_ll = findViewById(R.id.change_gender_ll);
        change_gender_text = findViewById(R.id.change_gender_text);
        change_birthday_ll = findViewById(R.id.change_birthday_ll);
        change_birthday_text = findViewById(R.id.change_birthday_text);
        change_tags_ll = findViewById(R.id.change_tags_ll);
        tags_ll = findViewById(R.id.tags_ll);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_change_info;
    }
}
