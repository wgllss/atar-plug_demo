package com.common.business.code.net;

import com.common.framework.utils.PreferenceUtil;

/**
 * @author：atar
 * @date: 2019/5/8
 * @description:
 */
public class Config {

    public static final String IP = PreferenceUtil.getInstance().getIP("pos.wenxiansheng.cn");
    public static final String prefix = PreferenceUtil.getInstance().getPrefix("https");


    public static final String URL = prefix + "://" + IP;
    public static final String SECRET_KEY = URL.contains("pos.wenxiansheng.cn") ? "JpxiDbVm6wbhZNgfRiQ7KPIbcSoSqh" : URL.contains("pos-stage.wenxiansheng.cn") ? "2yyi1wXMmolksAQFzK9pjDUUA25ELP" : "z0nRGBMPliWG6ywtdG4Y8f5TdbZ9ll";
}
