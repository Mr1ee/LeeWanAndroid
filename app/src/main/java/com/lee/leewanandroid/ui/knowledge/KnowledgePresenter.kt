package com.lee.leewanandroid.ui.knowledge

import com.lee.leewanandroid.net.BaseResponse
import com.lee.leewanandroid.ui.base.BasePresenter
import com.lee.leewanandroid.utils.Logger
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class KnowledgePresenter(override var mView: Contract.View) : Contract.Presenter,
    BasePresenter<Contract.View>() {

    companion object {
        private var TAG = KnowledgePresenter::class.java.simpleName
    }

    override fun getKnowledgeTreeData() {
        repo.knowledgeTreeData
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map {
                if (it.errorCode == BaseResponse.RESULT_CODE_SUCCESS) {
                    it.data
                } else {
                    throw Exception(it.errorMsg)
                }
            }
            .subscribe({
                it?.run {
                    mView.showKnowledgeTreeData(this)
                }
            }, {
                Logger.e(TAG, "", it)
            }).gather()
    }

    override fun subscribe() {
    }
}