package com.atar.host.app.activity.web;

import com.common.framework.widget.CommonToast;
import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.CallBackFunction;

/**
 * ****************************************************************************************************************************************************************************
 * 此类注册 无需要权限，无需其他回调，反之不提取出来，直接写在activity里面
 *
 * @author:Atar
 * @createTime: 2018/9/11 下午5:25
 * @version:1.0.0
 * @modifyTime:
 * @modifyAuthor:
 * @description :
 * **************************************************************************************************************************************************************************
 */
public class NativeRegisterBridgeUitls {

    private static String TAG = NativeRegisterBridgeUitls.class.getSimpleName();

    public static void initRegisterHandler(final DynamicWebviewActivity activity, final BridgeWebView webView) {
        if (activity == null || webView == null) {
            return;
        }

        webView.registerHandler("native_setRefreshing", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                //webview 调用原生方法 主动刷新
                if (activity != null) {
                    activity.autoRefresh();
                }
            }
        });

        webView.registerHandler("native_onStopRefresh", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                //webview 调用原生方法 停止刷新
                if (activity != null) {
                    activity.finishBoth();
                }
            }
        });

        webView.registerHandler("native_setRefreshMode", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                //webview 调用原生方法 设置刷新模式
                /* 刷新模式 0:上下都可以刷新 1:只上面可下拉 2:只有下面可上拉 3 上下都不可以拉 不设置默认只可以下拉刷新不能加载更多  */
                if (activity != null) {
                    if ("0".equals(data)) {
                        activity.setEnableRefresh(true);
                        activity.setEnableLoadMore(true);
                        activity.setEnableAutoLoadMore(true);
                    } else if ("1".equals(data)) {
                        activity.setEnableRefresh(true);
                        activity.setEnableLoadMore(false);
                    } else if ("2".equals(data)) {
                        activity.setEnableRefresh(false);
                        activity.setEnableLoadMore(true);
                        activity.setEnableAutoLoadMore(true);
                    } else if ("3".equals(data)) {
                        activity.setEnableRefresh(false);
                        activity.setEnableLoadMore(false);
                    } else {
                        activity.setEnableRefresh(true);
                        activity.setEnableLoadMore(false);
                    }
                }
            }
        });

//        webView.registerHandler("native_setTopRightText", new BridgeHandler() {
//            @Override
//            public void handler(String data, CallBackFunction function) {
//                //webview 调用原生方法 顶部右边文字
//                if (activity != null) {
//                    activity.setTopRightText(data);
//                }
//            }
//        });

//        webView.registerHandler("native_setTopRightIcon", new BridgeHandler() {
//            @Override
//            public void handler(String data, CallBackFunction function) {
//                //webview 调用原生方法 顶部右边图片
//                if (activity != null) {
//                    activity.setTopRightIcon(data);
//                }
//            }
//        });
//
//        webView.registerHandler("native_setOnDrawerBackEnabled", new BridgeHandler() {
//            @Override
//            public void handler(String data, CallBackFunction function) {
//                //webview 调用原生方法 是否滑动返回 data=1 可以滑动返回 data =0 不能滑动返回
//                if (activity != null) {
//                    activity.setOnDrawerBackEnabled("1".equals(data));
//                }
//            }
//        });
//
//        webView.registerHandler("native_setZoomTextSize", new BridgeHandler() {
//            @Override
//            public void handler(String data, CallBackFunction function) {
//                //webview 调用原生方法 是否缩放字体大小 data=1 可以缩放字体 data =0 不缩放字体
//                if (activity != null) {
//                    Message msg = new Message();
//                    msg.what = EnumMsgWhat.REFRESH_HANDLER4;
//                    msg.obj = "1".equals(data);
//                    activity.onHandlerData(msg);
//                }
//            }
//        });

        webView.registerHandler("native_toast", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                //webview 调用原生方法 toast
                CommonToast.show(data);
            }
        });

//        webView.registerHandler("native_toastLong", new BridgeHandler() {
//            @Override
//            public void handler(String data, CallBackFunction function) {
//                //webview 调用原生方法 toast 长时间
//                if (activity != null) {
//                    CommonToast.show(activity, data, 2, 4, Gravity.CENTER, 3000, false);
//                }
//            }
//        });
//
//        webView.registerHandler("native_putString", new BridgeHandler() {
//            @Override
//            public void handler(String data, CallBackFunction function) {
//                //webview 调用原生方法 存放到本地离线 data为json 如 data = {"KEY":"ID","VALUE":"15616"} 已经做了按照不同登录用户存入了
//                try {
//                    String spKey = JSONUtils.getString(data, "KEY", "");
//                    String value = JSONUtils.getString(data, "VALUE", "");
//                    AppConfigSetting.getInstance().putString(spKey, value);
//                } catch (Exception e) {
//                    function.onCallBack(e.getMessage());
//                    ShowLog.e(TAG, e);
//                }
//            }
//        });
//        webView.registerHandler("native_getString", new BridgeHandler() {
//            @Override
//            public void handler(String spKey, CallBackFunction function) {
//                //webview 调用原生方法 获取存放到本地离线key value
//                String value = AppConfigSetting.getInstance().getString(spKey, "");
//                if (function != null) {
//                    function.onCallBack(value);
//                }
//            }
//        });

//        webView.registerHandler("native_gettoken", new BridgeHandler() {
//            @Override
//            public void handler(String spKey, CallBackFunction function) {
//                //webview 调用原生方法 获取存放到本地离线key value
//                String exchangetoken = PreferenceUtil.getInstance().getexchangetoken();
//                function.onCallBack(exchangetoken);
////                String value = AppConfigSetting.getInstance().getString(spKey, "");
////                if (function != null) {
////                    function.onCallBack(value);
////                }
//            }
//        });
    }
}
