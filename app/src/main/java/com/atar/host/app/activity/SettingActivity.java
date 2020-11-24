package com.atar.host.app.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;

import com.atar.host.app.R;
import com.atar.host.app.adapter.SkinAdapter;
import com.atar.host.app.beans.DynamicSkinBean;
import com.atar.host.app.configs.AppConfigJson;
import com.atar.host.app.configs.SkinTypeBean;
import com.common.framework.activity.CommonActivity;
import com.common.framework.appconfig.AppConfigModel;
import com.common.framework.skin.SkinMode;
import com.common.framework.skin.SkinResourcesManager;
import com.common.framework.stack.ActivityManager;
import com.common.framework.utils.ShowLog;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class SettingActivity extends CommTitleResouseActivity implements AdapterView.OnItemClickListener {
    private String TAG = SettingActivity.class.getSimpleName();

    private ListView listview;


    private List<SkinTypeBean> list = new ArrayList<SkinTypeBean>();
    private SkinAdapter mSkinAdapter = new SkinAdapter(list);

    @Override
    public void initControl(Bundle savedInstanceState) {
        super.initControl(savedInstanceState);
        addContentLayout(R.layout.activity_setting);
        listview = findViewById(R.id.listview);
    }

    @Override
    protected void initValue() {
        super.initValue();
        setActivityTitle("设置皮肤");

        String current_confing_content = SkinResourcesManager.getInstance(this).getCurrent_confing_content();
        Gson gson = new Gson();
        AppConfigJson mAppConfigJson = null;
        try {
            mAppConfigJson = gson.fromJson(current_confing_content, AppConfigJson.class);
        } catch (Exception e) {
            ShowLog.e(TAG, e);
        }

        if (mAppConfigJson == null) {
            return;
        }
        if (mAppConfigJson.getSkinconfigJson() != null && mAppConfigJson.getSkinconfigJson().getSkinTypes() != null && mAppConfigJson.getSkinconfigJson().getSkinTypes().size() > 0) {
            list.addAll(mAppConfigJson.getSkinconfigJson().getSkinTypes());
        }

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getSkinType() == getCurrentSkinType()) {
                mSkinAdapter.setCurrentPostiotn(i);
                break;
            }
        }

        listview.setAdapter(mSkinAdapter);
    }

    @Override
    protected void bindEvent() {
        super.bindEvent();
        listview.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        int skinType = list.get(position).getSkinType();
        AppConfigModel.getInstance().putInt(SkinMode.SKIN_MODE_KEY, skinType, true);
        mSkinAdapter.setCurrentPostiotn(position);
        for (Activity activity : ActivityManager.getActivityManager().getActivityStack()) {
            if (activity instanceof CommonActivity) {
                ((CommonActivity) activity).loadSkin(skinType);
            }
        }
    }

    @Override
    public void onChangeSkin(int skinType) {
        super.onChangeSkin(skinType);
        if (mSkinAdapter != null) {
            mSkinAdapter.setSkinType(skinType);
        }
    }
}
