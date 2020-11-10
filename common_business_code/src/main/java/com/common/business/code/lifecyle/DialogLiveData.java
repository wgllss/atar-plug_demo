package com.common.business.code.lifecyle;


import com.common.business.code.bean.DialogBean;

import androidx.lifecycle.MutableLiveData;

/**
 * @author：atar
 * @date: 2020/9/14
 * @description:
 */
public class DialogLiveData<T> extends MutableLiveData<T> {
    private DialogBean bean = new DialogBean();

    public void setValue(boolean isShow) {
        bean.setShow(isShow);
        bean.setMsg("");
        setValue((T) bean);
    }

    public void setValue(boolean isShow, String msg) {
        bean.setShow(isShow);
        bean.setMsg(msg);
        setValue((T) bean);
    }

}
