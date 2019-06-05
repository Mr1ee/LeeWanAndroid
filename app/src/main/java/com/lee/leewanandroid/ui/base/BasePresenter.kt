package com.lee.leewanandroid.ui.base

import com.lee.leewanandroid.data.PreferenceHelper
import com.lee.leewanandroid.data.RemoteRepo
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BasePresenter<T : IView> : IPresenter<T>, PreferenceHelper {

    private val mDisposables = CompositeDisposable()
    open var repo = RemoteRepo.instance

    override fun detachView() {
        mDisposables.clear()
    }

    override var loginStatus: Boolean
        get() = repo.loginStatus
        set(value) {
            repo.loginStatus = value
        }

    override var loginAccount: String
        get() = repo.loginAccount
        set(value) {
            repo.loginAccount = value
        }

    override var isNightMode: Boolean
        get() = repo.isNightMode
        set(value) {
            repo.isNightMode = value
        }

    fun Disposable.gather() {
        mDisposables.add(this)
    }
}