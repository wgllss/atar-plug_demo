package com.common.framework.network;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.BufferedSink;

/**
 * @author：atar
 * @date: 2019/5/9
 * @description:
 */
public class RequestBodyWrapper extends RequestBody {

    private String postBody;

    public <T> RequestBodyWrapper(String model) {
        postBody = model;
        check();
    }

    @Override
    public MediaType contentType() {
        return MediaType.parse("application/json; charset=utf-8");
    }

    @Override
    public void writeTo(BufferedSink sink) throws IOException {
        sink.write(postBody.getBytes(), 0, postBody.getBytes().length);
    }

    private void check() {
        if (postBody == null || postBody.getBytes() == null)
            throw new NullPointerException("content == null");
    }

    public String getBody() {
        return postBody;
    }
}
