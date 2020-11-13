package com.app.sub.activity;

import android.os.Bundle;

import com.app.sub.R;
import com.common.business.code.activity.BasePresenter;
import com.common.business.code.activity.BaseViewModelActivity;
import com.common.framework.activity.PluginCommonActivity;

public class MainSub4Activity extends BaseViewModelActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_sub4);
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected Class getModelClass() {
        return null;
    }

    @Override
    protected void initControl(Bundle savedInstanceState) {

    }

    @Override
    protected void initValue() {

    }
}
