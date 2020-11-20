/**
 *
 */
package com.atar.host.app.configs;

import android.content.Context;
import android.content.res.AssetManager;
import android.text.TextUtils;

import com.atar.host.app.BuildConfig;
//import com.atar.host.app.services.DownLoadSevice;
import com.common.framework.Threadpool.ThreadPoolTool;
import com.common.framework.appconfig.AppConfigDownloadManager;
import com.common.framework.appconfig.AppConfigModel;
import com.common.framework.skin.SkinResourcesManager;
import com.common.framework.utils.ShowLog;
import com.common.framework.utils.ZzLog;
import com.google.gson.Gson;

import static com.atar.host.app.activity.web.OffineImplWebViewClient.HOST_OFFINE_FILE_PATH_KEY;

/**
 * ****************************************************************************************************************************************************************************
 *
 * @author :Atar
 * @createTime:2017-5-24上午11:13:53
 * @version:1.0.0
 * @modifyTime:
 * @modifyAuthor:
 * @description: ****************************************************************************************************************************************************************************
 */
public class AppConfigUtils {
    private static String TAG = AppConfigUtils.class.getSimpleName();

    /**
     * 获取离线assets下webview 所用文件
     *
     * @author :Atar
     * @createTime:2017-6-6上午10:13:20
     * @version:1.0.0
     * @modifyTime:
     * @modifyAuthor:
     * @description:
     */
    public static void getOffineFilePath(final AssetManager am) {
        ThreadPoolTool.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                try {

                    String[] resJs = am.list("js");
                    String[] resImg = am.list("img");
                    String[] resCss = am.list("css");
                    String[] html = am.list("html");
                    String strOfflineResources = "";
                    if (resJs != null && resJs.length > 0) {
                        for (int i = 0; i < resJs.length; i++) {
                            strOfflineResources += resJs[i];
                        }
                    }
                    if (resImg != null && resImg.length > 0) {
                        for (int i = 0; i < resImg.length; i++) {
                            strOfflineResources += resImg[i];
                        }
                    }
                    if (resCss != null && resCss.length > 0) {
                        for (int i = 0; i < resCss.length; i++) {
                            strOfflineResources += resCss[i];
                        }
                    }
                    if (html != null && html.length > 0) {
                        for (int i = 0; i < html.length; i++) {
                            strOfflineResources += html[i];
                        }
                    }
                    AppConfigModel.getInstance().putString(HOST_OFFINE_FILE_PATH_KEY,
                            strOfflineResources, true);
                    ZzLog.e("加载完成：");
                } catch (Exception e) {
                    ShowLog.e(TAG, e);
                }
            }
        });
    }

    /**
     * 读取服务端文件 如txt中的json
     *
     * @param FileUrl :文件地址
     * @author :Atar
     * @createTime:2017-5-24上午11:15:57
     * @version:1.0.0
     * @modifyTime:
     * @modifyAuthor:
     * @description:
     */
    public static void getServerTextJson(Context context, final String FileUrl) {
        AppConfigDownloadManager.getInstance().getServerJson(FileUrl, new AppConfigDownloadManager.HttpCallBackResult() {
            @Override
            public void onResult(String result) {
//                if (result != null) {
//                    Gson gson = new Gson();
//                    AppConfigJson mAppConfigJson = null;
//                    try {
//                        mAppConfigJson = gson.fromJson(result, AppConfigJson.class);
//                    } catch (Exception e) {
//                        ShowLog.e(TAG, e);
//                    }
//                    if (mAppConfigJson == null) {
//                        return;
//                    }
//                    String versionName = mAppConfigJson.getConfigVersionName();
//                    String localVersion = BuildConfig.VERSION_NAME;
//
//                    //处理配置文件版本内容
//                    if (versionName.compareToIgnoreCase(localVersion) > 0) {
//                        //线上配置文件版本比宿主apk版本大
//                        String configFileContentVersionName = AppConfigModel.getInstance().getString(Config.SAVE_CONFIG_FILE_VERSION_KEY, BuildConfig.VERSION_NAME);
//                        if (versionName.compareToIgnoreCase(configFileContentVersionName) > 0) {
//                            //线上配置文件内容版本比本地该版本大
//                            AppConfigModel.getInstance().putString(Config.SAVE_CONFIG_FILE_VERSION_KEY, mAppConfigJson.getConfigVersionName(), true);
//                            AppConfigModel.getInstance().putString(Config.SAVE_CONFIG_FILE_CONTENT_KEY, result, true);
//                        } else {
//                            //本地已经存在
//                            ZzLog.e("本地已经存在 该内容");
//                        }
//                    } else {
//                        AppConfigModel.getInstance().putString(Config.SAVE_CONFIG_FILE_VERSION_KEY, mAppConfigJson.getConfigVersionName(), true);
//                        AppConfigModel.getInstance().putString(Config.SAVE_CONFIG_FILE_CONTENT_KEY, result, true);
//                    }
//
//                    //处理引导图
//                    if (mAppConfigJson.getGuideImageConfigJson() != null && !TextUtils.isEmpty(mAppConfigJson.getGuideImageConfigJson().getGuideImageVersion())) {
//                        String localGuideImageVersion = AppConfigModel.getInstance().getString(Config.SAVE_LOAD_IMAGE_VERSION_KEY, BuildConfig.VERSION_NAME);
//                        if (mAppConfigJson.getGuideImageConfigJson().getGuideImageVersion().compareToIgnoreCase(localGuideImageVersion) > 0) {
//                            AppConfigModel.getInstance().putString(Config.SAVE_LOAD_IMAGE_CONTENT_KEY, gson.toJson(mAppConfigJson.getGuideImageConfigJson().getList()), true);
//                            AppConfigModel.getInstance().putString(Config.SAVE_LOAD_IMAGE_VERSION_KEY, mAppConfigJson.getGuideImageConfigJson().getGuideImageVersion(), true);
//                            //下载引导图 供下次使用
//                            DownLoadSevice.startDownloadGuideImage(context, gson.toJson(mAppConfigJson.getGuideImageConfigJson().getList()));
//                        }
//                    }
//
//                    //处理皮肤
//                    if (mAppConfigJson.getSkinconfigJson() != null && !TextUtils.isEmpty(mAppConfigJson.getSkinconfigJson().getSkinVersionName())) {
//                        //下载皮肤 供下次使用
//                        SkinResourcesManager.getInstance(context).downLoadSkin(null, mAppConfigJson.getSkinconfigJson().getSkinVersionName(), mAppConfigJson.getSkinconfigJson().getSkinReplaceMinVersion());
//                    }
//                }
            }
        });
    }

//    /**
//     * @author :Atar
//     * @createTime:2017-5-24下午3:17:37
//     * @version:1.0.0
//     * @modifyTime:
//     * @modifyAuthor:
//     * @description:
//     */
//    public static void switchActivity(OnClickInfo onClickInfo, Context context) {
//        try {
//            if (onClickInfo != null) {
//                // if (onClickInfo.isNeedLogin() && JoinInTGBActivity.startJoinInTGBActivity
//                // (context)) {// 需要登陆
//                // return;
//                // }
//
//                String className = onClickInfo.getClassName();
//                if (className != null && className.contains("com.open.weex") && VERSION.SDK_INT <
//                        VERSION_CODES.ICE_CREAM_SANDWICH) {
//                    CommonToast.show("Android系统版本太低,请升级系统");
//                    return;
//                }
//                if (onClickInfo.getSpecialInfo() != null && onClickInfo.getSpecialInfo().length()
//                        > 0) {// 特殊点击事件 跳转 切换tabhost等
//                    switchMainTab(onClickInfo.getSpecialInfo(), className);
//                    return;
//                }
//                if (className == null || className.length() == 0) {
//                    return;
//                }
//                Class<?> cls = Class.forName(className);
//                Intent intent = new Intent(context, cls);
//                if (ActivityManager.getActivityManager().getActivityStack() != null &&
//                        ActivityManager.getActivityManager().getActivityStack().size() > 0) {
//                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                }
//                intent = getIntentFromOptionJson(context, intent, onClickInfo.getOptionJson(),
//                        onClickInfo.getOnEventInfo());
//                IntentUtil.startOtherActivity(context, intent);
//            }
//        } catch (Exception e) {
//            CrashHandler.getInstance().CrashException(e, CrashHandler.CRASHT_YPE_CATCH);
//            ShowLog.e(TAG, e);
//        }
//    }
//
//    /**
//     * 处理特殊跳转事件
//     *
//     * @param specialInfo
//     * @param subTabClassActivityName
//     * @author :Atar
//     * @createTime:2017-5-24下午3:15:07
//     * @version:1.0.0
//     * @modifyTime:
//     * @modifyAuthor:
//     * @description:
//     */
//    public static void switchMainTab(String specialInfo, String subTabClassActivityName) {
//        try {
//            if (specialInfo != null && specialInfo.length() > 0) {
//                JSONObject specialJson = new JSONObject(specialInfo);
//                int tabHostposition = 0;
//                int viewPagerItem = -1;
//                if (specialJson != null && specialJson.length() > 0) {
//                    if (specialJson.has("tabHostposition")) {
//                        tabHostposition = specialJson.getInt("tabHostposition");
//                    }
//                    if (specialJson.has("viewPagerItem")) {
//                        viewPagerItem = specialJson.getInt("viewPagerItem");
//                    } else {
//                        viewPagerItem = -1;
//                    }
//                }
//                // if (ActivityManager.getActivityManager().getActivity(MainTabActivity.class) !=
//                // null) {
//                // ActivityManager.getActivityManager().getActivity(MainTabActivity.class)
//                // .switchMainTab(tabHostposition, subTabClassActivityName, viewPagerItem);
//                // }
//            }
//        } catch (Exception e) {
//            CrashHandler.getInstance().CrashException(e, CrashHandler.CRASHT_YPE_CATCH);
//            ShowLog.e(TAG, e);
//        }
//    }
//
//    /**
//     * 从json中获取参数传入到intent中去 适合app中所有跳转
//     *
//     * @param intent
//     * @return
//     * @author :Atar
//     * @createTime:2017-5-24上午11:25:36
//     * @version:1.0.0
//     * @modifyTime:
//     * @modifyAuthor:
//     * @description:
//     */
//    public static Intent getIntentFromOptionJson(Context context, Intent intent, String
//            optionJson, String onEventInfo) {
//        try {
//            if (optionJson != null && optionJson.length() > 0) {// 解析跳转传入参数
//                JSONArray jsonArray = new JSONArray(optionJson);
//                if (jsonArray != null && jsonArray.length() > 0) {
//                    for (int i = 0; i < jsonArray.length(); i++) {
//                        JSONObject jsonObject = jsonArray.getJSONObject(i);
//                        if (jsonObject != null) {
//                            if (jsonObject.has("intentKeyValueClassName")) {
//                                String intentKeyValueClassName = jsonObject.getString
//                                        ("intentKeyValueClassName");
//                                String intentKey = jsonObject.getString("intentKey");
//                                if ("int".equals(intentKeyValueClassName)) {
//                                    int intentKeyValue = jsonObject.getInt("intentKeyValue");
//                                    intent.putExtra(intentKey, intentKeyValue);
//                                } else if ("String".equals(intentKeyValueClassName)) {
//                                    String intentKeyValue = jsonObject.getString("intentKeyValue");
//                                    if (intentKeyValue != null) {
//                                        if (!"TAB_OPTION_JSON_KEY".equals(intentKey) &&
//                                                !"VIEWPAGER_OPTION_JSON_KEY".equals(intentKey) &&
//                                                intentKeyValue.contains(".js")
//                                                && intentKeyValue.contains("build/src")) {
//                                            // 传入weex js地址 ，自动原生 拼接可切换环境
//                                            try {
//                                                String[] str = intentKeyValue.split("/");
//                                                if (str != null && str.length > 0) {
//                                                    String path = str[0];
//                                                    path = UrlParamCommon.CONFIG_IP.contains("com" +
//                                                            ".cn") ? path + "/" : "";
//                                                    intentKeyValue = UrlParamCommon.CONFIG_HOST +
//                                                            path + intentKeyValue;
//                                                }
//                                            } catch (Exception e) {
//                                                CrashHandler.getInstance().CrashException(e, CrashHandler.CRASHT_YPE_CATCH);
//                                                ShowLog.e(TAG, e);
//                                            }
//                                        } else if (!"TAB_OPTION_JSON_KEY".equals(intentKey) &&
//                                                !"VIEWPAGER_OPTION_JSON_KEY".equals(intentKey) &&
//                                                intentKeyValue.contains(".html")
//                                                && intentKeyValue.contains("assets/html")) {
//                                            // 传入html地址 ，自动原生 拼接可切换环境
//                                            intentKeyValue = UrlParamCommon.CONFIG_HOST +
//                                                    intentKeyValue;
//                                        }
//                                    }
//                                    intent.putExtra(intentKey, intentKeyValue);
//                                } else if ("double".equals(intentKeyValueClassName)) {
//                                    double intentKeyValue = jsonObject.getDouble("intentKeyValue");
//                                    intent.putExtra(intentKey, intentKeyValue);
//                                } else if ("ArrayList<String>".equals(intentKeyValueClassName)) {
//                                    String json = jsonObject.getString("intentKeyValue");
//                                    if (json != null && json.length() > 0) {
//                                        JSONArray jsonArray1 = new JSONArray(json);
//                                        ArrayList<String> list = new ArrayList<String>();
//                                        for (int j = 0; j < jsonArray1.length(); j++) {
//                                            list.add(jsonArray1.getString(j));
//                                        }
//                                        intent.putStringArrayListExtra(intentKey, list);
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//        } catch (Exception e) {
//            CrashHandler.getInstance().CrashException(e, CrashHandler.CRASHT_YPE_CATCH);
//            ShowLog.e(TAG, e);
//        }
//        setEventInfo(context, onEventInfo);
//        return intent;
//    }
//
//    /**
//     * 设置统计事件
//     *
//     * @param onEventInfo
//     * @author :Atar
//     * @createTime:2017-7-24下午4:11:49
//     * @version:1.0.0
//     * @modifyTime:
//     * @modifyAuthor:
//     * @description:
//     */
//    public static void setEventInfo(Context context, String onEventInfo) {
//        try {
//            if (onEventInfo != null && onEventInfo.length() > 0) {// 解析点击统计事件
//                JSONObject onEventJson = new JSONObject(onEventInfo);
//                if (onEventJson != null) {
//                    String eventType = "";
//                    String eventID = "";
//                    String eventName = "";
//                    if (onEventJson.has("eventType")) {
//                        eventType = onEventJson.getString("eventType");
//                    }
//                    if (onEventJson.has("eventID")) {
//                        eventID = onEventJson.getString("eventID");
//                    }
//                    if (onEventJson.has("eventName")) {
//                        eventName = onEventJson.getString("eventName");
//                    }
//                    if ("0".equals(eventType)) {
//                        // 百度统计
//                        // StatService.onEvent(context, eventID, eventName, 1);
//                    } else if ("1".equals(eventType)) {
//                        // 友盟统计
//                        // MobclickAgent.onEvent(context, eventName);
//                    }
//                }
//            }
//        } catch (Exception e) {
//            CrashHandler.getInstance().CrashException(e, CrashHandler.CRASHT_YPE_CATCH);
//            ShowLog.e(TAG, e);
//        }
//    }
//
//    /**
//     * 得到默认配置数据
//     *
//     * @return
//     * @author :Atar
//     * @createTime:2017-5-24下午1:28:35
//     * @version:1.0.0
//     * @modifyTime:
//     * @modifyAuthor:
//     * @description:
//     */
//    public static String getDefaultSetting() {
//        return "{\"versionName\":\"2.91\",\"isReplace\":true,\"skinVersion\":\"3.00\",\"main_tab_json\":[{\"ID\":10000,\"menuName\":\"首页\",\"menuNameColor\":\"#ffffff,#ffffff\"," +
//                "\"menuNameSelcetColor\":\"#ffffff,#ffffff\",\"menuNameTextSize\":12,\"menuNameMarginTop\":5,\"itemBgColor\":\"#212423,#212423\",\"itemBgSelectColor\":\"#D53533,#D53533\"," +
//                "\"isShowIcon\":true,\"menuIcon\":\"assets://images/icon_tab_home_nor.png\",\"selectMenuIcon\":\"assets://images/icon_tab_home_pre.png\",\"menuIconWidthAndHeight\":20," +
//                "\"orientation\":1,\"onClickInfo\":{\"className\":\"com.cjml.app.ui.activitys.main.HomeActivity\"}},{\"ID\":10001,\"menuName\":\"订单记录\",\"menuNameColor\":\"#ffffff,#ffffff\"," +
//                "\"menuNameSelcetColor\":\"#ffffff,#ffffff\",\"menuNameTextSize\":12,\"menuNameMarginTop\":5,\"itemBgColor\":\"#212423,#212423\",\"itemBgSelectColor\":\"#D53533,#D53533\"," +
//                "\"isShowIcon\":true,\"menuIcon\":\"assets://images/icon_tab_order.png\",\"selectMenuIcon\":\"assets://images/icon_tab_order.png\",\"menuIconWidthAndHeight\":20,\"orientation\":1," +
//                "\"isShowinfoNumRedBg\":true,\"infoNum\":0,\"infoNumColor\":\"#ffffff\",\"infoNumSize\":15,\"infoNumMarginLeft\":14,\"infoNumMarginTop\":8,\"onClickInfo\":{\"className\":\"com" +
//                ".cjml.app.ui.activitys.main.OrderHistoryActivity\"}},{\"ID\":10002,\"menuName\":\"扫描记录\",\"menuNameColor\":\"#ffffff,#ffffff\",\"menuNameSelcetColor\":\"#ffffff,#ffffff\"," +
//                "\"menuNameTextSize\":12,\"menuNameMarginTop\":5,\"itemBgColor\":\"#212423,#212423\",\"itemBgSelectColor\":\"#D53533,#D53533\",\"isShowIcon\":true," +
//                "\"menuIcon\":\"assets://images/icon_tab_scan.png\",\"selectMenuIcon\":\"assets://images/icon_tab_scan.png\",\"menuIconWidthAndHeight\":20,\"orientation\":1," +
//                "\"onClickInfo\":{\"className\":\"com.cjml.app.ui.activitys.main.ScanHistoryActivity\"}},{\"ID\":10002,\"menuName\":\"我的\",\"menuNameColor\":\"#ffffff,#ffffff\"," +
//                "\"menuNameSelcetColor\":\"#ffffff,#ffffff\",\"menuNameTextSize\":12,\"menuNameMarginTop\":5,\"itemBgColor\":\"#212423,#212423\",\"itemBgSelectColor\":\"#D53533,#D53533\"," +
//                "\"isShowIcon\":true,\"menuIcon\":\"assets://images/icon_tab_me_nor.png\",\"selectMenuIcon\":\"assets://images/icon_tab_me_pre.png\",\"menuIconWidthAndHeight\":20,\"orientation\":1," +
//                "\"onClickInfo\":{\"className\":\"com.cjml.app.ui.activitys.main.MeActivity\"}}]}";
//    }
}
