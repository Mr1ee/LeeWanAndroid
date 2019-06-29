package com.lee.leewanandroid.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.widget.TextView
import androidx.annotation.Nullable
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.FragmentTransaction
import com.lee.leewanandroid.Constants
import com.lee.leewanandroid.R
import com.lee.leewanandroid.ui.base.BaseActivity
import com.lee.leewanandroid.ui.home.HomeFragment
import com.lee.leewanandroid.ui.knowledge.KnowledgeFragment
import com.lee.leewanandroid.widget.ToastUtils
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_toolbar.*

class MainActivity : BaseActivity() {

    companion object {
        private var TAG = MainActivity::class.java.simpleName
    }

    private var mHomePagerFragment: HomeFragment? = null
    private var mKnowledgeFragment: KnowledgeFragment? = null
    private var mNavigationFragment: HomeFragment? = null
    private var mWxArticleFragment: HomeFragment? = null
    private var mProjectFragment: HomeFragment? = null

    private var mLastFgIndex = -1
    private var mCurrentFgIndex = 0
    private var clickTime: Long = 0

    private var mDialog: AlertDialog? = null

    override fun getLayoutId(): Int = R.layout.activity_main

    override fun initView() {
        initDrawerLayout()
        showFragment(mCurrentFgIndex)
        initNavigationView()
        initBottomNavigationView()
        main_floating_action_btn.onClickEvent {
            jumpToTheTop()
        }
    }

    private fun initBottomNavigationView() {
        bottom_navigation_view.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.tab_main_pager -> showFragment(Constants.TYPE_HOME_PAGER)
                R.id.tab_knowledge_hierarchy -> showFragment(Constants.TYPE_KNOWLEDGE)
                R.id.tab_navigation -> showFragment(Constants.TYPE_NAVIGATION)
                R.id.tab_wx_article -> showFragment(Constants.TYPE_WX_ARTICLE)
                R.id.tab_project -> showFragment(Constants.TYPE_PROJECT)
                else -> {
                }
            }
            true
        }
    }

    private fun initNavigationView() {
        val mUsTv = nav_view.getHeaderView(0).findViewById<TextView>(R.id.nav_header_login)
        mUsTv.text = getString(R.string.login)
        mUsTv.setOnClickListener {
            startActivity(Intent(this, AnimationActivity::class.java))
        }
    }

    private fun showFragment(index: Int) {
        mCurrentFgIndex = index
        val transaction = supportFragmentManager.beginTransaction()
        hideFragment(transaction)
        mLastFgIndex = index
        when (index) {
            Constants.TYPE_HOME_PAGER -> {
                toolbar_title.text = getString(R.string.home_pager)
                if (mHomePagerFragment == null) {
                    mHomePagerFragment = HomeFragment.newInstance()
                    mHomePagerFragment?.let {
                        transaction.add(R.id.fragment_group, it)
                    }
                }
                mHomePagerFragment?.let {
                    transaction.show(it)
                }
            }
            Constants.TYPE_KNOWLEDGE -> {
                toolbar_title.text = getString(R.string.knowledge_hierarchy)
                if (mKnowledgeFragment == null) {
                    mKnowledgeFragment = KnowledgeFragment.newInstance()
                    mKnowledgeFragment?.let {
                        transaction.add(R.id.fragment_group, it)
                    }
                }
                mKnowledgeFragment?.let {
                    transaction.show(it)
                }
            }
            Constants.TYPE_NAVIGATION -> {
                toolbar_title.text = getString(R.string.navigation)
                if (mNavigationFragment == null) {
                    mNavigationFragment = HomeFragment.newInstance()
                    mNavigationFragment?.let {
                        transaction.add(R.id.fragment_group, it)
                    }
                }
                mNavigationFragment?.let {
                    transaction.show(it)
                }
            }
            Constants.TYPE_WX_ARTICLE -> {
                toolbar_title.text = getString(R.string.wx_article)
                if (mWxArticleFragment == null) {
                    mWxArticleFragment = HomeFragment.newInstance()
                    mWxArticleFragment?.let {
                        transaction.add(R.id.fragment_group, it)
                    }
                }
                mWxArticleFragment?.let {
                    transaction.show(it)
                }
            }
            Constants.TYPE_PROJECT -> {
                toolbar_title.text = getString(R.string.project)
                if (mProjectFragment == null) {
                    mProjectFragment = HomeFragment.newInstance()
                    mProjectFragment?.let {
                        transaction.add(R.id.fragment_group, it)
                    }
                }
                mProjectFragment?.let {
                    transaction.show(it)
                }
            }

            else -> {
            }
        }
        transaction.commit()
    }

    private fun hideFragment(transaction: FragmentTransaction) {
        when (mLastFgIndex) {
            Constants.TYPE_HOME_PAGER -> mHomePagerFragment?.let {
                transaction.hide(it)
            }
            Constants.TYPE_KNOWLEDGE -> mKnowledgeFragment?.let {
                transaction.hide(it)
            }
            Constants.TYPE_NAVIGATION -> mNavigationFragment?.let {
                transaction.hide(it)
            }
            Constants.TYPE_WX_ARTICLE -> mWxArticleFragment?.let {
                transaction.hide(it)
            }
            Constants.TYPE_PROJECT -> mProjectFragment?.let {
                transaction.hide(it)
            }
            else -> {
            }
        }
    }

    private fun initDrawerLayout() {
        val toggle = ActionBarDrawerToggle(
            this,
            drawer_layout,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        toggle.syncState()
        drawer_layout.addDrawerListener(toggle)
    }

    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            mCurrentFgIndex = savedInstanceState.getInt(Constants.CURRENT_FRAGMENT_KEY)
        }
        super.onCreate(savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(Constants.CURRENT_FRAGMENT_KEY, mCurrentFgIndex)
    }

    private fun jumpToTheTop() {
        when (mCurrentFgIndex) {
            Constants.TYPE_HOME_PAGER -> mHomePagerFragment?.jumpToTheTop()
            Constants.TYPE_KNOWLEDGE -> mKnowledgeFragment?.jumpToTheTop()
            Constants.TYPE_WX_ARTICLE -> mWxArticleFragment?.jumpToTheTop()
            Constants.TYPE_NAVIGATION -> mNavigationFragment?.jumpToTheTop()
            Constants.TYPE_PROJECT -> mProjectFragment?.jumpToTheTop()
            else -> {
            }
        }
    }

    override fun onBackPressed() {
        val currentTime = System.currentTimeMillis()
        if (currentTime - clickTime > Constants.DOUBLE_INTERVAL_TIME) {
            ToastUtils.showToast(getString(R.string.double_click_exit_toast))
            clickTime = System.currentTimeMillis()
        } else {
            finish()
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_toolbar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}
