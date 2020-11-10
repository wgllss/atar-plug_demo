/**
 *
 */
package com.atar.host.app.activity.web;

import android.annotation.SuppressLint;
import android.os.Environment;
import android.webkit.WebView;

import com.atar.host.app.R;
import com.common.framework.Threadpool.ThreadPoolTool;
import com.common.framework.http.HttpRequest;
import com.common.framework.interfaces.HandlerListener;
import com.common.framework.utils.FileUtils;
import com.common.framework.utils.MDPassword;
import com.common.framework.utils.StringUtils;
import com.common.framework.utils.ZzLog;
import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.google.gson.Gson;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * ****************************************************************************************************************************************************************************
 * 动态webview 处理工具
 *
 * @author :Atar
 * @createTime:2017-6-28下午3:28:37
 * @version:1.0.0
 * @modifyTime:
 * @modifyAuthor:
 * @description: ****************************************************************************************************************************************************************************
 */
public class DynamicHtmlUtils {
    private static String TAG = DynamicHtmlUtils.class.getSimpleName();
    private static String SAVE_HTML_PATH = Environment.getExternalStorageDirectory() + "/" + "" +
            ".android_html/.cache/.kmkmjknmjkn/";
    /**
     * 存储文件状态，让每启动一次app 保存html文件一次，删除上次启动保存的文件
     */
    public static Map<String, String> map = new HashMap<String, String>();

    /**
     * 初始化动态webview 所有加载项
     *
     * @param activity
     * @param onHandlerDataListener
     * @param mWebView
     * @param options
     * @param mode
     * @param url
     * @return
     * @author :Atar
     * @createTime:2017-7-28上午11:39:05
     * @version:1.0.0
     * @modifyTime:
     * @modifyAuthor:
     * @description:
     */
    public static String getInitValue(WebViewActivity activity, HandlerListener onHandlerDataListener, BridgeWebView mWebView, String options, String mode, String
            url) {
        String optionsJson = "";
        /* 向html传入初始参数 start */
        try {
            Map<String, String> optionsMap = new HashMap<String, String>();
//            optionsMap.put("skinType", Integer.toString(skinType));
//            String isLoadImage = AppConfigSetting.getInstance().getBoolean(GlobeSettings.SHOW_IMAGE_KEY, true) ? "Y" : "N";
//            optionsMap.put("isLoadImage", isLoadImage);
            if (options != null && options.length() > 0) {
                JSONObject jsonObject = new JSONObject(options);
                if (jsonObject != null) {
                    Iterator<String> iterator = jsonObject.keys();
                    while (iterator.hasNext()) {
                        String key = (String) iterator.next();
                        String value = jsonObject.getString(key);
                        optionsMap.put(key, value);
                    }
                }
            }
            optionsJson = new Gson().toJson(optionsMap);
        } catch (Exception e) {
        }
        optionsJson = StringUtils.nullToBlank(optionsJson);
        /* 向html传入初始参数 end */

        OffineImplWebViewClient mOffineImplWebViewClient = new OffineImplWebViewClient(activity, mWebView, onHandlerDataListener);
        if (mWebView != null) {
//            mImplOnTouchChanceTextSizeListener.setWebViewLoadSuccess(false);
//            mWebView.setOnTouchListener(mImplOnTouchChanceTextSizeListener);

            /* 刷新模式 0:上下都可以刷新 1:只上面可下拉 2:只有下面可上拉 3 上下都不可以拉 默认只可以下拉刷新不能加载更多 start */
            if (activity != null) {
                if ("0".equals(mode)) {
                    activity.setEnableRefresh(true);
                    activity.setEnableLoadMore(true);
                    activity.setEnableAutoLoadMore(true);
                } else if ("1".equals(mode)) {
                    activity.setEnableRefresh(true);
                    activity.setEnableLoadMore(false);
                } else if ("2".equals(mode)) {
                    activity.setEnableRefresh(false);
                    activity.setEnableLoadMore(true);
                    activity.setEnableAutoLoadMore(true);
                } else if ("3".equals(mode)) {
                    activity.setEnableRefresh(false);
                    activity.setEnableLoadMore(false);
                } else {
                    activity.setEnableRefresh(true);
                    activity.setEnableLoadMore(false);
                }
            }
            /* 刷新模式 end */

            mOffineImplWebViewClient.initWebViewSettings(mWebView);

            mWebView.setWebViewClient(mOffineImplWebViewClient);
            mWebView.setWebChromeClient(new ImplWebChromeClient(activity));

            if (url != null && url.length() > 0) {
                // if (url.contains("")) {
                // CookieTool.synCookies(activity, url, mPullToRefreshWebView.getRefreshableView());
                // }
                ZzLog.i(TAG, "webView--url--->" + url);
//                if (ZzLog.DEBUG) {// 测试的时候直接加载服务器上的
//                if (HttpRequest.IsUsableNetWork(activity)) {
                mWebView.loadUrl(url);
//                } else {
//                    loadLocalHtml(activity, mWebView, url);
//                }
//                } else {
//                loadLocalHtml(activity, mWebView, url);
//                }
//                if (!map.containsKey(url) && HttpRequest.IsUsableNetWork(activity)) {
//                    saveHtml(url);
//                    map.put(url, "1");
//                }
            }
        }
        return optionsJson;
    }

    /**
     * 加载本地html
     *
     * @param url
     * @author :Atar
     * @createTime:2017-7-27下午4:38:19
     * @version:1.0.0
     * @modifyTime:
     * @modifyAuthor:
     * @description:
     */
    public static void loadLocalHtml(WebViewActivity activity, WebView
            mWebView, String url) {
        try {
            File file = new File(SAVE_HTML_PATH);
            if (!FileUtils.exists(SAVE_HTML_PATH)) {
                FileUtils.createDir(SAVE_HTML_PATH);
            }
            String strLocalFileName = url.substring(url.lastIndexOf("/") + 1, url.length())
                    .replace(".html", "");
            File htmlFile = new File(file.getAbsolutePath(), MDPassword.getPassword32
                    (strLocalFileName));
            if (htmlFile.exists()) {
                mWebView.loadUrl("file:///" + htmlFile
                        .getAbsolutePath());
            } else {
                if (HttpRequest.IsUsableNetWork(activity)) {
                    mWebView.loadUrl(url);
                } else {
                    activity.showloading(activity.getResources().getString(R.string
                            .emobilenetuseless_msg));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 存储html文件
     *
     * @param htmlUrl
     * @author :Atar
     * @createTime:2017-7-26下午4:19:43
     * @version:1.0.0
     * @modifyTime:
     * @modifyAuthor:
     * @description:
     */
    public static void saveHtml(final String htmlUrl) {
        ThreadPoolTool.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                try {
//                    if (htmlUrl.contains(".html")) {
                    URL url = new URL(htmlUrl);
                    HttpURLConnection conn = null;
                    if ("https".equals(url.getProtocol())) {
                        trustAllHosts();
                        HttpsURLConnection https = (HttpsURLConnection) url.openConnection();
                        https.setDefaultHostnameVerifier(hnv);
                        https.setHostnameVerifier(hnv);
                        https.setDefaultSSLSocketFactory(mSSLSocketFactory);
                        https.setSSLSocketFactory(mSSLSocketFactory);
                        https.setConnectTimeout(3 * HttpRequest.connectTimeOut);
                        https.setReadTimeout(3 * HttpRequest.connectTimeOut);
                        conn = https;
                    } else {
                        conn = (HttpURLConnection) url.openConnection();
                    }
                    conn.setConnectTimeout(5000);
                    InputStream instream = conn.getInputStream();
                    if (instream != null) {

                        String result = "";
                        InputStreamReader inputreader = new InputStreamReader(instream);
                        BufferedReader buffreader = new BufferedReader(inputreader);
                        String line;
                        // 分行读取
                        while ((line = buffreader.readLine()) != null) {
                            result += "\n" + line;
                        }
                        inputreader.close();
                        instream.close();
                        buffreader.close();
                        if (result != null && result.length() > 0) {
                            File file = new File(SAVE_HTML_PATH);
                            if (!FileUtils.exists(SAVE_HTML_PATH)) {
                                FileUtils.createDir(SAVE_HTML_PATH);
                            }
                            String strLocalFileName = htmlUrl.substring(htmlUrl.lastIndexOf
                                    ("/") + 1, htmlUrl.length()).replace(".html", "");
                            File htmlFile = new File(file.getAbsolutePath(), MDPassword
                                    .getPassword32(strLocalFileName));
                            htmlFile.deleteOnExit();
                            htmlFile.createNewFile();
                            FileOutputStream outStream = new FileOutputStream(htmlFile);
                            outStream.write(result.getBytes());
                            outStream.flush();
                            outStream.close();
                        }
                    }
//                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 信任所有host
     */
    public static HostnameVerifier hnv = new HostnameVerifier() {

        @Override
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    };

    /**
     * 设置https
     *
     * @author :Atar
     * @createTime:2015-9-17下午4:57:39
     * @version:1.0.0
     * @modifyTime:
     * @modifyAuthor:
     * @description:
     */
    @SuppressLint("TrulyRandom")
    public static void trustAllHosts() {
        try {
            TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {

                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[]{};
                }

                public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }

                public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }
            }};
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, trustAllCerts, new SecureRandom());
            if (mSSLSocketFactory == null) {
                mSSLSocketFactory = sc.getSocketFactory();
            }
            HttpsURLConnection.setDefaultHostnameVerifier(hnv);
            HttpsURLConnection.setDefaultSSLSocketFactory(mSSLSocketFactory);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static SSLSocketFactory mSSLSocketFactory;
}
