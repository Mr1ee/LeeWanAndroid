package com.lee.leewanandroid.ui.base

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import com.jakewharton.rxbinding2.view.RxView
import com.lee.leewanandroid.widget.ToastUtils
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import java.util.concurrent.TimeUnit

abstract class BaseActivity : AppCompatActivity() {
    private val mDisposables = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(getLayoutId())

        parseExtra(intent)

        initView()

        setClickListener()
    }

    open fun parseExtra(intent: Intent?) {}

    abstract fun initView()

    open fun setClickListener() {}

    @LayoutRes
    abstract fun getLayoutId(): Int

    fun showProgressDialog(cancelable: Boolean) {
        if (cancelable) {
        } else {
        }
    }

    fun closeProgressDialog() {

    }

    /**
     * 防抖动
     */
    fun View.onClickEvent(action: () -> Unit) {
        RxView.clicks(this)
            .throttleFirst(2, TimeUnit.SECONDS)
            .subscribe {
                action.invoke()
            }.gather()
    }

    fun showToast(msg: String?) {
        ToastUtils.showToast(text = msg)
    }

    fun showToast(resId: Int) {
        ToastUtils.showToast(resId = resId)
    }

    fun Disposable.gather() {
        mDisposables.add(this)
    }

    override fun onStop() {
        super.onStop()
        mDisposables.clear()
    }
}