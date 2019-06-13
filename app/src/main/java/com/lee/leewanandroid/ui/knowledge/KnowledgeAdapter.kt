package com.lee.leewanandroid.ui.knowledge

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.lee.leewanandroid.R
import com.lee.leewanandroid.entities.article.KnowledgeTreeData

class KnowledgeAdapter(layoutRes: Int, data: List<KnowledgeTreeData>) :
    BaseQuickAdapter<KnowledgeTreeData, BaseViewHolder>(layoutRes, data) {
    /**
     * Implement this method and use the helper to adapt the view to the given item.
     *
     * @param helper A fully initialized helper.
     * @param item   The item that needs to be displayed.
     */
    override fun convert(helper: BaseViewHolder?, item: KnowledgeTreeData?) {
        helper?.let {
            item?.run {
                if (name.isNullOrEmpty()) {
                    return
                }

                it.setText(R.id.knowledge_title, name)

                children?.let { child ->
                    val childTitles = StringBuilder()
                    for (data in child) {
                        childTitles.append(data.name).append("   ")
                    }
                    it.setText(R.id.title_child, childTitles.toString())
                }
            }
        }
    }
}