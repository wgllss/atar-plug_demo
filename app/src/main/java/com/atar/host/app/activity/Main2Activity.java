package com.atar.host.app.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.provider.Settings;

import com.atar.host.app.R;
import com.atar.host.app.activity.proxy.ProxyActivity;
import com.atar.host.app.activity.web.DynamicWebviewActivity;
import com.atar.host.app.adapter.HostMenuAdapter;
import com.atar.host.app.beans.MenuItemBean;
import com.atar.host.app.configs.Config;
import com.atar.host.app.presenter.TestPresenter;
import com.atar.host.app.configs.AppConfigUtils;
import com.atar.host.app.viewmodels.TestViewModel;
import com.common.business.code.utils.IntentUtil;
import com.common.framework.appconfig.AppConfigModel;
import com.common.framework.download.DownLoadFileBean;
import com.common.framework.download.DownLoadFileManager;
import com.common.framework.interfaces.HandlerListener;
import com.common.framework.utils.ShowLog;

import java.util.ArrayList;
import java.util.List;

import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class Main2Activity extends CommTitleResouseActivity<TestViewModel, TestPresenter> implements HandlerListener, HostMenuAdapter.OnItemClickListener {

    private static String TAG = Main2Activity.class.getSimpleName();

    private final int INSTALL_PACKAGES_REQUESTCODE = 12334;
    private final int GET_UNKNOWN_APP_SOURCES = 12338;

    private String ip = "192.168.96.84";
    private String url;

    private String strDownloadFileName;
    private String apk_sdk_path;

    private RecyclerView recyclerview;
    private List<MenuItemBean> list = new ArrayList<MenuItemBean>();
    private HostMenuAdapter mHostMenuAdapter = new HostMenuAdapter(list);

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
        setOnDrawerBackEnabled(false);
    }

    @Override
    protected void initValue() {
        super.initValue();
        setActivityTitle("首页");
        recyclerview = findViewById(R.id.recyclerview);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));




        list.add(new MenuItemBean(0, "到插件内"));
        list.add(new MenuItemBean(1, "BridgeWebView 加载 在线html 离线js 样式 图片"));
        list.add(new MenuItemBean(2, "WebView 加载 在线 html"));
        list.add(new MenuItemBean(3, "下载插件"));
        list.add(new MenuItemBean(5, "设置服务器ip地址"));
        list.add(new MenuItemBean(6, "设置"));

        recyclerview.setAdapter(mHostMenuAdapter);
        mHostMenuAdapter.setListener(this);

        setIP();

        //请求安装未知应用来源的权限
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission
                .READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.REQUEST_INSTALL_PACKAGES}, INSTALL_PACKAGES_REQUESTCODE);
        DownLoadFileManager.getInstance().initTempFilePercent(1, this, url, strDownloadFileName, Config.strDownloadDir);
        AppConfigUtils.getOffineFilePath(getAssets());
    }

    @Override
    public void onItemClick(MenuItemBean info) {
        switch (info.getItemID()) {
            case 0:
                setIP();
                String className = "com.app.sub.activity.MainActivity";
//                IntentUtil.startOtherActivity(thisContext, new Intent(thisContext, ProxyActivity.class));
                ProxyActivity.startProxyActivity(Main2Activity.this, className, apk_sdk_path);
                break;
            case 1:
                setIP();
                String url = "http://" + ip + ":8080/assets/html/topic5.html";
                DynamicWebviewActivity.startWebViewActivity(Main2Activity.this, url, apk_sdk_path);
                break;
            case 2:
                IntentUtil.startOtherActivity(thisContext, new Intent(thisContext, MainWebViewActivity.class));
                break;
            case 3:
                setIP();
                DownLoadFileManager.getInstance().downLoad(Main2Activity.this, Main2Activity.this, 1, this.url, 2, true, strDownloadFileName, Config.strDownloadDir);
                break;
            case 5:
                IntentUtil.startOtherActivity(thisContext, new Intent(thisContext, SetHostIPAddressActivity.class));
                break;
            case 6:
                IntentUtil.startOtherActivity(thisContext, new Intent(thisContext, SettingActivity.class));
                break;
        }
    }

    @Override
    public void onHandlerData(Message msg) {
        switch (msg.what) {
            case DownLoadFileBean.DOWLOAD_FLAG_FAIL:
                ShowLog.i(TAG, msg.arg2 + ":下载失败");
                break;
            case DownLoadFileBean.DOWLOAD_FLAG_SUCCESS:
                list.get(3).setItemName("下载插件(" + 100 + "%)");
                mHostMenuAdapter.refreshItem(3, list.get(3), recyclerview, (LinearLayoutManager) recyclerview.getLayoutManager());
                ShowLog.i(TAG, msg.arg2 + ":下载成功");
                break;
            case DownLoadFileBean.DOWLOAD_FLAG_ING:
                int progress = (Integer) msg.obj;
                list.get(3).setItemName("下载插件(" + progress + "%)");
                mHostMenuAdapter.refreshItem(3, list.get(3), recyclerview, (LinearLayoutManager) recyclerview.getLayoutManager());
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

    public void setIP() {
        ip = AppConfigModel.getInstance().getString(Config.SAVE_HOST_IP_KEY, "");
        url = ip + ":8080/assets/apk/down_plugin/release/app_sub1-release.apk";
        if (!url.contains("http://")) {
            url = "http://" + url;
        }
        strDownloadFileName = url.substring(url.lastIndexOf("/") + 1, url.length());
        apk_sdk_path = Config.strDownloadDir + strDownloadFileName;
    }

    @Override
    public void onBackPressed() {
        exitApp();
    }
}
