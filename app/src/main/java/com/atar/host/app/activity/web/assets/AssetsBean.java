package com.atar.host.app.activity.web.assets;

/**
 * @author：atar
 * @date: 2020/11/10
 * @description:
 */
public class AssetsBean {
    private String plugin_model_name;//插件模块名字
    private String save_path_key_name;//存储插件模块下的文件路劲

    public String getPlugin_model_name() {
        return plugin_model_name;
    }

    public void setPlugin_model_name(String plugin_model_name) {
        this.plugin_model_name = plugin_model_name;
    }

    public String getSave_path_key_name() {
        return save_path_key_name;
    }

    public void setSave_path_key_name(String save_path_key_name) {
        this.save_path_key_name = save_path_key_name;
    }
}
