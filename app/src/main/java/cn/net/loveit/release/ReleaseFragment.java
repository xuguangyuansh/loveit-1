package cn.net.loveit.release;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.widget.FrameLayout;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

import cn.net.base.BaseFragment;
import cn.net.base.constant.RoutePath;
import cn.net.loveit.R;
import cn.net.loveit.main.MainActivity;
import cn.net.loveit.release.getphoto.PhotoFragment;
import cn.net.loveit.release.permission.PhotoPromiseFragment;

@Route(path = RoutePath.FRAGMENT_PHOTO_RELEASE_URI)
public class ReleaseFragment extends BaseFragment {

    private FrameLayout fl_release;
    private String[] photoPromise = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
    private BaseFragment fragment;

    @Override
    protected void initIntentDatas() {

    }

    public void finish(){
        if (getActivity() instanceof MainActivity){
            ((MainActivity) getActivity()).jump2MainFragment();
        }else if (getActivity() instanceof ReleasePhotoActivity){
            getActivity().finish();
        }
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_release;
    }

    @Override
    protected void initViews() {
        fl_release = getRootView().findViewById(R.id.fl_release);
        getFragmentByPermission();
    }

    public void getFragmentByPermission() {
        if (checkPermission()) {
            fragment = (PhotoFragment) ARouter.getInstance().build(RoutePath.FRAGMENT_PHOTO_URI).navigation();
        } else {
            fragment = (PhotoPromiseFragment) ARouter.getInstance().build(RoutePath.FRAGMENT_PHOTO_PROMISE_URI).navigation();
        }
        FragmentManager manager = getChildFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fl_release,fragment);
        transaction.commitAllowingStateLoss();
    }

    public boolean checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            boolean isGranted = false;
            for (int i = 0; i < photoPromise.length; i++) {
                if (getActivity().checkSelfPermission(photoPromise[i]) == PackageManager.PERMISSION_GRANTED) {
                    isGranted = true;
                } else {
                    isGranted = false;
                }
            }
            if (isGranted) {
                return true;
            }
            return false;
        } else {
            return true;
        }
    }

}
