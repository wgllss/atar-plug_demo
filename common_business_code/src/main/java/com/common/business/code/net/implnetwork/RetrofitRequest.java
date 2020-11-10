package com.common.business.code.net.implnetwork;

import com.common.business.code.bean.ResBaseModel;
import com.common.framework.network.RetrofitWrap;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import okhttp3.RequestBody;

/**
 * @author：atar
 * @date: 2019/5/11
 * @description:
 */
public class RetrofitRequest {

    public static Flowable<ResBaseModel> getResult(String url, String timestamp) {
        return RetrofitWrap.getInstance().create(RestService.class).get(url, timestamp);
    }

    public static Flowable<ResBaseModel> postDada(String url, RequestBody body, String timestamp) {
        return RetrofitWrap.getInstance().create(RestService.class).post(url, body, timestamp);
    }

    public static Observable<ResBaseModel> getObservableResult(String url, String timestamp) {
        return RetrofitWrap.getInstance().create(RestService.class).getObservable(url, timestamp);
    }

    public static Observable<ResBaseModel> postObservableData(String url, RequestBody body, String timestamp) {
        return RetrofitWrap.getInstance().create(RestService.class).postObservable(url, body, timestamp);
    }
}
