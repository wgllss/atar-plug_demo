package com.atar.host.app.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.Message;
import android.text.TextUtils;
import android.widget.ImageView;

import com.atar.host.app.configs.Config;
import com.common.framework.download.DownLoadFileBean;
import com.common.framework.download.DownLoadFileManager;
import com.common.framework.interfaces.HandlerListener;
import com.common.framework.utils.ZzLog;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class DownLoadSevice extends Service implements HandlerListener {

    private String TAG = DownLoadSevice.class.getSimpleName();

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        try {
            if (intent != null) {
                int type = intent.getIntExtra(STR_DOWN_LOAD_TYPE, 0);
                switch (type) {
                    case DOWN_LOAD_TYPE:
                        int whichUrl = intent.getIntExtra(DOWN_LOAD_WHICH_URL_KEY, 0);
                        String downloadUrl = intent.getStringExtra(DOWN_LOAD_URL_KEY);
                        String strDownloadFileName = downloadUrl.substring(downloadUrl.lastIndexOf("/") + 1, downloadUrl.length());
                        DownLoadFileManager.getInstance().downLoad(null, this, whichUrl, downloadUrl, 2, true, strDownloadFileName, Config.strDownloadDir);
                        break;
                    case DOWN_LOAD_GUIDE_IMAGE_TYPE://下载引导图
                        String imgContent = intent.getStringExtra(DOWN_LOAD_GUIDE_IMAGE_CONTENT_KEY);
                        if (!TextUtils.isEmpty(imgContent)) {
                            Gson gson = new Gson();
                            List<String> list = gson.fromJson(imgContent, new TypeToken<List<String>>() {
                            }.getType());
                            if (list != null && list.size() > 0) {
                                for (int i = 0; i < list.size(); i++) {
                                    SimpleDraweeView imgaeView = new SimpleDraweeView(this);
                                    imgaeView.setScaleType(ImageView.ScaleType.FIT_XY);
                                    imgaeView.setImageURI(list.get(i));
                                }
                            }
                        }
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onHandlerData(Message msg) {
        try {
            switch (msg.what) {
//                case UpdateApkTools.UPDATA_NO_NEW_VERSION:
//                    ZzLog.i(TAG, "当前已是最新版本");
//                    break;
//                case UpdateApkTools.UPDATA_IS_NEW_VERSION:
//                    if ("Android".equals(Build.BRAND)) {
//                        if (msg.obj != null) {
//                            updateApkInfo = null;
//                            updateApkInfo = (UpdateApkInfo) msg.obj;
//                            if (updateApkInfo != null && !TextUtils.isEmpty(updateApkInfo.getUrl())) {
//                                String fileUrl = updateApkInfo.getUrl();
//                                int which = fileUrl.hashCode();
//                                AppConfigDownloadManager.getInstance().downLoadAppConfigFile(null, this, updateApkInfo.getVersion(), updateApkInfo.getVersionMin(), which, fileUrl, 1, true, MDPassword.getPassword32(UpdateApkTools.fileName), UpdateApkTools.strDownloadDir);
//                            }
//                        }
//                    } else {
//                        ZzLog.e("该品牌：" + Build.BRAND + "-不接收自动升级");
//                    }
//                    break;
//                case UpdateApkTools.GET_UPDATA_INFO_TIME_OUT:
//                    ZzLog.i(TAG, "联网超时");
//                    break;
                case DownLoadFileBean.DOWLOAD_FLAG_FAIL:
                    ZzLog.i(TAG, msg.arg2 + "---" + "下载失败");
                    break;
                case DownLoadFileBean.DOWLOAD_FLAG_SUCCESS:
                    ZzLog.i(TAG, msg.arg2 + "---" + "下载成功");
//                    if (updateApkInfo != null && !TextUtils.isEmpty(updateApkInfo.getUrl())) {
//                        int which = 1000;
//                        String fileUrl = updateApkInfo.getUrl();
//                        if (!TextUtils.isEmpty(fileUrl)) {
//                            which = fileUrl.hashCode();
//                        }
//                        if (which == msg.arg2) {//代表下载升级apk，可能后面扩展到下载其他的
//                            if ("Android".equals(Build.BRAND)) {
//                                //android 品牌 桑达 接收自动升级
//                                EventUtils.post(EnumMessage.install_event, updateApkInfo);
//                            } else {
//                                ZzLog.e("该品牌：" + Build.BRAND + "-不接收自动升级");
//                            }
//                        }
//                    }
//                    break;
                case DownLoadFileBean.DOWLOAD_FLAG_ING:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static final String STR_DOWN_LOAD_TYPE = "DOWN_LOAD_TYPE";


    //下载引导图
    private static final int DOWN_LOAD_GUIDE_IMAGE_TYPE = 1001;
    private static final String DOWN_LOAD_GUIDE_IMAGE_CONTENT_KEY = "DOWN_LOAD_GUIDE_IMAGE_CONTENT_KEY";

    //下载
    private static final String DOWN_LOAD_URL_KEY = "DOWN_LOAD_URL_KEY";
    private static final String DOWN_LOAD_WHICH_URL_KEY = "DOWN_LOAD_WHICH_URL_KEY";
    private static final int DOWN_LOAD_TYPE = 1000;

    //下载文件
    public static void startDownload(Context context, String downloadUrl, int whichUrl) {
        try {
            Intent intent = new Intent(context, DownLoadSevice.class);
            intent.putExtra(STR_DOWN_LOAD_TYPE, DOWN_LOAD_TYPE);
            intent.putExtra(DOWN_LOAD_URL_KEY, downloadUrl);
            intent.putExtra(DOWN_LOAD_WHICH_URL_KEY, whichUrl);
            context.startService(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //下载引导图
    public static void startDownloadGuideImage(Context context, String content) {
        try {
            Intent intent = new Intent(context, DownLoadSevice.class);
            intent.putExtra(STR_DOWN_LOAD_TYPE, DOWN_LOAD_GUIDE_IMAGE_TYPE);
            intent.putExtra(DOWN_LOAD_GUIDE_IMAGE_CONTENT_KEY, content);
            context.startService(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
