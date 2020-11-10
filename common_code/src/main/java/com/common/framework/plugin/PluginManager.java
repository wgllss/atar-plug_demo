package com.common.framework.plugin;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;

import com.common.framework.Threadpool.ThreadPoolTool;
import com.common.framework.common.CommonHandler;
import com.common.framework.utils.ZzLog;

import java.lang.reflect.Method;

import dalvik.system.DexClassLoader;

/**
 * ****************************************************************************************************************************************************************************
 * 插件管理
 *
 * @author:Atar
 * @createTime: 2018/8/27 上午11:10
 * @version:1.0.0
 * @modifyTime:
 * @modifyAuthor:
 * @description :
 * **************************************************************************************************************************************************************************
 */
public class PluginManager {
    private static PluginManager instance = new PluginManager();
    private Context context;
    private DexClassLoader pluginDexClassLoader;
    private Resources pluginResources;
    private PackageInfo pluginPackageArchiveInfo;

    private AssetManager mAssetManager;

    public static final String CLASS_NAME = "CLASS_NAME_KEY";
    //是否到宿主页面
    public static final String HOST_CLASS_NAME = "HOST_CLASS_NAME_KEY";

    public static PluginManager getInstance() {
        return instance;
    }

    public void setContext(Context context) {
        this.context = context.getApplicationContext();
    }

    public DexClassLoader getPluginDexClassLoader() {
        return pluginDexClassLoader;
    }

    public Resources getPluginResources() {
        return pluginResources;
    }

    public AssetManager getAssets() {
        return mAssetManager;
    }

    public void loadApk(final String apkPath, final PluginListener mPluginListener) {
        ThreadPoolTool.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                if (context == null) {
                    return;
                }
                if (pluginDexClassLoader != null && pluginResources != null && pluginPackageArchiveInfo != null) {
                    //已经加载过
                    if (mPluginListener != null) {
                        CommonHandler.getInstatnce().getHandler().post(new Runnable() {
                            @Override
                            public void run() {
                                mPluginListener.success(pluginResources, pluginPackageArchiveInfo);
                            }
                        });
                    }
                    return;
                }
                try {
                    pluginDexClassLoader = new DexClassLoader(apkPath, context.getDir("dex", Context.MODE_PRIVATE).getAbsolutePath(), null, context.getClassLoader());
                    pluginPackageArchiveInfo = context.getPackageManager().getPackageArchiveInfo(apkPath, PackageManager.GET_ACTIVITIES);

                    int flags = PackageManager.GET_META_DATA | PackageManager.GET_ACTIVITIES | PackageManager.GET_SERVICES
                            | PackageManager.GET_PROVIDERS | PackageManager.GET_RECEIVERS;

                    final PackageManager packageManager = context.getPackageManager();
                    final PackageInfo packageInfo = packageManager.getPackageArchiveInfo(apkPath, flags);
                    if (packageInfo == null) {
                        return;
                    }

                    final ApplicationInfo applicationInfo = packageInfo.applicationInfo;
                    if (applicationInfo == null) {
                        return;
                    }
                    applicationInfo.sourceDir = applicationInfo.publicSourceDir = apkPath;
                    try {
                        pluginResources = packageManager.getResourcesForApplication(applicationInfo);
                    } catch (PackageManager.NameNotFoundException e) {
                    }

                    // 以下写法是带hook写法，最好不要这样写
//                     AssetManager assets = AssetManager.class.newInstance();
//                     Method addAssetPath = AssetManager.class.getMethod("addAssetPath", String.class);
//                     addAssetPath.invoke(assets, apkPath);
//                     pluginResources = new Resources(assets, context.getResources().getDisplayMetrics(), context.getResources().getConfiguration());
                     mAssetManager = pluginResources.getAssets();

                    //不需要设置主题 坑位
                    // mTheme = pluginResources.newTheme();
                    // mTheme.setTo(context.getTheme());
                    if (mPluginListener != null) {
                        CommonHandler.getInstatnce().getHandler().post(new Runnable() {
                            @Override
                            public void run() {
                                mPluginListener.success(pluginResources, pluginPackageArchiveInfo);
                            }
                        });
                    }
                } catch (Exception e) {
                    if (mPluginListener != null) {
                        mPluginListener.fail(e);
                    }
                }
            }
        });
    }

    public boolean exists(String className) {
        int flag = 0;
        if (pluginPackageArchiveInfo != null && pluginPackageArchiveInfo.activities != null && pluginPackageArchiveInfo.activities.length > 0) {
            for (int i = 0; i < pluginPackageArchiveInfo.activities.length; i++) {
                ZzLog.e("pluginPackageArchiveInfo.activities[i].name-->" + pluginPackageArchiveInfo.activities[i].name + "--className-->" + className);
                if (className.equals(pluginPackageArchiveInfo.activities[i].name)) {
                    flag = 1;
                    break;
                }
            }
        }
        return flag == 1;
    }
}
