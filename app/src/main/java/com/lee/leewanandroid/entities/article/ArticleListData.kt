package com.lee.leewanandroid.entities.article

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ArticleListData(
    @SerializedName("curPage") var curPage: Int = 0,
    @SerializedName("datas") var datas: List<ArticleItemData>? = null,
    @SerializedName("offset") var offset: Int = 0,
    @SerializedName("over") var over: Boolean = false,
    @SerializedName("pageCount") var pageCount: Int = 0,
    @SerializedName("size") var size: Int = 0,
    @SerializedName("total") var total: Int = 0
) : Serializable