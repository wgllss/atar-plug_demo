package com.common.framework.utils;


/**
 * 全局存储SharePreference
 */
public class PreferenceUtil {


    private static PreferenceUtil instance;

    public static PreferenceUtil getInstance() {
        if (instance == null) {
            instance = new PreferenceUtil();
        }
        return instance;
    }

    /***
     * 获取token
     * @return
     */
    public String getToken() {
        return SpUtils.get(AppBuildConfig.getApplication(), "token", "token", "").toString();
    }

    /***
     * 设置token
     */
    public void setToken(String json) {
        SpUtils.put(AppBuildConfig.getApplication(), "token", "token", json);
    }

    /***
     * 店员信息
     */
    public void setSystemUser(String json) {
        SpUtils.put(AppBuildConfig.getApplication(), "SystemUser", "SystemUser", json);
    }

    /***
     * 店员信息
     * @return
     */
    public String getSystemUser() {
        return SpUtils.get(AppBuildConfig.getApplication(), "SystemUser", "SystemUser", "").toString();
    }


    public void setShop(String json) {
        SpUtils.put(AppBuildConfig.getApplication(), "shop", "shop", json);
    }

    public String getShop() {
        return SpUtils.get(AppBuildConfig.getApplication(), "shop", "shop", "").toString();
    }


    public void clear() {
        SpUtils.clear(AppBuildConfig.getApplication(), "token");
        SpUtils.clear(AppBuildConfig.getApplication(), "shop");
        SpUtils.clear(AppBuildConfig.getApplication(), "SystemUser");
    }


    public void setIP(String ip) {
        SpUtils.put(AppBuildConfig.getApplication(), "ipsp", "IP_KEY", ip);
    }

    public void setPrefix(String prefix) {
        SpUtils.put(AppBuildConfig.getApplication(), "ipsp", "PREFIX_KEY", prefix);
    }

    public String getIP(String defaultIP) {
        return SpUtils.get(AppBuildConfig.getApplication(), "ipsp", "IP_KEY", defaultIP).toString();
    }

    public String getPrefix(String defaultPrefix) {
        return SpUtils.get(AppBuildConfig.getApplication(), "ipsp", "PREFIX_KEY", defaultPrefix).toString();
    }


    /**
     * 设置打印宽度
     *
     * @param printWidth
     */
    public void setPrintWidth(int printWidth) {
        SpUtils.put(AppBuildConfig.getApplication(), "AECR_C9_printwidth", "printWidth", printWidth);
    }

    /**
     * 得到打印宽度
     *
     * @return
     */
    public int getPrintWidth() {
        return (int) SpUtils.get(AppBuildConfig.getApplication(), "AECR_C9_printwidth", "printWidth", 384);
    }


    //存入数据
    public void putJson(String key, String Json) {
        SpUtils.put(AppBuildConfig.getApplication(), "key_json", key, Json);
    }

    //获取数据
    public String getJson(String key) {
        return (String) SpUtils.get(AppBuildConfig.getApplication(), "key_json", key, "");
    }

}

