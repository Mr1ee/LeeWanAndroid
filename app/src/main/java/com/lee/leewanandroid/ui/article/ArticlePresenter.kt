package com.lee.leewanandroid.ui.article

import android.Manifest
import com.lee.leewanandroid.ui.base.BasePresenter
import com.lee.leewanandroid.ui.base.IPresenter
import com.lee.leewanandroid.ui.base.IView
import com.tbruyelle.rxpermissions2.RxPermissions

class ArticlePresenter(override var mView: Contract.View) : Contract.Presenter<Contract.View>,
    BasePresenter<Contract.View>() {

    companion object {
        private var TAG = ArticlePresenter::class.java.simpleName
    }

    override fun shareEventWithPermissionVerify(rxPermissions: RxPermissions) {
        rxPermissions
            .request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            .subscribe { granted ->
                if (granted == true) {
                    mView.shareArticle()
                } else {
                    mView.shareError()
                }
            }.gather()
    }

    override fun subscribe() {
    }

}