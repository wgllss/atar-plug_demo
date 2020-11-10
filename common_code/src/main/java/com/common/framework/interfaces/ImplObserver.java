package com.common.framework.interfaces;

import android.text.TextUtils;

import com.common.framework.common.CommonNetWorkExceptionToast;
import com.common.framework.enums.EnumErrorMsg;
import com.common.framework.reflection.ServerException;
import com.common.framework.utils.AppBuildConfig;
import com.common.framework.utils.NetWorkUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import org.json.JSONException;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.net.UnknownServiceException;

import javax.net.ssl.SSLHandshakeException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * @author：atar
 * @date: 2020/9/27
 * @description:
 */
public abstract class ImplObserver<T> implements Observer<T> {

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(T o) {

    }

    @Override
    public void onError(Throwable t) {
        int msgWhat = 0;
        int code = 0;
        String errorMessage = "";
        if (t instanceof ConnectException) {
            msgWhat = EnumErrorMsg.EHttpIO_Msg;
        } else if (t instanceof SocketTimeoutException) {//
            // 联网超时
            msgWhat = EnumErrorMsg.EConnectTimeout_Msg;
        } else if (t instanceof UnknownHostException) {
            if (!NetWorkUtil.isNetWorkActive(AppBuildConfig.getApplication())) {
                msgWhat = EnumErrorMsg.EMobileNetUseless_Msg;
            } else {
                msgWhat = EnumErrorMsg.EUnknownHost_msg;
            }
        } else if (t instanceof SSLHandshakeException) {
            msgWhat = EnumErrorMsg.EHttpRequestSSLHandshakeException;
        } else if (t instanceof JsonSyntaxException || t instanceof JSONException) {
            msgWhat = EnumErrorMsg.EJsonParser_Msg;
        } else if (t instanceof MalformedURLException) {// 网络协议错误
            msgWhat = EnumErrorMsg.EHttpProtocol_Msg;
        } else if (t instanceof UnknownServiceException) {// 服务端出错
            msgWhat = EnumErrorMsg.EUnknownService_msg;
        } else if (t instanceof UnsupportedEncodingException) {// 通信编码错误
            msgWhat = EnumErrorMsg.EUnsupportedEncoding_msg;
        } else if (t instanceof IOException) {// 服务器开小差,请重试
            msgWhat = EnumErrorMsg.EHttpIO_Msg;
        } else if (t instanceof ServerException) {
            msgWhat = ((ServerException) t).what;
            errorMessage = ((ServerException) t).errorMessage;
            code = ((ServerException) t).code;
            try {
                String data = "";
                if (((ServerException) t).data instanceof String) {
                    data = (String) ((ServerException) t).data;
                } else {
                    Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();
                    data = gson.toJson(((ServerException) t).data);
                }
                if (TextUtils.isEmpty(errorMessage)) {
                    errorMessage = "网络连接失败" + code;
                }
                onError(code, errorMessage, data);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return;
        } else {
            msgWhat = EnumErrorMsg.ENotDefine_Msg;
            errorMessage = t.getMessage();
        }
        CommonNetWorkExceptionToast.onError(msgWhat, errorMessage);
        //当其他情况也需要传递外层，让弹窗消失
        onError(-1,"","");
    }

    @Override
    public void onComplete() {

    }

    public abstract void onError(int code, String errorMessage, String data);
}
