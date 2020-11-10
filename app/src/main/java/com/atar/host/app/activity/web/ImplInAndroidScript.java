/**
 *
 */
package com.atar.host.app.activity.web;

import android.webkit.JavascriptInterface;

import java.util.Map;

/**
 * ****************************************************************************************************************************************************************************
 *
 * @author :Atar
 * @createTime:2017-6-2下午1:31:36
 * @version:1.0.0
 * @modifyTime:
 * @modifyAuthor:
 * @description: ****************************************************************************************************************************************************************************
 */
public class ImplInAndroidScript {

    private Map<String, String> tempJson;

    public ImplInAndroidScript(Map<String, String> tempJson) {
        this.tempJson = tempJson;
    }

    /**
     * 得到特殊包含html 代码的json
     *
     * @param msgWhat
     * @param whichThred1
     * @param whichThred2
     * @return
     * @author :Atar
     * @createTime:2017-6-2下午2:12:12
     * @version:1.0.0
     * @modifyTime:
     * @modifyAuthor:
     * @description:
     */
    @JavascriptInterface
    public String getJson(String msgWhat, String whichThred1, String whichThred2) {
        try {
            String key = msgWhat + "_" + whichThred1 + "_" + whichThred2;
            if (tempJson != null && tempJson.containsKey(key)) {
                String resultJson = tempJson.get(key);
                tempJson.remove(key);
                return resultJson;
            }
        } catch (Exception e) {

        }
        return "";
    }

}