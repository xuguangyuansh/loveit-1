package cn.net.base;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import cn.net.base.utils.LogUtils;
import cn.net.third.track.UMTrack;

/**
 * Desc: Fragment的父类
 */

public abstract class BaseFragment extends Fragment {

    // RootView
    private View rootView;
    // 依附的Context
    protected BaseActivity activity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(getLayoutId(), container, false);
        initViews();
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initOnViewCreated();
    }

    protected View getRootView() {
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.activity = (BaseActivity) context;
        initIntentDatas();
    }

    /**
     * 初始化参数
     */
    protected abstract void initIntentDatas();

    /**
     * 获取Activity要填充的layoutId
     *
     * @return
     */
    protected abstract int getLayoutId();

    /**
     * tootbar title
     *
     * @return
     */
    protected String getTitleStr() {
        return null;
    }

    ;

    /**
     * 初始化Views
     */
    protected abstract void initViews();

    protected void initOnViewCreated(){}


    @Override
    public void onResume() {
        super.onResume();
        try{
            UMTrack.getInstance(getActivity()).onPageStart(this.getClass().getSimpleName());
            LogUtils.e("BaseonResume",this.getClass().getSimpleName());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        try{
            UMTrack.getInstance(getActivity()).onPageEnd(this.getClass().getSimpleName());
            LogUtils.e("BaseonPause",this.getClass().getSimpleName());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}