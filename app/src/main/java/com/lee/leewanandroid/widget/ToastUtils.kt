package com.lee.leewanandroid.widget

import android.content.Context
import android.widget.Toast
import com.lee.leewanandroid.WanAndroidApp

object ToastUtils {

    private val instance: Context by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
        WanAndroidApp.mContext
    }

    fun showToast(text: String?, duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(instance, text, duration).show()
    }

    fun showToast(resId: Int, duration: Int = Toast.LENGTH_SHORT) {
        showToast(instance.resources.getString(resId), duration)
    }

}