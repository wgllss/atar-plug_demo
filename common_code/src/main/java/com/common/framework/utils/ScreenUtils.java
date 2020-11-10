package com.common.framework.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.WindowManager;

import java.lang.reflect.Field;

/**
 * ScreenUtils
 * <ul>
 * <strong>Convert between dp and sp</strong>
 * <li>{@link ScreenUtils#dpToPx(Context, float)}</li>
 * <li>{@link ScreenUtils#pxToDp(Context, float)}</li>
 * </ul>
 * 
 * @author <a href="http://www.trinea.cn" target="_blank">Trinea</a> 2014-2-14
 */
public class ScreenUtils {

	public static float dpToPx(Context context, float dp) {
		if (context == null) {
			return -1;
		}
		return dp * context.getResources().getDisplayMetrics().density;
	}

	public static float pxToDp(Context context, float px) {
		if (context == null) {
			return -1;
		}
		return px / context.getResources().getDisplayMetrics().density;
	}

	public static float dpToPxInt(Context context, float dp) {
		return (int) (dpToPx(context, dp) + 0.5f);
	}

	public static float pxToDpCeilInt(Context context, float px) {
		return (int) (pxToDp(context, px) + 0.5f);
	}

	public static float getIntToDip(float intSize) {
		if (AppBuildConfig.getApplication() == null) {
			return 0.00f;
		}
		int size = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, intSize, AppBuildConfig.getApplication().getResources().getDisplayMetrics());
		return size;
	}

	public static float getIntToDip(Context context, float dp) {
		if (context == null) {
			return 0.00f;
		}
		return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
	}

	/**
	 * 获取状态栏高度
	 * 
	 * @author :Atar
	 * @createTime:2014-7-10下午1:00:18
	 * @version:1.0.0
	 * @modifyTime:
	 * @modifyAuthor:
	 * @return
	 * @description:
	 */
	protected int getStatusHight(Activity mActivity) {
		Rect frame = new Rect();
		mActivity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
		int statusBarHeight = frame.top;
		return statusBarHeight;
	}

	/**
	 * 得到状态栏高度
	 * 
	 * @return
	 */
	public static int getStatusBarHeight(Activity act) {
		/*
		 * 方法一，荣耀3c无效 Rect frame = new Rect(); act.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame); int statusBarHeight = frame.top; return statusBarHeight;
		 */

		/*
		 * 方法二，荣耀3c无效 Rect rectgle= new Rect(); Window window= act.getWindow(); window.getDecorView().getWindowVisibleDisplayFrame(rectgle); int StatusBarHeight= rectgle.top; int contentViewTop=
		 * window.findViewById(Window.ID_ANDROID_CONTENT).getTop(); int statusBar = contentViewTop - StatusBarHeight; return statusBar;
		 */
		// 方法三，荣耀3c有效
		Class<?> c = null;
		Object obj = null;
		Field field = null;
		int x = 0, sbar = 0;
		try {
			c = Class.forName("com.android.internal.R$dimen");
			obj = c.newInstance();
			field = c.getField("status_bar_height");
			x = Integer.parseInt(field.get(obj).toString());
			sbar = act.getResources().getDimensionPixelSize(x);
			return sbar;
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return 0;
	}

	/**
	 * 获得屏幕宽度
	 *
	 * @param context
	 * @return
	 */
	public static int getScreenWidth(Context context) {
		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics outMetrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(outMetrics);
		return outMetrics.widthPixels;
	}

	/**
	 * 获得屏幕高度
	 *
	 * @param context
	 * @return
	 */
	public static int getScreenHeight(Context context) {
		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics outMetrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(outMetrics);
		return outMetrics.heightPixels;
	}

	public static int dp2px(float dipValue) {
		try {
			final float scale = AppBuildConfig.getApplication().getResources().getDisplayMetrics().density;
			return (int) (dipValue * scale + 0.5f);
		} catch (Exception e) {
			return 0;
		}
	}

	//获取底部导航栏高度
	public static int getNavigationBarHeight(Activity mActivity) {

		Resources resources = mActivity.getResources();

		int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");

		int height = resources.getDimensionPixelSize(resourceId);

		ZzLog.e("navigation bar>>>", "height:" + height);

		return height;

	}

}
