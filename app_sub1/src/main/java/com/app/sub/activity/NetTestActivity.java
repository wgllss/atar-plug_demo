package com.app.sub.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.app.sub.R;
import com.app.sub.beans.MediaData;
import com.app.sub.presenter.TestPresenter;
import com.app.sub.viewmodels.TestViewModel;
import com.common.framework.utils.GsonUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

/**
 * @author：atar
 * @date: 2020/11/7
 * @description:
 */
public class NetTestActivity extends CommTitleResouseActivity<TestViewModel, TestPresenter> {

    private TextView txt_result;
    private TextView txt_click_net;
    private SimpleDraweeView imgView;

    @Override
    protected TestPresenter createPresenter() {
        return new TestPresenter();
    }

    @Override
    protected Class getModelClass() {
        return TestViewModel.class;
    }

    @Override
    public void initControl(@Nullable Bundle savedInstanceState) {
        super.initControl(savedInstanceState);
        addContentLayout(R.layout.activity_net_test);

        setActivityTitle("网络请求");

        txt_click_net = findViewById(R.id.txt_click_net);
        txt_result = findViewById(R.id.txt_result);
        imgView = findViewById(R.id.imgView);

        findViewById(R.id.txt_click_net).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresneter.getposmedialistwithoutlogin(thisContext);
            }
        });

        viewModel.mediaData.observe(thisContext, new Observer<MediaData>() {
            @Override
            public void onChanged(MediaData mediaData) {
                if (mediaData != null) {
                    txt_result.setText("返回数据:\n" + GsonUtils.beanToJson(mediaData));
                    imgView.setImageURI(mediaData.getTenantlogo());
                }
            }
        });

        viewModel.error.observe(thisContext, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (!TextUtils.isEmpty(s)) {
                    txt_result.setText("返回数据:\n" + s);
                }
            }
        });

        MediaData mediaData = new MediaData();
        mediaData.setTenantlogo("909090");
        viewModel.mediaData.setValue(mediaData);
    }
}
