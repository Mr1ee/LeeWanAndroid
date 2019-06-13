package com.lee.leewanandroid.ui.knowledge

import com.lee.leewanandroid.entities.article.KnowledgeTreeData
import com.lee.leewanandroid.ui.base.IPresenter
import com.lee.leewanandroid.ui.base.IView

interface Contract {

    interface View : IView {
        fun showKnowledgeTreeData(knowledgeTreeData: List<KnowledgeTreeData>)

    }

    interface Presenter : IPresenter<View> {
        fun getKnowledgeTreeData()
    }
}