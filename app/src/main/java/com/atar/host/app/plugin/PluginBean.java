package com.atar.host.app.plugin;

/**
 * @author：atar
 * @date: 2020/11/19
 * @description:
 */
public class PluginBean {
    private String pluginPackgeName; //插件模块包名
    private String pluginPath; //插件模块路劲
    private String pluginVersion;//插件版本
    private String pluginShowName;//插件模块显示名字

    public String getPluginPackgeName() {
        return pluginPackgeName;
    }

    public String getPluginPath() {
        return pluginPath;
    }

    public String getPluginVersion() {
        return pluginVersion;
    }

    public String getPluginShowName() {
        return pluginShowName;
    }
}
