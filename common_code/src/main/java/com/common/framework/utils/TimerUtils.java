package com.common.framework.utils;


import com.common.framework.interfaces.TimerListener;

import java.util.concurrent.TimeUnit;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by raohaohao on 2018/11/26.
 */

public class TimerUtils {

    /**
     * 倒计时
     *
     * @param compositeDisposable
     * @param count
     * @param unit
     * @param listener
     */
    public static void CountDown(final CompositeDisposable compositeDisposable, final int count, TimeUnit unit, final TimerListener listener) {
        DisposableObserver mObserver = new DisposableObserver<Long>() {

            @Override
            public void onNext(Long num) {
                if (compositeDisposable == null || compositeDisposable.isDisposed()) {
                    ZzLog.e("界面已经关闭");
                    return;
                }
                listener.onNext(String.valueOf(num));
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
                if (compositeDisposable == null || compositeDisposable.isDisposed()) {
                    ZzLog.e("界面已经关闭");
                    return;
                }
//                ZzLog.e("onComplete");
                listener.onComplete();
            }
        };
        if (mObserver != null && compositeDisposable != null) {
            compositeDisposable.add(mObserver);
        }
        Observable.interval(0, 1, unit)
                .take(count + 1)
                .map(new Function<Long, Long>() {
                    @Override
                    public Long apply(Long aLong) throws Exception {
                        return count - aLong;
                    }
                })
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mObserver);
    }

    /**
     * 倒计时
     *
     * @param lifecycleOwner
     * @param count
     * @param unit
     * @param listener
     */
    public static void CountDown(final LifecycleOwner lifecycleOwner, final int count, TimeUnit unit, final TimerListener listener) {
        DisposableObserver mObserver = new DisposableObserver<Long>() {

            @Override
            public void onNext(Long num) {
                if (lifecycleOwner == null || lifecycleOwner.getLifecycle().getCurrentState() == Lifecycle.State.DESTROYED) {
                    ZzLog.e("界面已经关闭");
                    return;
                }
                listener.onNext(String.valueOf(num));
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
                if (lifecycleOwner == null || lifecycleOwner.getLifecycle().getCurrentState() == Lifecycle.State.DESTROYED) {
                    ZzLog.e("界面已经关闭");
                    return;
                }
//                ZzLog.e("onComplete");
                listener.onComplete();
            }
        };
        Observable.interval(0, 1, unit)
                .take(count + 1)
                .map(new Function<Long, Long>() {
                    @Override
                    public Long apply(Long aLong) throws Exception {
                        return count - aLong;
                    }
                })
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mObserver);
    }

    /**
     * 多少秒倒计时
     *
     * @param compositeDisposable
     * @param count
     * @param listener
     */
    public static void CountDown(final CompositeDisposable compositeDisposable, final int count, final TimerListener listener) {
        CountDown(compositeDisposable, count, TimeUnit.SECONDS, listener);
    }

    /**
     * @param compositeDisposable
     * @param initialDelay        多少毫秒后执行
     * @param seconds             多少s后操作
     * @param listener
     */
    public static void CountDownSeconds(final CompositeDisposable compositeDisposable, long initialDelay, final int seconds, final TimerListener listener) {
        DisposableObserver mObserver = new DisposableObserver<Long>() {

            @Override
            public void onNext(Long num) {
                if (compositeDisposable == null || compositeDisposable.isDisposed()) {
                    ZzLog.e("界面已经关闭");
                    return;
                }
                ZzLog.e(String.valueOf(num));
                listener.onNext(String.valueOf(num));
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
                if (compositeDisposable == null || compositeDisposable.isDisposed()) {
                    ZzLog.e("界面已经关闭");
                    return;
                }
                ZzLog.e("onComplete");
                listener.onComplete();
            }
        };
        if (mObserver != null && compositeDisposable != null) {
            compositeDisposable.add(mObserver);
        }
        Observable.interval(initialDelay, 1, TimeUnit.SECONDS)
                .take(seconds + 1)
                .map(new Function<Long, Long>() {
                    @Override
                    public Long apply(Long aLong) throws Exception {
                        return seconds - aLong;
                    }
                })
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mObserver);
    }

    /**
     * 每隔minutes分钟执行
     *
     * @param compositeDisposable
     * @param minutes
     * @param listener
     */
    public static void timer(final CompositeDisposable compositeDisposable, int minutes, final TimerListener listener) {
        DisposableObserver mObserver = new DisposableObserver<Long>() {

            @Override
            public void onNext(Long num) {
                if (compositeDisposable == null || compositeDisposable.isDisposed()) {
                    ZzLog.e("界面已经关闭");
                    return;
                }
                listener.onNext(String.valueOf(num));
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onComplete() {
                if (compositeDisposable == null || compositeDisposable.isDisposed()) {
                    ZzLog.e("界面已经关闭");
                    return;
                }
                ZzLog.e("onComplete");
            }
        };
        Observable.interval(minutes, TimeUnit.MINUTES)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mObserver);
    }

    /**
     * 每隔seconds秒钟执行
     *
     * @param compositeDisposable
     * @param seconds
     * @param listener
     */
    public static void timerInterval(final CompositeDisposable compositeDisposable, int seconds, final TimerListener listener) {
        DisposableObserver mObserver = new DisposableObserver<Long>() {

            @Override
            public void onNext(Long num) {
                if (compositeDisposable == null || compositeDisposable.isDisposed()) {
                    ZzLog.e("界面已经关闭");
                    return;
                }
                listener.onNext(String.valueOf(num));
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onComplete() {
                if (compositeDisposable == null || compositeDisposable.isDisposed()) {
                    ZzLog.e("界面已经关闭");
                    return;
                }
                ZzLog.e("onComplete");
            }
        };
        Observable.interval(seconds, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mObserver);
    }
}
