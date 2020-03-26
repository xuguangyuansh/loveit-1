package cn.net.loveit.release.getphoto;




import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.viewpager.widget.ViewPager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import cn.net.base.BaseFragment;
import cn.net.base.constant.RoutePath;
import cn.net.base.utils.ViewUtils;
import cn.net.camera.CameraFragment;
import cn.net.gallery.GalleryFragment;
import cn.net.home.adapter.HomeAdapter;
import cn.net.loveit.R;
import cn.net.loveit.release.ReleaseFragment;
import cn.net.view.viewpager.NoScrollViewPager;

@Route(path = RoutePath.FRAGMENT_PHOTO_URI)
public class PhotoFragment extends BaseFragment {

    private TabLayout photo_tablayout;
    private String [] mTitle = {"图库","照片"};
    private NoScrollViewPager photo_viewpager;
    private List<BaseFragment> fragments=new ArrayList<>();
    private HomeAdapter homeAdapter;
    private GalleryFragment galleryFragment;
    private CameraFragment cameraFragment;
    private LinearLayout photo_image;
    private LinearLayout photo_continue;

    @Override
    protected void initIntentDatas() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_photo;
    }

    @Override
    protected void initViews() {
        photo_viewpager = getRootView().findViewById(R.id.photo_viewpager);
        photo_viewpager.setNoScroll(true);

        photo_image = getRootView().findViewById(R.id.photo_image);
        photo_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getParentFragment() instanceof ReleaseFragment){
                    ((ReleaseFragment) getParentFragment()).finish();
                }
            }
        });

        photo_continue = getRootView().findViewById(R.id.photo_continue);
        photo_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (photo_viewpager==null)
                    return;
                int currentItem = photo_viewpager.getCurrentItem();
                if (currentItem==0){
                    if (galleryFragment!=null){
                        galleryFragment.cropAndSaveImage();
                    }
                }else if (currentItem==1){
                }
            }
        });

        fragments.clear();
        galleryFragment = (GalleryFragment) ARouter.getInstance().build(RoutePath.FRAGMENT_GALLERY_URI).navigation();
        cameraFragment = (CameraFragment) ARouter.getInstance().build(RoutePath.FRAGMENT_CAMERA_URI).navigation();
        fragments.add(galleryFragment);
        fragments.add(cameraFragment);

        homeAdapter = new HomeAdapter(getChildFragmentManager(), fragments,mTitle);
        photo_viewpager.setAdapter(homeAdapter);


        photo_tablayout = getRootView().findViewById(R.id.photo_tablayout);
        for (int i = 0;i<mTitle.length;i++){
            photo_tablayout.addTab(photo_tablayout.newTab().setText(mTitle[i]));
        }
        photo_tablayout.setupWithViewPager(photo_viewpager);
        photo_tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        photo_viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position==0){
                    if (galleryFragment!=null)
                        galleryFragment.initTask();
                    if (cameraFragment!=null)
                        cameraFragment.releaseCamera();
                    photo_continue.setVisibility(View.VISIBLE);
                }else if (position==1){
                    if (cameraFragment!=null){
                        cameraFragment.openCamera();
                    }
                    photo_continue.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
    //为了让相机停止工作
    public int getCurrentItem(){
        return photo_viewpager.getCurrentItem();
    }

}
