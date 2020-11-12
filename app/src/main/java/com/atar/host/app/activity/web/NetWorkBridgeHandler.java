package com.atar.host.app.activity.web;

import com.atar.host.app.net.UrlParamCommon;
import com.common.framework.Threadpool.ThreadPoolTool;
import com.common.framework.http.HttpUrlConnectionRequest;
import com.common.framework.interfaces.NetWorkCallListener;
import com.common.framework.network.NetWorkMsg;
import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.Map;

/**
 * ****************************************************************************************************************************************************************************
 * webview 网络桥接处理
 *
 * @author:Atar
 * @createTime: 2018/9/11 上午11:12
 * @version:1.0.0
 * @modifyTime:
 * @modifyAuthor:
 * @description : //webview 调用原生方法 网络请求
 * **************************************************************************************************************************************************************************
 */
public class NetWorkBridgeHandler implements BridgeHandler {
    private String TAG = NetWorkBridgeHandler.class.getSimpleName();

    private DynamicWebviewActivity dynamicWebviewActivity;
    //暂存带有html返回结果
    private Map<String, String> tempJson;
    private Gson gson = new Gson();

    public NetWorkBridgeHandler(DynamicWebviewActivity dynamicWebviewActivity, Map<String, String> tempJson) {
        this.dynamicWebviewActivity = dynamicWebviewActivity;
        this.tempJson = tempJson;
    }

    @Override
    public void handler(String data, final CallBackFunction function) {
        try {
            if (dynamicWebviewActivity == null || dynamicWebviewActivity.isFinishing()) {
                return;
            }
            final HtmlData htmlData = gson.fromJson(data, HtmlData.class);
            if (htmlData != null) {
                final String msgWhat = htmlData.getMsgWhat();
                final String whichThred1 = htmlData.getWhichThred1();
                final String whichThred2 = htmlData.getWhichThred2();
                String url = htmlData.getUrl();
                final String requestMethod = htmlData.getRequestMethod();
                String paramsJson = htmlData.getParamsJson();
                final String specialHtml = htmlData.getSpecialHtml();
                final String httpRequestMethod = "POST".equals(requestMethod) ? HttpUrlConnectionRequest.POST : HttpUrlConnectionRequest.GET;
                int msgwhat = 0;
                try {
                    msgwhat = Integer.valueOf(msgWhat);
                } catch (Exception e) {
                    msgwhat = 0;
                }
                int msgArg1 = 0, msgArg2 = 0;
                if (whichThred1 != null && whichThred1.length() > 0) {
                    try {
                        msgArg1 = Integer.valueOf(whichThred1.trim());
                    } catch (Exception e) {
                        msgArg1 = 0;
                    }
                }
                if (whichThred2 != null && whichThred2.length() > 0) {
                    try {
                        msgArg2 = Integer.valueOf(whichThred2.trim());
                    } catch (Exception e) {
                        msgArg2 = 0;
                    }
                }
                Map<String, String> map = null;
                if (paramsJson != null && paramsJson.length() > 0) {
                    map = gson.fromJson(paramsJson, new TypeToken<HashMap<String, String>>() {
                    }.getType());
                } else {
                    map = new HashMap<String, String>();
                }
                if (!url.contains("http")) {
//                    url = UrlParamCommon.HOST_API + url;
                }
                Object[] params = new Object[]{url, map, UrlParamCommon.UTF_8, dynamicWebviewActivity};
                final int requestMsgwhat = msgwhat;
                ThreadPoolTool.getInstance().setAsyncTask(msgwhat, msgArg1, msgArg2, new NetWorkCallListener() {
                    @Override
                    public void NetWorkCall(final NetWorkMsg msg) {
                        try {
                            if (dynamicWebviewActivity != null) {
                                dynamicWebviewActivity.finishBoth();
                            }
                            String resultJson = (String) msg.obj;
                            if (requestMsgwhat == msg.what && msg.obj != null) {
                                callWebViewData(resultJson, msgWhat, whichThred1, whichThred2, specialHtml, htmlData, function);
                            } else {
                                onCallBack(htmlData, resultJson, function);
                            }
                        } catch (Exception e) {
                            onCallBack(htmlData, e.getMessage(), function);
                        }
                    }
                }, dynamicWebviewActivity, HttpUrlConnectionRequest.class.getName(), httpRequestMethod, params, String.class);
            }
        } catch (Exception e) {

        }
    }

    /**
     * 返回数据通知webview调回到html
     *
     * @param resultJson
     * @param strMsgWhat
     * @param strMsgArg1
     * @param strMsgArg2
     * @author :Atar
     * @createTime:2017-7-28上午10:26:24
     * @version:1.0.0
     * @modifyTime:
     * @modifyAuthor:
     * @description:
     */
    private void callWebViewData(String resultJson, String strMsgWhat, String strMsgArg1, String strMsgArg2, String specialHtml, HtmlData htmlData, CallBackFunction function) {
        if (resultJson != null && resultJson.length() > 0 && "1".equals(specialHtml)) {
            if (tempJson != null) {
                tempJson.put(strMsgWhat + "_" + strMsgArg1 + "_" + strMsgArg2, resultJson);
            }
            resultJson = "";
        }
        onCallBack(htmlData, resultJson, function);
    }

    private void onCallBack(HtmlData htmlData, String resultJson, CallBackFunction function) {
        if (function != null && htmlData != null) {
            htmlData.setResult(resultJson);
            function.onCallBack(gson.toJson(htmlData));
        }
    }
}
