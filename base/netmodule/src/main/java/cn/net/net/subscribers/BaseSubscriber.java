package cn.net.net.subscribers;


import cn.net.net.listener.ProgressCancelListener;
import cn.net.net.listener.SubscriberOnNextListener;

import rx.Subscriber;


public class BaseSubscriber<T> extends Subscriber<T> implements ProgressCancelListener {

    private SubscriberOnNextListener mSubscriberOnNextListener;


    public BaseSubscriber(SubscriberOnNextListener mSubscriberOnNextListener) {
        this.mSubscriberOnNextListener = mSubscriberOnNextListener;
    }

    @Override
    public void onCancelProgress() {
        if (!this.isUnsubscribed())
            this.unsubscribe();
    }

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        if (mSubscriberOnNextListener != null)
            mSubscriberOnNextListener.onError(e);
    }

    @Override
    public void onNext(T t) {
        if (mSubscriberOnNextListener != null)
            mSubscriberOnNextListener.onNext(t);
    }
}
