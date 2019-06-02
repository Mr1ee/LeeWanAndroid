package com.lee.leewanandroid.net

import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import com.lee.leewanandroid.WanAndroidApp
import com.lee.leewanandroid.data.ApiService
import com.lee.leewanandroid.net.interceptor.NetCacheInterceptor
import com.lee.leewanandroid.net.interceptor.OfflineCacheInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import okhttp3.logging.HttpLoggingInterceptor




class RetrofitServiceManager// 创建 OKHttpClient//连接超时时间//写操作 超时时间//读操作超时时间

// 添加公共参数拦截器
//        val commonInterceptor = HttpCommonInterceptor.Builder()
//            .addHeaderParams("paltform", "android")
//            .addHeaderParams("userToken", "1234343434dfdfd3434")
//            .addHeaderParams("userId", "123445")
//            .build()
//        builder.addInterceptor(commonInterceptor)
//        builder.cache(cache)
//设置超时
//错误重连
//cookie认证

// 创建Retrofit
private constructor() {

    companion object {
        const val DEFAULT_TIME_OUT = 10L//超时时间 10s
        const val DEFAULT_READ_TIME_OUT = 20L

        /**
         * 获取RetrofitServiceManager
         * @return
         */
        fun getInstance(): RetrofitServiceManager {
            return SingletonHolder.INSTANCE
        }
    }

    private var mRetrofit: Retrofit

    init {
        val builder = OkHttpClient.Builder()
        val logInterceptor = HttpLoggingInterceptor()
        logInterceptor.level = HttpLoggingInterceptor.Level.BODY
        builder.addNetworkInterceptor(logInterceptor)
        builder.addNetworkInterceptor(NetCacheInterceptor())
        builder.addInterceptor(OfflineCacheInterceptor())
        builder.connectTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS)
        builder.readTimeout(DEFAULT_READ_TIME_OUT, TimeUnit.SECONDS)
        builder.writeTimeout(DEFAULT_READ_TIME_OUT, TimeUnit.SECONDS)
        builder.retryOnConnectionFailure(true)
        builder.cookieJar(
            PersistentCookieJar(
                SetCookieCache(),
                SharedPrefsCookiePersistor(WanAndroidApp.mContext)
            )
        )
        mRetrofit = Retrofit.Builder()
            .client(builder.build())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(ApiService.BASE_URL)
            .build()
    }

    private object SingletonHolder {
        val INSTANCE = RetrofitServiceManager()
    }

    /**
     * 获取对应的Service
     * @param service Service 的 class
     * @param <T>
     * @return
    </T> */
    fun <T> create(service: Class<T>): T {
        return mRetrofit.create(service)
    }
}