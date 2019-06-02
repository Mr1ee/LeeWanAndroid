package com.lee.leewanandroid.utils

import android.util.Log
import com.youth.banner.BuildConfig

object Logger {
    private var debug = BuildConfig.DEBUG

    fun d(tag: String?, msg: String?) {
        if (debug) {
            Log.d(tag, msg)
        }
    }

    fun i(tag: String?, msg: String?) {
        if (debug) {
            Log.i(tag, msg)
        }
    }

    fun w(tag: String?, msg: String?) {
        if (debug) {
            Log.w(tag, msg)
        }
    }

    fun e(tag: String?, msg: String?) {
        if (debug) {
            Log.e(tag, msg)
        }
    }

    fun e(tag: String?, msg: String?, e: Throwable) {
        if (debug) {
            Log.e(tag, msg, e)
        }
    }
}