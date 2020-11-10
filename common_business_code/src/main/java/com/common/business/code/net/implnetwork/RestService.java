package com.common.business.code.net.implnetwork;


import com.common.business.code.bean.ResBaseModel;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Url;

/**
 * @author：atar
 * @date: 2019/5/11
 * @description:
 */
public interface RestService {

    @GET
    Flowable<ResBaseModel> get(@Url String url, @Header("X-ZZ-Timestamp") String timestamp);


    @POST
    Flowable<ResBaseModel> post(@Url String url, @Body RequestBody body, @Header("X-ZZ-Timestamp") String timestamp);

    @GET
    Observable<ResBaseModel> getObservable(@Url String url, @Header("X-ZZ-Timestamp") String timestamp);


    @POST
    Observable<ResBaseModel> postObservable(@Url String url, @Body RequestBody body, @Header("X-ZZ-Timestamp") String timestamp);
}
