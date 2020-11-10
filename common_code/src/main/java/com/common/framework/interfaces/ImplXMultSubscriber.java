package com.common.framework.interfaces;

/**
 * @author：atar
 * @date: 2020/9/16
 * @description:
 */
public abstract class ImplXMultSubscriber<T> implements MultSubscriber {

    @Override
    public void onComplete(int what, int which1, int which2, int which3) {

    }

    @Override
    public void onNext(int what, int which1, int which2, int which3, Object t) {
        onNext((T) t);
    }

    @Override
    public void onError(int what, int which1, int which2, int which3, int code, String errorMessage, String data) {
        onError(code, errorMessage, data);
    }

    public abstract void onNext(T t);

    public abstract void onError(int code, String errorMessage, String data);
}
