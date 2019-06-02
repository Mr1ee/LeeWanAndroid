package com.lee.leewanandroid.net.interceptor

import com.lee.leewanandroid.utils.isNetworkConnected
import java.io.IOException

import okhttp3.Interceptor
import okhttp3.Response

class OfflineCacheInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        if (!isNetworkConnected()) {
            val offlineCacheTime = 60 * 60 * 24 * 28//离线的时候的缓存的过期时间,4周
            request = request.newBuilder()
                .header("Cache-Control", "public, only-if-cached, max-stale=$offlineCacheTime")
                .removeHeader("Pragma")
                .build()
        }
        return chain.proceed(request)
    }
}