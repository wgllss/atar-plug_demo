package com.atar.host.app.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.provider.Settings;
import android.view.View;
import android.widget.TextView;

import com.atar.host.app.R;
import com.atar.host.app.activity.proxy.ProxyActivity;
import com.atar.host.app.activity.web.WebViewActivity;
import com.atar.host.app.contans.Config;
import com.atar.host.app.presenter.TestPresenter;
import com.atar.host.app.utils.AppConfigUtils;
import com.atar.host.app.viewmodels.TestViewModel;
import com.common.business.code.activity.CommonTitleActivity;
import com.common.business.code.utils.IntentUtil;
import com.common.framework.download.DownLoadFileBean;
import com.common.framework.download.DownLoadFileManager;
import com.common.framework.interfaces.HandlerListener;
import com.common.framework.plugin.PluginManager;
import com.common.framework.utils.ShowLog;

import java.io.File;

import androidx.core.app.ActivityCompat;


public class Main2Activity extends CommTitleResouseActivity<TestViewModel, TestPresenter> implements HandlerListener {

    private static String TAG = Main2Activity.class.getSimpleName();

    private final int INSTALL_PACKAGES_REQUESTCODE = 12334;
    private final int GET_UNKNOWN_APP_SOURCES = 12338;

    private TextView txt_download;


    private String url = "192.168.96.97:8080/assets/apk/down_plugin/release/app_sub1-release.apk";
//    private String url = "192.168.1.4:8080/assets/apk/down_plugin/release/app_sub1-release.apk";
//    private String url = "192.168.1.6:8080/assets/apk/down_plugin/app_sub1-debug.apk";


    @Override
    protected TestPresenter createPresenter() {
        return new TestPresenter();
    }

    @Override
    protected Class getModelClass() {
        return TestViewModel.class;
    }

    @Override
    public void initControl(Bundle savedInstanceState) {
        super.initControl(savedInstanceState);
        addContentLayout(R.layout.activity_main2);

        setActivityTitle("首页");


        txt_download = (TextView) findViewById(R.id.txt_download);

        if (!url.contains("http://")) {
            url = "http://" + url;
        }
        String strDownloadFileName = url.substring(url.lastIndexOf("/") + 1, url.length());
        String apk_sdk_path = Config.strDownloadDir + strDownloadFileName;
        findViewById(R.id.txt_to_plugin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final String className = "com.app.sub.activity.MainActivity";
                ProxyActivity.startProxyActivity(Main2Activity.this, className, apk_sdk_path);
            }
        });
        txt_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DownLoadFileManager.getInstance().downLoad(Main2Activity.this, Main2Activity.this, 1, url, 1, true, strDownloadFileName, Config.strDownloadDir);
            }
        });

        findViewById(R.id.txt_web).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String url = "file:///android_asset/html/topic5.html";
                String url = "http://192.168.96.97:8080/assets/html/topic5.html";
                WebViewActivity.startWebViewActivity(Main2Activity.this, url, apk_sdk_path);
            }
        });

        findViewById(R.id.txt_webview).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentUtil.startOtherActivity(thisContext, new Intent(thisContext, MainWebViewActivity.class));
            }
        });

        //请求安装未知应用来源的权限
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission
                .READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.REQUEST_INSTALL_PACKAGES}, INSTALL_PACKAGES_REQUESTCODE);

        DownLoadFileManager.getInstance().initTempFilePercent(1, this, url, strDownloadFileName, Config.strDownloadDir);

        AppConfigUtils.getOffineFilePath(getAssets());
    }

    @Override
    public void onHandlerData(Message msg) {
        switch (msg.what) {
            case DownLoadFileBean.DOWLOAD_FLAG_FAIL:
                ShowLog.i(TAG, msg.arg2 + ":下载失败");
                break;
            case DownLoadFileBean.DOWLOAD_FLAG_SUCCESS:
                File downloadFile = null;
                switch (msg.arg2) {
                    case 1:
                        break;
                    case 2:
                        break;
                }
                txt_download.setText("下载插件(" + 100 + "%)");
                ShowLog.i(TAG, msg.arg2 + ":下载成功");
                break;
            case DownLoadFileBean.DOWLOAD_FLAG_ING:
                int progress = (Integer) msg.obj;
                txt_download.setText("下载插件(" + progress + "%)");
                ShowLog.i(TAG, msg.arg2 + ":下载进度" + progress);
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults != null && grantResults.length == 3 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED && grantResults[2] == PackageManager.PERMISSION_GRANTED) {
//            clickFinish(null);
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                boolean b = getPackageManager().canRequestPackageInstalls();
                if (!b) {
                    //将用户引导至安装未知应用界面。
                    Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES);
                    startActivityForResult(intent, GET_UNKNOWN_APP_SOURCES);
                    return;
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        exitApp();
    }


}
