package com.common.framework.utils;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 作者：huangchen on 2016/5/10 09:42
 * 邮箱：huangchen@yonghui.cn
 * 时间处理类
 */
public class TimeUtils {
    private static long lastClickTime;
    @SuppressLint("SimpleDateFormat")
    public static final SimpleDateFormat DEFAULT_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 防止控件被重复点击
     *
     * @return
     */
    public static boolean isFastDoubleClick() {
        return isFastDoubleClick(200);
    }

    public static boolean isFastDoubleClick(long maxTime) {
        long time = System.currentTimeMillis();
        long endTime = time - lastClickTime;
        if (0 < endTime && endTime < maxTime) {
            return true;
        }
        lastClickTime = time;
        return false;
    }


    /**
     * yyyy-MM-dd HH:mm:ss 转化为时间戳
     *
     * @param yyyymmddHHmmss
     * @return
     * @author :Atar
     * @createTime:2014-12-29上午11:07:47
     * @version:1.0.0
     * @modifyTime:
     * @modifyAuthor:
     * @description:
     */
    public static long getTime(String yyyymmddHHmmss) {
        Date date = null;
        long time = 0;
        try {
            date = DEFAULT_DATE_FORMAT.parse(yyyymmddHHmmss);
            time = date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }

    public static String getStringTimeFromLongCustomFormat(long time, String timeFormat) {
        String strTime = "--";
        try {
            if (time > 0) {
                SimpleDateFormat formatter = new SimpleDateFormat(timeFormat);
                strTime = formatter.format(Long.valueOf(time));
            }
        } catch (Exception e) {

        }
        return strTime;
    }

    /**
     * get current time in milliseconds
     *
     * @return
     */
    public static long getCurrentTimeInLong() {
        return System.currentTimeMillis();
    }

    /**
     * get current time in milliseconds
     *
     * @return
     */
    public static String getCurrentTimeInString(SimpleDateFormat dateFormat) {
        return getTime(getCurrentTimeInLong(), dateFormat);
    }


    /**
     * long time to string
     *
     * @param timeInMillis
     * @param dateFormat
     * @return
     */
    public static String getTime(long timeInMillis, SimpleDateFormat dateFormat) {
        return dateFormat.format(new Date(timeInMillis));
    }

    /**
     * 得到当前时间 自定义格式
     *
     * @param format
     * @return
     * @author :Atar
     * @createTime:2016-12-13下午3:29:48
     * @version:1.0.0
     * @modifyTime:
     * @modifyAuthor:
     * @description:
     */
    public static String getCurrentTimeIn2String(String format) {
        SimpleDateFormat DATE_FORMAT_DATE = new SimpleDateFormat(format);
        return getTime(System.currentTimeMillis(), DATE_FORMAT_DATE);
    }
}
