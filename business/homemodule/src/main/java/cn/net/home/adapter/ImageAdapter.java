package cn.net.home.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class ImageAdapter extends PagerAdapter {
    private Context ctx;

    public ImageAdapter(Context ctx){
        this.ctx=ctx;
    }
    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view==o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = new View(ctx);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewPager.LayoutParams.MATCH_PARENT,ViewPager.LayoutParams.MATCH_PARENT));
        if (position%4==0) {
            view.setBackgroundColor(Color.parseColor("#ff0000"));
        }else if (position%4==1) {
            view.setBackgroundColor(Color.parseColor("#00ff00"));
        }else if (position%4==2) {
            view.setBackgroundColor(Color.parseColor("#0000ff"));
        }else if (position%4==3) {
            view.setBackgroundColor(Color.parseColor("#ffffff"));
        }
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
