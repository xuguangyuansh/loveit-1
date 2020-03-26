package cn.net.loveit.release.task;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;

import com.xinlan.imageeditlibrary.editimage.fliter.PhotoProcessing;

public class HandleImageTask extends AsyncTask<Bitmap, Void, Bitmap> {
    private int position;
    private HandleImageListener listener;
    Bitmap bitmap;
    public HandleImageTask(int position){
        this.position=position;
    }
    @Override
    protected Bitmap doInBackground(Bitmap... params) {
        bitmap = params[0];
        if (bitmap==null) {
            return null;
        }
//        srcBitmap = BitmapFactory.decodeStream(get)
        return PhotoProcessing.filterPhoto(bitmap, position);
    }


    @Override
    protected void onPostExecute(Bitmap result) {
        super.onPostExecute(result);
        if (result == null)
            return;
//        if (fliterBit != null && (!fliterBit.isRecycled())) {
//            fliterBit.recycle();
//        }
//        fliterBit = result;
//        activity.mainImage.setImageBitmap(fliterBit);
//        currentBitmap = fliterBit;
        if (listener!=null)
            listener.onFinished(result);

        if (bitmap!=null&&!bitmap.isRecycled()){
            bitmap.recycle();
        }
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    public void setHandleImageListener(HandleImageListener listener){
        this.listener=listener;
    }
}
