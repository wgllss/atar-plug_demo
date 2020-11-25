package com.atar.host.app.activity.proxy;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.KeyEvent;

import com.atar.host.app.plugin.ProxyManager;
import com.common.business.code.activity.BaseActivity;
import com.common.business.code.utils.IntentUtil;
import com.common.framework.activity.CommonActivity;
import com.common.framework.plugin.PluginListener;
import com.common.framework.plugin.PluginManager;
import com.common.framework.utils.ZzLog;
import com.common.framework.widget.CommonToast;

import androidx.annotation.NonNull;

public class ProxyActivity extends CommonActivity {

    private static final String TAG = ProxyActivity.class.getSimpleName();

    public static boolean isLoadApk = true;
    private ProxyManager mProxyManager = ProxyManager.getInstance(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (isLoadApk) {
            mProxyManager.onCreate(savedInstanceState);
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (mProxyManager != null) {
            mProxyManager.onRestart();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mProxyManager != null) {
            mProxyManager.onStart();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mProxyManager != null) {
            mProxyManager.onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mProxyManager != null) {
            mProxyManager.onPause();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mProxyManager != null) {
            mProxyManager.onStop();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mProxyManager != null) {
            mProxyManager.onDestroy();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mProxyManager != null) {
            mProxyManager.onSaveInstanceState(outState);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (mProxyManager != null) {
            mProxyManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (mProxyManager != null) {
            mProxyManager.onNewIntent(intent);
        }
    }

    @Override
    public Resources getResources() {
        if (mProxyManager != null) {
            return mProxyManager.getResources();
        } else {
            return super.getResources();
        }
    }

    @Override
    public AssetManager getAssets() {
        return mProxyManager == null ? super.getAssets() : mProxyManager.getAssets();
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        if (mProxyManager != null) {
            super.startActivityForResult(mProxyManager.getIntent(intent), requestCode);
        } else {
            super.startActivityForResult(intent, requestCode);
        }
    }

    @Override
    public void onChangeSkin(int skinType) {
        super.onChangeSkin(skinType);
        if (mProxyManager != null) {
            mProxyManager.onChangeSkin(skinType);
        }
    }

    @Override
    public void onOpenDrawerComplete() {
        if (mProxyManager != null) {
            mProxyManager.onOpenDrawerComplete();
        } else {
            IntentUtil.finish(this);
        }
    }

    @Override
    public void onBackPressed() {
        if (mProxyManager != null) {
            mProxyManager.onBackPressed();
        }
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (mProxyManager != null) {
            mProxyManager.onKeyUp(keyCode, event);
        }
        return super.onKeyUp(keyCode, event);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (mProxyManager != null) {
            mProxyManager.onKeyDown(keyCode, event);
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (mProxyManager != null) {
            mProxyManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (mProxyManager != null) {
            mProxyManager.onWindowFocusChanged(hasFocus);
        }
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mProxyManager != null) {
            mProxyManager.onDetachedFromWindow();
        }
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (mProxyManager != null) {
            mProxyManager.onAttachedToWindow();
        }
    }

    public static void startProxyActivity(final BaseActivity context, String optionJson, final String className, String apk_sdk_path) {
        if (isLoadApk) {
            context.showloading();
            PluginManager.getInstance().setContext(context);
            PluginManager.getInstance().loadApk(apk_sdk_path, new PluginListener() {
                @Override
                public void success(Resources pluginResources, PackageInfo pluginPackageArchiveInfo) {
                    context.hideLoading();
                    if (PluginManager.getInstance().exists(className)) {
                        Intent intent = new Intent(context, ProxyActivity.class);
                        intent.putExtra(PluginManager.CLASS_NAME, className);
                        IntentUtil.startOtherActivity(context, intent);
                    }
                }

                @Override
                public void fail(Exception e) {
                    ZzLog.e(e);
                    context.hideLoading();
                    CommonToast.show(e.getMessage());
                }
            });
        } else {
            try {
                Intent intent = new Intent(context, Class.forName(className));
                IntentUtil.startOtherActivity(context, intent);
            } catch (Exception e) {
            }
        }
    }
}
