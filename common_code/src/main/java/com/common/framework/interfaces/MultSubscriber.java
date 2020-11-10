package com.common.framework.interfaces;

/**
 * @author：atar
 * @date: 2019/1/8
 * @description:
 */
public interface MultSubscriber {
    void onNext(int what, int which1, int which2, int which3, Object t);

    void onError(int what, int which1, int which2, int which3, int code, String errorMessage, String data);

    void onComplete(int what, int which1, int which2, int which3);
}
