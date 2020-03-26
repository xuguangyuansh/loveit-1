package cn.net.loveit;


import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;

import com.alibaba.android.arouter.launcher.ARouter;
import com.mob.MobSDK;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;

import cn.net.base.LoveitApplication;
import cn.net.third.push.Push;
import cn.net.third.share.WeChatShare;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class MyApplication extends LoveitApplication {
    @Override
    public void onCreate() {
        super.onCreate();

        MobSDK.init(this);
        Push.getInstance().setShowBadge();


        if(isMainProcess()) {
            if (isDebug()) {
                UMConfigure.setLogEnabled(true);
            }

            UMConfigure.init(this, UMConfigure.DEVICE_TYPE_PHONE, null);
            UMConfigure.setProcessEvent(true);
            MobclickAgent.setCatchUncaughtExceptions(true);
            // 选用AUTO页面采集模式
            MobclickAgent.setPageCollectionMode(MobclickAgent.PageMode.LEGACY_MANUAL);
        }

//        WeChatShare.getInstance(this).regToWx();
//        HashMap<String, String> map = new HashMap<>();
//        map.put("type","text");
//        map.put("description","test");
//        WeChatShare.getInstance(this).doShare(WeChatShare.SHARE_TO_TIMELINE,map);



    }

    @Override
    public boolean isDebug() {
        return BuildConfig.DEBUG;
    }

    /**
     * 获取当前进程名
     */
    private String getCurrentProcessName() {
        int pid = android.os.Process.myPid();
        String processName = "";
        ActivityManager manager = (ActivityManager) getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo process : manager.getRunningAppProcesses()) {
            if (process.pid == pid) {
                processName = process.processName;
            }
        }
        return processName;
    }

    /**
     * 包名判断是否为主进程
     *
     * @param
     * @return
     */
    public boolean isMainProcess() {
        return getApplicationContext().getPackageName().equals(getCurrentProcessName());
    }

}
