package cn.net.base.utils;

import android.content.Context;
import android.content.pm.PackageInfo;


public class AppInfoUtils {

    public static PackageInfo getMyPackageInfo(Context context) {
        PackageInfo info = null;
        try {
            info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
        } catch (Exception e) {
        }
        return info;
    }
}
