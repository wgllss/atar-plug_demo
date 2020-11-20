package com.app.sub.activity;

import android.os.Bundle;

import com.app.sub.R;
import com.common.business.code.activity.BasePresenter;
import com.common.business.code.activity.CommonTitleActivity;
import com.common.business.code.lifecyle.BaseViewModel;
import com.common.framework.skin.SkinUtils;

import androidx.annotation.Nullable;

/**
 * @author：atar
 * @date: 2020/11/5
 * @description:
 */
public class CommTitleResouseActivity<VM extends BaseViewModel, P extends BasePresenter> extends CommonTitleActivity<VM, P> {

    @Override
    protected int getTitleBarResID() {
        return R.layout.activity_common_title;
    }

    @Override
    protected int getLoadingResID() {
        return R.layout.common_loading;
    }

    @Override
    public void initControl(@Nullable Bundle savedInstanceState) {
        super.initControl(savedInstanceState);
        setTitleBarUI(R.id.txt_activity_common_host_title, R.id.img_back, R.id.layout_activity_title_bar);
    }

    @Override
    protected P createPresenter() {
        return null;
    }

    @Override
    protected Class getModelClass() {
        return null;
    }

    @Override
    public void onChangeSkin(int skinType) {
        super.onChangeSkin(skinType);
        SkinUtils.setBackgroundColor(this, R.string.common_tab_bg_color_array_name, skinType, layout_activity_title_bar);
        SkinUtils.setTextColor(this, R.string.common_activity_title_color_array_name, skinType, txt_activity_title);
        SkinUtils.setImageDrawable(this, R.string.common_top_title_bar_back_drawable_array_name, skinType, img_back);

        SkinUtils.setBackgroundColor(this, R.string.background_color_name, commonContentBg);
    }
}
