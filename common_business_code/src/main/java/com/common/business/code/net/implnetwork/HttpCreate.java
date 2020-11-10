package com.common.business.code.net.implnetwork;

import com.common.business.code.bean.ResBaseModel;
import com.common.framework.common.CommonNetWorkExceptionToast;
import com.common.framework.enums.EnumErrorMsg;
import com.common.framework.interfaces.MultSubscriber;
import com.common.framework.network.ServerTime;
import com.common.framework.reflection.ServerException;
import com.common.framework.utils.AppBuildConfig;
import com.common.framework.utils.NetWorkUtil;
import com.common.framework.utils.ZzLog;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import org.json.JSONException;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.ConnectException;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.net.UnknownServiceException;

import javax.net.ssl.SSLHandshakeException;

import androidx.lifecycle.LifecycleOwner;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.subscribers.ResourceSubscriber;

/**
 * @author：atar
 * @date: 2019/5/9
 * @description:
 */
public abstract class HttpCreate {

    private String[] str = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
    protected int reCount = 0;
    protected CompositeDisposable mCompositeSubscription;
    protected ResourceSubscriber resourceSubscriber;
    protected String url;
    protected String timestamp;
    protected LifecycleOwner owner = null;
    protected boolean hasLifecycleOwner;

    public HttpCreate(CompositeDisposable mCompositeSubscription, String url) {
        this.mCompositeSubscription = mCompositeSubscription;
        this.url = url;
        timestamp = String.valueOf(ServerTime.getDefault().getTimeStamp());
    }

    public HttpCreate(LifecycleOwner owner, String url) {
        this.owner = owner;
        this.url = url;
        timestamp = String.valueOf(ServerTime.getDefault().getTimeStamp());
    }

    protected String getNonce() {
        StringBuilder nonce = new StringBuilder();
        for (int i = 0; i < 20; i++) {
            int random = (int) (Math.random() * str.length);
            nonce.append(str[random]);
        }
        return nonce.toString();
    }

    public Flowable subscribe(MultSubscriber subscriber, int what, int which1, int which2, int which3, Type classType, boolean isShowErrorToast) {
        ResourceSubscriber resourceSubscriber = new ResourceSubscriber() {
            @Override
            public void onNext(Object o) {
                try {
                    if (Transformer.isNotdetach(mCompositeSubscription) && subscriber != null) {
                        subscriber.onNext(what, which1, which2, which3, o);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
//                    CrashReport.postCatchedException(e);
                }
            }

            @Override
            public void onError(Throwable t) {
                try {
                    if (Transformer.isNotdetach(mCompositeSubscription) && subscriber != null) {
                        commonDoError(subscriber, t, what, which1, which2, which3, classType, isShowErrorToast);
                        onComplete();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onComplete() {
                if (Transformer.isNotdetach(mCompositeSubscription) && subscriber != null) {
                    subscriber.onComplete(what, which1, which2, which3);
                }
            }
        };
        this.resourceSubscriber = resourceSubscriber;
        if (mCompositeSubscription != null) {
            mCompositeSubscription.add(resourceSubscriber);
        }
        return createRestService(what, classType);
    }

    public Flowable subscribeWith(MultSubscriber subscriber, int what, int which1, int which2, int which3, Type classType, boolean isShowErrorToast) {
        this.resourceSubscriber = getResourceSubscriber(subscriber, what, which1, which2, which3, classType, isShowErrorToast);
        return createRequestRestService(what, classType);
    }

    public ResourceSubscriber getResourceSubscriber(MultSubscriber subscriber, int what, int which1, int which2, int which3, Type classType, boolean isShowErrorToast) {
        ResourceSubscriber resourceSubscriber = new ResourceSubscriber() {
            @Override
            public void onNext(Object o) {
                try {
                    if (!Transformer.isNotdetach(hasLifecycleOwner, owner)) {
                        return;
                    }
                    subscriber.onNext(what, which1, which2, which3, o);
                } catch (Exception e) {
                    e.printStackTrace();
//                    CrashReport.postCatchedException(e);
                }
            }

            @Override
            public void onError(Throwable t) {
                try {
                    if (Transformer.isNotdetach(hasLifecycleOwner, owner)) {
                        commonDoError(subscriber, t, what, which1, which2, which3, classType, isShowErrorToast);
                        onComplete();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onComplete() {
                if (Transformer.isNotdetach(hasLifecycleOwner, owner)) {
                    subscriber.onComplete(what, which1, which2, which3);
                }
            }
        };
        return resourceSubscriber;
    }

    public Flowable subscribe(MultSubscriber subscriber, int what, Type classType) {
        return subscribe(subscriber, what, 0, 0, 0, classType, true);
    }

    public Flowable subscribe(MultSubscriber subscriber, int what, Type classType, boolean isShowErrorToast) {
        return subscribe(subscriber, what, 0, 0, 0, classType, isShowErrorToast);
    }

    public Flowable subscribe(MultSubscriber subscriber, int what, int which1, Type classType) {
        return subscribe(subscriber, what, which1, 0, 0, classType, true);
    }

    public Flowable subscribe(MultSubscriber subscriber, int what, int which1, Type classType, boolean isShowErrorToast) {
        return subscribe(subscriber, what, which1, 0, 0, classType, isShowErrorToast);
    }

    public Flowable subscribe(MultSubscriber subscriber, int what, int which1, int which2, Type classType) {
        return subscribe(subscriber, what, which1, which2, 0, classType, true);
    }

    public Flowable subscribe(MultSubscriber subscriber, int what, int which1, int which2, Type classType, boolean isShowErrorToast) {
        return subscribe(subscriber, what, which1, which2, 0, classType, isShowErrorToast);
    }

    public Flowable subscribe(MultSubscriber subscriber, int what, int which1, int which2, int which3, Type classType) {
        return subscribe(subscriber, what, which1, which2, which3, classType, true);
    }


    public Flowable<ResBaseModel> subscribeWith(MultSubscriber subscriber, int what, Type classType) {
        return subscribeWith(subscriber, what, 0, 0, 0, classType, true);
    }

    public Flowable subscribeWith(MultSubscriber subscriber, int what, Type classType, boolean isShowErrorToast) {
        return subscribeWith(subscriber, what, 0, 0, 0, classType, isShowErrorToast);
    }

    public Flowable subscribeWith(MultSubscriber subscriber, int what, int which1, Type classType) {
        return subscribeWith(subscriber, what, which1, 0, 0, classType, true);
    }

    public Flowable subscribeWith(MultSubscriber subscriber, int what, int which1, Type classType, boolean isShowErrorToast) {
        return subscribeWith(subscriber, what, which1, 0, 0, classType, isShowErrorToast);
    }

    public Flowable subscribeWith(MultSubscriber subscriber, int what, int which1, int which2, Type classType) {
        return subscribeWith(subscriber, what, which1, which2, 0, classType, true);
    }

    public Flowable subscribeWith(MultSubscriber subscriber, int what, int which1, int which2, Type classType, boolean isShowErrorToast) {
        return subscribeWith(subscriber, what, which1, which2, 0, classType, isShowErrorToast);
    }

    public Flowable subscribeWith(MultSubscriber subscriber, int what, int which1, int which2, int which3, Type classType) {
        return subscribeWith(subscriber, what, which1, which2, which3, classType, true);
    }

    protected abstract Flowable createRestService(int what, Type classType);

    public abstract Flowable createRequestRestService(int what, Type classType);

    public abstract Observable createRestService(Type classType);

    /**
     * 公共处理异常
     *
     * @param subscriber
     * @param t
     * @param what
     * @param which1
     * @param which2
     * @param which3
     * @param classType
     * @param isShowErrorToast
     */
    private void commonDoError(MultSubscriber subscriber, Throwable t, int what, int which1, int which2, int which3, Type classType, boolean isShowErrorToast) {
        int msgWhat = what;
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
            if (code == 100058) {
                reCount++;
                if (reCount < 2) {
                    timestamp = String.valueOf(ServerTime.getDefault().getTimeStamp());
                    ZzLog.e("重试一次");
                    subscribeWith(subscriber, what, which1, which2, which3, classType, isShowErrorToast);
                    return;
                }
            }
            try {
                String data = "";
                if (((ServerException) t).data instanceof String) {
                    data = (String) ((ServerException) t).data;
                } else {
                    Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();
                    data = gson.toJson(((ServerException) t).data);
                }

                subscriber.onError(msgWhat, which1, which2, which3, code, errorMessage, data);
            } catch (Exception e) {
                e.printStackTrace();
//                CrashReport.postCatchedException(e);
            }
            return;
        } else {
            msgWhat = EnumErrorMsg.ENotDefine_Msg;
            errorMessage = t.getMessage();
        }
        if (isShowErrorToast) {
            CommonNetWorkExceptionToast.onError(msgWhat, which1, which2, which3, code, errorMessage);
            subscriber.onError(what, which1, which2, which3, code, errorMessage, "");
        }
    }

}
