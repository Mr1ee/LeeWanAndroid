package com.lee.leewanandroid.ui.home

import com.lee.leewanandroid.entities.article.ArticleItemData
import com.lee.leewanandroid.entities.article.ArticleListData
import com.lee.leewanandroid.entities.article.BannerData
import com.lee.leewanandroid.ui.base.BasePresenter
import com.lee.leewanandroid.ui.base.BaseView

interface Contract {
    interface View : BaseView {
        fun showBanners(banners: List<BannerData>?)

        fun showTopArticle(topArticle: List<ArticleItemData>?)

        fun showArticles(articles: List<ArticleItemData>?, isRefresh: Boolean)
    }

    interface Presenter : BasePresenter<View> {
        fun loadBannerData(isShowStatusView: Boolean)

        fun loadTopArticle()

        fun loadArticles(isShowStatusView: Boolean)

        fun refreshLayout(isShowStatusView: Boolean)

        fun reload()

        fun getHomePagerData(isShowStatusView: Boolean)

        fun loadMore()
    }
}