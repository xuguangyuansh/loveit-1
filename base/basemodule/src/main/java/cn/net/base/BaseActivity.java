package cn.net.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.facade.annotation.Route;

import cn.net.base.constant.RoutePath;
import cn.net.base.utils.LogUtils;
import cn.net.basemodule.R;
import cn.net.third.track.UMTrack;
import cn.net.view.network.NoNetWorkView;
import cn.net.view.titleView.TitleView;

import java.util.ArrayList;
import java.util.List;

@Route(path = RoutePath.ACTIVITY_BASE_URI)
public abstract class BaseActivity extends AppCompatActivity {

    private LinearLayout title_container;
    private FrameLayout content_container;
    private TitleView titleView;
    private NoNetWorkView no_net_view;
    boolean isGrant=false;
    List<String> noGrantPermission=new ArrayList<>();
    public static int REQUEST_CODE_NET_PERMISSION=10;
    private NetworkReceiver netReceiver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initStatusBar();
        initAnmi();
        //设置竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_base);
        title_container = findViewById(R.id.title_container);
        content_container = findViewById(R.id.content_container);
        no_net_view = findViewById(R.id.no_net_view);
//        LogUtils.e("zz",this.getClass().getSimpleName()+".."+this.hideTitle());
        initNoNetView();
        LayoutInflater.from(this).inflate(getLayoutId(), content_container);
        initData(savedInstanceState);
        initIntentDatas();
        if (!hideTitle()) {
            if (!isToolBar()) {
                //隐藏toolbar
                hideToolBar();
                View view = initTitle();
                if (view != null) {
                    title_container.addView(view);
                }
                title_container.setVisibility(View.VISIBLE);
            } else {
                title_container.setVisibility(View.GONE);
            }
        } else {
            title_container.setVisibility(View.GONE);
            //隐藏toolbar
            hideToolBar();
        }
        initView();
        initPresenter();
        if (netReceiver==null){
            netReceiver=new NetworkReceiver();
            registerReceiver(netReceiver,new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        }
    }

    public void initAnmi() {

    }

    public void requestPermission(String permission){
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M) {
            int hasPermission = checkSelfPermission(permission);
            if (hasPermission== PackageManager.PERMISSION_DENIED){
                requestPermissions(new String[]{permission}, REQUEST_CODE_NET_PERMISSION);
            }else{
                //直接获取网络状态

            }
        }
    }

    private void initNoNetView() {
//        String[] permission=new String[]{Manifest.permission.ACCESS_NETWORK_STATE};
//        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M) {
//            int hasPermission = checkSelfPermission(Manifest.permission.ACCESS_NETWORK_STATE);
//            if (hasPermission== PackageManager.PERMISSION_DENIED){
//                被拒绝之后不显示
//                showNoNetView(false,0,0);
//            }else{
//                直接获取网络状态
//                isShowNoNetView();
//            }
//
//        }else{
//            直接获取网络状态
            isShowNoNetView();
//        }
    }

    public void isShowNoNetView(){
        if (isNetworkConnected(this)){
            int apnType = getAPNType(this);
            if (apnType==0||apnType==3){
                showNoNetView(true,R.string.no_net_top,R.string.no_net_bottom);
            }else{
                showNoNetView(false,0,0);
            }
        }else{
            showNoNetView(true,R.string.no_net_top,R.string.no_net_bottom);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==REQUEST_CODE_NET_PERMISSION){
            if (grantResults!=null){
                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults[i]==PackageManager.PERMISSION_DENIED){
                        isGrant=false;
                        noGrantPermission.add(permissions[i]);
                    }else{
                        isGrant=true;
                    }
                }
                if (isGrant){
                    //执行请求
                    doPermission();
                }else{
                    //没有授权
                    doNotPermission(noGrantPermission);
                }
            }
        }
    }

    private void doPermission() {

    }

    private void doNotPermission(List<String> noGrantPermission) {

    }

    public void showNoNetView(boolean show, int top, int bottom) {
        if (no_net_view != null) {
            if (show) {
                no_net_view.setCloseClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (no_net_view != null) {
                            no_net_view.setVisibility(View.GONE);
                        }
                    }
                });
                no_net_view.setTopText(top);
                no_net_view.setBottomText(bottom);
                no_net_view.setVisibility(View.VISIBLE);
            } else {
                no_net_view.setVisibility(View.GONE);
            }
        }
    }

    private void hideToolBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.hide();
    }

    protected abstract boolean hideTitle();
//    {
//        LogUtils.e("zz","base");
//        return true;
//    }


    public View initTitle() {
        titleView = new TitleView(this);
        titleView.setBackImageRes(R.mipmap.common_back);
        titleView.setBackPressListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        return titleView;
    }

    public TitleView getTitleView() {
        return titleView;
    }


    public boolean isToolBar() {
        return true;
    }

    private void initData(Bundle savedInstanceState) {

    }

    protected abstract void initPresenter();

    protected abstract void initIntentDatas();

    protected abstract void initStatusBar();

    protected abstract void initView();

    public abstract int getLayoutId();


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        initNewIntent(intent);
    }

    public void initNewIntent(Intent intent) {

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        try {
            if (LoveitApplication.getInstance().isEnglish()) {
                LoveitApplication.getInstance().switchLanguage(LoveitApplication.LOCALE_LANGUAGE_US,
                        LoveitApplication.CHINA, this, false);
            } else {
                LoveitApplication.getInstance().switchLanguage(LoveitApplication.LOCALE_LANGUAGE_CN,
                        LoveitApplication.US, this, false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isNetworkConnected(Context context) {
        if (context==null){
            return false;
        }
        ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
        if (mNetworkInfo != null) {
            return mNetworkInfo.isAvailable();
        }
        return false;
    }

    public int getAPNType(Context context) {
        int netType = 0;
        if (context==null){
            return netType;
        }
        ConnectivityManager connMgr = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo == null) {
            return netType;
        }
        int nType = networkInfo.getType();
        if (nType == ConnectivityManager.TYPE_WIFI) {
            netType = 1;// wifi
        } else if (nType == ConnectivityManager.TYPE_MOBILE) {
            int nSubType = networkInfo.getSubtype();
            TelephonyManager mTelephony = (TelephonyManager) context
                    .getSystemService(Context.TELEPHONY_SERVICE);
            if (nSubType == TelephonyManager.NETWORK_TYPE_UMTS
                    && !mTelephony.isNetworkRoaming()) {
                netType = 2;// 3G
            } else {
                netType = 3;// 2G
            }
        }
        return netType;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (netReceiver!=null){
            unregisterReceiver(netReceiver);
            netReceiver=null;
        }
    }

    public class NetworkReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeInfo = manager.getActiveNetworkInfo();
            //如果无网络连接activeInfo为null
            //也可获取网络的类型
            if (activeInfo != null) {
                //网络连接
                showNoNetView(false,0,0);
            } else {
                //网络断开
                showNoNetView(true,R.string.no_net_top,R.string.no_net_bottom);
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        try{
            UMTrack.getInstance(this).onPause();
        }catch (Exception e){

        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        try{
            UMTrack.getInstance(this).onResume();
        }catch (Exception e){

        }
    }
}
