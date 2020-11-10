package com.atar.host.app.activity.web;

import android.app.Activity;
import android.graphics.Rect;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;

import com.common.framework.utils.ScreenUtils;
import com.common.framework.utils.ZzLog;

/**
 * @author：atar
 * @date: 2020/6/2
 * @description:
 */
public class AndroidBug5497Workaround {
    /**
     * 适用于调整activity内的布局大小
     */

    public static void assistActivity(Activity activity) {
        new AndroidBug5497Workaround(activity);
    }

    /**
     * 适用于调整popuwindow/dialog布局大小
     *
     * @param activity
     * @param contentView
     */
    public static void assistActivity(Activity activity, ViewGroup contentView) {
        new AndroidBug5497Workaround(activity, contentView);
    }

    private Activity activity;
    private View mChildOfContent;
    private int usableHeightPrevious;
    private ViewGroup.LayoutParams frameLayoutParams;
    private boolean isRootContentView = false;

    //适用于调整activity内的布局大小
    private AndroidBug5497Workaround(Activity activity) {
        this.activity = activity;
        isRootContentView = false;
        FrameLayout content = (FrameLayout) activity.findViewById(android.R.id.content);

        //获取当前界面的根布局
        mChildOfContent = content.getChildAt(0);
        if (mChildOfContent == null) {
            mChildOfContent = (FrameLayout) activity.findViewById(android.R.id.content);

        }
        //在根布局增加布局变化的监听
        mChildOfContent.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            public void onGlobalLayout() {
                possiblyResizeChildOfContent();
            }
        });

        frameLayoutParams = (ViewGroup.LayoutParams) mChildOfContent.getLayoutParams();
    }

    //适用于调整popuwindow/dialog布局大小
    private AndroidBug5497Workaround(Activity activity, ViewGroup contentView) {
        this.activity = activity;
        isRootContentView = true;
        //获取当前界面的根布局
        mChildOfContent = contentView;
        //在根布局增加布局变化的监听
        mChildOfContent.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            public void onGlobalLayout() {

                possiblyResizeChildOfContent();


            }
        });

        frameLayoutParams = (ViewGroup.LayoutParams) mChildOfContent.getLayoutParams();
    }

    private void possiblyResizeChildOfContent() {
        int navigationbarHeight = 0;
        if ("LANDI".equals(Build.BRAND)) {//联迪设备

        } else {
            navigationbarHeight = ScreenUtils.getNavigationBarHeight(activity);
        }
        //可见的高度
        int usableHeightNow = computeUsableHeight() - navigationbarHeight;

        if (usableHeightNow != usableHeightPrevious) {

            //根布局的高度
            int rootViewHeight = mChildOfContent.getRootView().getHeight() - navigationbarHeight;

            //这个判断是为了解决19之前的版本不支持沉浸式状态栏导致布局显示不完全的问题
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
                Rect frame = new Rect();
                activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
                int statusBarHeight = frame.top;
                rootViewHeight -= statusBarHeight;
            }
            int heightDifference = Math.abs(rootViewHeight - usableHeightNow);
            //当前可见区域大小不足根布局大小的5/6时，认为是软键盘弹出
            if (heightDifference > (rootViewHeight / 6)) {
//                Log.e("min77","computeUsableHeight heightDifference > rootViewHeight / 6");
                // keyboard probably just became visible
                frameLayoutParams.height = usableHeightNow;
            } else {
                // keyboard probably just became hidden
                frameLayoutParams.height = rootViewHeight;

            }

            //更新布局大小
            mChildOfContent.setLayoutParams(frameLayoutParams);
            mChildOfContent.requestLayout();

            ZzLog.i("min77", "computeUsableHeight 刷新 rootViewHeight = " + rootViewHeight + " ,frameLayoutParams.height = " + frameLayoutParams.height + " , usableHeightPrevious = " + usableHeightPrevious);
            usableHeightPrevious = usableHeightNow;
        }
    }

    /**
     * 计算当前界面可见的高度
     */
    private int computeUsableHeight() {
        Rect frame = new Rect();
        mChildOfContent.getWindowVisibleDisplayFrame(frame);
        ZzLog.i("min77", "computeUsableHeight  = " + (frame.bottom - frame.top));
        return (frame.bottom - frame.top);
    }
}
