package com.common.business.code.lifecyle;

import com.common.business.code.bean.DialogBean;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * @author：atar
 * @date: 2020/11/7
 * @description:
 */
public class BaseViewModel extends ViewModel {

    /**
     * 用来通知 Activity／Fragment 是否显示等待Dialog
     */
    public DialogLiveData<DialogBean> showDialog = new DialogLiveData<>();
    /**
     * 当ViewModel层出现错误需要通知到Activity／Fragment
     */
    public MutableLiveData<String> error = new MutableLiveData<>();
}
