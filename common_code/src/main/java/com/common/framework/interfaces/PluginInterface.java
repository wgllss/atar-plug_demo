package com.common.framework.interfaces;

import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

/**
 * ****************************************************************************************************************************************************************************
 * 插件代理接口
 *
 * @author:Atar
 * @createTime: 2018/8/27 上午11:05
 * @version:1.0.0
 * @modifyTime:
 * @modifyAuthor:
 * @description :
 * **************************************************************************************************************************************************************************
 */
public interface PluginInterface<A extends FragmentActivity> {

    void attachContext(A context);

    void onCreate(Bundle savedInstanceState);

    void onRestart();

    void onStart();

    void onResume();

    void onPause();

    void onStop();

    void onDestroy();

    void onSaveInstanceState(Bundle outState);

    void onActivityResult(int requestCode, int resultCode, Intent data);

    void onNewIntent(Intent intent);

    Resources getResources();

    AssetManager getAssets();

    void onChangeSkin(int skinType);

    void onOpenDrawerComplete();

    boolean onKeyDown(int keyCode, KeyEvent event);

    boolean onKeyUp(int keyCode, KeyEvent event);

    void onWindowFocusChanged(boolean hasFocus);

    void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults);

    void onAttachedToWindow();

    void onDetachedFromWindow();

    boolean onTouchEvent(MotionEvent event);

    void onBackPressed();
}
