package com.atar.host.app.activity.web;

/**
 * ****************************************************************************************************************************************************************************
 * html 交互数据
 *
 * @author:Atar
 * @createTime: 2018/9/11 上午10:13
 * @version:1.0.0
 * @modifyTime:
 * @modifyAuthor:
 * @description : http 请求错误code 原生已经通用处理，此处无需处理
 * **************************************************************************************************************************************************************************
 */
public class HtmlData {
    private String msgWhat; //哪一个请求标志，必须每一个请求标志都不相同
    private String whichThred1; //同一请求Url 但请求中参数1不相同
    private String whichThred2;//同一请求Url 但请求中参数2不相同
    private String url;//请求地址
    private String requestMethod;//请求方法
    private String paramsJson;//请求参数
    private String specialHtml;//请求返回json是否含有html代码 有传:1 ,  无传:0
    private String result;// 请求返回结果

    public String getMsgWhat() {
        return msgWhat;
    }

    public void setMsgWhat(String msgWhat) {
        this.msgWhat = msgWhat;
    }

    public String getWhichThred1() {
        return whichThred1;
    }

    public void setWhichThred1(String whichThred1) {
        this.whichThred1 = whichThred1;
    }

    public String getWhichThred2() {
        return whichThred2;
    }

    public void setWhichThred2(String whichThred2) {
        this.whichThred2 = whichThred2;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
    }

    public String getParamsJson() {
        return paramsJson;
    }

    public void setParamsJson(String paramsJson) {
        this.paramsJson = paramsJson;
    }

    public String getSpecialHtml() {
        return specialHtml;
    }

    public void setSpecialHtml(String specialHtml) {
        this.specialHtml = specialHtml;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
