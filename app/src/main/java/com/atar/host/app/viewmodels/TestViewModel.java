package com.atar.host.app.viewmodels;

import com.atar.host.app.beans.MediaData;
import com.common.business.code.lifecyle.BaseViewModel;

import androidx.lifecycle.MutableLiveData;

/**
 * @author：atar
 * @date: 2020/10/30
 * @description:
 */
public class TestViewModel extends BaseViewModel {

    public MutableLiveData<String> testString = new MutableLiveData<>();
    public MutableLiveData<MediaData> mediaData = new MutableLiveData<>();
}
