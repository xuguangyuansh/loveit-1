package cn.net.loveit.main;

import android.Manifest;

import androidx.viewpager.widget.ViewPager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

import java.util.ArrayList;
import java.util.List;

import cn.net.base.BaseActivity;
import cn.net.base.BaseFragment;
import cn.net.base.constant.RoutePath;
import cn.net.base.utils.LogUtils;
import cn.net.home.adapter.HomeAdapter;
import cn.net.loveit.R;
import cn.net.loveit.release.getphoto.PhotoFragment;
import cn.net.loveit.release.permission.PhotoPromiseFragment;
import cn.net.loveit.friends.FriendsFragment;
import cn.net.loveit.release.ReleaseFragment;
import cn.net.third.push.Push;
import cn.net.third.push.PushListener;
import cn.net.view.slidelayout.LeftSlideLayout;
import cn.net.view.viewpager.NoScrollViewPager;

@Route(path = RoutePath.ACTIVITY_MAIN_URI)
public class MainActivity extends BaseActivity /*implements SlideTouchListener */{


    private MainFragment mainFragment;
    private PhotoFragment photoFragment;
    private FriendsFragment friendsFragment;
    private PhotoPromiseFragment promiseFragment;

    private String[] photoPromise={Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.CAMERA};

    private LeftSlideLayout main_layout;
    private NoScrollViewPager main_viewpager;
    List<BaseFragment> fragments=new ArrayList<>();

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
        //TODO 别名
        Push.getInstance().setAlias("test1");
        Push.getInstance().registerPushReceiver();
        Push.getInstance().getRegistrationId(new PushListener() {
            @Override
            public void getRegistrationId(String id) {

            }
        });
    }

    public void jump2MainFragment(){
        if (main_viewpager!=null)
            main_viewpager.setCurrentItem(1);
    }

    @Override
    protected void initView() {
//        main_layout = findViewById(R.id.main_layout);
//           main_layout.setSlideTouchListener(this);
//
//        if (mainFragment==null){
//            mainFragment = (MainFragment) ARouter.getInstance().build(RoutePath.FRAGMENT_MAIN_URI).navigation();
//        }
//        if (photoFragment==null)
//            photoFragment = (PhotoFragment) ARouter.getInstance().build(RoutePath.FRAGMENT_PHOTO_URI).navigation();
//        if (friendsFragment==null)
//            friendsFragment = (FriendsFragment) ARouter.getInstance().build(RoutePath.FRAGMENT_FRIENDS_URI).navigation();
//        if (promiseFragment==null)
//            promiseFragment = (PhotoPromiseFragment) ARouter.getInstance().build(RoutePath.FRAGMENT_PHOTO_PROMISE_URI).navigation();
//        FragmentManager manager = getSupportFragmentManager();
//        FragmentTransaction transaction = manager.beginTransaction();
//        transaction.replace(R.id.fl_main,mainFragment);
//        if (checkPermission()){
//            transaction.replace(R.id.fl_camera,photoFragment);
//        }else{
//            transaction.replace(R.id.fl_camera,promiseFragment);
//        }
//        transaction.commitAllowingStateLoss();

        //viewpager方案
        main_viewpager = findViewById(R.id.main_viewpager);
        fragments.clear();
        for (int i = 0; i < 2; i++) {
            BaseFragment fragment;
            if (i==0){
                LogUtils.e("zz","MainActivity run");
                fragment=(ReleaseFragment)ARouter.getInstance().build(RoutePath.FRAGMENT_PHOTO_RELEASE_URI).navigation();
            }else{
                fragment = (MainFragment) ARouter.getInstance().build(RoutePath.FRAGMENT_MAIN_URI).navigation();
            }
            fragments.add(fragment);
        }
        HomeAdapter homeAdapter = new HomeAdapter(getSupportFragmentManager(), fragments, new String[]{"", ""});
        main_viewpager.setAdapter(homeAdapter);
        main_viewpager.setCurrentItem(1);
        main_viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position==0){
                    main_viewpager.setNoScroll(true);
                }else if (position==1){
                    main_viewpager.setNoScroll(false);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Push.getInstance().removePushReceiver();
    }

//    @Override
//    public void onOpen() {
//        FragmentManager manager = getSupportFragmentManager();
//        FragmentTransaction transaction = manager.beginTransaction();
//        if (checkPermission()){
//            transaction.replace(R.id.fl_camera,photoFragment);
//        }else{
//            transaction.replace(R.id.fl_camera,promiseFragment);
//        }
//        transaction.commitAllowingStateLoss();
//    }

//    @Override
//    public void onClose() {
//
//    }
}
