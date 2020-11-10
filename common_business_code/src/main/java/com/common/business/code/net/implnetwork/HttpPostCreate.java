package com.common.business.code.net.implnetwork;

import android.text.TextUtils;

import com.common.business.code.bean.ResBaseModel;
import com.common.business.code.net.Config;
import com.common.framework.enums.EnumErrorMsg;
import com.common.framework.network.RequestBodyWrapper;
import com.common.framework.network.ServerTime;
import com.common.framework.reflection.ServerException;
import com.common.framework.utils.CryptoUtils;
import com.common.framework.utils.ZzLog;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.lang.reflect.Type;

import androidx.lifecycle.LifecycleOwner;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Function;
import okhttp3.RequestBody;

/**
 * @author：atar
 * @date: 2019/5/9
 * @description:
 */
public class HttpPostCreate extends HttpCreate {

    private String body;

    public HttpPostCreate(CompositeDisposable mCompositeSubscription, String url, String body) {
        super(mCompositeSubscription, url);
        this.body = body;
    }

    public HttpPostCreate(LifecycleOwner owner, String url, String body) {
        super(owner, url);
        this.body = body;
        hasLifecycleOwner = owner != null;
    }

    public static HttpPostCreate newInstance(LifecycleOwner owner, String url, String body) {
        return new HttpPostCreate(owner, url, body);
    }

    public static HttpPostCreate newInstance(String url, String body) {
        return newInstance(null, url, body);
    }

    @Override
    protected Flowable createRestService(int what, Type classType) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Config.SECRET_KEY);

        String nonce = getNonce();
        String urlPick = "nonce" + nonce;
        stringBuilder.append(urlPick);
        stringBuilder.append(timestamp);

        RequestBody postBody = new RequestBodyWrapper(body);
        stringBuilder.append(body);

        String sign = CryptoUtils.genMD5Str(stringBuilder.toString());

        String postUrl = url + "?nonce=" + nonce + "&sign=" + sign;
        Flowable<ResBaseModel> flowable = RetrofitRequest.postDada(postUrl, postBody, timestamp);
        Transformer.requestActual(flowable, resourceSubscriber, mCompositeSubscription, what, classType);
        return flowable;
    }

    @Override
    public Flowable<ResBaseModel> createRequestRestService(int what, Type classType) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Config.SECRET_KEY);

        String nonce = getNonce();
        String urlPick = "nonce" + nonce;
        stringBuilder.append(urlPick);
        stringBuilder.append(timestamp);

        RequestBody postBody = new RequestBodyWrapper(body);
        stringBuilder.append(body);

        String sign = CryptoUtils.genMD5Str(stringBuilder.toString());

        String postUrl = url + "?nonce=" + nonce + "&sign=" + sign;
        Flowable<ResBaseModel> flowable = RetrofitRequest.postDada(postUrl, postBody, timestamp);
        Transformer.requestActual(flowable, resourceSubscriber, hasLifecycleOwner, owner, what, classType);
        return flowable;
    }

    @Override
    public Observable<ResBaseModel> createRestService(Type typeOfT) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Config.SECRET_KEY);

        String nonce = getNonce();
        String urlPick = "nonce" + nonce;
        stringBuilder.append(urlPick);
        stringBuilder.append(timestamp);

        RequestBody postBody = new RequestBodyWrapper(body);
        stringBuilder.append(body);

        String sign = CryptoUtils.genMD5Str(stringBuilder.toString());

        String postUrl = url + "?nonce=" + nonce + "&sign=" + sign;
        return RetrofitRequest.postObservableData(postUrl, postBody, timestamp)
                .flatMap(new Function<ResBaseModel, ObservableSource<ResBaseModel>>() {
                    @Override
                    public ObservableSource<ResBaseModel> apply(ResBaseModel t) throws Exception {
                        try {
                            if (!Transformer.isNotdetach(hasLifecycleOwner, owner)) {
                                return Observable.error(new ServerException(EnumErrorMsg.EHttpRequestFinishied, 0, "", ""));
                            }
                            if (t == null) {
                                return Observable.error(new Exception("response's model is null"));
                            } else {
                                if (t.now > 0) {
                                    ServerTime.getDefault().syncTimeStamp(t.now);
                                }
                                if (t.isSuccess() && t.data != null) {
                                    if (typeOfT != String.class) {
                                        Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();
                                        String result = gson.toJson(t.data);
                                        if (!TextUtils.isEmpty(result)) {
                                            t.data = gson.fromJson(result, typeOfT);
                                            if (!Transformer.isNotdetach(hasLifecycleOwner, owner)) {
                                                return Observable.error(new ServerException(EnumErrorMsg.EHttpRequestFinishied, 0, "", ""));
                                            }
                                            return Observable.just(t);
                                        } else {
                                            return Observable.error(new Exception("response's model is null"));
                                        }
                                    } else {
                                        t.data = t.data.toString();
                                        if (!Transformer.isNotdetach(hasLifecycleOwner, owner)) {
                                            return Observable.error(new ServerException(EnumErrorMsg.EHttpRequestFinishied, 0, "", ""));
                                        }
                                        return Observable.just(t);
                                    }
                                } else {
                                    if (t.code == 100058) {
                                        reCount++;
                                        if (reCount < 2) {
                                            timestamp = String.valueOf(ServerTime.getDefault().getTimeStamp());
                                            ZzLog.e("重试一次");
                                            return createRestService(typeOfT);
                                        }
                                    }
                                    return Observable.error(new ServerException(0, t.code, t.message, t.data));
                                }
                            }
                        } catch (Exception e) {
                            return Observable.error(e);
                        }
                    }
                });
    }
}
