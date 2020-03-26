package cn.net.net;

import cn.net.net.interceptor.TokenInterceptord;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class NetClient {

    private static final int DEFAULT_TIMEOUT_TEST = 20 * 3;
    private static final int DEFAULT_TIMEOUT = 50;

    private Retrofit retrofit;

    private NetClient() {
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient().newBuilder()
                .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(new TokenInterceptord())
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY));
        if (ApiContants.STAGE_URL.equals(ApiContants.BASE_URL)) {
            httpClientBuilder.connectTimeout(DEFAULT_TIMEOUT_TEST, TimeUnit.SECONDS);
        } else {
            httpClientBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        }


        retrofit = new Retrofit.Builder()
                .client(httpClientBuilder.build())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(ApiContants.BASE_URL)
                .build();
    }

    public NetClient(String baseUrl) {
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient().newBuilder()
                .addInterceptor(new TokenInterceptord())
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY));
        httpClientBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);


        retrofit = new Retrofit.Builder()
                .client(httpClientBuilder.build())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(baseUrl)
                .build();
    }

    private static class SingletonHolder {
        private static final NetClient INSTANCE = new NetClient();
    }

    public static NetClient getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public <T> T createApi(Class<T> tClass) {
        return retrofit.create(tClass);
    }


    public static void requestData(Observable observable, Subscriber mySubScriber, Func1 func1) {
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

    public static void requestData(Observable observable, Subscriber mySubScriber, Func1 func1, Func1 func2) {
        observable.subscribeOn(Schedulers.io())
                .flatMap(func1)
                .filter(func2)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mySubScriber);
    }

    public static void requestData(Observable observable, Subscriber mySubScriber, Func1 func1, Func1 func2, Func1 func3) {
        observable.subscribeOn(Schedulers.io())
                .flatMap(func1)
                .filter(func2)
                .flatMap(func3)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mySubScriber);
    }


}
