package cn.net.search;

import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

import cn.net.base.BaseActivity;
import cn.net.base.constant.RoutePath;

@Route(path = RoutePath.ACTIVITY_SEARCH_URI)
public class SearchActivity extends BaseActivity implements View.OnClickListener, TextView.OnEditorActionListener {
//    public static final int SEARCH_TYPE_NONE=1;
//    public static final int SEARCH_TYPE_ALL=4;

//    private int type=SEARCH_TYPE_NONE;
    private SearchNormalFragment searchNFragment;
    private TextView search_cancel;
    private EditText search_edit;
    private SearchAllFragment searchAllFragment;

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
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
//        if (type==SEARCH_TYPE_NONE){
            if (searchNFragment==null)
                searchNFragment = (SearchNormalFragment) ARouter.getInstance().build(RoutePath.FRAGMENT_SEARCH_URI).navigation();
            transaction.replace(R.id.fl_search,searchNFragment);
//        }
        transaction.commitAllowingStateLoss();

        search_cancel = findViewById(R.id.search_cancel);
        search_edit = findViewById(R.id.search_edit);
        search_edit.setFocusable(true);
        search_edit.setFocusableInTouchMode(true);
        search_edit.requestFocus();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

        search_cancel.setOnClickListener(this);
        search_edit.setOnEditorActionListener(this);
        search_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String text=s.toString();
                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                if (TextUtils.isEmpty(text)){
                    searchNFragment = (SearchNormalFragment) ARouter.getInstance().build(RoutePath.FRAGMENT_SEARCH_URI).navigation();
                    transaction.replace(R.id.fl_search,searchNFragment);
                }else{
                    //TODO search
                    searchAllFragment = (SearchAllFragment) ARouter.getInstance().build(RoutePath.FRAGMENT_SEARCH_ALL_URI).navigation();
                    transaction.replace(R.id.fl_search,searchAllFragment);
//                     type=SEARCH_TYPE_ALL;
                }
                transaction.commitAllowingStateLoss();
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (R.id.search_cancel==id){
            finish();
        }
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        switch (actionId){
            case EditorInfo.IME_ACTION_SEARCH:
                //TODO search
                InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                manager.hideSoftInputFromWindow(search_edit.getWindowToken(), 0);

                break;
        }
        return true;
    }

}
