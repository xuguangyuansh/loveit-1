package cn.net.user.net.service;

import java.util.HashMap;

import cn.net.base.BaseResult;
import cn.net.model.tag.BindTag;
import cn.net.model.tag.TagListBean;
import cn.net.net.ApiContants;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import rx.Observable;

public interface TabService {
    @Headers({"Content-Type: application/json","Accept: application/json"})
    @GET(ApiContants.GET_TAG_LIST)
    Observable<BaseResult<TagListBean>> getTagList(@QueryMap HashMap<String,String> params);


    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST(ApiContants.BIND_TAG)
    Observable<BaseResult<BindTag>> bindTag(@Body HashMap<String,Object> params);
}

