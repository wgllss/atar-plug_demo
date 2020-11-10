package com.common.framework.reflection;

/**
 * @author：atar
 * @date: 2019/5/9
 * @description:
 */
public class ServerException extends Exception {
    public int what;
    public int code;
    public String errorMessage;
    public Object data;

//    public ServerException(int what, int code, String errorMessage) {
//        super(errorMessage);
//        this.what = what;
//        this.code = code;
//        this.errorMessage = errorMessage;
//    }

    public ServerException(int what, int code, String errorMessage, Object data) {
        this.what = what;
        this.code = code;
        this.errorMessage = errorMessage;
        this.data = data;
    }
}
