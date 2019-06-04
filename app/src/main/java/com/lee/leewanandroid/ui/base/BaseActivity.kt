package com.lee.leewanandroid.ui.base

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import com.jakewharton.rxbinding2.view.RxView
import com.lee.leewanandroid.widget.ToastUtils
import io.reactivex.disposables.CompositeDisposable
import java.util.concurrent.TimeUnit

abstract class BaseActivity : AppCompatActivity() {

    abstract fun initView()

    @LayoutRes
    abstract fun getLayoutId(): Int

    private val mDisposables = CompositeDisposable()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(getLayoutId())
        initView()
    }

    /**
     * 防抖动
     */
    fun View.onClickEvent(action: () -> Unit) {
        mDisposables.add(RxView.clicks(this)
            .throttleFirst(2, TimeUnit.SECONDS)
            .subscribe {
                action.invoke()
            })
    }

    fun showToast(msg: String?) {
        ToastUtils.showToast(text = msg)
    }

    fun showToast(resId: Int) {
        ToastUtils.showToast(resId = resId)
    }

    override fun onStop() {
        super.onStop()
        mDisposables.clear()
    }
}