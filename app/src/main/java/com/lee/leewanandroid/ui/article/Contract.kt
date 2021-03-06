package com.lee.leewanandroid.ui.article

import com.lee.leewanandroid.ui.base.IPresenter
import com.lee.leewanandroid.ui.base.IView
import com.tbruyelle.rxpermissions2.RxPermissions

interface Contract {

    interface View : IView {
        fun shareArticle()

        fun shareError()
    }

    interface Presenter<T : IView> : IPresenter<T> {
        fun shareEventWithPermissionVerify(rxPermissions: RxPermissions)

    }
}