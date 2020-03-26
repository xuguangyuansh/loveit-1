package cn.net.base.utils;

import android.util.Log;

import androidx.viewpager.widget.ViewPager;

import java.lang.reflect.Field;

public class ViewUtils {

    public static void clearGutterSize(ViewPager viewPager) {
        try {
            Field field = ViewPager.class.getDeclaredField("mDefaultGutterSize");
            field.setAccessible(true);
            field.set(viewPager, 0);

            viewPager.requestLayout();
        } catch (Exception e) {
            Log.d("MyViewPager", "#clearGutterSize:", e);
        }
    }

}
