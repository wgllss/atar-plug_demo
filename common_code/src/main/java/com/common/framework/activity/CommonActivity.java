package com.common.framework.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import com.common.framework.interfaces.OnOpenDrawerCompleteListener;
import com.common.framework.stack.ActivityManager;
import com.common.framework.utils.NoFastClickUtils;
import com.common.framework.utils.StatusBarUtils;
import com.common.framework.widget.DrawerBack;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import io.reactivex.disposables.CompositeDisposable;

/**
 * @author：atar
 * @date: 2019/5/8
 * @description:
 */
public abstract class CommonActivity extends AppCompatActivity implements OnOpenDrawerCompleteListener {

    public CompositeDisposable compositeDisposable = new CompositeDisposable();
    /* 从左向右滑动代替返回已封装 ,onCreate 实例化一个，其它只需要继承此类，不需要的调用setOnDrawerBackEnabled（）方法 */
    private DrawerBack mDrawerBack;
    private boolean isActive;// 应该程序是否在前台

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityManager.getActivityManager().pushActivity(this);
        mDrawerBack = new DrawerBack(this);
        mDrawerBack.setOnOpenDrawerCompleteListener(this);
        StatusBarUtils.translucentStatusBar(this, null);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!isActive) {
            isActive = true;
            OnRunBackground(!isActive);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (compositeDisposable != null) {
            compositeDisposable.clear();
        }
        NoFastClickUtils.clearAll();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (!isAppOnForeground()) {
            isActive = false;
            OnRunBackground(!isActive);
        }
    }

    protected void setFragment(int replaceLayoutID, Fragment f) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        f.setUserVisibleHint(true);
        ft.replace(replaceLayoutID, f);
        // ft.commit();
        ft.commitAllowingStateLoss();
    }

    /**
     * 是否显示fragment
     *
     * @param mFragment
     * @author :Atar
     * @createTime:2015-6-23上午9:56:22
     * @version:1.0.0
     * @modifyTime:
     * @modifyAuthor:
     * @description:
     */
    public void isShowFragment(Fragment mFragment, boolean isShow) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        if (mFragment != null) {
            if (isShow) {
                ft.show(mFragment);
            } else {
                ft.hide(mFragment);
            }
            ft.commitAllowingStateLoss();
        }
    }

    /**
     * 打开或者关闭从左向右滑动代替返回
     *
     * @param enable
     * @author :Atar
     * @createTime:2014-6-6上午10:10:07
     * @version:1.0.0
     * @modifyTime:
     * @modifyAuthor:
     * @description:
     */
    public void setOnDrawerBackEnabled(boolean enable) {
        if (mDrawerBack != null) {
            mDrawerBack.setOnDrawerBackEnabled(enable);
        }
    }

    public AssetManager getHostAssets() {
        return super.getAssets();
    }

    public Resources getHostResources() {
        return super.getResources();
    }

    /**
     * 监听DrawerCallbacks
     *
     * @param mOnOpenDrawerCompleteListener
     * @author :Atar
     * @createTime:2014-11-4下午8:19:55
     * @version:1.0.0
     * @modifyTime:
     * @modifyAuthor:
     * @description:
     */
    public void setOnOpenDrawerCompleteListener(OnOpenDrawerCompleteListener
                                                        mOnOpenDrawerCompleteListener) {
        if (mDrawerBack != null) {
            mDrawerBack.setOnOpenDrawerCompleteListener(mOnOpenDrawerCompleteListener);
        }
    }

    /**
     * 主动调用滑动返回
     *
     * @author :Atar
     * @createTime:2015-12-16上午10:38:16
     * @version:1.0.0
     * @modifyTime:
     * @modifyAuthor:
     * @description:
     */
    public void setBackFinishActivity() {
        if (mDrawerBack != null) {
            mDrawerBack.openDrawer();
        }
    }

    public void setOnBackFacusView(View view) {
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        setOnDrawerBackEnabled(false);
                        break;
                    case MotionEvent.ACTION_CANCEL:
                    case MotionEvent.ACTION_OUTSIDE:
                    case MotionEvent.ACTION_UP:
                        setOnDrawerBackEnabled(true);
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
    }

    /**
     * 用滑动返回
     *
     * @author :Atar
     * @createTime:2014-6-20下午4:00:42
     * @version:1.0.0
     * @modifyTime:
     * @modifyAuthor:
     * @description:
     */
    public void DrawerBackFinish() {
        if (mDrawerBack != null) {
            mDrawerBack.openDrawer();
        } else {
            finish();
        }
    }

    /**
     * 监听是否在后台运行
     *
     * @param isOnBackGround
     * @author :Atar
     * @createTime:2015-11-9下午5:23:08
     * @version:1.0.0
     * @modifyTime:
     * @modifyAuthor:
     * @description:
     */
    protected void OnRunBackground(boolean isOnBackGround) {

    }

    /**
     * 应用是否在前台
     *
     * @return
     * @author :Atar
     * @createTime:2015-11-9下午5:13:48
     * @version:1.0.0
     * @modifyTime:
     * @modifyAuthor:
     * @description:
     */
    protected boolean isAppOnForeground() {
        android.app.ActivityManager activityManager = (android.app.ActivityManager)
                getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
        String packageName = getApplicationContext().getPackageName();
        List<android.app.ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        if (appProcesses == null)
            return false;
        for (android.app.ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(packageName) && appProcess.importance ==
                    android.app.ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return true;
            }
        }
        return false;
    }

    /**
     * 抽象方法监听改变皮肤
     *
     * @param skinType
     * @author :Atar
     * @createTime:2014-8-18上午10:43:57
     * @version:1.0.0
     * @modifyTime:
     * @modifyAuthor:
     * @description:
     */
    public void ChangeSkin(int skinType) {

    }

    @Override
    public boolean onMoveRight() {
        return false;
    }
}
