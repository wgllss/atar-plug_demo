package com.common.framework.interfaces;

import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Bundle;

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
}
