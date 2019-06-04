package com.lee.leewanandroid.ui.article

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.Menu
import android.view.MenuItem
import android.webkit.WebChromeClient
import android.webkit.WebView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.appbar.AppBarLayout
import com.just.agentweb.AgentWeb
import com.just.agentweb.DefaultWebClient
import com.just.agentweb.NestedScrollAgentWebView
import com.lee.leewanandroid.Constants
import com.lee.leewanandroid.R
import com.lee.leewanandroid.entities.article.ArticleItemData
import com.lee.leewanandroid.ui.base.BaseActivity
import com.lee.leewanandroid.utils.Logger
import com.tbruyelle.rxpermissions2.RxPermissions
import kotlinx.android.synthetic.main.activity_article_detail.*
import kotlinx.android.synthetic.main.layout_toolbar.*

class ArticleDetailActivity : BaseActivity(), Contract.View {
    companion object {
        private var TAG = ArticleDetailActivity::class.java.simpleName
        private const val EXTRA_ARTICLE = "extra_article"
        private const val EXTRA_SHOW_COLLECT_ICON = "extra_show_ccollect_icon"

        fun navigation(
            context: Context,
            articleId: Int = -1,
            articleTitle: String = "",
            articleLink: String = "",
            isCollected: Boolean = false,
            showCollectIcon: Boolean = false
        ) {
            navigation(
                context,
                showCollectIcon,
                ArticleItemData(
                    id = articleId,
                    title = articleTitle,
                    link = articleLink,
                    collect = isCollected
                )
            )
        }

        fun navigation(context: Context, showCollectIcon: Boolean, article: ArticleItemData) {
            val intent = Intent(context, ArticleDetailActivity::class.java).apply {
                putExtras(Bundle().apply {
                    putBoolean(EXTRA_SHOW_COLLECT_ICON, showCollectIcon)
                    putSerializable(EXTRA_ARTICLE, article)
                })
            }
            context.startActivity(intent)
        }
    }

    private lateinit var mArticle: ArticleItemData
    private var mShowCollectIcon: Boolean = false

    private lateinit var mCollectItem: MenuItem

    private lateinit var mAgentWeb: AgentWeb

    private lateinit var mPresenter: ArticlePresenter


    override fun getLayoutId(): Int = R.layout.activity_article_detail

    override fun initView() {
        parseExtra(intent)

        mPresenter = ArticlePresenter(this)

        initToolbar()
        initAgentWeb()
    }

    private fun initToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.run {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowTitleEnabled(false)
            @Suppress("DEPRECATION")
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                toolbar_title.text = Html.fromHtml(mArticle.title, Html.FROM_HTML_MODE_LEGACY)
            } else {
                toolbar_title.text = Html.fromHtml(mArticle.title)
            }
            toolbar_title.isSelected = true
        }

        toolbar.setNavigationOnClickListener { onBackPressed() }
    }

    private fun initAgentWeb() {
        @Suppress("DEPRECATION")
        val webChromeClient = object : WebChromeClient() {
            override fun onReceivedTitle(view: WebView, title: String) {
                super.onReceivedTitle(view, title)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    toolbar_title.text = Html.fromHtml(title, Html.FROM_HTML_MODE_LEGACY)
                } else {
                    toolbar_title.text = Html.fromHtml(title)
                }
            }
        }

        val layoutParams = CoordinatorLayout.LayoutParams(-1, -1)
        layoutParams.behavior = AppBarLayout.ScrollingViewBehavior()
        val mNestedWebView = NestedScrollAgentWebView(this)
        mAgentWeb = AgentWeb.with(this)
            .setAgentWebParent(content_layout, layoutParams)
            .useDefaultIndicator()
            .setWebView(mNestedWebView)
            .setWebChromeClient(webChromeClient)
            .setMainFrameErrorView(R.layout.agentweb_error_page, -1)
            .setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.ASK)
            .createAgentWeb()
            .ready()
            .go(mArticle.link)
    }

    private fun parseExtra(intent: Intent?) {
        try {
            intent?.extras?.getSerializable(EXTRA_ARTICLE)?.let {
                mArticle = it as ArticleItemData
            } ?: throw IllegalArgumentException(
                "intent not has Article extras! plz call ArticleDetailActivity.navigation" +
                        " to start ArticleDetailActivity"
            )
            mShowCollectIcon = intent.extras?.getBoolean(EXTRA_SHOW_COLLECT_ICON) ?: false
        } catch (e: Exception) {
            Logger.e(TAG, "", e)
            this@ArticleDetailActivity.finish()
        }
    }

    override fun shareArticle() {
        val intent = Intent(Intent.ACTION_SEND)
        intent.putExtra(
            Intent.EXTRA_TEXT,
            getString(R.string.share_type_url, getString(R.string.app_name), title, mArticle.link)
        )
        intent.type = "text/plain"
        startActivity(intent)
    }

    override fun shareError() {
        showToast(R.string.write_permission_not_allowed)
    }

    override fun setLoadingStatus(active: Boolean) {
    }

    override fun onBackPressed() {
        if (!mAgentWeb.back()) {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_acticle_detail, menu)
        mCollectItem = menu.findItem(R.id.item_collect).apply {
            isVisible = mShowCollectIcon
            setIcon(if (mArticle.collect) R.drawable.ic_like_white else R.drawable.ic_like_not_white)
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_share -> mPresenter.shareEventWithPermissionVerify(RxPermissions(this))
            R.id.item_collect -> collectClickEvent()
            R.id.item_system_browser -> startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(mArticle.link)
                )
            )
            else -> {
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun collectClickEvent() {
    }

    /**
     * 让菜单同时显示图标和文字
     *
     * @param featureId Feature id
     * @param menu      Menu
     * @return menu if opened
     */
    override fun onMenuOpened(featureId: Int, menu: Menu?): Boolean {
        if (menu != null) {
            if (Constants.MENU_BUILDER.toLowerCase() == menu.javaClass.simpleName.toLowerCase()) {
                try {
                    @SuppressLint("PrivateApi")
                    val method = menu.javaClass.getDeclaredMethod(
                        "setOptionalIconsVisible",
                        java.lang.Boolean.TYPE
                    )
                    method.isAccessible = true
                    method.invoke(menu, true)
                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }
        }
        return super.onMenuOpened(featureId, menu)
    }

    override fun onResume() {
        mAgentWeb.webLifeCycle.onResume()
        super.onResume()
    }

    override fun onPause() {
        mAgentWeb.webLifeCycle.onPause()
        super.onPause()
    }

    override fun onDestroy() {
        mAgentWeb.webLifeCycle.onDestroy()
        super.onDestroy()
    }
}