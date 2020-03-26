package net.social.mine.other;

import android.content.Intent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.viewpager.widget.ViewPager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.google.android.material.tabs.TabLayout;

import net.social.mine.R;

import cn.net.base.BaseActivity;
import cn.net.base.constant.RoutePath;

@Route(path = RoutePath.ACTIVITY_OTHER_PAGE_URI)
public class OtherPageActivity extends BaseActivity {

    private LinearLayout other_page_contact;
    private LinearLayout other_page_fans_ll;
    private TextView other_page_fans_text;
    private LinearLayout other_page_focus_ll;
    private TextView other_page_focus_text;
    private TextView other_page_nickname;
    private ImageView other_page_photo;
    private TabLayout other_page_tablayout;
    private LinearLayout other_page_tags_ll;
    private TextView other_page_to_focus;
    private ViewPager other_page_viewpager;
    private String customerId;

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
        Intent intent = getIntent();
        customerId = intent.getStringExtra("customerId");
    }

    @Override
    protected void initStatusBar() {

    }

    @Override
    protected void initView() {
        other_page_contact = findViewById(R.id.other_page_contact);
        other_page_fans_ll = findViewById(R.id.other_page_fans_ll);
        other_page_fans_text = findViewById(R.id.other_page_fans_text);
        other_page_focus_ll = findViewById(R.id.other_page_focus_ll);
        other_page_focus_text = findViewById(R.id.other_page_focus_text);
        other_page_nickname = findViewById(R.id.other_page_nickname);
        other_page_photo = findViewById(R.id.other_page_photo);
        other_page_tablayout = findViewById(R.id.other_page_tablayout);
        other_page_tags_ll = findViewById(R.id.other_page_tags_ll);
        other_page_to_focus = findViewById(R.id.other_page_to_focus);
        other_page_viewpager = findViewById(R.id.other_page_viewpager);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_other_pager;
    }
}
