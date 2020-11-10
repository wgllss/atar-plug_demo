package com.app.sub.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.app.sub.R;
import com.app.sub.presenter.TestPresenter;
import com.app.sub.viewmodels.TestViewModel;
import com.common.business.code.utils.IntentUtil;
import com.common.framework.utils.ZzLog;



public class MainActivity extends CommTitleResouseActivity<TestViewModel, TestPresenter> {

    @Override
    public void initControl(Bundle savedInstanceState) {
        super.initControl(savedInstanceState);
        addContentLayout(R.layout.activity_main_sub);
        setActivityTitle("插件子页1");

        viewModel.testString.setValue("我是插件页中间的内容大发大地方  打打飞大发发的安抚");

        findViewById(R.id.txt_onclick).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentUtil.startOtherActivity(thisContext, new Intent(thisContext, MainSub2Activity.class));
                ZzLog.e("txt_onclick --");
            }
        });
    }

    @Override
    protected Class getModelClass() {
        return TestViewModel.class;
    }

    @Override
    public void onStart() {
        super.onStart();
        ZzLog.e("onStart()");
    }

    @Override
    public void onOpenDrawerComplete() {
        super.onOpenDrawerComplete();
        ZzLog.e("onOpenDrawerComplete()");
    }

    public class ClickAction {

        public void test(View v) {
            IntentUtil.startOtherActivity(thisContext, new Intent(thisContext, MainSub2Activity.class));
            ZzLog.e("txt_onclick --");
        }
    }
}
