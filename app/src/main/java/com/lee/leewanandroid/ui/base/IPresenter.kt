package com.lee.leewanandroid.ui.base

interface IPresenter<T> {
    var view: T

    fun subscribe()

    fun unsubscribe()
}