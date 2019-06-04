package com.lee.leewanandroid.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.jakewharton.rxbinding2.view.RxView
import com.lee.leewanandroid.widget.ToastUtils
import io.reactivex.disposables.CompositeDisposable
import java.util.concurrent.TimeUnit

abstract class BaseFragment : Fragment() {

    private val mDisposables = CompositeDisposable()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(getLayoutId(), container, false)
        initView(view)
        return view
    }

    abstract fun initView(view: View)
    abstract fun getLayoutId(): Int

    /**
     * 防抖动
     */
    fun View.onClickEvent(action: () -> Unit) {
        mDisposables.add(
            RxView.clicks(this)
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