package com.lee.leewanandroid.ui.home

import android.os.Build
import android.text.Html
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.Nullable
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.lee.leewanandroid.R
import com.lee.leewanandroid.entities.article.ArticleItemData
import com.lee.leewanandroid.utils.ImageLoader

class ArticleListAdapter(layoutResId: Int, @Nullable data: List<ArticleItemData>) :
    BaseQuickAdapter<ArticleItemData, BaseViewHolder>(layoutResId, data) {

    override fun convert(helper: BaseViewHolder, item: ArticleItemData) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            helper.setText(
                R.id.tv_article_title,
                Html.fromHtml(item.title, Html.FROM_HTML_MODE_LEGACY)
            ).setText(R.id.tv_article_author, item.author).setImageResource(
                R.id.iv_article_like,
                if (item.collect) R.drawable.ic_like else R.drawable.ic_like_not
            )
        } else {
            @Suppress("DEPRECATION")
            helper.setText(
                R.id.tv_article_title,
                Html.fromHtml(item.title)
            ).setText(R.id.tv_article_author, item.author).setImageResource(
                R.id.iv_article_like,
                if (item.collect) R.drawable.ic_like else R.drawable.ic_like_not
            )
        }
        if (!item.chapterName.isNullOrEmpty()) {
            val classifyName = item.superChapterName + " / " + item.chapterName
            helper.setText(R.id.tv_article_chapterName, classifyName)
        }
        if (!item.niceDate.isNullOrEmpty()) {
            helper.setText(R.id.tv_article_niceDate, item.niceDate)
        }
        helper.getView<TextView>(R.id.tv_article_top).visibility =
            if (item.type == 1) View.VISIBLE else View.GONE

        helper.getView<TextView>(R.id.tv_article_fresh).visibility =
            if (item.fresh) View.VISIBLE else View.GONE

        item.tags?.let {
            if (it.isNotEmpty()) {
                helper.setText(R.id.tv_article_tag, it[0].name)
                    .getView<TextView>(R.id.tv_article_tag).visibility = View.VISIBLE
            } else {
                helper.getView<TextView>(R.id.tv_article_tag).visibility = View.GONE
            }
        }

        if (!item.envelopePic.isNullOrEmpty()) {
            helper.getView<ImageView>(R.id.iv_article_thumbnail).visibility = View.VISIBLE
            item.envelopePic?.let {
                ImageLoader.load(
                    mContext,
                    it,
                    helper.getView(R.id.iv_article_thumbnail)
                )
            }

        } else {
            helper.getView<ImageView>(R.id.iv_article_thumbnail).visibility = View.GONE
        }

        helper.addOnClickListener(R.id.tv_article_chapterName)
        helper.addOnClickListener(R.id.iv_article_like)
        helper.addOnClickListener(R.id.tv_article_tag)

    }
}
