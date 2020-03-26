package cn.net.loveit.release.dialog;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

import java.util.Date;

import cn.net.base.BaseActivity;
import cn.net.base.constant.RoutePath;
import cn.net.base.utils.LogUtils;
import cn.net.loveit.R;
import cn.net.view.utils.BlurBuilder;
import cn.net.view.utils.TimeUtil;

@Route(path = RoutePath.ACTIVITY_RELEASE_SELECT_URI)
public class ReleaseSelectActivity extends BaseActivity implements View.OnClickListener {

    private ImageView release_select_iv;
    private Bitmap blurBg;
    private LinearLayout release_select_buy;
    private ImageView release_select_close;
    private TextView release_select_day;
    private LinearLayout release_select_make;
    private LinearLayout release_select_sale;
    private TextView release_select_time;
    private TextView release_select_week;

    @Override
    protected boolean hideTitle() {
        return true;
    }


    @Override
    protected void initPresenter() {

    }

    @Override
    protected void initIntentDatas() {

    }

    @Override
    protected void initStatusBar() {

    }

    @Override
    protected void initView() {
        release_select_iv = findViewById(R.id.release_select_iv);
        release_select_iv.setImageBitmap(BlurBuilder.blur(this,release_select_iv));

        release_select_buy = findViewById(R.id.release_select_buy);
        release_select_close = findViewById(R.id.release_select_close);
        release_select_day = findViewById(R.id.release_select_day);
        release_select_make = findViewById(R.id.release_select_make);
        release_select_sale = findViewById(R.id.release_select_sale);
        release_select_time = findViewById(R.id.release_select_time);
        release_select_week = findViewById(R.id.release_select_week);

        release_select_close.setOnClickListener(this);
        release_select_make.setOnClickListener(this);
        release_select_sale.setOnClickListener(this);
        release_select_buy.setOnClickListener(this);

        release_select_day.setText(TimeUtil.getDayOfMonth(new Date()));
        release_select_time.setText(TimeUtil.getYearAndMonth(new Date()));
        release_select_week.setText(TimeUtil.getWeek(new Date()));
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_release_select;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BlurBuilder.recycle();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.push_bottom_in,R.anim.push_bottom_out);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.release_select_close:
                finish();
                break;
            case R.id.release_select_buy:
                break;
            case R.id.release_select_make:
                ARouter.getInstance().build(RoutePath.ACTIVITY_RELEASE_PHOTO_URI).navigation();
                finish();
                break;
            case R.id.release_select_sale:
                break;
        }
    }
}
