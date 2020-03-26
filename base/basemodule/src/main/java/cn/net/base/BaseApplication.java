package cn.net.base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;

import androidx.multidex.MultiDex;

import com.alibaba.android.arouter.launcher.ARouter;

import java.util.Locale;

public abstract class BaseApplication extends Application {
    public static final String LOCALE_LANGUAGE_CN = "lan-CN";
    public static final String LOCALE_LANGUAGE_US = "lan-US";
    public static final String CHINA = "china";
    public static final String US = "us";
    private static BaseApplication mInstance;

    public static BaseApplication getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;

    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
    }

    public void updateLanguage(String language, String country) {

//        Log.e("language:" + language + " country:" + country);
//        LanguageCache.setLanguage(language + "-" + country);

        Resources res = getResources();
        Configuration config = res.getConfiguration();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            config.setLocale(new Locale(language));
        } else {
            config.locale = new Locale(language);
        }
        DisplayMetrics dm = res.getDisplayMetrics();
        res.updateConfiguration(config, dm);
        res.flushLayoutCache();


//        LanguageCache.setMyLanguage(this, language);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (isEnglish()) {
            updateLanguage(LOCALE_LANGUAGE_CN, CHINA);
        } else {
            updateLanguage(LOCALE_LANGUAGE_US, US);
        }
    }

    public boolean isEnglish() {
        return false;
    }

    public void switchLanguage(String language, String country, Activity act, boolean restartApp) {
//        Logger.e("switchLanguage language:" + language + " country:" + country);
        updateLanguage(language, country);

        if (restartApp) {
            Intent home = new Intent();
            home.setClassName(act, "xxxxxxxxx");
            home.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            act.startActivity(home);
            act.finish();
        }
    }


    public abstract boolean isDebug();

    public String getLanguage() {
        return "";
    }
}
