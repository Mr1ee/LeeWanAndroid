package com.lee.leewanandroid.net.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class NetCacheInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)
        val onlineCacheTime = 0//在线的时候的缓存过期时间，如果想要不缓存，直接时间设置为0
        return response.newBuilder()
            .header("Cache-Control", "public, max-age=$onlineCacheTime")
            .removeHeader("Pragma")
            .build()
    }
}