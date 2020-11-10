package com.common.framework.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Atar on 2018/7/26.
 */

public class NoFastClickUtils {
    private static final int MIN_DELAY_TIME = 1000;  // 两次点击间隔不能少于1000ms
    private static Map<String, Long> map = new HashMap<String, Long>();

    public static boolean isFastClick(String fastClickKey) {
        boolean flag = true;
        long currentClickTime = System.currentTimeMillis();
        long lastClickTime = 0;
        if (map != null && map.containsKey(fastClickKey)) {
            lastClickTime = map.get(fastClickKey);
        }
        if ((currentClickTime - lastClickTime) >= MIN_DELAY_TIME) {
            flag = false;
        }
        if (map != null) {
            map.put(fastClickKey, currentClickTime);
        }
        return flag;
    }

    public static boolean isFastClick(String fastClickKey,int delayTime) {
        boolean flag = true;
        long currentClickTime = System.currentTimeMillis();
        long lastClickTime = 0;
        if (map != null && map.containsKey(fastClickKey)) {
            lastClickTime = map.get(fastClickKey);
        }
        if ((currentClickTime - lastClickTime) >= delayTime) {
            flag = false;
        }
        if (map != null) {
            map.put(fastClickKey, currentClickTime);
        }
        return flag;
    }

    public static void removeKey(String fastClickKey) {
        if (map != null) {
            map.remove(fastClickKey);
        }
    }

    public static void clearAll() {
        if (map != null) {
            map.clear();
        }
    }
}
