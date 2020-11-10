package com.common.business.code.activity;

import android.os.Bundle;

import com.common.business.code.lifecyle.BaseViewModel;

import androidx.lifecycle.ViewModelProviders;

/**
 * @author：atar
 * @date: 2020/11/6
 * @description:
 */
public abstract class BaseViewModelActivity<VM extends BaseViewModel, P extends BasePresenter> extends BaseActivity {
    public VM viewModel;
    protected P mPresneter;
//    public SharedViewModel sharedViewModel;



    @Override
    public void init(Bundle savedInstanceState) {
        initViewModel();
        mPresneter = createPresenter();
        if (mPresneter != null) {
            mPresneter.setViewModel(viewModel);
        }
        super.init(savedInstanceState);
    }

    protected abstract P createPresenter();

    /**
     * 初始化ViewModel
     */

    private void initViewModel() {
        try {
            if (getModelClass() == null) {
                return;
            }
            viewModel = ViewModelProviders.of(thisContext).get(getModelClass());
//            sharedViewModel = ((SeparationApplication) getApplication()).getShareViewModel();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected abstract Class<VM> getModelClass();

}
