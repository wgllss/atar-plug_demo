package com.common.framework.common;

import com.common.framework.interfaces.MultSubscriber;

/**
 * @author：atar
 * @date: 2019/2/19
 * @description:
 */
public interface CommonRereshUInteface extends MultSubscriber {

    void onRefreshUI(int what, int which1, int which2, int which3, Object t);

    void onToast(String toastContent);

    void showloading();

    void showloading(String showText);

    void hideLoading();
}
