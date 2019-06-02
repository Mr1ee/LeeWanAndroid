package com.lee.leewanandroid.ui.base

interface BasePresenter<T> {
    var view: T

    fun subscribe()

    fun unsubscribe()
}