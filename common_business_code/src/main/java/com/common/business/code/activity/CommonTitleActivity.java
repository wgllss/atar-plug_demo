package com.common.business.code.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.common.business.code.lifecyle.BaseViewModel;
import com.common.framework.utils.ScreenUtils;
import com.common.framework.utils.ZzLog;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModel;

/**
 * @author：atar
 * @date: 2020/10/22
 * @description:
 */
public abstract class CommonTitleActivity<VM extends BaseViewModel, P extends BasePresenter> extends BaseViewModelActivity<VM, P> implements View.OnClickListener {
    protected View commonContentBg;
    protected TextView txt_activity_title;
    protected ImageView img_back;
    protected View layout_activity_title_bar;

    @Override
    public void initControl(@Nullable Bundle savedInstanceState) {
        setContentView(getTitleBarResID());
    }

    /**
     * titleBar 布局
     *
     * @return
     */
    protected int getTitleBarResID() {
        return 0;
    }


    @Override
    protected void bindEvent() {
        super.bindEvent();
    }

    @Override
    protected void initValue() {

    }

    /**
     * @param txt_activity_title_ResID :titleBar 文字
     * @param img_back_resID           ：返回控件
     */
    protected void setTitleBarUI(int txt_activity_title_ResID, int img_back_resID, int layout_activity_title_barID) {
        this.img_back = findViewById(img_back_resID);
        this.txt_activity_title = findViewById(txt_activity_title_ResID);
        this.layout_activity_title_bar = findViewById(layout_activity_title_barID);
        if (img_back != null) {
            img_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setBackFinishActivity();
                }
            });
        }
    }

    @Override
    public void onClick(View v) {

    }

    /**
     * 添加中间布局
     *
     * @param layoutResId
     * @author :Atar
     * @createTime:2014-6-13下午9:07:25
     * @version:1.0.0
     * @modifyTime:
     * @modifyAuthor:
     * @description:
     */
    protected void addContentLayout(int layoutResId) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        commonContentBg = inflater.inflate(layoutResId, null);
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        lp.gravity = Gravity.TOP | Gravity.LEFT;
        lp.topMargin = (int) ScreenUtils.getIntToDip(thisContext, 81);
        addContentView(commonContentBg, lp);
    }

    public void setActivityTitle(String title) {
        if (txt_activity_title != null) {
            txt_activity_title.setText(title);
        } else {
            ZzLog.e("txt_activity_title is null view ");
        }
    }
}
