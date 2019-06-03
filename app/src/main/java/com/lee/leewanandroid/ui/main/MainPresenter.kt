package com.lee.leewanandroid.ui.main

import com.lee.leewanandroid.data.RemoteRepo
import com.lee.leewanandroid.entities.article.ArticleItemData
import com.lee.leewanandroid.net.BaseResponse
import com.lee.leewanandroid.utils.Logger
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers

class MainPresenter(override var view: Contract.View) : Contract.Presenter {
    companion object {
        private var TAG = MainPresenter::class.java.simpleName
    }

    private val mDisposables = CompositeDisposable()
    private val repo = RemoteRepo.instance

    private var currentPage: Int = 0
    private var isRefresh = true

    override fun subscribe() {
    }

    override fun unsubscribe() {
        mDisposables.clear()
    }

    override fun loadBannerData(isShowStatusView: Boolean) {
        repo
            .bannerData.subscribeOn(
            Schedulers.io()
        ).observeOn(AndroidSchedulers.mainThread())
            .map {
                if (it.errorCode == BaseResponse.RESULT_CODE_SUCCESS) {
                    it.data
                } else {
                    throw Exception(it.errorMsg)
                }
            }
            .subscribe({
                view.showBanners(it)
            }, {
                Logger.e(TAG, "", it)
            }).gather()
    }

    override fun loadTopArticle() {
        repo
            .topArticles.subscribeOn(
            Schedulers.io()
        ).observeOn(AndroidSchedulers.mainThread())
            .map {
                if (it.errorCode == BaseResponse.RESULT_CODE_SUCCESS) {
                    it.data
                } else {
                    throw Exception(it.errorMsg)
                }
            }
            .subscribe({
                view.showTopArticle(it)
            }, {
                Logger.e(TAG, "", it)
            }).gather()
    }

    override fun loadArticles(isShowStatusView: Boolean) {
        repo
            .getArticleList(currentPage)
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
                view.showArticles(it?.datas, isRefresh)
            }, {
                Logger.e(TAG, "", it)
            }).gather()
    }

    override fun getHomePagerData(isShowStatusView: Boolean) {
        loadBannerData(isShowStatusView)
        Observable.zip(repo.topArticles.map {
            if (it.errorCode == BaseResponse.RESULT_CODE_SUCCESS) {
                it.data
            } else {
                throw Exception(it.errorMsg)
            }
        }, repo.getArticleList(0).map {
            if (it.errorCode == BaseResponse.RESULT_CODE_SUCCESS) {
                it.data?.datas
            } else {
                throw Exception(it.errorMsg)
            }
        }, object :
            BiFunction<List<ArticleItemData>?, List<ArticleItemData>?, List<ArticleItemData>?> {
            override fun apply(
                topArticle: List<ArticleItemData>,
                articles: List<ArticleItemData>
            ): List<ArticleItemData> {
                val list: ArrayList<ArticleItemData> = topArticle as ArrayList<ArticleItemData>
                list.addAll(articles)
                return list
            }
        }).subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread()).subscribe({
                view.showArticles(it, isRefresh)
            }, {
                Logger.e(TAG, "", it)
            }).gather()
    }

    override fun loadMore() {
        isRefresh = false
        currentPage++
        loadArticles(false)
    }

    override fun refreshLayout(isShowStatusView: Boolean) {
        isRefresh = true
        currentPage = 0
        getHomePagerData(isShowStatusView)
    }

    override fun reload() {
        refreshLayout(true)
    }

    private fun Disposable.gather() {
        mDisposables.add(this)
    }
}