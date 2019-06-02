package com.lee.leewanandroid

import android.annotation.SuppressLint
import android.app.Application
import android.content.ComponentCallbacks2
import android.content.Context
import com.bumptech.glide.Glide
import com.squareup.leakcanary.LeakCanary

class WanAndroidApp : Application() {


    companion object {
        private val TAG = WanAndroidApp::class.java.simpleName
        @SuppressLint("StaticFieldLeak")
        lateinit var mContext: Context
    }


    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            LeakCanary.install(this)
        }
        mContext = applicationContext
    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        // clear Glide cache
        if (level == ComponentCallbacks2.TRIM_MEMORY_UI_HIDDEN) {
            Glide.get(this).clearMemory()
        }
        // trim memory
        Glide.get(this).trimMemory(level)
    }

    override fun onLowMemory() {
        super.onLowMemory()
        // low memory clear Glide cache
        Glide.get(this).clearMemory()
    }

}