package cn.net.loveit.release;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

import cn.net.base.BaseActivity;
import cn.net.base.constant.RoutePath;
import cn.net.loveit.R;


@Route(path = RoutePath.ACTIVITY_RELEASE_PHOTO_URI)
public class ReleasePhotoActivity extends BaseActivity {

    private ReleaseFragment releaseFragment;

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
        releaseFragment = (ReleaseFragment) ARouter.getInstance().build(RoutePath.FRAGMENT_PHOTO_RELEASE_URI).navigation();

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.release_photo_fl,releaseFragment);
        transaction.commitAllowingStateLoss();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_release_photo;
    }
}
