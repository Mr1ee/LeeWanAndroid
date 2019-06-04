package com.lee.leewanandroid.ui.base

import com.lee.leewanandroid.data.RemoteRepo
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BasePresenter<T> : IPresenter<T> {

    private val mDisposables = CompositeDisposable()
    open var repo = RemoteRepo.instance

    override fun unsubscribe() {
        mDisposables.clear()
    }

    fun Disposable.gather() {
        mDisposables.add(this)
    }
}