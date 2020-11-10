package com.common.framework.utils;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;

import androidx.annotation.ColorInt;
import androidx.core.graphics.ColorUtils;
import androidx.core.view.ViewCompat;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

/**
 * ****************************************************************************************************************************************************************************
 *
 * @author :Atar
 * @createTime:2018/5/17-10:27
 * @version:1.0.0
 * @modifyTime:
 * @modifyAuthor:
 * @description: 设置透明状态栏工具
 * ***************************************************************************************************************************************************************************
 **/
public class StatusBarUtils {

    public static void translucentStatusBar(Activity activity, ViewGroup toolbar) {
        try {
            if (activity == null) {
                return;
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = activity.getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.setStatusBarColor(Color.TRANSPARENT);

                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
                ViewGroup mContentView = (ViewGroup) window.findViewById(Window.ID_ANDROID_CONTENT);
                View mChildView = mContentView.getChildAt(0);
                if (mChildView != null) {
                    ViewCompat.setFitsSystemWindows(mChildView, false);
                    ViewCompat.requestApplyInsets(mChildView);
                }
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                // 透明状态栏
                activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && toolbar != null) {
                int statusBarHeight = ScreenUtils.getStatusBarHeight(activity);
                int layoutHeight = (statusBarHeight > 0 ? statusBarHeight : 36) + (int) ScreenUtils.getIntToDip(45);
                ViewGroup.LayoutParams lp = toolbar.getLayoutParams();
                lp.height = layoutHeight;
                toolbar.setPadding(0, statusBarHeight, 0, 0);
            }
        } catch (Exception e) {

        }
    }


    /**
     * Android 6.0 以上设置状态栏颜色
     */
    public static void setStatusBar(Activity activity, @ColorInt int color) {
        try {
            if (activity == null) {
                return;
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                Window window = activity.getWindow();

                // 设置状态栏底色颜色
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.setStatusBarColor(color);

                // 如果亮色，设置状态栏文字为黑色
                if (isLightColor(color)) {
                    window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                } else {
                    window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
                }
            }
        } catch (Exception e) {

        }
    }

    /**
     * 判断颜色是不是亮色
     *
     * @param color
     * @return
     * @from https://stackoverflow.com/questions/24260853/check-if-color-is-dark-or-light-in-android
     */
    private static boolean isLightColor(@ColorInt int color) {
        return ColorUtils.calculateLuminance(color) >= 0.5;
    }

    /**
     * 获取StatusBar颜色，默认白色
     *
     * @return
     */
    protected @ColorInt
    int getStatusBarColor() {
        return Color.WHITE;
    }

    public static void setAndroidNativeLightStatusBar(Activity activity, boolean dark) {
        View decor = activity.getWindow().getDecorView();
        if (dark) {
            decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        } else {
            decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        }
    }
}
