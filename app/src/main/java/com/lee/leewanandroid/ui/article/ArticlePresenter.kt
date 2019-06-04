package com.lee.leewanandroid.ui.article

import android.Manifest
import com.lee.leewanandroid.ui.base.BasePresenter
import com.tbruyelle.rxpermissions2.RxPermissions

class ArticlePresenter(override var view: Contract.View) : Contract.Presenter,
    BasePresenter<Contract.View>() {

    companion object {
        private var TAG = ArticlePresenter::class.java.simpleName
    }

    override fun shareEventWithPermissionVerify(rxPermissions: RxPermissions) {
        rxPermissions
            .request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            .subscribe { granted ->
                if (granted == true) {
                    view.shareArticle()
                } else {
                    view.shareError()
                }
            }.gather()
    }

    override fun subscribe() {
    }

}