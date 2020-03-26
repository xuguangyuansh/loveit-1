package cn.net.gallery;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.net.base.BaseFragment;
import cn.net.base.constant.RoutePath;
import cn.net.base.utils.LogUtils;
import cn.net.gallery.adapter.GalleryAdapter;
import cn.net.gallery.callback.BitmapCropCallback;
import cn.net.gallery.callback.OnItemClickListener;
import cn.net.gallery.constants.UCrop;
import cn.net.gallery.task.GalleryTask;
import cn.net.model.gallery.Image;
import cn.net.view.decoration.SpacesItemDecoration;
import cn.net.gallery.callback.UCropFragmentCallback;
import cn.net.gallery.view.GestureCropImageView;
import cn.net.gallery.view.UCropView;
import cn.net.view.utils.UIUtil;

import static android.app.Activity.RESULT_OK;


@Route(path = RoutePath.FRAGMENT_GALLERY_URI)
public class GalleryFragment extends BaseFragment implements UCropFragmentCallback, OnItemClickListener {

    private static final int GRID_COUNT = 4;
    private UCropView gallery_photo;
    private RecyclerView gallery_gridview;
    private List<Image> data = new ArrayList<>();
    private GalleryAdapter adapter;
    private GestureCropImageView mGestureCropImageView;
    private static final String SAMPLE_CROPPED_IMAGE_NAME = "CropImage";
    private Uri outputUri;
    private View mBlockingView;
    public static final int DEFAULT_COMPRESS_QUALITY = 90;
    public static final Bitmap.CompressFormat DEFAULT_COMPRESS_FORMAT = Bitmap.CompressFormat.JPEG;


    @Override
    protected void initIntentDatas() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_gallery;
    }

    @Override
    protected void initViews() {

        gallery_photo = getRootView().findViewById(R.id.gallery_photo);
        mGestureCropImageView = gallery_photo.getCropImageView();
        mGestureCropImageView.setRotateEnabled(false);
        mGestureCropImageView.setMaxScaleMultiplier(5.0f);
        mGestureCropImageView.setImageToWrapCropBoundsAnimDuration(500);
//        mGestureCropImageView.setTransformImageListener(mImageListener);
        String destinationFileName = SAMPLE_CROPPED_IMAGE_NAME+System.currentTimeMillis()+".jpg";
        outputUri = Uri.fromFile(new File(getContext().getCacheDir(), destinationFileName));


//        addBlockingView(getRootView());

        gallery_gridview = getRootView().findViewById(R.id.gallery_gridview);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), GRID_COUNT);
        gridLayoutManager.setSmoothScrollbarEnabled(true);
        gallery_gridview.setLayoutManager(gridLayoutManager);
        gallery_gridview.addItemDecoration(new SpacesItemDecoration(UIUtil.dip2px(getContext(), 1), GRID_COUNT));
        gallery_gridview.setLayoutManager(gridLayoutManager);

        if (adapter == null)
            adapter = new GalleryAdapter(getContext(), data);
        adapter.setOnItemClickListener(this);
        gallery_gridview.setAdapter(adapter);

    }

    @Override
    public void onResume() {
        super.onResume();
        initTask();
    }

    public void initTask() {
        GalleryTask galleryTask = new GalleryTask(getContext().getContentResolver());
        galleryTask.setOnDataListener(new GalleryTask.OnDataListener() {
            @Override
            public void onData(List<Image> data) {
                try {
                    mGestureCropImageView.setImageUri(Uri.fromFile(new File(data.get(0).getUrl())),outputUri);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                GalleryFragment.this.data.clear();
                GalleryFragment.this.data.addAll(data);
                adapter.notifyDataSetChanged();

            }
        });
        galleryTask.execute();
    }

    /**
     * Adds view that covers everything below the Toolbar.
     * When it's clickable - user won't be able to click/touch anything below the Toolbar.
     * Need to block user input while loading and cropping an image.
     */
    private void addBlockingView(View view) {
        if (mBlockingView == null) {
            mBlockingView = new View(getContext());
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            mBlockingView.setLayoutParams(lp);
            mBlockingView.setClickable(true);
        }

        ((LinearLayout) view.findViewById(R.id.ucrop_container)).addView(mBlockingView);
    }


    public void cropAndSaveImage() {
//        mBlockingView.setClickable(true);
//        callback.loadingProgress(true);

        mGestureCropImageView.cropAndSaveImage(DEFAULT_COMPRESS_FORMAT, DEFAULT_COMPRESS_QUALITY, new BitmapCropCallback() {

            @Override
            public void onBitmapCropped(@NonNull Uri resultUri, int offsetX, int offsetY, int imageWidth, int imageHeight) {
                onCropFinish(getResult(resultUri, mGestureCropImageView.getTargetAspectRatio(), offsetX, offsetY, imageWidth, imageHeight));
            }

            @Override
            public void onCropFailure(@NonNull Throwable t) {
                onCropFinish(getError(t));
            }
        });
    }

    protected UCropResult getResult(Uri uri, float resultAspectRatio, int offsetX, int offsetY, int imageWidth, int imageHeight) {
        return new UCropResult(RESULT_OK, new Intent()
                .putExtra(UCrop.EXTRA_OUTPUT_URI, uri)
                .putExtra(UCrop.EXTRA_OUTPUT_CROP_ASPECT_RATIO, resultAspectRatio)
                .putExtra(UCrop.EXTRA_OUTPUT_IMAGE_WIDTH, imageWidth)
                .putExtra(UCrop.EXTRA_OUTPUT_IMAGE_HEIGHT, imageHeight)
                .putExtra(UCrop.EXTRA_OUTPUT_OFFSET_X, offsetX)
                .putExtra(UCrop.EXTRA_OUTPUT_OFFSET_Y, offsetY)
        );
    }

    protected UCropResult getError(Throwable throwable) {
        return new UCropResult(UCrop.RESULT_ERROR, new Intent().putExtra(UCrop.EXTRA_ERROR, throwable));
    }

    @Override
    public void onCropFinish(UCropResult result) {
        switch (result.mResultCode) {
            case RESULT_OK:
                handleCropResult(result.mResultData);
                break;
            case UCrop.RESULT_ERROR:
                handleCropError(result.mResultData);
                break;
        }
    }

    private void handleCropError(Intent result) {
        final Throwable cropError = UCrop.getError(result);
        if (cropError != null) {
            Toast.makeText(getContext(), cropError.getMessage(), Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getContext(), R.string.toast_unexpected_error, Toast.LENGTH_SHORT).show();
        }
    }

    private void handleCropResult(Intent result) {
        final Uri resultUri = UCrop.getOutput(result);
        if (resultUri != null) {
            ARouter.getInstance().build(RoutePath.ACTIVITY_HANDLE_IMAGE_URI).withParcelable("photoUri",resultUri).navigation();
        } else {
            Toast.makeText(getContext(), R.string.toast_cannot_retrieve_cropped_image, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onItemClick(int position) {
        try {
            mGestureCropImageView.setImageUri(Uri.fromFile(new File(data.get(position).getUrl())),outputUri);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class UCropResult {

        public int mResultCode;
        public Intent mResultData;

        public UCropResult(int resultCode, Intent data) {
            mResultCode = resultCode;
            mResultData = data;
        }

    }


}

