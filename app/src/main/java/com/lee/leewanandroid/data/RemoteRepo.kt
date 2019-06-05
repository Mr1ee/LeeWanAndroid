package com.lee.leewanandroid.data

import android.content.Context
import android.content.SharedPreferences
import com.lee.leewanandroid.Constants
import com.lee.leewanandroid.WanAndroidApp
import com.lee.leewanandroid.entities.article.*
import com.lee.leewanandroid.entities.todo.TodoItemData
import com.lee.leewanandroid.entities.todo.TodoListData
import com.lee.leewanandroid.net.BaseResponse
import com.lee.leewanandroid.net.RetrofitServiceManager
import io.reactivex.Observable

class RemoteRepo : DataSource, PreferenceHelper {

    companion object {
        val instance: RemoteRepo by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            RemoteRepo()
        }
    }

    private val mPreferences: SharedPreferences = WanAndroidApp.mContext.getSharedPreferences(
        Constants.MY_SHARED_PREFERENCE, Context.MODE_PRIVATE
    )

    override var loginStatus: Boolean
        get() = mPreferences.getBoolean(Constants.LOGIN_STATUS, false)
        set(value) {
            mPreferences.edit().putBoolean(Constants.LOGIN_STATUS, value).apply()
        }
    override var loginAccount: String
        get() = mPreferences.getString(Constants.ACCOUNT, "") ?: ""
        set(value) {
            mPreferences.edit().putString(Constants.ACCOUNT, value).apply()
        }
    override var isNightMode: Boolean
        get() = mPreferences.getBoolean(Constants.NIGHT_MODE, false)
        set(value) {
            mPreferences.edit().putBoolean(Constants.NIGHT_MODE, value).apply()
        }

    private val mService = RetrofitServiceManager.getInstance().create(ApiService::class.java)
    /**
     * 广告栏
     * https://www.wanandroid.com/banner/json
     *
     * @return 广告栏数据
     */
    override val bannerData: Observable<BaseResponse<List<BannerData>>>
        get() = mService.bannerData
    /**
     * 获取首页置顶文章列表
     * https://www.wanandroid.com/article/top/json
     */
    override val topArticles: Observable<BaseResponse<List<ArticleItemData>>>
        get() = mService.topArticles
    /**
     * 常用网站
     * https://www.wanandroid.com/friend/json
     *
     * @return 常用网站数据
     */
    override val usefulSites: Observable<BaseResponse<List<UsefulSiteData>>>
        get() = mService.usefulSites
    /**
     * 热搜
     * https://www.wanandroid.com//hotkey/json
     *
     * @return 热门搜索数据
     */
    override val topSearchData: Observable<BaseResponse<List<TopSearchData>>>
        get() = mService.topSearchData
    /**
     * 导航
     * https://www.wanandroid.com/navi/json
     *
     * @return 导航数据
     */
    override val navigationListData: Observable<BaseResponse<List<NavigationListData>>>
        get() = mService.navigationListData
    /**
     * 项目分类
     * https://www.wanandroid.com/project/tree/json
     *
     * @return 项目分类数据
     */
    override val projectTreeData: Observable<BaseResponse<List<ProjectTreeData>>>
        get() = mService.projectTreeData
    /**
     * 获取公众号列表
     * https://wanandroid.com/wxarticle/chapters/json
     *
     * @return 公众号列表数据
     */
    override val wxChapterListData: Observable<BaseResponse<List<WxChapterData>>>
        get() = mService.wxChapterListData
    /**
     * 知识体系
     * https://www.wanandroid.com/tree/json
     *
     * @return 知识体系数据
     */
    override val knowledgeTreeData: Observable<BaseResponse<List<KnowledgeTreeData>>>
        get() = mService.knowledgeTreeData

    /**
     * 获取文章列表
     * https://www.wanandroid.com/article/list/0/json
     *
     * @param pageNum
     */
    override fun getArticleList(pageNum: Int): Observable<BaseResponse<ArticleListData>> {
        return mService.getArticleList(pageNum)
    }

    /**
     * 搜索
     * https://www.wanandroid.com/article/query/0/json
     *
     * @param page page
     * @param k    POST search key
     * @return 搜索数据
     */
    override fun getSearchResultList(
        page: Int,
        k: String
    ): Observable<BaseResponse<ArticleListData>> {
        return mService.getSearchResultList(page, k)
    }

    /**
     * 登录
     * https://www.wanandroid.com/user/login
     *
     * @param username user name
     * @param password password
     * @return 登录数据
     */
    override fun login(username: String, password: String): Observable<BaseResponse<LoginData>> {
        return mService.login(username, password)
    }

    /**
     * 注册
     * https://www.wanandroid.com/user/register
     *
     * @param username   user name
     * @param password   password
     * @param repassword re password
     * @return 注册数据
     */
    override fun register(
        username: String,
        password: String,
        repassword: String
    ): Observable<BaseResponse<LoginData>> {
        return mService.register(username, password, repassword)
    }

    /**
     * 退出登录
     * https://www.wanandroid.com/user/logout/json
     *
     * @return 登录数据
     */
    override fun logout(): Observable<BaseResponse<LoginData>> {
        return mService.logout()
    }

    /**
     * 收藏站内文章
     * https://www.wanandroid.com/lg/collect/1165/json
     *
     * @param id article id
     * @return 收藏站内文章数据
     */
    override fun addCollectArticle(id: Int): Observable<BaseResponse<ArticleListData>> {
        return mService.addCollectArticle(id)
    }

    /**
     * 收藏站外文章
     * https://www.wanandroid.com/lg/collect/add/json
     *
     * @param title  title
     * @param author author
     * @param link   link
     * @return 收藏站外文章数据
     */
    override fun addCollectOutsideArticle(
        title: String,
        author: String,
        link: String
    ): Observable<BaseResponse<ArticleListData>> {
        return mService.addCollectOutsideArticle(title, author, link)
    }

    /**
     * 获取收藏列表
     * https://www.wanandroid.com/lg/collect/list/0/json
     *
     * @param page page number
     * @return 收藏列表数据
     */
    override fun getCollectList(page: Int): Observable<BaseResponse<ArticleListData>> {
        return mService.getCollectList(page)
    }

    /**
     * 文章列表中取消收藏文章
     * https://www.wanandroid.com/lg/uncollect_originId/2333/json
     *
     * @param id 列表中文章的id
     * @return 取消站内文章数据
     */
    override fun cancelCollectArticle(id: Int): Observable<BaseResponse<ArticleListData>> {
        return mService.cancelCollectArticle(id)
    }

    /**
     * 收藏列表中取消收藏文章
     * https://www.wanandroid.com/lg/uncollect/2805/json
     *
     * @param id       article id
     * @param originId originId 代表的是你收藏之前的那篇文章本身的id；
     * 但是收藏支持主动添加，这种情况下，没有originId则为-1
     * @return 取消收藏列表中文章数据
     */
    override fun cancelCollectInCollectPage(
        id: Int,
        originId: Int
    ): Observable<BaseResponse<ArticleListData>> {
        return mService.cancelCollectInCollectPage(id, originId)
    }

    /**
     * 项目列表数据
     * https://www.wanandroid.com/project/list/1/json?cid=294
     *
     * @param page page num
     * @param cid  child page id
     * @return 项目列表数据
     */
    override fun getProjectListData(
        page: Int,
        cid: Int
    ): Observable<BaseResponse<ArticleListData>> {
        return mService.getProjectListData(page, cid)
    }

    /**
     * 获取当前公众号的数据
     * https://wanandroid.com/wxarticle/list/405/1/json
     *
     * @param id
     * @param page
     * @return 获取当前公众号的数据
     */
    override fun getWxArticlesData(id: Int, page: Int): Observable<BaseResponse<ArticleListData>> {
        return mService.getWxArticlesData(id, page)
    }

    /**
     * 指定搜索内容，搜索当前公众号的某页的此类数据
     * https://wanandroid.com/wxarticle/list/405/1/json?k=Java
     *
     * @param id
     * @param page
     * @param k
     * @return 指定搜索内容，搜索当前公众号的某页的此类数据
     */
    override fun getWxSearchData(
        id: Int,
        page: Int,
        k: String
    ): Observable<BaseResponse<ArticleListData>> {
        return mService.getWxSearchData(id, page, k)
    }

    /**
     * 知识体系下的文章
     * https://www.wanandroid.com/article/list/0?cid=60
     *
     * @param page page num
     * @param cid  second page id
     * @return 知识体系文章数据
     */
    override fun getKnowledgeListData(
        page: Int,
        cid: Int
    ): Observable<BaseResponse<ArticleListData>> {
        return mService.getKnowledgeListData(page, cid)
    }

    /**
     * 获取TODO列表
     * https://www.wanandroid.com/lg/to\do/v2/list/{page}/json
     *
     *
     * 页码从1开始，拼接在url 上
     * status 状态， 1-完成；0未完成; 默认全部展示；
     * type 创建时传入的类型, 默认全部展示
     * priority 创建时传入的优先级；默认全部展示
     * orderby 1:完成日期顺序；2.完成日期逆序；3.创建日期顺序；4.创建日期逆序(默认)；（1和2只能获取到已完成的TODO）
     *
     * @return
     */
    override fun getTodoListData(
        page: Int,
        map: Map<String, Any>
    ): Observable<BaseResponse<TodoListData>> {
        return mService.getTodoListData(page, map)
    }

    /**
     * 新增一条TODO
     * https://www.wanandroid.com/lg/to\do/add/json
     *
     *
     * title: 新增标题（必须）
     * content: 新增详情（可选）
     * date: 2018-08-01 预定完成时间（不传默认当天，建议传）
     * type: 大于0的整数（可选）；
     * priority 大于0的整数（可选）；
     *
     * @return
     */
    override fun addTodo(map: Map<String, Any>): Observable<BaseResponse<TodoItemData>> {
        return mService.addTodo(map)
    }

    /**
     * 更新一条TODO
     * https://www.wanandroid.com/lg/to\do/update/{id}/json
     *
     *
     * id: 拼接在链接上，为唯一标识
     * title: 更新标题 （必须）
     * content: 新增详情（必须）
     * date: 2018-08-01（必须）
     * status: 0 // 0为未完成，1为完成
     * type: ；
     * priority: ；
     *
     * @return
     */
    override fun updateTodo(
        id: Int,
        map: Map<String, Any>
    ): Observable<BaseResponse<TodoItemData>> {
        return mService.updateTodo(id, map)
    }

    /**
     * 删除一条TODO
     * https://www.wanandroid.com/lg/to\do/delete/{id}/json
     *
     *
     * id: 拼接在链接上，为唯一标识
     *
     * @return
     */
    override fun deleteTodo(id: Int): Observable<BaseResponse<TodoItemData>> {
        return mService.deleteTodo(id)
    }

    /**
     * 仅更新完成状态TODO
     * https://www.wanandroid.com/lg/to\do/done/{id}/json
     *
     *
     * id: 拼接在链接上，为唯一标识
     * status: 0或1，传1代表未完成到已完成，反之则反之。
     * @return
     */
    override fun updateTodoStatus(id: Int, status: Int): Observable<BaseResponse<TodoItemData>> {
        return mService.updateTodoStatus(id, status)
    }
}