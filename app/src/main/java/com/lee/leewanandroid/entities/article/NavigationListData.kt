package com.lee.leewanandroid.entities.article

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class NavigationListData(
    @SerializedName("articles") var articles: List<ArticleItemData>? = null,
    @SerializedName("cid") var cid: Int = 0,
    @SerializedName("name") var name: String? = null
) : Serializable