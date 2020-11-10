package com.common.business.code.net.implnetwork;

import android.text.TextUtils;

import com.common.business.code.bean.ResBaseModel;
import com.common.framework.enums.EnumErrorMsg;
import com.common.framework.network.ServerTime;
import com.common.framework.reflection.ServerException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.reactivestreams.Publisher;

import java.lang.reflect.Type;

import androidx.lifecycle.LifecycleOwner;
import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.ResourceSubscriber;

import static androidx.lifecycle.Lifecycle.State.DESTROYED;

/**
 * @author：atar
 * @date: 2019/5/8
 * @description:
 */
public class Transformer {

    private static Gson gson = new Gson();

    public static Flowable<ResBaseModel> requestActual(Flowable<ResBaseModel> flowable, ResourceSubscriber subscriber, CompositeDisposable mCompositeSubscription, int what, Type classType) {
        flowable.flatMap(flatMap(mCompositeSubscription, what, classType))
                .compose(schedulers())
                .subscribeWith(subscriber);
        return flowable;
    }

    public static Flowable<ResBaseModel> requestActual(Flowable<ResBaseModel> flowable, ResourceSubscriber subscriber, boolean hasLifecycleOwner, LifecycleOwner owner, int what, Type classType) {
        flowable.flatMap(flatMap(hasLifecycleOwner, owner, what, classType))
                .compose(schedulers())
                .subscribeWith(subscriber);
        return flowable;
    }

    /**
     * 切换线程
     *
     * @param <T>
     * @return
     */
    public static <T> FlowableTransformer<T, T> schedulers() {
        return upstream -> upstream.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io());
    }


    /**
     * 过滤数据
     *
     * @return
     */
    public static Function<ResBaseModel, Publisher<?>> flatMap(CompositeDisposable mCompositeSubscription, int what, Type typeOfT) {
        return new Function<ResBaseModel, Publisher<?>>() {
            @Override
            public Publisher<Object> apply(ResBaseModel t) throws Exception {
                try {
                    if (isNotdetach(mCompositeSubscription)) {
                        if (t == null) {
                            return Flowable.error(new Exception("response's model is null"));
                        } else {
                            if (t.now > 0) {
                                ServerTime.getDefault().syncTimeStamp(t.now);
                            }
                            if (t.isSuccess() && t.data != null) {
                                if (typeOfT != String.class) {
                                    Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();
                                    String result = gson.toJson(t.data);
                                    if (!TextUtils.isEmpty(result)) {
                                        if (isNotdetach(mCompositeSubscription)) {
                                            return Flowable.just(gson.fromJson(result, typeOfT));
                                        } else {
                                            return Flowable.error(new ServerException(EnumErrorMsg.EHttpRequestFinishied, 0, "", ""));
                                        }
                                    } else {
                                        return Flowable.error(new Exception("response's model is null"));
                                    }
                                } else {
                                    return Flowable.just(t.data.toString());
                                }
                            } else {
                                return Flowable.error(new ServerException(what, t.code, t.message, t.data));
                            }
                        }
                    } else {
                        return Flowable.error(new ServerException(EnumErrorMsg.EHttpRequestFinishied, 0, "", ""));
                    }
                } catch (Exception e) {
                    return Flowable.error(e);
                }
            }
        };
    }

    public static Function<ResBaseModel, Publisher<?>> flatMap(boolean hasLifecycleOwner, LifecycleOwner owner, int what, Type typeOfT) {
        return new Function<ResBaseModel, Publisher<?>>() {
            @Override
            public Publisher<Object> apply(ResBaseModel t) throws Exception {
                try {
                    if (!isNotdetach(hasLifecycleOwner, owner)) {
                        // ignore
                        return Flowable.error(new ServerException(EnumErrorMsg.EHttpRequestFinishied, 0, "", ""));
                    }
                    if (t == null) {
                        return Flowable.error(new Exception("response's model is null"));
                    } else {
                        if (t.now > 0) {
                            ServerTime.getDefault().syncTimeStamp(t.now);
                        }
                        if (t.isSuccess() && t.data != null) {
                            if (typeOfT != String.class) {
                                Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();
                                String result = gson.toJson(t.data);
                                if (!TextUtils.isEmpty(result)) {
                                    if (!isNotdetach(hasLifecycleOwner, owner)) {
                                        return Flowable.error(new ServerException(EnumErrorMsg.EHttpRequestFinishied, 0, "", ""));
                                    }
                                    return Flowable.just(gson.fromJson(result, typeOfT));
                                } else {
                                    return Flowable.error(new Exception("response's model is null"));
                                }
                            } else {
                                return Flowable.just(t.data.toString());
                            }
                        } else {
                            return Flowable.error(new ServerException(what, t.code, t.message, t.data));
                        }
                    }
                } catch (Exception e) {
                    return Flowable.error(e);
                }
            }
        };
    }


    public static Function<ResBaseModel, ObservableSource<?>> flatMapObservable(boolean hasLifecycleOwner, LifecycleOwner owner, int what, Type typeOfT) {
        return new Function<ResBaseModel, ObservableSource<?>>() {
            @Override
            public ObservableSource<Object> apply(ResBaseModel t) throws Exception {
                try {
                    if (!isNotdetach(hasLifecycleOwner, owner)) {
                        // ignore
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
                                    if (!isNotdetach(hasLifecycleOwner, owner)) {
                                        return Observable.error(new ServerException(EnumErrorMsg.EHttpRequestFinishied, 0, "", ""));
                                    }
                                    return Observable.just(gson.fromJson(result, typeOfT));
                                } else {
                                    return Observable.error(new Exception("response's model is null"));
                                }
                            } else {
                                return Observable.just(t.data.toString());
                            }
                        } else {
                            return Observable.error(new ServerException(what, t.code, t.message, t.data));
                        }
                    }
                } catch (Exception e) {
                    return Observable.error(e);
                }
            }
        };
    }

    /**
     * @param mCompositeSubscription
     * @return
     */
    public static boolean isNotdetach(CompositeDisposable mCompositeSubscription) {
        return (mCompositeSubscription != null && mCompositeSubscription.size() > 0 && !mCompositeSubscription.isDisposed());
    }

    public static boolean isNotdetach(boolean hasLifecycleOwner, LifecycleOwner owner) {
        if (owner != null) {
            if (owner.getLifecycle().getCurrentState() == DESTROYED) {
                return false;
            } else {
                return true;
            }
        }
        if (hasLifecycleOwner) {
            return false;
        }
        return true;
    }
}
