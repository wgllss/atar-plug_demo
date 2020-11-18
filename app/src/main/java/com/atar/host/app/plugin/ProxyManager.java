package com.atar.host.app.plugin;

import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.KeyEvent;

import com.atar.host.app.activity.proxy.ProxyActivity;
import com.common.framework.activity.CommonActivity;
import com.common.framework.interfaces.PluginInterface;
import com.common.framework.plugin.PluginManager;
import com.common.framework.utils.ShowLog;
import com.common.framework.utils.ZzLog;

import androidx.annotation.NonNull;


/**
 * ****************************************************************************************************************************************************************************
 * 代理管理器
 *
 * @author:Atar
 * @createTime: 2018/8/27 下午2:17
 * @version:1.0.0
 * @modifyTime:
 * @modifyAuthor:
 * @description :
 * **************************************************************************************************************************************************************************
 */
public class ProxyManager implements PluginInterface<CommonActivity> {

    private String TAG = ProxyManager.class.getSimpleName();

    private CommonActivity activity;
    private PluginInterface pluginInterface;

    public static ProxyManager getInstance(CommonActivity activity) {
        ProxyManager mProxyManager = new ProxyManager();
        mProxyManager.activity = activity;
        return mProxyManager;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        try {
            if (activity == null) {
                return;
            }
            String className = activity.getIntent().getStringExtra(PluginManager.CLASS_NAME);
            ZzLog.e("className-->" + className);
            if (PluginManager.getInstance().getPluginDexClassLoader() != null) {
                Class<?> aClass = PluginManager.getInstance().getPluginDexClassLoader().loadClass(className);
                //创建该Activity的示例
                Object newInstance = aClass.newInstance();
                //程序健壮性检查
                if (newInstance instanceof PluginInterface) {
                    pluginInterface = (PluginInterface) newInstance;
                    //将代理Activity的实例传递给三方Activity
                    pluginInterface.attachContext(activity);
                    //创建bundle用来与三方apk传输数据
                    //调用三方Activity的onCreate，
                    pluginInterface.onCreate(savedInstanceState);
                }
            }
        } catch (Exception e) {
            ShowLog.e(TAG, e);
        }
    }

    @Override
    public void attachContext(CommonActivity context) {
        if (pluginInterface != null) {
            pluginInterface.attachContext(context);
        }
    }

    @Override
    public void onRestart() {
        if (pluginInterface != null) {
            pluginInterface.onRestart();
        }
    }

    @Override
    public void onStart() {
        if (pluginInterface != null) {
            pluginInterface.onStart();
        }
    }

    @Override
    public void onResume() {
        if (pluginInterface != null) {
            pluginInterface.onResume();
        }
    }

    @Override
    public void onPause() {
        if (pluginInterface != null) {
            pluginInterface.onPause();
        }
    }

    @Override
    public void onStop() {
        if (pluginInterface != null) {
            pluginInterface.onStop();
        }
    }

    @Override
    public void onDestroy() {
        if (pluginInterface != null) {
            pluginInterface.onDestroy();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (pluginInterface != null) {
            pluginInterface.onSaveInstanceState(outState);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (pluginInterface != null) {
            pluginInterface.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onNewIntent(Intent intent) {
        if (pluginInterface != null) {
            pluginInterface.onNewIntent(intent);
        }
    }

    @Override
    public Resources getResources() {
        return PluginManager.getInstance().getPluginResources();
    }

    @Override
    public AssetManager getAssets() {
        return PluginManager.getInstance().getAssets();
    }

    public Intent getIntent(Intent intent) {
        if (activity == null) {
            return intent;
        }
        if (intent.getBooleanExtra(PluginManager.HOST_CLASS_NAME, false)) {
            return intent;
        }
        Intent newIntent = new Intent(activity, ProxyActivity.class);
        newIntent.putExtra(PluginManager.CLASS_NAME, intent.getComponent().getClassName());
        return newIntent;
    }

    @Override
    public void onChangeSkin(int skinType) {
        if (pluginInterface != null) {
            pluginInterface.onChangeSkin(skinType);
        }
    }

    @Override
    public void onOpenDrawerComplete() {
        if (pluginInterface != null) {
            pluginInterface.onOpenDrawerComplete();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return pluginInterface == null ? false : pluginInterface.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        return pluginInterface == null ? false : pluginInterface.onKeyUp(keyCode, event);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        if (pluginInterface != null) {
            pluginInterface.onWindowFocusChanged(hasFocus);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (pluginInterface != null) {
            pluginInterface.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    public void onAttachedToWindow() {
        if (pluginInterface != null) {
            pluginInterface.onAttachedToWindow();
        }
    }

    @Override
    public void onDetachedFromWindow() {
        if (pluginInterface != null) {
            pluginInterface.onDetachedFromWindow();
        }
    }
}
