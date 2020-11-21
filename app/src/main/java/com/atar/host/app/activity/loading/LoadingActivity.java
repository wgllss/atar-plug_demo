package com.atar.host.app.activity.loading;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.atar.host.app.BuildConfig;
import com.atar.host.app.R;
import com.atar.host.app.activity.Main2Activity;
import com.atar.host.app.adapter.PagerAdAdapter;
import com.atar.host.app.aplication.AtarApplication;
import com.atar.host.app.configs.AppConfigUtils;
import com.atar.host.app.configs.Config;
import com.atar.host.app.services.DownLoadSevice;
import com.common.business.code.activity.BaseActivity;
import com.common.business.code.utils.IntentUtil;
import com.common.framework.appconfig.AppConfigModel;
import com.common.framework.interfaces.TimerListener;
import com.common.framework.skin.SkinResourcesManager;
import com.common.framework.utils.ServiceUtil;
import com.common.framework.utils.TimerUtils;
import com.common.framework.utils.ZzLog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import androidx.viewpager.widget.ViewPager;

public class LoadingActivity extends BaseActivity implements ViewPager.OnPageChangeListener, PagerAdAdapter.OnItemClickListener {

    private String loadImage_Version = BuildConfig.VERSION_NAME;

    private TextView txt_time;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        AppConfigUtils.getOffineFilePath(getAssets());
        AppConfigUtils.getServerTextJson(this, Config.config_file_url);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected boolean isloadSkin() {
        return false;
    }

    @Override
    protected void initControl(Bundle savedInstanceState) {
        setContentView(R.layout.activity_loading);
        txt_time = findViewById(R.id.txt_time);
    }

    @Override
    protected void bindEvent() {

    }

    @Override
    protected void initValue() {
        setOnDrawerBackEnabled(false);
        String versionName = BuildConfig.VERSION_NAME;
        loadImage_Version = AppConfigModel.getInstance().getString(Config.SAVE_LOAD_IMAGE_VERSION_KEY, loadImage_Version);// 获取引导版本
        if (versionName.compareToIgnoreCase(loadImage_Version) > 0) {
            finishThisDey(3);
        } else {
            if (AppConfigModel.getInstance().getBoolean(LoadingActivity.class.getSimpleName() + loadImage_Version, true)) {
                String loadingImageContent = AppConfigModel.getInstance().getString(Config.SAVE_LOAD_IMAGE_CONTENT_KEY, "");
                ZzLog.e("2:" + loadImage_Version);
                if (TextUtils.isEmpty(loadingImageContent)) {
                    loadingImageContent = "[\n" +
                            "            \"http://up.deskcity.org/pic_source/18/94/3c/18943c1580096f016fde111f8ab6d124.jpg\",\n" +
                            "            \"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1605777774692&di=83a281d94ad50b6e24ceb520ddd2c148&imgtype=0&src=http%3A%2F%2Fg.hiphotos.baidu.com%2Fzhidao%2Fpic%2Fitem%2F9345d688d43f87941a6570a2d71b0ef41ad53aa7.jpg\"\n" +
                            "        ]";
                }
                try {
                    Gson gson = new Gson();
                    List<String> list = gson.fromJson(loadingImageContent, new TypeToken<List<String>>() {
                    }.getType());
                    ViewPager viewpager = findViewById(R.id.viewpager);
                    viewpager.addOnPageChangeListener(this);
                    PagerAdAdapter pagerAdAdapter = new PagerAdAdapter(list);
                    pagerAdAdapter.setOnItemClickListener(this);
                    viewpager.setAdapter(pagerAdAdapter);

                    AppConfigModel.getInstance().putBoolean(LoadingActivity.class.getSimpleName() + loadImage_Version, false, true);
                } catch (Exception e) {
                    finishThisDey(3);
                }
            } else {
                finishThisDey(3);
            }
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onItemClisk(int position) {
        IntentUtil.startOtherActivity(this, new Intent(this, Main2Activity.class));
        IntentUtil.finishWhthoutTween(this);
    }


    //倒计时几秒后跳转
    private void finishThisDey(int time) {
        getWindow().getDecorView().setBackground(getResources().getDrawable(R.drawable.welcome));
        txt_time.setVisibility(View.VISIBLE);
        TimerUtils.CountDown(compositeDisposable, time, new TimerListener() {
            @Override
            public void onNext(String num) {
                txt_time.setText(num + "s后调转");
            }

            @Override
            public void onComplete() {
                IntentUtil.startOtherActivity(LoadingActivity.this, new Intent(LoadingActivity.this, Main2Activity.class));
                IntentUtil.finishWhthoutTween(LoadingActivity.this);
            }
        });
    }

    @Override
    public void onBackPressed() {
    }
}
