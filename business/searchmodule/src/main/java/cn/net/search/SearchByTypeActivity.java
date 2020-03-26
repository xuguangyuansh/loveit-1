package cn.net.search;

import android.content.Context;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

import cn.net.base.BaseActivity;
import cn.net.base.constant.RoutePath;
import cn.net.search.make.SearchMakeFragment;
import cn.net.search.sale.SearchSaleFragment;
import cn.net.search.user.SearchUserFragment;

@Route(path = RoutePath.ACTIVITY_SEARCH_BY_TYPE_URI)
public class SearchByTypeActivity extends BaseActivity implements View.OnClickListener, TextView.OnEditorActionListener {
    public static final int SEARCH_TYPE_MAKE=1;
    public static final int SEARCH_TYPE_SALE=2;
    public static final int SEARCH_TYPE_USER=3;
    private int type;
    private SearchMakeFragment makeFragment;
    private SearchSaleFragment saleFragment;
    private SearchUserFragment userFragment;
    private TextView search_type_cancel;
    private EditText search_type_edit;
    private ImageView search_type_icon;

    @Override
    protected boolean hideTitle() {
        return true;
    }

    @Override
    protected void initPresenter() {

    }

    @Override
    protected void initIntentDatas() {
        Intent intent = getIntent();
        type=intent.getIntExtra("type",0);
    }

    @Override
    protected void initStatusBar() {

    }

    @Override
    protected void initView() {
        search_type_cancel = findViewById(R.id.search_type_cancel);
        search_type_edit = findViewById(R.id.search_type_edit);
        search_type_icon = findViewById(R.id.search_type_icon);

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        if (type==SEARCH_TYPE_MAKE){
            search_type_edit.setHint(getString(R.string.search_make));
            search_type_icon.setImageResource(R.mipmap.ic_tab_home_s);
            makeFragment = (SearchMakeFragment) ARouter.getInstance().build(RoutePath.FRAGMENT_SEARCH_MAKE_URI).navigation();
            transaction.replace(R.id.fl_search_type, makeFragment);
        }else if (type==SEARCH_TYPE_SALE){
            search_type_icon.setImageResource(R.mipmap.ic_tab_sale_n);
            search_type_edit.setHint(getString(R.string.search_sale));
            saleFragment = (SearchSaleFragment) ARouter.getInstance().build(RoutePath.FRAGMENT_SEARCH_SALE_URI).navigation();
            transaction.replace(R.id.fl_search_type, saleFragment);
        }else if (type==SEARCH_TYPE_USER){
            search_type_icon.setImageResource(R.mipmap.ic_tab_me_n);
            search_type_edit.setHint(getString(R.string.search_user));
            userFragment = (SearchUserFragment) ARouter.getInstance().build(RoutePath.FRAGMENT_SEARCH_USER_URI).navigation();
            transaction.replace(R.id.fl_search_type, userFragment);
        }
        transaction.commitAllowingStateLoss();

        search_type_cancel.setOnClickListener(this);

        search_type_edit.setFocusable(true);
        search_type_edit.setFocusableInTouchMode(true);
        search_type_edit.requestFocus();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        search_type_edit.setOnEditorActionListener(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_search_type;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (R.id.search_type_cancel==id){
            finish();
        }
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        switch (actionId){
            case EditorInfo.IME_ACTION_SEARCH:
                //TODO search
                InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                manager.hideSoftInputFromWindow(search_type_edit.getWindowToken(), 0);

                break;
        }
        return true;
    }
}
