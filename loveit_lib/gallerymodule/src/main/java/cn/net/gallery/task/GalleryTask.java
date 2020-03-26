package cn.net.gallery.task;

import android.content.ContentResolver;
import android.database.Cursor;
import android.os.AsyncTask;
import android.provider.MediaStore;

import java.util.ArrayList;
import java.util.List;

import cn.net.model.gallery.Image;

public class GalleryTask extends AsyncTask<Void, Void, Cursor> {
    private ContentResolver cr;
    private OnDataListener listener;
    private static final String SELECTION_IMAGE_MIME_TYPE = MediaStore.Images.Media.MIME_TYPE + "=? or " + MediaStore.Images.Media.MIME_TYPE + "=? or " + MediaStore.Images.Media.MIME_TYPE + "=? or " + MediaStore.Images.Media.MIME_TYPE + "=?";


    public GalleryTask(ContentResolver cr) {
        this.cr = cr;
    }

    public void setOnDataListener(OnDataListener listener) {
        this.listener = listener;
    }

    @Override
    protected Cursor doInBackground(Void... voids) {
        try {
            Cursor cursor = cr.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, SELECTION_IMAGE_MIME_TYPE,
                    new String[]{"image/jpeg", "image/png", "image/jpg", "image/gif"}, MediaStore.Images.Media.DATE_MODIFIED + " desc");
            return cursor;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    protected void onPostExecute(Cursor cursor) {
        super.onPostExecute(cursor);
        if (cursor != null) {
            List<Image> images = new ArrayList<>();
//            Log.e("zz",cursor.getCount()+"");
            while (cursor.moveToNext()) {
                int size = cursor.getInt(cursor.getColumnIndex(MediaStore.Images.Media.SIZE));
                if (size<=0){
                    continue;
                }
                Image image = new Image();
                String url = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                image.setUrl(url);
                String buckId = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.BUCKET_ID));
                image.setBuckId(buckId);
                String buckName = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.BUCKET_DISPLAY_NAME));
//                Log.e("zz",url+"..."+buckName);
                image.setBuckName(buckName);

                images.add(image);
            }
            if (listener != null)
                listener.onData(images);
        }

    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }

    public static interface OnDataListener {
        void onData(List<Image> data);
    }
}
