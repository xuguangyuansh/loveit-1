package cn.net.loveit.detail;

import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.alibaba.android.arouter.facade.annotation.Route;

import cn.net.base.BaseActivity;
import cn.net.base.constant.RoutePath;
import cn.net.base.utils.Util;
import cn.net.loveit.R;
import cn.net.view.utils.UIUtil;

@Route(path= RoutePath.ACTIVITY_GOOD_DETAIL)
public class GoodDetailActivity extends BaseActivity {

    private LinearLayout good_image_ll;

    @Override
    protected boolean hideTitle() {
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
    public boolean isToolBar() {
        return false;
    }

    @Override
    protected void initView() {
        getTitleView().setTitle("");

        good_image_ll = findViewById(R.id.good_image_ll);
        good_image_ll.removeAllViews();
        for (int i = 0; i < 3; i++) {
            ImageView imageView = new ImageView(this);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.height= UIUtil.dip2px(this,300);
            layoutParams.bottomMargin=UIUtil.dip2px(this,4);
            imageView.setBackgroundColor(Color.BLUE);
            good_image_ll.addView(imageView,layoutParams);
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_good_detail;
    }
}
