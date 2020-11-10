package com.app.sub.presenter;

import com.app.sub.beans.MediaData;
import com.app.sub.net.EnumMsgWhat;
import com.app.sub.net.NetWorkInterface;
import com.app.sub.viewmodels.TestViewModel;
import com.common.business.code.activity.BasePresenter;
import com.common.framework.utils.ZzLog;

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
                        ZzLog.e(mMediaData.toString());
                        viewModel.mediaData.setValue(mMediaData);
                    } else {
                        ZzLog.e("null");
                    }
                }
                break;
        }
    }

    @Override
    public void onError(int what, int which1, int which2, int which3, int code, String errorMessage, String data) {
        viewModel.error.setValue(errorMessage);
    }
}
