package com.lee.leewanandroid.ui.knowledge

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.lee.leewanandroid.R
import com.lee.leewanandroid.entities.article.KnowledgeTreeData
import com.lee.leewanandroid.ui.base.BaseMvpFragment
import kotlinx.android.synthetic.main.fragment_konwledge_page.*
import kotlinx.android.synthetic.main.fragment_konwledge_page.view.*

class KnowledgeFragment : BaseMvpFragment<Contract.View, KnowledgePresenter>(), Contract.View {
    companion object {
        private var TAG = KnowledgeFragment::class.java.simpleName

        fun newInstance() = KnowledgeFragment()
    }

    override val mPresenter: KnowledgePresenter = KnowledgePresenter(this)

    private lateinit var knowledgeAdapter: KnowledgeAdapter

    override fun getLayoutId(): Int = R.layout.fragment_konwledge_page

    override fun initView(view: View) {
        view.apply {
            mPresenter.getKnowledgeTreeData()
            knowledgeAdapter = KnowledgeAdapter(R.layout.item_knowledge_tree_list, arrayListOf())
            knowledgeAdapter.setOnItemClickListener({ adapter, view, position -> })

            rv_knowledge_pager.layoutManager = LinearLayoutManager(activity)
            rv_knowledge_pager.setHasFixedSize(true)
            rv_knowledge_pager.adapter = knowledgeAdapter
        }
    }

    fun jumpToTheTop() {
        rv_knowledge_pager.smoothScrollToPosition(0)
    }


    override fun showKnowledgeTreeData(knowledgeTreeData: List<KnowledgeTreeData>) {
        if (knowledgeAdapter.data.size < knowledgeTreeData.size) {
            knowledgeAdapter.replaceData(knowledgeTreeData)
        }
    }

    private fun initRefreshLayout() {
        smart_refresh_layout.setOnRefreshListener { refreshLayout ->
            mPresenter.getKnowledgeTreeData()
            refreshLayout.finishRefresh()
        }
    }
}