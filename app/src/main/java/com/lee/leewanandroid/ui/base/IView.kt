package com.lee.leewanandroid.ui.base

interface IView {
    /**
     * Show error message
     *
     * @param errorMsg error message
     */
    fun showErrorMsg(errorMsg: String)

    fun showLoading(cancelable: Boolean)

    fun hideLoading()

    fun showError()

    fun showNoNetwork()

    fun showEmpty()

    fun showContent()

    fun handleLoginSuccess()

    fun handleLogoutSuccess()
}