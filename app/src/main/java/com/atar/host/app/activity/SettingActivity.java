package com.atar.host.app.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.CompoundButton;

import com.atar.host.app.R;
import com.common.framework.activity.CommonActivity;
import com.common.framework.appconfig.AppConfigModel;
import com.common.framework.skin.SkinMode;
import com.common.framework.stack.ActivityManager;
import com.zf.view.UISwitchButton;

public class SettingActivity extends CommTitleResouseActivity implements CompoundButton.OnCheckedChangeListener {
    public UISwitchButton common_ui_switch_button;
    @Override
    public void initControl(Bundle savedInstanceState) {
        super.initControl(savedInstanceState);
        addContentLayout(R.layout.activity_setting);
        common_ui_switch_button = findViewById(R.id.common_ui_switch_button);
    }

    @Override
    protected void initValue() {
        super.initValue();
        setActivityTitle("设置皮肤");
        if (getCurrentSkinType() == SkinMode.DAY_MODE) {
            common_ui_switch_button.setChecked(false);
        } else {
            common_ui_switch_button.setChecked(true);
        }
    }

    @Override
    protected void bindEvent() {
        super.bindEvent();
        common_ui_switch_button.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        int skinType = 0;
        if (isChecked) {
            skinType = SkinMode.NIGHT_MODE;
        } else {
            skinType = SkinMode.DAY_MODE;
        }
        AppConfigModel.getInstance().putInt(SkinMode.SKIN_MODE_KEY, skinType,true);
        for (Activity activity : ActivityManager.getActivityManager().getActivityStack()) {
            if (activity instanceof CommonActivity) {
                ((CommonActivity) activity).loadSkin(skinType);
            }
        }
    }
}
