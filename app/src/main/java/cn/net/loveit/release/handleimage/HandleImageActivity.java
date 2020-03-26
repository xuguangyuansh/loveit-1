package cn.net.loveit.release.handleimage;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.os.Parcelable;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import cn.net.base.BaseActivity;
import cn.net.base.constant.RoutePath;
import cn.net.camera.task.SavePictureTask;
import cn.net.loveit.R;
import cn.net.loveit.release.task.HandleImageListener;
import cn.net.loveit.release.task.HandleImageTask;
import cn.net.view.utils.FileUtil;
import cn.net.view.utils.GlideUtil;
import cn.net.view.utils.UIUtil;


@Route(path = RoutePath.ACTIVITY_HANDLE_IMAGE_URI)
public class HandleImageActivity extends BaseActivity implements SavePictureTask.OnPictureSaveListener {

    private ImageView crop_image;
    private Uri photoUri;
    private LinearLayout photo_image;
    private LinearLayout photo_continue;
    private LinearLayout handle_image_filter_ll;
    private Bitmap savedBitmap;
    private boolean isOrigin=true;

    private String[] filters=new String[]{"原始","软化","黑白","经典","华丽","复古","优雅","电影","回忆","优格","流年","发光"};

    @Override
    protected boolean hideTitle() {
        return true;
    }

    @Override
    protected void initPresenter() {

    }

    @Override
    protected void initIntentDatas() {
        Intent intent = getIntent();
        Parcelable photoUrl = intent.getParcelableExtra("photoUri");
        if (photoUrl!=null&& photoUrl instanceof Uri){
            photoUri= (Uri) photoUrl;
        }
    }

    @Override
    protected void initStatusBar() {

    }

    @Override
    protected void initView() {
        photo_image = findViewById(R.id.photo_image);
        photo_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        photo_continue = findViewById(R.id.photo_continue);
        photo_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isOrigin){
                    String path = FileUtil.getFilePathByUri(HandleImageActivity.this, photoUri);
                    ARouter.getInstance().build(RoutePath.ACTIVITY_PUBLISH_URI).withString("url",path).navigation();
                    finish();
                }else {
                    SavePictureTask task = new SavePictureTask(getOutputMediaFile(), HandleImageActivity.this, HandleImageActivity.this);
                    task.execute(savedBitmap);
                }
            }
        });

        handle_image_filter_ll = findViewById(R.id.handle_image_filter_ll);
        initFilterView();

        crop_image = findViewById(R.id.crop_image);
        if (photoUri!=null) {
            GlideUtil.getInstance(this).skipMemoryCache(true).setDiskCacheStrategy(DiskCacheStrategy.NONE).loadImage(photoUri, crop_image);
//            try {
//                savedBitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(photoUri));
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            }
        }
    }
    private void initFilterView() {
        int width = UIUtil.dip2px(this, 86);
        int height = UIUtil.dip2px(this, 80);
        int space = UIUtil.dip2px(this, 10);
        int left = UIUtil.dip2px(this, 15);
        handle_image_filter_ll.removeAllViews();
        for (int i = 0; i < filters.length; i++) {
            ImageView imageView = new ImageView(this);
            HorizontalScrollView.LayoutParams layoutParams = new HorizontalScrollView.LayoutParams(width, height);
            if (i==0){
                layoutParams.leftMargin=left;
            }else{
                layoutParams.leftMargin=space;
            }
            if (i==filters.length-1){
                layoutParams.rightMargin=left;
            }
            if (i==0){
                GlideUtil.getInstance(this).skipMemoryCache(true).setDiskCacheStrategy(DiskCacheStrategy.NONE).loadImage(photoUri,imageView);
            }else{
                HandleImageTask handleImageTask = new HandleImageTask(i);
                handleImageTask.setHandleImageListener(new HandleImageListener() {
                    @Override
                    public void onFinished(Bitmap bitmap) {
                        GlideUtil.getInstance(HandleImageActivity.this).skipMemoryCache(true).setDiskCacheStrategy(DiskCacheStrategy.NONE).loadImage(bitmap,imageView);

                    }
                });
                try {
                    Bitmap unHandleBitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(photoUri));
                    handleImageTask.execute(unHandleBitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
            int finalI = i;
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (finalI ==0){
                        GlideUtil.getInstance(HandleImageActivity.this).skipMemoryCache(true).setDiskCacheStrategy(DiskCacheStrategy.NONE).loadImage(photoUri,crop_image);
//                        try {
//                            savedBitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(photoUri));
//                        } catch (FileNotFoundException e) {
//                            e.printStackTrace();
//                        }
                        isOrigin=true;
                    }else{
                        isOrigin=false;
                        HandleImageTask handleImageTask = new HandleImageTask(finalI);
                        handleImageTask.setHandleImageListener(new HandleImageListener() {
                            @Override
                            public void onFinished(Bitmap bitmap) {
                                GlideUtil.getInstance(HandleImageActivity.this).skipMemoryCache(true).setDiskCacheStrategy(DiskCacheStrategy.NONE).loadImage(bitmap,crop_image);
                                savedBitmap=bitmap;
                            }
                        });
                        try {
                            Bitmap unHandleBitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(photoUri));
                            handleImageTask.execute(unHandleBitmap);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            imageView.setLayoutParams(layoutParams);
            handle_image_filter_ll.addView(imageView);
        }
    }

    public File getOutputMediaFile() {
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "loveit");
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CHINESE).format(new Date());
        File mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                "IMG_" + timeStamp + ".jpg");

        return mediaFile;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_handle_image;
    }

    @Override
    public void onSaved(String result) {
        ARouter.getInstance().build(RoutePath.ACTIVITY_PUBLISH_URI).withString("url",result).navigation();
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (savedBitmap!=null&&!savedBitmap.isRecycled()){
            savedBitmap.recycle();
        }
    }
}
