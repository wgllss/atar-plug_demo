/**
 *
 */
package com.atar.host.app.activity.web;

import android.annotation.SuppressLint;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;

import com.common.framework.application.AppConfigModel;
import com.common.framework.interfaces.HandlerListener;
import com.common.framework.plugin.PluginManager;
import com.common.framework.utils.ZzLog;
import com.github.lzyzsd.jsbridge.BridgeWebView;

import java.io.InputStream;

/**
 * ****************************************************************************************************************************************************************************
 *
 * @author :Atar
 * @createTime:2017-6-2上午10:37:14
 * @version:1.0.0
 * @modifyTime:
 * @modifyAuthor:
 * @description: ****************************************************************************************************************************************************************************
 */
public class OffineImplWebViewClient extends ImplWebViewClient {
    /**
     * 保存webview 已有离线文件 key
     */
    public static final String OFFINE_FILE_PATH_KEY = "OFFINE_FILE_PATH_KEY";
    /**
     * assets离线文件
     */
    private String strOfflineResources = "";

    public OffineImplWebViewClient(WebViewActivity activity, BridgeWebView webView,
                                   HandlerListener onHandlerDataListener) {
        super(activity, webView, onHandlerDataListener);
        strOfflineResources = AppConfigModel.getInstance().getString(OFFINE_FILE_PATH_KEY, "");
    }

    @SuppressWarnings("deprecation")
    @SuppressLint("NewApi")
    @Override
    public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
        ZzLog.e(TAG, "shouldInterceptRequest thread id: " + Thread.currentThread().getId() +
                "---url-->" + url);
        int lastSlash = url.lastIndexOf("/");
        if (lastSlash != -1) {
            String suffix = url.substring(lastSlash + 1);
            if (strOfflineResources != null && strOfflineResources.length() > 0 && suffix != null
                    && suffix.length() > 0 && strOfflineResources.contains(suffix) && activity !=
                    null) {
                String mimeType = "";
                String offline_res = "";
                if (suffix.endsWith(".js")) {
                    mimeType = "application/x-javascript";
                    offline_res = "js/";
                } else if (suffix.endsWith(".css")) {
                    mimeType = "text/css";
                    offline_res = "css/";
                } else if (suffix.endsWith(".png")) {
                    mimeType = "image/png";
                    offline_res = "img/";// 主要加载预置表情
                } else {
                    if (url.contains("img/null")) {
                        return super.shouldInterceptRequest(view, url);
                    } else {
                        mimeType = "text/html";
                        offline_res = "html/";// 主要加载预置表情
                    }
                }
                try {
                    InputStream is = PluginManager.getInstance().getAssets().open(offline_res + suffix);
                    return new WebResourceResponse(mimeType, "UTF-8", is);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return super.shouldInterceptRequest(view, url);
    }
}
