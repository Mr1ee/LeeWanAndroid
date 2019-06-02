package com.lee.leewanandroid.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.disposables.CompositeDisposable
import java.util.concurrent.TimeUnit

abstract class BaseFragment : Fragment() {

    private val mDisposables = CompositeDisposable()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view)
    }

    abstract fun initView(view: View)

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

    override fun onStop() {
        super.onStop()
        mDisposables.clear()
    }
}