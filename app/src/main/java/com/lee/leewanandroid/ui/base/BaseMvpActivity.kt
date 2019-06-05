package com.lee.leewanandroid.ui.base

abstract class BaseMvpActivity<V : IView, P : IPresenter<V>> : BaseActivity(), IView {

    abstract val mPresenter: P

    override fun showLoading(cancelable: Boolean) {
        showProgressDialog(cancelable)
    }

    override fun hideLoading() {
        closeProgressDialog()
    }

    override fun showEmpty() {
    }

    override fun showNoNetwork() {
    }

    override fun showError() {
    }

    override fun showErrorMsg(errorMsg: String) {
    }

    override fun showContent() {
    }

    override fun handleLoginSuccess() {
    }

    override fun handleLogoutSuccess() {
    }

    override fun onDestroy() {
        mPresenter.detachView()
        super.onDestroy()
    }
}