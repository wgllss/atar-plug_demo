package com.atar.host.app.activity;

import android.os.Bundle;

import com.atar.host.app.R;

public class Main3Activity extends CommTitleResouseActivity {

    @Override
    public void initControl(Bundle savedInstanceState) {
        super.initControl(savedInstanceState);
        addContentLayout(R.layout.activity_main3);
        setActivityTitle("宿主页2");
    }
}
