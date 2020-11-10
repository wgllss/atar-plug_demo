package com.common.framework.utils;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * @author：atar
 * @date: 2019/1/30
 * @description:
 */
public class RxJavaUtils {

    /**
     * 操作异步处理事务
     *
     * @param mObservableOnSubscribe
     * @param mObserver
     * @param <T>
     */
    public static <T> void work(CompositeDisposable compositeDisposable, ObservableOnSubscribe<T> mObservableOnSubscribe, DisposableObserver mObserver) {
        if (mObserver != null && compositeDisposable != null) {
            compositeDisposable.add(mObserver);
        }
        Observable.create(mObservableOnSubscribe)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribe(mObserver);
    }
}
