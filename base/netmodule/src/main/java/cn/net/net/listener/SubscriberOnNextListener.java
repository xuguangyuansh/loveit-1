package cn.net.net.listener;


public interface SubscriberOnNextListener<T> {

    void onNext(T t);

    void onError(Throwable throwable);
}
