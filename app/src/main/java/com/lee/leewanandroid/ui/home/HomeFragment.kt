package com.lee.leewanandroid.ui.home

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.lee.leewanandroid.R
import com.lee.leewanandroid.entities.article.ArticleItemData
import com.lee.leewanandroid.entities.article.BannerData
import com.lee.leewanandroid.ui.article.ArticleDetailActivity
import com.lee.leewanandroid.ui.base.BaseFragment
import com.lee.leewanandroid.utils.Logger
import com.lee.leewanandroid.widget.BannerGlideImageLoader
import com.youth.banner.BannerConfig
import com.youth.banner.Transformer
import kotlinx.android.synthetic.main.fragment_main_page.*
import kotlinx.android.synthetic.main.fragment_main_page.view.*
import kotlinx.android.synthetic.main.main_head_banner.*

class HomeFragment : BaseFragment(), Contract.View {
    companion object {
        private var TAG = HomeFragment::class.java.simpleName

        fun newInstance(): HomeFragment = HomeFragment()
    }

    private lateinit var mPresenter: HomePresenter
    private lateinit var mAdapter: ArticleListAdapter

    override fun setLoadingStatus(active: Boolean) {
        if (active) {

        } else {

        }
    }

    override fun showBanners(banners: List<BannerData>?) {
        banners?.let {
            val mBanner = head_banner
            val bannerImageList = ArrayList<String>()
            val mBannerTitleList = ArrayList<String>()
            val mBannerUrlList = ArrayList<String>()
            val bannerIdList = ArrayList<Int>()
            for (bannerData in it) {
                bannerData.title?.let { title1 ->
                    mBannerTitleList.add(title1)
                } ?: mBannerTitleList.add("")

                bannerData.imagePath?.let { imagePath1 ->
                    bannerImageList.add(imagePath1)
                } ?: bannerImageList.add("")

                bannerData.url?.let { url1 ->
                    mBannerUrlList.add(url1)
                } ?: mBannerUrlList.add("")

                bannerIdList.add(bannerData.id)
            }
            //设置banner样式
            mBanner.setBannerStyle(BannerConfig.NUM_INDICATOR_TITLE)
            //设置图片加载器
            mBanner.setImageLoader(BannerGlideImageLoader())
            //设置图片集合
            mBanner.setImages(bannerImageList)
            //设置banner动画效果
            mBanner.setBannerAnimation(Transformer.Accordion)
            //设置标题集合（当banner样式有显示title时）
            mBanner.setBannerTitles(mBannerTitleList)
            //设置自动轮播，默认为true
            mBanner.isAutoPlay(true)
            //设置轮播时间
            mBanner.setDelayTime(2500)
            //设置指示器位置（当banner模式中有指示器时）
            mBanner.setIndicatorGravity(BannerConfig.CENTER)

            mBanner.setOnBannerListener { i ->
                context?.run {
                    ArticleDetailActivity.navigation(
                        this, bannerIdList[i],
                        mBannerTitleList[i], mBannerUrlList[i]
                    )
                }
            }
            //banner设置方法全部调用完毕时最后调用
            mBanner.start()
        }
    }

    override fun showTopArticle(topArticle: List<ArticleItemData>?) {
        Logger.d(TAG, topArticle?.toString())
    }

    override fun showArticles(articles: List<ArticleItemData>?, isRefresh: Boolean) {
        Logger.d(TAG, articles?.toString())
        articles?.let {
            if (isRefresh) {
                mAdapter.replaceData(it)
            } else {
                mAdapter.addData(it)
            }
        }
    }

    override fun initView(view: View) {
        initRefreshLayout(view)
        initRecyclerView(view)

        mPresenter = HomePresenter(this)
        mPresenter.refreshLayout(true)
    }

    override fun getLayoutId(): Int = R.layout.fragment_main_page

    private fun initRecyclerView(view: View) {
        view.rv_home_pager.run {
            layoutManager = LinearLayoutManager(activity)
            setHasFixedSize(true)

            mAdapter = ArticleListAdapter(
                R.layout.item_article_card,
                ArrayList()
            ).apply {
                val headerBanner =
                    layoutInflater.inflate(R.layout.main_head_banner, this@run, false)
                setHeaderView(headerBanner)
                setOnItemChildClickListener { _, child, position ->
                    clickChildEvent(
                        child,
                        position
                    )
                }
                setOnItemClickListener { _, _, position ->
                    startArticleDetailActivity(position)
                }
            }
            adapter = mAdapter
        }

    }

    private fun startArticleDetailActivity(position: Int) {
        context?.run {
            mAdapter.data[position]?.let {
                ArticleDetailActivity.navigation(this, true, it)
            }
        }
    }

    private fun clickChildEvent(view: View?, position: Int) {
        when (view?.id) {
            R.id.tv_article_chapterName -> {
            }
            R.id.iv_article_like -> collectClickEvent(position)
            R.id.tv_article_tag -> {
            }
            else -> {
            }
        }
    }

    private fun collectClickEvent(position: Int) {
        if (mAdapter.data[position].collect) {
            mAdapter.data[position].collect = false
            mAdapter.setData(position, mAdapter.data[position])
        } else {
            mAdapter.data[position].collect = true
            mAdapter.setData(position, mAdapter.data[position])
        }

    }

    private fun initRefreshLayout(view: View) {
        view.smart_refresh_layout.setOnRefreshListener { refreshLayout ->
            mPresenter.refreshLayout(false)
            refreshLayout.finishRefresh()
        }
        view.smart_refresh_layout.setOnLoadMoreListener { refreshLayout ->
            mPresenter.loadMore()
            refreshLayout.finishLoadMore()
        }
    }

    fun jumpToTheTop() {
        rv_home_pager?.smoothScrollToPosition(0)
    }

}