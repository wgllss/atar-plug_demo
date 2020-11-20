package com.atar.host.app.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.atar.host.app.R;
import com.atar.host.app.configs.Config;
import com.common.framework.appconfig.AppConfigModel;
import com.common.framework.widget.CommonToast;

public class SetHostIPAddressActivity extends CommTitleResouseActivity {

    private EditText edt_text;
    private TextView txt_save;

    @Override
    public void initControl(Bundle savedInstanceState) {
        super.initControl(savedInstanceState);
        addContentLayout(R.layout.activity_set_host_ipaddress);
        edt_text = findViewById(R.id.edt_text);
        txt_save = findViewById(R.id.txt_save);
    }

    @Override
    protected void bindEvent() {
        super.bindEvent();
        txt_save.setOnClickListener(this);
    }

    @Override
    protected void initValue() {
        super.initValue();
        setActivityTitle("修改IP地址");
        String ip = AppConfigModel.getInstance().getString(Config.SAVE_HOST_IP_KEY, "");
        edt_text.setHint(ip);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        String str = edt_text.getText().toString();
        if (TextUtils.isEmpty(str)) {
            CommonToast.show("输入为空");
        }
        AppConfigModel.getInstance().putString(Config.SAVE_HOST_IP_KEY, str, true);
        setBackFinishActivity();
    }
}
