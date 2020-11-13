package com.atar.host.app.contans;

import android.os.Environment;

/**
 * @author：atar
 * @date: 2020/10/22
 * @description:
 */
public class Config {

    public static final String strDownloadDir = Environment.getExternalStorageDirectory() + "/.cache/.download_plugin_apk/";
    //存储ip KEy
    public static final String SAVE_HOST_IP_KEY = "SAVE_HOST_IP_KEY";
}
