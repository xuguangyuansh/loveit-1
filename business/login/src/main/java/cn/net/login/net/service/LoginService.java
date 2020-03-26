package cn.net.login.net.service;


import java.util.HashMap;

import cn.net.base.BaseResult;
import cn.net.model.login.LoginBean;
import cn.net.net.ApiContants;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import rx.Observable;

public interface LoginService {
//    @FormUrlEncoded
    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST(ApiContants.LOGIN)
    Observable<BaseResult<LoginBean>> login(@Body HashMap<String,String> params);


    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST(ApiContants.REST_PWD)
    Observable<BaseResult<LoginBean>> restPwd(@Body HashMap<String,String> params);

}
