package com.app.sub.net;

import com.app.sub.beans.MediaData;
import com.common.business.code.bean.ResBaseModel;
import com.common.business.code.net.implnetwork.HttpGetCreate;
import com.common.framework.interfaces.MultSubscriber;

import java.util.Map;
import java.util.TreeMap;

import androidx.lifecycle.LifecycleOwner;
import io.reactivex.Flowable;

/**
 * @author：atar
 * @date: 2020/11/7
 * @description:
 */
public class NetWorkInterface {

    /**
     * pos媒体信息
     */
    public static Flowable<ResBaseModel> getposmedialistwithoutlogin(LifecycleOwner owner, MultSubscriber subscriber) {
        Map<String, Object> params = new TreeMap<>();
        params.put("type", "0");
        return HttpGetCreate.newInstance(owner, EnumUrl.getposmedialistwithoutlogin, params).subscribeWith(subscriber, EnumMsgWhat.msgwht_getposmedialistwithoutlogin, MediaData.class);
    }
}
