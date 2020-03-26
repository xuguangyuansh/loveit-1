package cn.net.loveit;

import android.app.ActivityManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.Keep;
import androidx.multidex.MultiDex;

import com.alibaba.android.arouter.launcher.ARouter;
import com.taobao.sophix.PatchStatus;
import com.taobao.sophix.SophixApplication;
import com.taobao.sophix.SophixEntry;
import com.taobao.sophix.SophixManager;
import com.taobao.sophix.listener.PatchLoadStatusListener;

import java.util.Date;

import cn.net.base.utils.DateUtil;
import cn.net.base.utils.SpUtil;

/**
 * Sophix入口类，专门用于初始化Sophix，不应包含任何业务逻辑。
 * 此类必须继承自SophixApplication，onCreate方法不需要实现。
 * 此类不应与项目中的其他类有任何互相调用的逻辑，必须完全做到隔离。
 * AndroidManifest中设置application为此类，而SophixEntry中设为原先Application类。
 * 注意原先Application里不需要再重复初始化Sophix，并且需要避免混淆原先Application类。
 * 如有其它自定义改造，请咨询官方后妥善处理。
 */
public class SophixStubApplication extends SophixApplication {
    private final String TAG = "SophixStubApplication";

    // 此处SophixEntry应指定真正的Application，并且保证RealApplicationStub类名不被混淆。
    @Keep
    @SophixEntry(MyApplication.class)
    static class RealApplicationStub {
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
//         如果需要使用MultiDex，需要在此处调用。
        MultiDex.install(this);
        initSophix();
    }

    private void initSophix() {
        String appVersion = "0.0.0";
        try {
            appVersion = this.getPackageManager()
                    .getPackageInfo(this.getPackageName(), 0)
                    .versionName;
        } catch (Exception e) {
        }
        final SophixManager instance = SophixManager.getInstance();
        instance.setContext(this)
                .setAppVersion(appVersion)
                .setSecretMetaData(null, null, null)
                .setEnableDebug(true)
                .setEnableFullLog()
                .setPatchLoadStatusStub(new PatchLoadStatusListener() {
                    @Override
                    public void onLoad(final int mode, final int code, final String info, final int handlePatchVersion) {
                        if (code == PatchStatus.CODE_LOAD_SUCCESS) {
                            Log.i(TAG, "sophix load patch success!");
                        } else if (code == PatchStatus.CODE_LOAD_RELAUNCH) {
                            // 如果需要在后台重启，建议此处用SharePreference保存状态。
                            Log.i(TAG, "sophix preload patch success. restart app to make effect.");
                            SophixManager.getInstance().killProcessSafely();
                        }else if (code==PatchStatus.CODE_LOAD_FAIL){
                            SophixManager.getInstance().cleanPatches();
                        }
                    }
                }).initialize();
    }


    @Override
    public void onCreate() {
        super.onCreate();
        if(isDebug()) {
            ARouter.openDebug();
            ARouter.openLog();
        }
        Log.e("zz","ARouter init");
        ARouter.init(this);
        // queryAndLoadNewPatch不可放在attachBaseContext 中，否则无网络权限，建议放在后面任意时刻，如onCreate中
        //需要判断如果小于20次就调用此方法
        String countStr = SpUtil.getInstance(this).getString(SpUtil.KEY_HOT_FIX_COUNT);
        if (TextUtils.isEmpty(countStr)||!countStr.contains("-")) {
            SophixManager.getInstance().queryAndLoadNewPatch();
            SpUtil.getInstance(this).addString(SpUtil.KEY_HOT_FIX_COUNT,new Date().getTime()+"-"+1);
        }else{
            String[] strs = countStr.split("-");
            if (DateUtil.isToday(new Date(Long.valueOf(strs[0])))){
                int count=Integer.valueOf(strs[1]);
                if (count<20){
                    SophixManager.getInstance().queryAndLoadNewPatch();
                    SpUtil.getInstance(this).addString(SpUtil.KEY_HOT_FIX_COUNT,new Date().getTime()+"-"+(count+1));
                }
            }else{
                SophixManager.getInstance().queryAndLoadNewPatch();
                SpUtil.getInstance(this).addString(SpUtil.KEY_HOT_FIX_COUNT,new Date().getTime()+"-"+1);
            }
        }
    }

    public boolean isDebug() {
        return BuildConfig.DEBUG;
    }

}
