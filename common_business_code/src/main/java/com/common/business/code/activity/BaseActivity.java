package com.common.business.code.activity;

import android.os.Bundle;

import com.common.business.code.dialog.CommonLoadingView;
import com.common.business.code.utils.IntentUtil;
import com.common.framework.activity.PluginCommonActivity;
import com.common.framework.application.CrashHandler;
import com.common.framework.stack.ActivityManager;
import com.common.framework.utils.ShowLog;
import com.common.framework.utils.ZzLog;
import com.common.framework.widget.CommonToast;

/**
 * @author：atar
 * @date: 2020/10/21
 * @description:
 */
public abstract class BaseActivity extends PluginCommonActivity {
    private CommonLoadingView loading = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            init(savedInstanceState);
        } catch (Exception e) {
            ZzLog.e(e);
        }
    }

    protected void init(Bundle savedInstanceState) {
        initControl(savedInstanceState);
        bindEvent();
        initValue();
    }
    @Override
    public void onOpenDrawerComplete() {
        IntentUtil.finishWithOutTween(thisContext);
    }

    @Override
    public boolean onMoveRight() {
        return false;
    }

    protected abstract void initControl(Bundle savedInstanceState);

    protected abstract void bindEvent();

    protected abstract void initValue();

    public void showloading() {
        showloading("请稍候...");
    }

    //是否loading
    public boolean isShowloading() {
        return (null != loading && loading.isShowing());
    }

    public void showloading(String showText) {
        try {
            if (null == loading) {
                loading = new CommonLoadingView(this);
            }
            if (loading.isShowing()) {
                loading.dismiss();
            }
            loading.show(showText);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void hideLoading() {
        try {
            if (null != loading) {
                loading.dismiss();
                loading = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private long exitTime = 0;

    protected void exitApp() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            CommonToast.show("再按一次退出程序");
            exitTime = System.currentTimeMillis();
        } else {
            ActivityManager.getActivityManager().exitApplication();
        }
    }

}
