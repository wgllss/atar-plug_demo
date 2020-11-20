package com.atar.host.app.activity.loading;

import android.app.Activity;
import android.os.Bundle;

import com.atar.host.app.R;
import com.common.framework.activity.CommonActivity;

public class WelcomeActivity extends CommonActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setOnDrawerBackEnabled(false);
    }

    @Override
    public void onOpenDrawerComplete() {

    }
}
