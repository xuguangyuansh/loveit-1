package cn.net.view.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

public class GlideUtil {
    /**
     * 弱网接收到之后不去加载图片
     */
    private Context ctx;
    private RequestOptions options;


    private GlideUtil(Context ctx){
        this.ctx=ctx.getApplicationContext();
        options = new RequestOptions();
    }
    private static GlideUtil instance;
    public static GlideUtil getInstance(Context ctx){
        if (instance==null){
            synchronized (GlideUtil.class){
                if (instance==null){
                    instance=new GlideUtil(ctx);
                }
            }
        }
        return instance;
    }

    public GlideUtil setCircleCrop(){
        if (options!=null)
            options.circleCrop();
        return instance;
    }

    public GlideUtil setImageSize(int width,int height){
        if (options!=null)
            options.override(width,height);
        return instance;
    }

    public GlideUtil setError(int error){
        if (options!=null)
            options.error(error);
        return instance;
    }

    public GlideUtil setPlaceHolder(int holder){
        if (options!=null)
            options.placeholder(holder);
        return instance;
    }

    public GlideUtil setTransform(Transformation transformation){
        if (options!=null)
            options.transform(transformation);
        return instance;
    }

    public GlideUtil setDiskCacheStrategy(DiskCacheStrategy strategy){
        if (options!=null)
            options.diskCacheStrategy(strategy);
        return instance;
    }

    public GlideUtil skipMemoryCache(boolean skip){
        if (options!=null)
            options.skipMemoryCache(skip);
        return instance;
    }

    public void loadImage(int imageId, ImageView iv){
        if (ctx!=null) {
            if (options != null) {
                Glide.with(ctx).load(imageId).apply(options).into(iv);
            }else{
                Glide.with(ctx).load(imageId).into(iv);
            }
        }
    }

    public void loadImage(Bitmap bitmap, ImageView iv){
        if (ctx!=null) {
            if (options != null) {
                Glide.with(ctx).load(bitmap).apply(options).into(iv);
            }else{
                Glide.with(ctx).load(bitmap).into(iv);
            }
        }
    }

    public void loadImage(Uri imageId, ImageView iv){
        if (ctx!=null) {
            if (options != null) {
                Glide.with(ctx).load(imageId).apply(options).into(iv);
            }else{
                Glide.with(ctx).load(imageId).into(iv);
            }
        }
    }



    public void loadImage(String url, ImageView iv){
        if (ctx!=null) {
            if (options != null) {
                Glide.with(ctx).load(url).apply(options).into(iv);
            }else{
                Glide.with(ctx).load(url).into(iv);
            }
        }
    }


    public void loadReSizeImage(int imageId, final ImageView iv, final int destWidth){
        if (ctx!=null) {
            if (options != null) {
                Glide.with(ctx).asBitmap().load(imageId).apply(options).into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        int height = resource.getHeight();
                        int width = resource.getWidth();
                        int destHeight=destWidth*height/width;
                        ViewGroup.LayoutParams layoutParams = iv.getLayoutParams();
                        layoutParams.height=destHeight;
                        layoutParams.width=destWidth;
                        iv.setLayoutParams(layoutParams);
                        iv.setImageBitmap(resource);
                    }
                });
            }else{
                Glide.with(ctx).asBitmap().load(imageId).into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        int height = resource.getHeight();
                        int width = resource.getWidth();
                        int destHeight=destWidth*height/width;
                        ViewGroup.LayoutParams layoutParams = iv.getLayoutParams();
                        layoutParams.height=destHeight;
                        layoutParams.width=destWidth;
                        iv.setLayoutParams(layoutParams);
                        iv.setImageBitmap(resource);
                    }
                });
            }
        }
    }

    public void loadReSizeImage(String imageId, final ImageView iv, final int destWidth){
        if (ctx!=null) {
            if (options != null) {
                Glide.with(ctx).asBitmap().load(imageId).apply(options).into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        int height = resource.getHeight();
                        int width = resource.getWidth();
                        int destHeight=destWidth*height/width;
                        ViewGroup.LayoutParams layoutParams = iv.getLayoutParams();
                        layoutParams.height=destHeight;
                        layoutParams.width=destWidth;
                        iv.setLayoutParams(layoutParams);
                        iv.setImageBitmap(resource);
                    }
                });
            }else{
                Glide.with(ctx).asBitmap().load(imageId).into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        int height = resource.getHeight();
                        int width = resource.getWidth();
                        int destHeight=destWidth*height/width;
                        ViewGroup.LayoutParams layoutParams = iv.getLayoutParams();
                        layoutParams.height=destHeight;
                        layoutParams.width=destWidth;
                        iv.setLayoutParams(layoutParams);
                        iv.setImageBitmap(resource);
                    }
                });
            }
        }
    }

    public void loadImageWithHeightReSize(String imageId, final ImageView iv, final int destHeight){
        if (ctx!=null) {
            if (options != null) {
                Glide.with(ctx).asBitmap().load(imageId).apply(options).into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        int height = resource.getHeight();
                        int width = resource.getWidth();
                        int destWidth=destHeight*width/height;
                        ViewGroup.LayoutParams layoutParams = iv.getLayoutParams();
                        layoutParams.height=destHeight;
                        layoutParams.width=destWidth;
                        iv.setLayoutParams(layoutParams);
                        iv.setImageBitmap(resource);
                    }
                });
            }else{
                Glide.with(ctx).asBitmap().load(imageId).into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        int height = resource.getHeight();
                        int width = resource.getWidth();
                        int destWidth=destHeight*width/height;
                        ViewGroup.LayoutParams layoutParams = iv.getLayoutParams();
                        layoutParams.height=destHeight;
                        layoutParams.width=destWidth;
                        iv.setLayoutParams(layoutParams);
                        iv.setImageBitmap(resource);
                    }
                });
            }
        }
    }
}
