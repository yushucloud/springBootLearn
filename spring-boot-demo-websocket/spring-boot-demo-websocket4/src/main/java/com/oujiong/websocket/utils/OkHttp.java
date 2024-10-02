package com.oujiong.websocket.utils;


import okhttp3.OkHttpClient;

import java.util.concurrent.TimeUnit;

/**
 * @Description: 工具类
 *
 * @author xub
 */
public final class OkHttp {

    private static final OkHttpClient OK_HTTP_CLIENT = new OkHttpClient.Builder().connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS).writeTimeout(30, TimeUnit.SECONDS).build();

    public static OkHttpClient singleton() {
        return OK_HTTP_CLIENT;
    }
}

