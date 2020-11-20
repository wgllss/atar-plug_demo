package com.atar.host.app.configs;

import java.util.List;

/**
 * @author：atar
 * @date: 2020/11/19
 * @description:
 */
public class SkinConfigJson {
    private String skinVersionName;//皮肤版本
    private String skinReplaceMinVersion;//皮肤需要强制替换的最小版本 (小于该版本下必须强制下载新的，不含等于)
    private List<SkinBean> all_skin;

    public String getSkinVersionName() {
        return skinVersionName;
    }

    public String getSkinReplaceMinVersion() {
        return skinReplaceMinVersion;
    }

    public List<SkinBean> getAll_skin() {
        return all_skin;
    }

}
