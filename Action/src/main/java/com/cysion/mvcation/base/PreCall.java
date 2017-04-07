package com.cysion.mvcation.base;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

/**
 * Created by CysionLiu on 2017/4/7.
 */

public interface PreCall {
    @GET
    Call<String> getResult(@Url String url,@QueryMap Map<String,String> maps);

    @POST
    Call<String> postResult(@Url String url,@QueryMap Map<String,String> maps);
}
