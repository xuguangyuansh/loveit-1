package cn.net.view.utils;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Display;

public class ScreenUtils {
    public static int getScreenWidth(Activity activity) {
        int widthPixels;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            DisplayMetrics outMetrics = new DisplayMetrics();
            activity.getWindowManager ().getDefaultDisplay().getRealMetrics(outMetrics);
            widthPixels= outMetrics.widthPixels;
        }else{
            Display display = activity.getWindowManager().getDefaultDisplay();
            widthPixels = display.getWidth();
        }
        return widthPixels;
    }

    public static int getScreenHeight(Activity activity) {
        int heightPixels;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            DisplayMetrics outMetrics = new DisplayMetrics();
            activity.getWindowManager ().getDefaultDisplay().getRealMetrics(outMetrics);
            heightPixels = outMetrics.heightPixels;
        }else{
            Display display = activity.getWindowManager().getDefaultDisplay();
            heightPixels = display.getHeight();
        }
        return heightPixels;
    }

    public static int getNavigationBarHeight(Activity activity) {
        Resources resources = activity.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height","dimen", "android");
        int height = resources.getDimensionPixelSize(resourceId);
        return height;
    }
}
