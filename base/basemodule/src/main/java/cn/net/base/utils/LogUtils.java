package cn.net.base.utils;

import android.util.Log;

import cn.net.base.LoveitApplication;

public class LogUtils {
    public static void v(String tag, String msg) {
        if (LoveitApplication.getInstance().isDebug() == false) {
            return;
        }
        Log.v(tag, msg);
    }

    public static void d(String tag, String msg) {
        if (LoveitApplication.getInstance().isDebug()  == false) {
            return;
        }
        Log.d(tag, msg);
    }

    public static void i(String tag, String msg) {
        if (LoveitApplication.getInstance().isDebug()  == false) {
            return;
        }
        Log.i(tag, msg);
    }

    public static void w(String tag, String msg) {
        if (LoveitApplication.getInstance().isDebug()  == false) {
            return;
        }
        Log.w(tag, msg);
    }

    public static void e(String tag, String msg) {
        if (LoveitApplication.getInstance().isDebug()  == false) {
            return;
        }
        Log.e(tag, msg);
    }
}
