package com.common.business.code.bean;

import android.text.TextUtils;

import com.common.framework.utils.PreferenceUtil;

/**
 * @author：atar
 * @date: 2019/5/11
 * @description:
 */
public class ResBaseModel<T> {
    public static final int CODE_SUCCESS = 0;
    public static final int CODE_LOGINERROR = 120014;//会员登录失效
    public static final int CODE_DIANYUAN_LOGINERROR = 100031;//店员登录失效
    public static final int CODE_DIANYUAN_LOGINERROR1 = 300016;//商户认证已过期
    public static final int SECURITYCODE = 330008;//需要输入支付密码
    public static final int SECURITYCODE_FAIL = 330010;//数字密码错误
    public static final int code_100011 = 100011;//登录用户和付款码用户不是同一个人的时候
    public static final int SECURITYCODE_100002 = 100002;//用户未设置数字支付密码
    public static final int SECURITYCODE_260011 = 260011;//小主，当前选择优惠券无效
    public static final int code_260058 = 260058;//商品价格或促销发生变化，请重新指定成交价

    public int code;
    public String message;
    public long now;
    public T data;


    public boolean isSuccess() {
        switch (code) {
            case CODE_DIANYUAN_LOGINERROR://店员登录失效
            case CODE_DIANYUAN_LOGINERROR1:
                PreferenceUtil.getInstance().setToken("");
                message = TextUtils.isEmpty(message) ? "当前登录失效" : message;
//                EventUtils.post(EnumMessage.CODE_DIANYUAN_LOGINERROR, message);
                return false;
            case CODE_LOGINERROR://会员登录失效
//                EventUtils.post(EnumMessage.CODE_LOGINERROR, message);
                return false;
        }
        return CODE_SUCCESS == code;
    }

}
