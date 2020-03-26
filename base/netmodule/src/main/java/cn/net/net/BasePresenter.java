package cn.net.net;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.view.View;


import java.net.UnknownHostException;

import cn.net.base.BaseActivity;
import cn.net.base.BaseResult;
import cn.net.base.utils.LogUtils;
import cn.net.net.listener.PresenterCallback;
import cn.net.net.listener.SubscriberOnNextListener;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;


public abstract class BasePresenter {

    private static final String TAG = "BasePresenter";

    protected Context mContext;

    protected View root_view;

    protected Subscriber mySubscriber;

    protected PresenterCallback mCallback;

    public void setPresenterCallback(PresenterCallback callback){
        this.mCallback = callback;
    }

    public BasePresenter(Context context, View root_view) {
        this.mContext = context;
        this.root_view = root_view;
    }

    public abstract void initView();

    public abstract void refresh();

    public abstract void destroy();

    public abstract void onLoading();

    public abstract void onLoadingComplete();

    public void netDisable() {
       if(mContext instanceof BaseActivity){
           try{
               ((BaseActivity)mContext).showNoNetView(true, R.string.no_net_top, R.string.no_net_bottom);
           }catch (Exception e){}
       }

    }

    public void requestData(Observable observable, Subscriber mySubScriber, Func1 func1) {
        if (func1 != null) {
            observable.subscribeOn(Schedulers.io())
                    .filter(func1)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(mySubScriber);
        } else {
            observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(mySubScriber);
        }
    }

    protected SubscriberOnNextListener myNextListener = new SubscriberOnNextListener<BaseResult>() {

        @Override
        public void onNext(BaseResult result) {
//            if(result.getErrcode()==-222){
//                netDisable();
//            }
//            if (!ResultManager.handleResultStatus(result, mContext)) {
                onMyResult(result);
//            }
        }

        @Override
        public void onError(Throwable throwable) {
//            if (!ResultManager.handleNetRequestError(throwable))
                onMyError(throwable);
        }
    };

    protected SubscriberOnNextListener myStringNextListener = new SubscriberOnNextListener<String>() {

        @Override
        public void onNext(String result) {
//            if(result.getErrcode()==-222){
//                netDisable();
//            }
//            if (!ResultManager.handleResultStatus(result, mContext)) {
            onMyResult(result);
//            }
        }

        @Override
        public void onError(Throwable throwable) {
//            if (!ResultManager.handleNetRequestError(throwable))
            onMyError(throwable);
        }
    };

    public void setMyNextListener(SubscriberOnNextListener myNextListener) {
        this.myNextListener = myNextListener;
    }

    protected void onMyResult(String result) {
        if (mCallback!=null){
            LogUtils.e("zz",result);
        }
    }

    protected void onMyResult(BaseResult result) {
        if (mCallback!=null){
            if (result.isSuccess()) {
                if (result.getData()!=null)
                    mCallback.onCallBack(result.isSuccess(), result.getData());
            }else{
//                if (result.getData()!=null)
                    mCallback.onCallBack(false, result);
            }
        }
    }

    protected void onMyError(Throwable throwable) {
//        Logger.e(TAG,throwable);
        if (throwable instanceof UnknownHostException || throwable instanceof java.io.IOException) {
            netDisable();
        }
        Log.e("zz",throwable.getMessage());
    }

}
