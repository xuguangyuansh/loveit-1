package cn.net.base.utils;

import android.content.Context;
import android.widget.Toast;

import cn.net.base.LoveitApplication;


public class ToastHelper {
    private static Toast toast;

    public static void showToast(Context context, String string) {
        showToast(string);
    }

    public static void showToast(String string) {
        try {
//            if (toast == null) {
                toast = Toast.makeText(LoveitApplication.getInstance().getApplicationContext(), string, Toast.LENGTH_SHORT);
            /*} else {
                toast.setText(string);
            }*/
            toast.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showToast(Context context, int string) {
        try {
            String message = context.getResources().getString(string);
            showToast(context, message);
        } catch (Exception e) {
        }
    }

    public static void doConcel() {
        if (toast != null) {
            toast.cancel();
            toast = null;
        }
    }

}