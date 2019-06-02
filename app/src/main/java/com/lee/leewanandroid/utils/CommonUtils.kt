package com.lee.leewanandroid.utils

import android.content.Context
import android.net.ConnectivityManager
import com.lee.leewanandroid.WanAndroidApp


fun isNetworkConnected(): Boolean {
    val connectivityManager =
        WanAndroidApp.mContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val info = connectivityManager.activeNetworkInfo
    return info != null && info.isConnected
}