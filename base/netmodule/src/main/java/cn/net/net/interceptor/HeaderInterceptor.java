package cn.net.net.interceptor;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class HeaderInterceptor implements Interceptor {
    private static final String TAG = "net.wecash.retrofit";

    private Map<String, String> mHeaders;


    public HeaderInterceptor() {
    }

    public HeaderInterceptor(Map<String, String> addHeaders) {
        this.mHeaders = addHeaders;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        //        Log.e(TAG, request.url().toString());

        Request.Builder builder = chain.request()
                .newBuilder();
        if (mHeaders != null && mHeaders.size() > 0) {
            Set<String> keys = mHeaders.keySet();
            for (String headerKey : keys) {
                builder.addHeader(headerKey, mHeaders.get(headerKey)).build();
            }
            //Log.e(TAG, "headers:" + mHeaders.toString());
        }

        return chain.proceed(builder.build());

    }
}