package com.common.framework.common;

import android.content.Context;

import com.common.framework.enums.EnumErrorMsg;
import com.common.framework.http.HttpRequest;
import com.common.framework.interfaces.NetWorkCallListener;
import com.common.framework.network.NetWorkMsg;
import com.common.framework.utils.AppBuildConfig;
import com.common.framework.utils.ZzLog;
import com.common.framework.widget.CommonToast;

import java.util.Date;


/**
 * @author：atar
 * @date: 2019/1/11
 * @description:
 */
public class CommonNetWorkExceptionToast {

    private static String toastError[];
    private static boolean isShowError;

    // 提示时间间隔4秒钟
    private static int toastPeriod = 4;
    private static Date lastDate = null;

    /**
     * 是否显示错误异常提示
     *
     * @param isShowError0
     * @author :Atar
     * @createTime:2016-5-13下午4:34:36
     * @version:1.0.0
     * @modifyTime:
     * @modifyAuthor:
     * @description:
     */
    public static void setIsShowErrorToast(boolean isShowError0) {
        isShowError = isShowError0;
    }

    /**
     * setToastError: 从数组里获取String型 错误提示信息
     *
     * @param id: array资源数组id
     * @atour: Atar
     * @createTime:2014-5-20上午12:29:53
     * @modifyTime:
     * @modifyAtour:
     * @version: 1.0.0
     * @description:
     */
    public static void initToastError(Context context, int id) {
        toastError = context.getResources().getStringArray(id);
    }

    /**
     * toast提示内容
     *
     * @param errorArrayStringIndex
     * @author :Atar
     * @createTime:2014-12-12下午4:37:56
     * @version:1.0.0
     * @modifyTime:
     * @modifyAuthor:
     * @description:
     */
    public static void toast(int showToast, int errorArrayStringIndex) {
        if (isShowError && showToast != EnumErrorMsg.NetWorkMsgWhithoutToast) {
            toast(errorArrayStringIndex);
        }
    }

    /**
     * toast提示内容
     *
     * @param errorArrayStringIndex
     * @author :Atar
     * @createTime:2014-12-12下午4:37:56
     * @version:1.0.0
     * @modifyTime:
     * @modifyAuthor:
     * @description:
     */
    public static void toast(int errorArrayStringIndex) {
        if (isShowError) {
            CommonToast.show(toastError[errorArrayStringIndex]);
        }
    }

    public static void NetWorkCall(NetWorkMsg msg, NetWorkCallListener mNetWorkCallListener) {
        try {
            if (mNetWorkCallListener != null) {
                mNetWorkCallListener.NetWorkCall(msg);
            }
            switch (msg.what) {
                case EnumErrorMsg.EHttpIO_Msg:
                    String remark = (String) msg.obj;
                    if (remark != null && remark.length() > 0) {
                        if (HttpRequest.IsUsableNetWork(AppBuildConfig.getApplication())) {
                            toast(msg.arg3, 0);
                        } else {
                            if (!isInToastPeriod()) {
//                                CommonToast.show(toastError[1]);
                            }
                        }
                    } else {
                        toast(2);
                    }
                    break;
                case EnumErrorMsg.ENotFoundMethods_Msg:
                    toast(msg.arg3, 3);
                    break;
                case EnumErrorMsg.EHttpProtocol_Msg:
                    toast(msg.arg3, 4);
                    break;
                case EnumErrorMsg.EXmlParser_Msg:
                    toast(msg.arg3, 5);
                    break;
                case EnumErrorMsg.EXmlIO_Msg:
                    toast(msg.arg3, 6);
                    break;
                case EnumErrorMsg.EClassNotFound_Msg:
                    toast(msg.arg3, 7);
                    break;
                case EnumErrorMsg.EIllegalAccess_Msg:
                    toast(msg.arg3, 8);
                    break;
                case EnumErrorMsg.ESecurity_Msg:
                    toast(msg.arg3, 9);
                    break;
                case EnumErrorMsg.EMobileNetUseless_Msg:
                    if (!isInToastPeriod()) {
//                        CommonToast.show(toastError[1]);
                    }
                    ZzLog.i(toastError[1]);
                    break;
                case EnumErrorMsg.EConnectTimeout_Msg:
                    toast(msg.arg3, 10);
                    break;
                case EnumErrorMsg.EParamHasNull_Msg:
                    toast(msg.arg3, 11);
                    break;
                case EnumErrorMsg.EParamUnInvalid_Msg:
                    toast(msg.arg3, 12);
                    break;
                case EnumErrorMsg.EJsonParser_Msg:
                    toast(msg.arg3, 13);
                    break;
                case EnumErrorMsg.ENotDefine_Msg:
                    toast(msg.arg3, 14);
                    break;
                case EnumErrorMsg.EHttpRequestFail400:
                    toast(msg.arg3, 15);
                    break;
                case EnumErrorMsg.EHttpRequestFail403:
                    toast(msg.arg3, 16);
                    break;
                case EnumErrorMsg.EHttpRequestFail404:
                    toast(msg.arg3, 17);
                    break;
                case EnumErrorMsg.EHttpRequestFail502:
                    toast(msg.arg3, 18);
                    break;
                case EnumErrorMsg.EHttpRequestFail503:
                    toast(msg.arg3, 19);
                    break;
                case EnumErrorMsg.EHttpRequestFail504:
                    toast(msg.arg3, 20);
                    break;
                case EnumErrorMsg.EUnknownHost_msg:
                    toast(msg.arg3, 21);
                    break;
                case EnumErrorMsg.EUnknownService_msg:
                    toast(msg.arg3, 22);
                    break;
                case EnumErrorMsg.EUnsupportedEncoding_msg:
                    toast(msg.arg3, 23);
                    break;
                case EnumErrorMsg.EHttpRequestFail500:
                    toast(msg.arg3, 24);
                    break;
                case EnumErrorMsg.EHttpRequestFail405:
                    toast(msg.arg3, 25);
                    break;
                case EnumErrorMsg.EHttpRequestFail401:
                    toast(msg.arg3, 26);
                    break;
            }
        } catch (ClassCastException e) {
            toast(13);
            ZzLog.e("NetWorkCall--e--->" + e);
        } catch (Exception e) {
            ZzLog.e("NetWorkCall--e--->" + e);
        }
    }

    /**
     * 公共处理错误提示
     *
     * @param what
     * @param which1
     * @param which2
     * @param which3
     * @param code
     * @param errorMessage
     */
    public static void onError(int what, int which1, int which2, int which3, int code, String errorMessage) {
        try {
            switch (what) {
                case EnumErrorMsg.EHttpIO_Msg:
                    toast(1);
                    break;
                case EnumErrorMsg.ENotFoundMethods_Msg:
                    toast(3);
                    break;
                case EnumErrorMsg.EHttpProtocol_Msg:
                    toast(4);
                    break;
                case EnumErrorMsg.EXmlParser_Msg:
                    toast(5);
                    break;
                case EnumErrorMsg.EXmlIO_Msg:
                    toast(6);
                    break;
                case EnumErrorMsg.EClassNotFound_Msg:
                    toast(7);
                    break;
                case EnumErrorMsg.EIllegalAccess_Msg:
                    toast(8);
                    break;
                case EnumErrorMsg.ESecurity_Msg:
                    toast(9);
                    break;
                case EnumErrorMsg.EMobileNetUseless_Msg:
                    CommonToast.show(toastError[1]);
                    break;
                case EnumErrorMsg.EConnectTimeout_Msg:
                    toast(10);
                    break;
                case EnumErrorMsg.EParamHasNull_Msg:
                    toast(11);
                    break;
                case EnumErrorMsg.EParamUnInvalid_Msg:
                    toast(12);
                    break;
                case EnumErrorMsg.EJsonParser_Msg:
                    toast(13);
                    break;
                case EnumErrorMsg.ENotDefine_Msg:
                    CommonToast.show(errorMessage);
//                    toast(14);
                    break;
                case EnumErrorMsg.EHttpRequestFail400:
                    toast(15);
                    break;
                case EnumErrorMsg.EHttpRequestFail403:
                    toast(16);
                    break;
                case EnumErrorMsg.EHttpRequestFail404:
                    toast(17);
                    break;
                case EnumErrorMsg.EHttpRequestFail502:
                    toast(18);
                    break;
                case EnumErrorMsg.EHttpRequestFail503:
                    toast(19);
                    break;
                case EnumErrorMsg.EHttpRequestFail504:
                    toast(20);
                    break;
                case EnumErrorMsg.EUnknownHost_msg:
                    toast(21);
                    break;
                case EnumErrorMsg.EUnknownService_msg:
                    toast(22);
                    break;
                case EnumErrorMsg.EUnsupportedEncoding_msg:
                    toast(23);
                    break;
                case EnumErrorMsg.EHttpRequestFail500:
                    toast(24);
                    break;
                case EnumErrorMsg.EHttpRequestFail405:
                    toast(25);
                    break;
                case EnumErrorMsg.EHttpRequestFail401:
                    toast(26);
                    break;
                case EnumErrorMsg.EHttpRequestSSLHandshakeException:
                    toast(27);
                    break;
            }
        } catch (ClassCastException e) {
            toast(13);
            ZzLog.e("onError--e--->" + e);
        } catch (Exception e) {
            ZzLog.e("onError--e--->" + e);
        }
    }

    /**
     * 公共处理错误提示
     *
     * @param what
     * @param errorMessage
     */
    public static void onError(int what, String errorMessage) {
        try {
            switch (what) {
                case EnumErrorMsg.EHttpIO_Msg:
                    toast(1);
                    break;
                case EnumErrorMsg.ENotFoundMethods_Msg:
                    toast(3);
                    break;
                case EnumErrorMsg.EHttpProtocol_Msg:
                    toast(4);
                    break;
                case EnumErrorMsg.EXmlParser_Msg:
                    toast(5);
                    break;
                case EnumErrorMsg.EXmlIO_Msg:
                    toast(6);
                    break;
                case EnumErrorMsg.EClassNotFound_Msg:
                    toast(7);
                    break;
                case EnumErrorMsg.EIllegalAccess_Msg:
                    toast(8);
                    break;
                case EnumErrorMsg.ESecurity_Msg:
                    toast(9);
                    break;
                case EnumErrorMsg.EMobileNetUseless_Msg:
                    CommonToast.show(toastError[1]);
                    break;
                case EnumErrorMsg.EConnectTimeout_Msg:
                    toast(10);
                    break;
                case EnumErrorMsg.EParamHasNull_Msg:
                    toast(11);
                    break;
                case EnumErrorMsg.EParamUnInvalid_Msg:
                    toast(12);
                    break;
                case EnumErrorMsg.EJsonParser_Msg:
                    toast(13);
                    break;
                case EnumErrorMsg.ENotDefine_Msg:
                    CommonToast.show(errorMessage);
//                    toast(14);
                    break;
                case EnumErrorMsg.EHttpRequestFail400:
                    toast(15);
                    break;
                case EnumErrorMsg.EHttpRequestFail403:
                    toast(16);
                    break;
                case EnumErrorMsg.EHttpRequestFail404:
                    toast(17);
                    break;
                case EnumErrorMsg.EHttpRequestFail502:
                    toast(18);
                    break;
                case EnumErrorMsg.EHttpRequestFail503:
                    toast(19);
                    break;
                case EnumErrorMsg.EHttpRequestFail504:
                    toast(20);
                    break;
                case EnumErrorMsg.EUnknownHost_msg:
                    toast(21);
                    break;
                case EnumErrorMsg.EUnknownService_msg:
                    toast(22);
                    break;
                case EnumErrorMsg.EUnsupportedEncoding_msg:
                    toast(23);
                    break;
                case EnumErrorMsg.EHttpRequestFail500:
                    toast(24);
                    break;
                case EnumErrorMsg.EHttpRequestFail405:
                    toast(25);
                    break;
                case EnumErrorMsg.EHttpRequestFail401:
                    toast(26);
                    break;
                case EnumErrorMsg.EHttpRequestSSLHandshakeException:
                    toast(27);
                    break;
            }
        } catch (ClassCastException e) {
            toast(13);
            ZzLog.e("onError--e--->" + e);
        } catch (Exception e) {
            ZzLog.e("onError--e--->" + e);
        }
    }
    /**
     * 是否在提示时间间隔之内
     *
     * @return
     * @author :Atar
     * @createTime:2014-11-12上午11:26:54
     * @version:1.0.0
     * @modifyTime:
     * @modifyAuthor:
     * @description:
     */
    @SuppressWarnings("unused")
    private static boolean isInToastPeriod() {
        Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
        if (curDate != null) {
            long lastTime = 0;
            if (lastDate == null) {
                lastTime = 0;
            } else {
                lastTime = lastDate.getTime();
            }
            long timeLong = curDate.getTime() - lastTime;
            if (timeLong < toastPeriod * 1000) {
                return true;
            } else {
                lastDate = curDate;
                return false;
            }
        } else {
            return false;
        }
    }
}
