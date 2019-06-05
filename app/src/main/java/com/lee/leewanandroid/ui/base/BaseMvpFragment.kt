package com.lee.leewanandroid.ui.base


abstract class BaseMvpFragment<V : IView, P : IPresenter<V>> : BaseFragment(), IView {

    abstract val mPresenter: P

    override fun onDestroyView() {
        mPresenter.detachView()
        super.onDestroyView()
    }

    /**
     * Show error message
     *
     * @param errorMsg error message
     */
    override fun showErrorMsg(errorMsg: String) {
    }

    override fun showLoading(cancelable: Boolean) {
    }

    override fun hideLoading() {
    }

    override fun showError() {
    }

    override fun showNoNetwork() {
    }

    override fun showEmpty() {
    }

    override fun showContent() {
    }

    override fun handleLoginSuccess() {
    }

    override fun handleLogoutSuccess() {
    }
}