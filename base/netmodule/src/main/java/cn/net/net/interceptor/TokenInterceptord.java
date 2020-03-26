package cn.net.net.interceptor;

import android.content.pm.PackageInfo;
import android.text.TextUtils;
import android.util.Log;

import cn.net.base.BaseApplication;
import cn.net.base.BaseResult;
import cn.net.base.utils.AppInfoUtils;
import cn.net.base.utils.LogUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import cn.net.base.utils.SpUtil;
import cn.net.net.ApiContants;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;



public class TokenInterceptord implements Interceptor {

    private final String TAG = "tokeninter";

    @Override
    public Response intercept(Chain chain) throws IOException {


        try {
            // 记录相关信息到LogSDK
            String requestUrl;
            Map<String, Object> requestParams = new HashMap<>();
            Map<String, Object> requestHeaders = new HashMap<>();
            String requestMethod;
            String responseJson;
            long useTime;

            long requestTime = System.currentTimeMillis();

            Request oldRequest = chain.request();
            /**
             * 拦截请求，并修改参数
             */
            Request newRequest = addParam(oldRequest);
            requestUrl = newRequest.url().toString();
//            if (requestUrl.contains("front/"))
//                requestUrl = requestUrl.substring(requestUrl.indexOf("front/") + 6);
            requestMethod = newRequest.method();
            if (newRequest.method().endsWith("POST")) {
                if (newRequest.body() instanceof FormBody) {
                    FormBody body = (FormBody) newRequest.body();
                    for (int i = 0; i < body.size(); i++) {
                        requestParams.put(body.encodedName(i), body.encodedValue(i));
                    }
                    Log.e(TAG, "request params:" + requestParams.toString());
                } else if (newRequest.body() instanceof MultipartBody) {
                    MultipartBody body = (MultipartBody) newRequest.body();
                    for (int i = 0; i < body.size(); i++) {
                        MultipartBody.Part parts = body.parts().get(i);

                        // params. e.g: header:Content-Disposition: form-data; name="source" Content-Transfer-Encoding: binary
                        String key = parts.headers().toString();
                        if (!TextUtils.isEmpty(key) && key.contains("name=")) {
                            key = key.substring(key.indexOf("\"") + 1);
                            key = key.substring(0, key.indexOf("\""));

                            Buffer buffer = new Buffer();
                            parts.body().writeTo(buffer);
                            String value = buffer.readString(Charset.forName("UTF-8"));
                            requestParams.put(key, value);
                        }
                    }
                    Log.e(TAG, "request params:" + requestParams.toString());
                } else {
                    RequestBody requestBody= newRequest.body();
                    Buffer buffer = new Buffer();
                    requestBody.writeTo(buffer);
                    String paramsStr = buffer.readUtf8();
                    Log.e(TAG, "request params:" + newRequest.body().contentLength() + "--" + paramsStr);
                }
            }

            Headers headers = newRequest.headers();
            for (int i = 0; i < headers.size(); i++) {
                String key = headers.name(i);
                String value = headers.value(i);
                requestHeaders.put(key, value);
            }
            //Log.d(TAG, "request headers:" + requestHeaders.toString());

            Response response = chain.proceed(newRequest);

            MediaType mediaType = response.body().contentType();
            responseJson = response.body().string();

            useTime = System.currentTimeMillis() - requestTime;

            LogUtils.e(TAG, ApiContants.BASE_URL+requestUrl+"--"+requestMethod);
            LogUtils.e(TAG,responseJson+"--"+useTime);
            // record to LogSDK
//            LogSDK.recordHttpLog(requestUrl, requestMethod, requestParams, requestHeaders, responseJson, requestTime, useTime);
            if(responseJson.equals("Service Unavailable")){
                try {
                    BaseResult result = new BaseResult();
                    result.setMsg(responseJson);
                    result.setCode(-503);
                    MediaType MEDIATYPE = MediaType.parse("text/plain; charset=utf-8");
                    response = new Response.Builder().body(ResponseBody.create(MEDIATYPE, result.getJsonString())).build();
                    return response;
                } catch (Exception e1) {

                }
            }
            return response.newBuilder().body(ResponseBody.create(mediaType, responseJson)).build();
        } catch (Throwable e) {
            Log.e(TAG, e.getMessage(), e);
            e.printStackTrace();
            try {
                BaseResult result = new BaseResult();
                result.setMsg(e.getMessage());
                result.setCode(-222);
                MediaType MEDIATYPE = MediaType.parse("text/plain; charset=utf-8");
                Log.e(TAG, result.getJsonString());
                Response response = new Response.Builder().body(ResponseBody.create(MEDIATYPE, result.getJsonString())).build();
                return response;
            } catch (Exception e1) {
                Log.e(TAG, e1.getMessage(), e);
                e1.printStackTrace();
                throw e;
            }
        }

    }

    /**
     * 添加公共参数
     *
     * @param oldRequest
     * @return
     */
    private Request addParam(Request oldRequest) {

        HttpUrl.Builder builder = oldRequest.url()
                .newBuilder();
        //default version infomation need to change with app
        String versionName = "";
//        String versionCode = "";
//        String myLanguage = BaseApplication.getInstance().getLanguage();
//        myLanguage = myLanguage.replace("_","-");
//        String timeZone = Calendar.getInstance(Locale.getDefault()).getTimeZone().getID();
        try {
            PackageInfo info = AppInfoUtils.getMyPackageInfo(BaseApplication.getInstance().getApplicationContext());
            if (null != info) {
                versionName = info.versionName;
//                versionCode = String.valueOf(info.versionCode);
            }
        } catch (Exception e) {
        }

        //获取token
        String token = SpUtil.getInstance(BaseApplication.getInstance().getApplicationContext()).getString(SpUtil.KEY_TOKEN);

        LogUtils.e(TAG, "versionName:" + versionName);
        LogUtils.e(TAG, "token:" + token);
        LogUtils.e(TAG, "system:" + "android");
//        LogUtils.e(TAG, "versionCode:" + versionCode);
//        LogUtils.e(TAG, "myLanguage:" + myLanguage);
//        LogUtils.e(TAG, "timeZone:" + timeZone);
        // TODO 处理语言及版本参数值的获取
        Request newRequest = oldRequest.newBuilder()
                .method(oldRequest.method(), oldRequest.body())
                .url(builder.build())
//                .header("Accept-Language", myLanguage)
//                .header("timeZoneId", timeZone)
                .header("versionName", versionName)
                .header("token", token)
                .header("system", "android")
//                .header("versionCode", versionCode)
                .build();

        return newRequest;
    }
}
