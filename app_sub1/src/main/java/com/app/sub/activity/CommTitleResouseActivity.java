package com.app.sub.activity;

import android.os.Bundle;

import com.app.sub.R;
import com.common.business.code.activity.BasePresenter;
import com.common.business.code.activity.CommonTitleActivity;
import com.common.business.code.lifecyle.BaseViewModel;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModel;

/**
 * @author：atar
 * @date: 2020/11/5
 * @description:
 */
public class CommTitleResouseActivity<VM extends BaseViewModel, P extends BasePresenter> extends CommonTitleActivity<VM, P> {

    @Override
    protected int getTitleBarResID() {
        return R.layout.activity_common_title;
    }

    @Override
    public void initControl(@Nullable Bundle savedInstanceState) {
        super.initControl(savedInstanceState);
        setTitleBarUI(R.id.txt_activity_common_host_title, R.id.img_back);
    }

    @Override
    protected P createPresenter() {
        return null;
    }

    @Override
    protected Class getModelClass() {
        return null;
    }
}
