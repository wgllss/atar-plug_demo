package com.common.business.code.net.implnetwork;

import android.text.TextUtils;

import com.common.business.code.bean.ResBaseModel;
import com.common.business.code.net.Config;
import com.common.framework.enums.EnumErrorMsg;
import com.common.framework.network.ServerTime;
import com.common.framework.reflection.ServerException;
import com.common.framework.utils.CryptoUtils;
import com.common.framework.utils.ZzLog;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import androidx.lifecycle.LifecycleOwner;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Function;

/**
 * @author：atar
 * @date: 2019/5/9
 * @description:
 */
public class HttpGetCreate extends HttpCreate {

    private Map<String, Object> options;

    public HttpGetCreate(CompositeDisposable mCompositeSubscription, String url, Map<String, Object> options) {
        super(mCompositeSubscription, url);
        this.options = options;
    }

    public HttpGetCreate(LifecycleOwner owner, String url, Map<String, Object> options) {
        super(owner, url);
        this.options = options;
        hasLifecycleOwner = owner != null;
    }

    public static HttpGetCreate newInstance(LifecycleOwner owner, String url, Map<String, Object> options) {
        return new HttpGetCreate(owner, url, options);
    }

    public static HttpGetCreate newInstance(String url, Map<String, Object> options) {
        return newInstance(null, url, options);
    }

    @Override
    protected Flowable createRestService(int what, Type classType) {
        if (options == null) {
            options = new TreeMap();
        }
        String nonce = getNonce();

        options.put("nonce", nonce);

        StringBuilder stringBuilder = new StringBuilder();
        StringBuilder urlPick = new StringBuilder();
        stringBuilder.append(Config.SECRET_KEY);

        if (options != null) {
            Iterator iterator = options.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry entry = (Map.Entry) iterator.next();
                stringBuilder.append(entry.getKey().toString());
                urlPick.append(entry.getKey().toString() + "=");
                stringBuilder.append(entry.getValue().toString());
                urlPick.append(entry.getValue().toString());
                urlPick.append("&");
            }
        }
        stringBuilder.append(timestamp);
        String sign = CryptoUtils.genMD5Str(stringBuilder.toString());
        ZzLog.i(stringBuilder.toString() + "---->sign--->" + sign);

        String getUrl = url + "?" + urlPick.toString() + "sign=" + sign;

        Flowable flowable = RetrofitRequest.getResult(getUrl, timestamp);
        Transformer.requestActual(flowable, resourceSubscriber, mCompositeSubscription, what, classType);
        return flowable;
    }

    @Override
    public Flowable<ResBaseModel> createRequestRestService(int what, Type classType) {
        if (options == null) {
            options = new TreeMap();
        }
        String nonce = getNonce();

        options.put("nonce", nonce);

        StringBuilder stringBuilder = new StringBuilder();
        StringBuilder urlPick = new StringBuilder();
        stringBuilder.append(Config.SECRET_KEY);

        if (options != null) {
            Iterator iterator = options.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry entry = (Map.Entry) iterator.next();
                stringBuilder.append(entry.getKey().toString());
                urlPick.append(entry.getKey().toString() + "=");
                stringBuilder.append(entry.getValue().toString());
                urlPick.append(entry.getValue().toString());
                urlPick.append("&");
            }
        }
        stringBuilder.append(timestamp);
        String sign = CryptoUtils.genMD5Str(stringBuilder.toString());
        ZzLog.i(stringBuilder.toString() + "---->sign--->" + sign);

        String getUrl = url + "?" + urlPick.toString() + "sign=" + sign;
        Flowable<ResBaseModel> flowable = RetrofitRequest.getResult(getUrl, timestamp);
        Transformer.requestActual(flowable, resourceSubscriber, hasLifecycleOwner, owner, what, classType);
        return flowable;
    }


    @Override
    public Observable<ResBaseModel> createRestService(Type typeOfT) {
        if (options == null) {
            options = new TreeMap();
        }
        String nonce = getNonce();

        options.put("nonce", nonce);

        StringBuilder stringBuilder = new StringBuilder();
        StringBuilder urlPick = new StringBuilder();
        stringBuilder.append(Config.SECRET_KEY);

        if (options != null) {
            Iterator iterator = options.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry entry = (Map.Entry) iterator.next();
                stringBuilder.append(entry.getKey().toString());
                urlPick.append(entry.getKey().toString() + "=");
                stringBuilder.append(entry.getValue().toString());
                urlPick.append(entry.getValue().toString());
                urlPick.append("&");
            }
        }
        stringBuilder.append(timestamp);
        String sign = CryptoUtils.genMD5Str(stringBuilder.toString());
        ZzLog.i(stringBuilder.toString() + "---->sign--->" + sign);

        String getUrl = url + "?" + urlPick.toString() + "sign=" + sign;
        return RetrofitRequest.getObservableResult(getUrl, timestamp)
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
