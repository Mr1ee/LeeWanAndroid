package com.lee.leewanandroid.widget

import android.widget.Toast
import com.lee.leewanandroid.WanAndroidApp

object ToastUtils {

    private val instance: Toast by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
        Toast(WanAndroidApp.mContext).apply {
            duration = Toast.LENGTH_SHORT
        }
    }

    fun showToast(text: String?, duration: Int = Toast.LENGTH_SHORT) {
        instance.setText(text)
        instance.duration = duration
        instance.show()
    }

    fun showToast(resId: Int, duration: Int = Toast.LENGTH_SHORT) {
        showToast(WanAndroidApp.mContext.resources.getString(resId), duration)
    }

}