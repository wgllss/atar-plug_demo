package com.app.sub.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.app.sub.R;
import com.common.framework.application.AppConfigModel;
import com.common.framework.utils.ShowLog;
import com.common.framework.utils.ZzLog;

import java.io.InputStream;

import androidx.annotation.Nullable;

/**
 * @author：atar
 * @date: 2020/11/10
 * @description:
 */
public class SubWebViewActivity extends CommTitleResouseActivity {
    private String TAG = SubWebViewActivity.class.getSimpleName();

    private long time;
    /**
     * assets离线文件
     */
    private String strOfflineResources = "";
    public static final String HOST_OFFINE_FILE_PATH_KEY = "HOST_OFFINE_FILE_PATH_KEY";

    @Override
    public void initControl(@Nullable Bundle savedInstanceState) {
        super.initControl(savedInstanceState);
        addContentLayout(R.layout.activity_sub_webview);
        setActivityTitle("插件中 webview ");

        strOfflineResources = AppConfigModel.getInstance().getString(HOST_OFFINE_FILE_PATH_KEY, "");

        WebView webView = findViewById(R.id.webview);
        webView.setWebViewClient(new ImplWebViewClient());
        webView.loadUrl("http://192.168.96.97:8080/assets/html/topic5.html");
    }

    public class ImplWebViewClient extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            time = System.currentTimeMillis();
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            long lasttime = System.currentTimeMillis();
            ShowLog.e(TAG, "加载完成，耗时:" + (lasttime - time) + "ms");
        }

        @Nullable
        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
            ZzLog.e(TAG, "shouldInterceptRequest thread id: " + Thread.currentThread().getId() +
                    "---url-->" + System.currentTimeMillis());
            int lastSlash = url.lastIndexOf("/");
            if (lastSlash != -1) {
                String suffix = url.substring(lastSlash + 1);
                if (strOfflineResources != null && strOfflineResources.length() > 0 && suffix != null
                        && suffix.length() > 0 && strOfflineResources.contains(suffix) && thisContext !=
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
                    } else if (suffix.endsWith(".html")) {
                        mimeType = "text/html";
                        offline_res = "html/";// 主要加载预置表情
                    } else {
                        if (url.contains("img/null")) {
                            return super.shouldInterceptRequest(view, url);
                        }
                    }
                    try {
                        if (!TextUtils.isEmpty(offline_res)) {
                            ZzLog.e(TAG, offline_res + suffix);
                            InputStream is = thisContext.getAssets().open(offline_res + suffix);
                            return new WebResourceResponse(mimeType, "UTF-8", is);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            return super.shouldInterceptRequest(view, url);
        }
    }
}
