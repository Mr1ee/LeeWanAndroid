package com.lee.leewanandroid.ui.base

interface IPresenter<T : IView> {
    var mView: T

    fun subscribe()

    fun detachView()
}