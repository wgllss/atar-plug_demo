/**
 *
 */
package com.atar.host.app.activity.web;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.os.Message;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;

import com.atar.host.app.emuns.EnumMsgWhat;
import com.common.framework.interfaces.HandlerListener;
import com.github.lzyzsd.jsbridge.BridgeUtil;
import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.BridgeWebViewClient;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * ****************************************************************************************************************************************************************************
 *
 * @author :Atar
 * @createTime:2017-6-2上午10:30:56
 * @version:1.0.0
 * @modifyTime:
 * @modifyAuthor:
 * @description: ****************************************************************************************************************************************************************************
 */
public class ImplWebViewClient extends BridgeWebViewClient {
    protected String TAG = ImplWebViewClient.class.getSimpleName();

    protected DynamicWebviewActivity activity;

    private HandlerListener onHandlerDataListener;

    public ImplWebViewClient(DynamicWebviewActivity activity, BridgeWebView webView, HandlerListener
            onHandlerDataListener) {
        super(webView);
        this.activity = activity;
        this.onHandlerDataListener = onHandlerDataListener;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        try {
            url = URLDecoder.decode(url, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        if (url.startsWith(BridgeUtil.YY_RETURN_DATA)) { // 如果是返回数据
            webView.handlerReturnData(url);
            return true;
        } else if (url.startsWith(BridgeUtil.YY_OVERRIDE_SCHEMA)) { //
            webView.flushMessageQueue();
            return true;
        } else {
            if (url.indexOf("tel:") < 0) {
                view.loadUrl(url);
            } else if (url != null && url.contains("")) {
                initUrl(view, url);
            } else {
                view.loadUrl(url);
            }
            return true;
//            return super.shouldOverrideUrlLoading(view, url);
        }
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
//        if (url.contains("")) {
//            CookieManager cookieManager = CookieManager.getInstance();
//            String CookieStr = cookieManager.getCookie(url);
//            ZzLog.i(TAG, "Cookies = " + CookieStr);
////            CookieTool.getCookieFromWebViewUrl(activity, url);
//        }
        if (onHandlerDataListener != null) {
            Message msg = new Message();
            msg.what = EnumMsgWhat.msgWhat_handler_1;
            onHandlerDataListener.onHandlerData(msg);
        }
        if (activity != null) {
            activity.hideLoading();
        }
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        if (onHandlerDataListener != null) {
            Message msg = new Message();
            msg.what = EnumMsgWhat.msgWhat_handler;
            onHandlerDataListener.onHandlerData(msg);
        }
        super.onPageStarted(view, url, favicon);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onReceivedError(WebView view, int errorCode, String description, String
            failingUrl) {
        // 错误处理
        try {
            view.stopLoading();
        } catch (Exception e) {
        }
        try {
            view.clearView();
        } catch (Exception e) {
        }
        if (view.canGoBack()) {
            view.goBack();
        }
//        view.loadUrl("file:///android_asset/html/webview_template.html");
        // webView.getSettings().setCacheMode(WebSettings.LOAD_NORMAL);
        if (activity != null) {
            activity.hideLoading();
        }
    }

    @Override
    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
        // handler.cancel(); 默认的处理方式，WebView变成空白页
        handler.proceed();// 接受证书
//        super.onReceivedSslError(view, handler, error);
        // handleMessage(Message msg); 其他处理
    }

    private void initUrl(WebView view, String mUrl) {

    }

    @SuppressWarnings("deprecation")
    @SuppressLint({"NewApi", "SetJavaScriptEnabled"})
    public void initWebViewSettings(WebView webview) {
        if (webview != null) {
            WebSettings webSettings = webview.getSettings();
            webSettings.setDefaultTextEncodingName("UTF-8");
            webSettings.setJavaScriptEnabled(true);
            webSettings.setAllowFileAccess(true);
            webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
            webSettings.setTextSize(WebSettings.TextSize.NORMAL);
            webSettings.setLayoutAlgorithm(LayoutAlgorithm.NORMAL);
            webSettings.setDatabaseEnabled(true);

            // 本地数据库
            webSettings.setDomStorageEnabled(true);
            webSettings.setAppCacheMaxSize(1024 * 1024 * 20);
            String appCachePath = activity.getCacheDir().getAbsolutePath();
            webSettings.setAppCachePath(appCachePath);
            webSettings.setAppCacheEnabled(true);

            webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
            webSettings.setSupportMultipleWindows(true);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
            }

            webview.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    return true;
                }
            });
        }
    }
}
