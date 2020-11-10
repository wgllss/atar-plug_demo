package com.common.business.code.activity;

import android.text.TextUtils;

import com.common.business.code.lifecyle.BaseViewModel;
import com.common.framework.interfaces.MultSubscriber;
import com.common.framework.widget.CommonToast;
import com.google.gson.Gson;

import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author：atar
 * @date: 2020/11/7
 * @description:
 */
public class BasePresenter<T extends BaseViewModel> implements MultSubscriber {
    public T viewModel;
    public Gson gson = new Gson();

    public void setViewModel(T viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void onNext(int what, int which1, int which2, int which3, Object t) {

    }

    @Override
    public void onError(int what, int which1, int which2, int which3, int code, String errorMessage, String data) {
        if (viewModel != null) {
            viewModel.showDialog.setValue(false);
            viewModel.error.setValue(errorMessage);
        }
    }

    @Override
    public void onComplete(int what, int which1, int which2, int which3) {

    }

    /**
     * Observable 切换到主线程
     */
    public <T> ObservableTransformer<T, T> observableToMain() {
        return upstream -> upstream.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io());
    }

    /**
     * 提示错误消息
     *
     * @param errorMessage
     */
    public void showErrorMessage(String errorMessage) {
        if (!TextUtils.isEmpty(errorMessage)) {
            CommonToast.show(errorMessage);
        }
    }
}
