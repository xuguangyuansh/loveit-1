package cn.net.view.viewpager;

import android.content.Context;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import java.lang.reflect.Field;

public class TouchViewPager extends ViewPager {
    private int lastX;
    private int lastY;
    private int mTouchSlop;
    private float downX;
    private float dx;
    private int startX;
    private int startY;

    public TouchViewPager(@NonNull Context context) {
        super(context);
        init();
    }

    private void init() {
        mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
        try {
            Field mFlingDistance = ViewPager.class.getDeclaredField("mFlingDistance");
            mFlingDistance.setAccessible(true);
//            Field distance = mFlingDistance.get(this);//获取值
            mFlingDistance.set(this, 10); //你定义的值

            Field mMinimumVelocity =  ViewPager.class.getDeclaredField("mFlingDistance");
            mMinimumVelocity.setAccessible(true);
//            Field velocity = mMinimumVelocity.get(this);//获取值
            mMinimumVelocity.set(this, 5);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public TouchViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    @Override
    protected boolean canScroll(View v, boolean checkV, int dx, int x, int y) {
//        if (v instanceof ViewPager) {
//            Log.e("zzViewpager","canScroll2222");
//            return true;
//        }
//        if (getCurrentItem()==0&&dx>0){
//            return false;
//        }
        return super.canScroll(v, checkV, dx, x, y);
    }


}
