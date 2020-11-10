package com.app.sub.viewmodels;

import com.app.sub.beans.MediaData;
import com.common.business.code.lifecyle.BaseViewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * @author：atar
 * @date: 2020/10/30
 * @description:
 */
public class TestViewModel extends BaseViewModel {

    public MutableLiveData<String> testString = new MutableLiveData<>();
    public MutableLiveData<MediaData> mediaData = new MutableLiveData<>();
}
