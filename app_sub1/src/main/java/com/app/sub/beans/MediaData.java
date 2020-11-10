package com.app.sub.beans;

import java.util.List;

/**
 * @author：atar
 * @date: 2020/11/7
 * @description:
 */
public class MediaData {
    private List<String> img;
    private String defaultvideoimg;
    private List<String> video;
    private String tenantlogo;
    private List<MediadetaildtosBean> mediadetaildtos;

    public List<String> getImg() {
        return img;
    }

    public void setImg(List<String> img) {
        this.img = img;
    }

    public String getDefaultvideoimg() {
        return defaultvideoimg;
    }

    public void setDefaultvideoimg(String defaultvideoimg) {
        this.defaultvideoimg = defaultvideoimg;
    }

    public List<String> getVideo() {
        return video;
    }

    public void setVideo(List<String> video) {
        this.video = video;
    }

    public String getTenantlogo() {
        return tenantlogo;
    }

    public void setTenantlogo(String tenantlogo) {
        this.tenantlogo = tenantlogo;
    }

    public List<MediadetaildtosBean> getMediadetaildtos() {
        return mediadetaildtos;
    }

    public void setMediadetaildtos(List<MediadetaildtosBean> mediadetaildtos) {
        this.mediadetaildtos = mediadetaildtos;
    }
}
