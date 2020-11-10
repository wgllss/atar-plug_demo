package com.common.framework.application;

import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.StrictMode;

/**
 * @author：atar
 * @date: 2019/5/8
 * @description:
 */
public class CommonApplication {
    protected static final boolean DEVELOPER_MODE = false;
    protected static Application mInstance;

    public static Context getContext() {
        return mInstance;
    }

    public static void initApplication(Application mApplication) {
        CommonApplication.mInstance = mApplication;
    }
}
