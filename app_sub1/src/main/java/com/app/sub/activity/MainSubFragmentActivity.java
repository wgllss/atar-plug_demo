package com.app.sub.activity;

import android.os.Bundle;

import com.app.sub.R;
import com.app.sub.fragments.SubFragment;
import com.common.business.code.activity.CommonTitleActivity;

public class MainSubFragmentActivity extends CommTitleResouseActivity {

    @Override
    public void initControl(Bundle savedInstanceState) {
        super.initControl(savedInstanceState);
        addContentLayout(R.layout.activity_main_sub_fragment);
        setActivityTitle("插件中使用fragment");

        setFragment(R.id.fragment, SubFragment.newIntenes());
    }
}
