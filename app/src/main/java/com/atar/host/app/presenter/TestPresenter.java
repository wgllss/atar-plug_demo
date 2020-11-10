package com.atar.host.app.presenter;

import com.atar.host.app.beans.MediaData;
import com.atar.host.app.net.EnumMsgWhat;
import com.atar.host.app.net.NetWorkInterface;
import com.atar.host.app.viewmodels.TestViewModel;
import com.common.business.code.activity.BasePresenter;

import androidx.lifecycle.LifecycleOwner;

/**
 * @author：atar
 * @date: 2020/11/7
 * @description:
 */
public class TestPresenter extends BasePresenter<TestViewModel> {

    public void getposmedialistwithoutlogin(LifecycleOwner owner) {
        NetWorkInterface.getposmedialistwithoutlogin(owner, this);
    }

    @Override
    public void onNext(int what, int which1, int which2, int which3, Object t) {
        switch (what) {
            case EnumMsgWhat.msgwht_getposmedialistwithoutlogin:
                if (t != null) {
                    MediaData mMediaData = (MediaData) t;
                    if (mMediaData != null) {
                        viewModel.mediaData.setValue(mMediaData);
                    }
                }
                break;
        }
    }
}
