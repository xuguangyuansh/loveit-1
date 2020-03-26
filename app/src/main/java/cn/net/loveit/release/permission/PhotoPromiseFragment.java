package cn.net.loveit.release.permission;

import android.Manifest;
import android.content.pm.PackageManager;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.alibaba.android.arouter.facade.annotation.Route;

import java.util.ArrayList;
import java.util.List;

import cn.net.base.BaseFragment;
import cn.net.base.constant.RoutePath;
import cn.net.base.utils.LogUtils;
import cn.net.loveit.R;
import cn.net.loveit.main.MainActivity;
import cn.net.loveit.release.ReleaseFragment;

@Route(path = RoutePath.FRAGMENT_PHOTO_PROMISE_URI)
public class PhotoPromiseFragment extends BaseFragment {

    private Button photo_promise;
    private String[] photoPromise={Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.CAMERA};
    private List<String> noPromiseList=new ArrayList<>();
    @Override
    protected void initIntentDatas() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_photo_promise;
    }

    @Override
    protected void initViews() {
        photo_promise = getRootView().findViewById(R.id.bt_photo_promise);
        photo_promise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestPermissions(photoPromise,111);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        LogUtils.e("zz","onRequestPermissionsResult"+permissions.length+".."+grantResults.length);
        if (requestCode==111){
            noPromiseList.clear();
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i]!= PackageManager.PERMISSION_GRANTED){
                    noPromiseList.add(permissions[i]);
                }
            }
            if (noPromiseList.size()==0){
                //TODO next
                LogUtils.e("zz","next");
//                if (getActivity()!=null&& getActivity() instanceof MainActivity)
//                    ((MainActivity)getActivity()).onOpen();
                if ( getParentFragment() instanceof ReleaseFragment){
                    ((ReleaseFragment) getParentFragment()).getFragmentByPermission();
                }
            }else{
                LogUtils.e("zz",noPromiseList.size()+"");
            }
        }
    }
}
