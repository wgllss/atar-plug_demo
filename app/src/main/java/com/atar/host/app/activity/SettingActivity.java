package com.atar.host.app.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;

import com.atar.host.app.R;
import com.atar.host.app.beans.DynamicSkinBean;
import com.common.framework.activity.CommonActivity;
import com.common.framework.appconfig.AppConfigModel;
import com.common.framework.skin.SkinMode;
import com.common.framework.stack.ActivityManager;

import java.util.ArrayList;
import java.util.List;

public class SettingActivity extends CommTitleResouseActivity implements CompoundButton.OnCheckedChangeListener {
    public CheckBox common_ui_switch_button;
    private ListView listview;

    /**
     * SD卡目录 下载 资源文件 皮肤资源
     */
    private String SD_PATH = Environment.getExternalStorageDirectory() + "/.Android/.cache/.";

    private List<DynamicSkinBean> list = new ArrayList<DynamicSkinBean>();
//    private SkinAdapter mSkinAdapter = new SkinAdapter(list);

    @Override
    public void initControl(Bundle savedInstanceState) {
        super.initControl(savedInstanceState);
        addContentLayout(R.layout.activity_setting);
        common_ui_switch_button = findViewById(R.id.common_ui_switch_button);
        listview = findViewById(R.id.listview);
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
