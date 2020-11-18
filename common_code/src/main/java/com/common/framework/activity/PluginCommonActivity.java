package com.common.framework.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.common.framework.interfaces.OnOpenDrawerCompleteListener;
import com.common.framework.interfaces.PluginInterface;
import com.common.framework.utils.ZzLog;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;

/**
 * @author：atar
 * @date: 2020/10/21
 * @description:
 */
public class PluginCommonActivity extends CommonActivity implements PluginInterface<CommonActivity> {

    protected boolean isPlugin = false;
    protected CommonActivity thisContext;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        if (!isPlugin) {
            thisContext = this;
            super.onCreate(savedInstanceState);
        }
    }

    @Override
    public void attachContext(CommonActivity context) {
        isPlugin = true;
        thisContext = context;
    }

    @Override
    public void onNewIntent(Intent intent) {
        if (!isPlugin) {
            super.onNewIntent(intent);
        }
    }

    @Override
    public void onRestart() {
        if (!isPlugin) {
            super.onRestart();
        }
    }

    @Override
    public void onStart() {
        if (!isPlugin) {
            super.onStart();
        }
    }

    @Override
    public void onResume() {
        if (!isPlugin) {
            super.onResume();
        }
    }

    @Override
    public void onPause() {
        if (!isPlugin) {
            super.onPause();
        }
    }

    @Override
    public void onStop() {
        if (!isPlugin) {
            super.onStop();
        }
    }

    @Override
    public void onDestroy() {
        if (!isPlugin) {
            super.onDestroy();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (!isPlugin) {
            super.onSaveInstanceState(outState);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!isPlugin) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onChangeSkin(int skinType) {
    }

    @Override
    public void setContentView(int layoutResID) {
        if (isPlugin) {
            thisContext.setContentView(layoutResID);
        } else {
            super.setContentView(layoutResID);
        }
    }

    @Override
    public void addContentView(View view, ViewGroup.LayoutParams params) {
        if (isPlugin) {
            thisContext.addContentView(view, params);
        } else {
            super.addContentView(view, params);
        }
    }

    @Override
    public void setContentView(View view) {
        if (isPlugin) {
            thisContext.setContentView(view);
        } else {
            super.setContentView(view);
        }
    }


    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        if (isPlugin) {
            thisContext.setContentView(view, params);
        } else {
            super.setContentView(view, params);
        }
    }

    @Override
    public LayoutInflater getLayoutInflater() {
        if (isPlugin) {
            return thisContext.getLayoutInflater();
        } else {
            return super.getLayoutInflater();
        }
    }

    @Override
    public Window getWindow() {
        if (isPlugin) {
            return thisContext.getWindow();
        } else {
            return super.getWindow();
        }
    }

    @Override
    public <T extends View> T findViewById(@IdRes int id) {
        if (isPlugin) {
            return thisContext.findViewById(id);
        } else {
            return super.findViewById(id);
        }
    }

    @Override
    public ClassLoader getClassLoader() {
        if (isPlugin) {
            return thisContext.getClassLoader();
        } else {
            return super.getClassLoader();
        }
    }

    @Override
    public String getPackageName() {
        if (isPlugin) {
            return thisContext.getPackageName();
        } else {
            return super.getPackageName();
        }
    }

    @Override
    public WindowManager getWindowManager() {
        if (isPlugin) {
            return thisContext.getWindowManager();
        } else {
            return super.getWindowManager();
        }
    }


    @Override
    public ApplicationInfo getApplicationInfo() {
        if (isPlugin) {
            return thisContext.getApplicationInfo();
        } else {
            return super.getApplicationInfo();
        }
    }

    @Override
    public Object getSystemService(@NonNull String name) {
        if (isPlugin) {
            return thisContext.getSystemService(name);
        } else {
            return super.getSystemService(name);
        }
    }

    @Override
    public Context getApplicationContext() {
        if (isPlugin) {
            return thisContext.getApplicationContext();
        } else {
            return super.getApplicationContext();
        }
    }

    @Override
    public void finish() {
        if (isPlugin) {
            thisContext.finish();
        } else {
            super.finish();
        }
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (isPlugin) {
            return false;
        } else {
            return super.onTouchEvent(event);
        }
    }

    public void onBackPressed() {
        if (isPlugin) {
            thisContext.onBackPressed();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void startActivity(Intent intent) {
        if (isPlugin) {
            thisContext.startActivity(intent);
        } else {
            super.startActivity(intent);
        }
    }

    @Override
    public void onOpenDrawerComplete() {
        if (isPlugin) {
            thisContext.onOpenDrawerComplete();
        }
    }

    @Override
    public void setBackFinishActivity() {
        if (isPlugin) {
            thisContext.setBackFinishActivity();
        } else {
            super.setBackFinishActivity();
        }
    }

    @Override
    public AssetManager getAssets() {
        if (isPlugin) {
            return thisContext.getAssets();
        } else {
            return super.getAssets();
        }
    }

    @Override
    public Resources getResources() {
        if (isPlugin) {
            return thisContext.getResources();
        } else {
            return super.getResources();
        }
    }

    @Override
    public FragmentManager getSupportFragmentManager() {
        if (isPlugin) {
            return thisContext.getSupportFragmentManager();
        } else {
            return super.getSupportFragmentManager();
        }
    }

    @Override
    public void setOnDrawerBackEnabled(boolean enable) {
        if (isPlugin) {
            thisContext.setOnDrawerBackEnabled(enable);
        } else {
            super.setOnDrawerBackEnabled(enable);
        }
    }

    @Override
    public boolean onMoveRight() {
        if (isPlugin) {
            return thisContext.onMoveRight();
        }
        return false;
    }

    @Override
    public void setOnOpenDrawerCompleteListener(OnOpenDrawerCompleteListener mOnOpenDrawerCompleteListener) {
        if (isPlugin) {
            thisContext.setOnOpenDrawerCompleteListener(mOnOpenDrawerCompleteListener);
        } else {
            setOnOpenDrawerCompleteListener(mOnOpenDrawerCompleteListener);
        }
    }

    @Override
    public void setOnBackFacusView(View view) {
        if (isPlugin) {
            thisContext.setOnBackFacusView(view);
        } else {
            super.setOnBackFacusView(view);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (isPlugin) {
            return thisContext.onKeyDown(keyCode, event);
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (isPlugin) {
            return thisContext.onKeyUp(keyCode, event);
        } else {
            return super.onKeyUp(keyCode, event);
        }

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        if (isPlugin) {
            thisContext.onWindowFocusChanged(hasFocus);
        } else {
            super.onWindowFocusChanged(hasFocus);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (isPlugin) {
            thisContext.onRequestPermissionsResult(requestCode, permissions, grantResults);
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    public void onAttachedToWindow() {
        if (isPlugin) {
            thisContext.onDetachedFromWindow();
        } else {
            super.onDetachedFromWindow();
        }
    }

    @Override
    public void onDetachedFromWindow() {
        if (isPlugin) {
            thisContext.onDetachedFromWindow();
        } else {
            super.onDetachedFromWindow();
        }
    }
}
