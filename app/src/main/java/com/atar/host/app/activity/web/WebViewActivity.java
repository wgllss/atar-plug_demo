package com.atar.host.app.activity.web;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Message;
import android.preference.TwoStatePreference;

import com.atar.host.app.R;
import com.atar.host.app.activity.CommTitleResouseActivity;
import com.atar.host.app.emuns.EnumMsgWhat;
import com.atar.host.app.presenter.TestPresenter;
import com.atar.host.app.utils.AppConfigUtils;
import com.atar.host.app.activity.RefreshLayoutActivity;
import com.atar.host.app.viewmodels.TestViewModel;
import com.common.business.code.utils.IntentUtil;
import com.common.framework.application.CrashHandler;
import com.common.framework.interfaces.HandlerListener;
import com.common.framework.plugin.PluginListener;
import com.common.framework.plugin.PluginManager;
import com.common.framework.utils.ShowLog;
import com.common.framework.widget.CommonToast;
import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;

public class WebViewActivity extends RefreshLayoutActivity<TestViewModel, TestPresenter> implements HandlerListener {

    private String TAG = WebViewActivity.class.getSimpleName();

    private BridgeWebView webview;
    private String url;
    private String optionsJson = "";
    private boolean isWebViewLoadSuccess;
    //暂存带有html返回结果
    private Map<String, String> tempJson = new HashMap<String, String>();
    private ImplInAndroidScript mImplInAndroidScript = new ImplInAndroidScript(tempJson);

    private long time;

    @Override
    public void initControl(Bundle savedInstanceState) {
        super.initControl(savedInstanceState);
        addContentLayout(R.layout.activity_web_view);
        webview = findViewById(R.id.common_refresh_bridgewebview);
        setRefreshUI(findViewById(R.id.refreshLayout));
    }

    @Override
    protected void bindEvent() {
        super.bindEvent();
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setOnLoadMoreListener(this);
        setEnableRefresh(true);
        setEnableLoadMore(true);
        autoRefresh();
    }

    @Override
    protected void initValue() {
        super.initValue();
        setActivityTitle("宿主html页");
        url = getIntent().getStringExtra(URL_KEY);
        /* 向html传入初始参数 start */
        String mode = getIntent().getStringExtra(PULL_TO_REFRESH_MODE_KEY);
        String options = getIntent().getStringExtra(OPTIONS_KEY);
        webview.addJavascriptInterface(mImplInAndroidScript, "injs");
        optionsJson = DynamicHtmlUtils.getInitValue(this, this, webview, options, mode, url);
    }

    @Override
    public void onHandlerData(Message msg) {
        switch (msg.what) {
            case EnumMsgWhat.msgWhat_handler:// 加载html开始
                time = System.currentTimeMillis();
//                showloading();
                break;
            case EnumMsgWhat.msgWhat_handler_1:// 加载html初始化完成
                finishBoth();
                long lasttime = System.currentTimeMillis();
                ShowLog.e(TAG, "加载完成，耗时:" + (lasttime - time) + "ms");

                isWebViewLoadSuccess = true;
//                hideLoading();
                webview.send(optionsJson);//传入数据到h5
//                if (mImplOnTouchChanceTextSizeListener != null) {
//                    mImplOnTouchChanceTextSizeListener.setWebViewLoadSuccess(isWebViewLoadSuccess);
//                    webView.setOnTouchListener(mImplOnTouchChanceTextSizeListener);
//                }
                break;
            case EnumMsgWhat.msgWhat_handler_2:// webview loadUrl
                String javascriptUrl = (String) msg.obj;
//                loadWebViewUrl(javascriptUrl);
                break;
            case EnumMsgWhat.msgWhat_handler_4:// 设置缩放字体
//                if (mImplOnTouchChanceTextSizeListener != null) {
//                    mImplOnTouchChanceTextSizeListener.setZoomTextSize((Boolean) msg.obj);
//                    webView.setOnTouchListener(mImplOnTouchChanceTextSizeListener);
//                }
                break;
            case EnumMsgWhat.msgWhat_handler_5:// 退出登录

                break;
            case EnumMsgWhat.msgWhat_handler_6:// 设置右上角图片
//                setTopRightText((String) msg.obj);
                break;
        }
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        if (isWebViewLoadSuccess) {
            webview.callHandler("native_onPullUp", "", null);
        }
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        if (isWebViewLoadSuccess) {
            webview.callHandler("native_onPullDown", "", null);
        }
    }

    @Override
    public void onBackPressed() {
        if (webview != null) {
            if (webview.canGoBack()) {
                webview.goBack();
            } else {
                IntentUtil.finishWithOutTween(this);
            }
        }
    }

    private static String URL_KEY = "URL_KEY";
    private static String OPTIONS_KEY = "OPTIONS_KEY";//向h5传入的参数
    private static String PULL_TO_REFRESH_MODE_KEY = "PULL_TO_REFRESH_MODE_KEY";//刷新模式

    public static void startWebViewActivity(Context context, String url, String apk_sdk_path) {
//        PluginManager.getInstance().setContext(context);
//        PluginManager.getInstance().loadApk(apk_sdk_path, new PluginListener() {
//            @Override
//            public void success(Resources pluginResources, PackageInfo pluginPackageArchiveInfo) {
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtra(URL_KEY, url);
        IntentUtil.startOtherActivity(context, intent);
//            }
//
//            @Override
//            public void fail(Exception e) {
//                CommonToast.show(CrashHandler.crashToString(e));
//            }
//        });


    }
}
