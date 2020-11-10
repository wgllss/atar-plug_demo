package com.common.business.code.application;

import android.annotation.TargetApi;
import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Environment;

import com.common.business.code.net.Config;
import com.common.framework.Threadpool.ThreadPoolTool;
import com.common.framework.application.CommonApplication;
import com.common.framework.application.CrashHandler;
import com.common.framework.common.CommonNetWorkExceptionToast;
import com.common.framework.network.RetrofitWrap;
import com.common.framework.utils.AppBuildConfig;
import com.common.framework.utils.ShowLog;
import com.common_business_code.BuildConfig;
import com.common_business_code.R;
import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;

import androidx.multidex.MultiDex;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * @author：atar
 * @date: 2020/10/21
 * @description:
 */
public class BaseApplication extends Application {

    public static Application application = null;
    public static boolean isLog = BuildConfig.DEBUG;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        ThreadPoolTool.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                AppBuildConfig.APPLICATION = BaseApplication.class.getName();
                RetrofitWrap.DEFAULT_HOST=Config.URL;
                RetrofitWrap.level = BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE;
                CommonApplication.initApplication(BaseApplication.this);// 初始化全局Context
//                SkinResourcesManager.getInstance(AtarApplication.this).initSkinResources(true,
//                        "com.atar.skin", UrlParamCommon.download_skin_url);
                CommonNetWorkExceptionToast.initToastError(BaseApplication.this, R.array.err_toast_string);// 初始化全局网络错误提示信息
                ShowLog.setDebug(true);// 设置不显示日志 上线前记得改成false
                CommonNetWorkExceptionToast.setIsShowErrorToast(false);// 上线前记得设置不显示错误网络具体提示 测试时可开启

                DiskCacheConfig cacheConfig = DiskCacheConfig.newBuilder(application)
                        .setBaseDirectoryName("plugin")
                        .setBaseDirectoryPath(Environment.getExternalStorageDirectory())
                        .build(); //设置磁盘缓存的配置,生成配置文件 ImagePipelineConfig config = ImagePipelineConfig.newBuilder(this) .setMainDiskCacheConfig(cacheConfig) .build();
                ImagePipelineConfig config = ImagePipelineConfig.newBuilder(application)
                        .setBitmapsConfig(Bitmap.Config.RGB_565)
                        .setDownsampleEnabled(true)                             // 对图片进行自动缩放
                        .setResizeAndRotateEnabledForNetwork(true)   // 对网络图片进行resize处理，减
                        .setMainDiskCacheConfig(cacheConfig).build();
                Fresco.initialize(application, config);

                // CommonToast.initToastResouseId(R.drawable.corners_toast, R.color.black);//
                // 初始化toast字体颜色和背景
                CrashHandler.getInstance().init(BaseApplication.this);// 接收错误异常
            }
        });
    }
}
