package cn.net.camera;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.wonderkiln.camerakit.CameraKitEventCallback;
import com.wonderkiln.camerakit.CameraKitImage;
import com.wonderkiln.camerakit.CameraView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import cn.net.base.BaseFragment;
import cn.net.base.constant.RoutePath;
import cn.net.camera.task.SavePictureTask;
import cn.net.view.utils.ScreenUtils;
import cn.net.view.utils.UIUtil;

@Route(path = RoutePath.FRAGMENT_CAMERA_URI)
public class CameraFragment extends BaseFragment implements CameraKitEventCallback<CameraKitImage>, SavePictureTask.OnPictureSaveListener{

    private CameraView cameraView;
    private ImageView take_photo;

    @Override
    protected void initIntentDatas() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_camera;
    }

    @Override
    protected void initViews() {
        cameraView = getRootView().findViewById(R.id.camera_view);
        cameraView.setCropOutput(true);

        take_photo = getRootView().findViewById(R.id.take_photo);

        take_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                cameraView.takePhoto(getOutputMediaFile(),CameraFragment.this);
                cameraView.captureImage(CameraFragment.this);
            }
        });
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
    public void onResume() {
        super.onResume();
//        cameraView.start();
        Log.e("zzz","相机开始工作");
    }


    @Override
    public void onPause() {
        super.onPause();
//        cameraView.stop();
        Log.e("zzz","相机休息了");

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        cameraView.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

//    @Override
//    public void onImage(CameraKitView cameraKitView, byte[] bytes) {
//        Log.e("zz",cameraView.getImageMegaPixels()+"");
//        Log.e("zz",cameraView.getAspectRatio()+"");
//        Log.e("zz",cameraView.getPhotoResolution().getWidth()+"--and--"+cameraView.getPreviewResolution().getHeight());
//        Log.e("zz",cameraView.getPreviewResolution().getWidth()+"--and--"+cameraView.getPreviewResolution().getHeight());
//        Log.e("zz",cameraView.getZoomFactor()+"");
//
//        File file = getOutputMediaFile();
//        SavePictureTask savePictureTask = new SavePictureTask(file, this, getContext());
//        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
//        Log.e("zz","width"+bitmap.getWidth());
//        Log.e("zz","height"+bitmap.getHeight());
//        if (bitmap!=null)
//            savePictureTask.execute(bitmap);
//    }

    @Override
    public void onSaved(String result) {
        ARouter.getInstance().build(RoutePath.ACTIVITY_HANDLE_IMAGE_URI).withParcelable("photoUri",Uri.fromFile(new File(result))).navigation();
    }

    @Override
    public void callback(CameraKitImage cameraKitImage) {
        File file = getOutputMediaFile();
        SavePictureTask savePictureTask = new SavePictureTask(file, this, getContext());
//        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        Bitmap bitmap = cameraKitImage.getBitmap();
        Log.e("zz","width"+bitmap.getWidth());
        Log.e("zz","height"+bitmap.getHeight());
        if (bitmap!=null)
            savePictureTask.execute(bitmap);
    }

    public void releaseCamera() {
        if (cameraView!=null)
            cameraView.stop();
        Log.e("zzh","相机休息了");
    }


    public void openCamera() {
        if (cameraView!=null)
            cameraView.start();
        Log.e("zzh","相机开始工作");
    }
}
