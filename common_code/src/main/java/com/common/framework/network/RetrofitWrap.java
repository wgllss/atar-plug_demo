package com.common.framework.network;

import android.os.Build;
import android.text.TextUtils;

import com.common.framework.utils.AppBuildConfig;
import com.common.framework.utils.PackageUtil;
import com.common.framework.utils.PreferenceUtil;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitWrap {
    private static final int TIMEOUT = 60000;
    private static volatile RetrofitWrap retrofitWrap = null;

    public static HttpLoggingInterceptor.Level level;
    public static String DEFAULT_HOST = "";

    private Retrofit retrofit;
    private HeaderInterceptor HeaderInterceptor = new HeaderInterceptor();

    public static RetrofitWrap getInstance() {

        if (retrofitWrap == null) {
            synchronized (RetrofitWrap.class) {
                if (retrofitWrap == null) {
                    retrofitWrap = new RetrofitWrap();
                }
            }
        }
        return retrofitWrap;
    }

    private RetrofitWrap() {
        retrofit = initRetrofit();
    }


    private class HeaderInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            Request authorised = request
                    .newBuilder()
                    .addHeader("Connection", "keep-alive")
//                    .addHeader("Connection", "close")
//                    .addHeader("X-ZZ-Timestamp", timestamp)
                    .addHeader("X-ZZ-Device-Sn", Build.SERIAL)
                    .addHeader("v", PackageUtil.getVersionCode(AppBuildConfig.getApplication()) + "")
                    .addHeader("POS-Authorization", PreferenceUtil.getInstance().getToken())
                    .build();
            return chain.proceed(authorised);
        }
    }


    private static class BaseUrlInterceptor implements Interceptor {
        public volatile String host;

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request originalRequest = chain.request();
            if (!TextUtils.isEmpty(host)) {
                HttpUrl newUrl = originalRequest.url().newBuilder()
                        .host(host)
                        .build();
                originalRequest = originalRequest.newBuilder()
                        .url(newUrl)
                        .build();
            }

            return chain.proceed(originalRequest);
        }
    }


    private Retrofit initRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(DEFAULT_HOST)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(build())
                .build();
    }

    public OkHttpClient build() {
        if (level == HttpLoggingInterceptor.Level.NONE) {
            return new OkHttpClient.Builder()
                    .addInterceptor(HeaderInterceptor)//添加拦截器
//                  .addInterceptor(logging)//添加打印日志
                    .addInterceptor(new BaseUrlInterceptor())
                    .callTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
                    //设置连接超时
                    .connectTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
                    //设置从主机读信息超时
                    .readTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
                    //设置写信息超时
                    .writeTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
                    .retryOnConnectionFailure(true)//设置出现错误进行重新连接。
                    .cache(new Cache(AppBuildConfig.getApplication().getCacheDir(), 10 * 1024 * 1024)) //10M cache
                    .build();
        }
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(level);
        return new OkHttpClient.Builder()
                .addInterceptor(HeaderInterceptor)//添加拦截器
                .addInterceptor(logging)//添加打印日志
//                .addInterceptor(new BaseUrlInterceptor())
                //设置连接超时
                .callTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
                .connectTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
                //设置从主机读信息超时
                .readTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
                //设置写信息超时
                .writeTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
                .retryOnConnectionFailure(true)//设置出现错误进行重新连接。
                .cache(new Cache(AppBuildConfig.getApplication().getCacheDir(), 10 * 1024 * 1024)) //10M cache
                .build();
    }


    public <T> T create(final Class<T> service) {
        return retrofit.create(service);
    }
}

