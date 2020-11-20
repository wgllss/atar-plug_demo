/**
 *
 */
package com.atar.host.app.aplication;

import com.atar.host.app.configs.Config;
import com.common.business.code.application.BaseApplication;
import com.common.framework.Threadpool.ThreadPoolTool;
import com.common.framework.application.CrashHandler;
import com.common.framework.skin.SkinResourcesManager;


/**
 * ø
 * ****************************************************************************************************************************************************************************
 *
 * @author :Atar
 * @createTime:2017-8-9下午2:54:23
 * @version:1.0.0
 * @modifyTime:
 * @modifyAuthor:
 * @description: ****************************************************************************************************************************************************************************
 */
public class AtarApplication extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        ThreadPoolTool.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                SkinResourcesManager.getInstance(AtarApplication.this).initSkinResources(true,
                        "com.atar.skin", Config.download_skin_url);
                CrashHandler.getInstance().init(AtarApplication.this);
            }
        });
    }
}
