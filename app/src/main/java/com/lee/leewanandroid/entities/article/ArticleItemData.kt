package com.lee.leewanandroid.entities.article

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ArticleItemData(
    @SerializedName("apkLink") var apkLink: String? = null,
    @SerializedName("author") var author: String? = null,
    @SerializedName("chapterId") var chapterId: Int = 0,
    @SerializedName("chapterName") var chapterName: String? = null,
    @SerializedName("collect") var collect: Boolean = false,
    @SerializedName("courseId") var courseId: Int = 0,
    @SerializedName("desc") var desc: String? = null,
    @SerializedName("envelopePic") var envelopePic: String? = null,
    @SerializedName("id") var id: Int = 0,
    @SerializedName("link") var link: String? = null,
    @SerializedName("niceDate") var niceDate: String? = null,
    @SerializedName("origin") var origin: String? = null,
    @SerializedName("projectLink") var projectLink: String? = null,
    @SerializedName("superChapterId") var superChapterId: Int = 0,
    @SerializedName("superChapterName") var superChapterName: String? = null,
    @SerializedName("publishTime") var publishTime: Long = 0,
    @SerializedName("title") var title: String? = null,
    @SerializedName("visible") var visible: Int = 0,
    @SerializedName("zan") var zan: Int = 0,
    @SerializedName("type") var type: Int = 0,
    @SerializedName("fresh") var fresh: Boolean = false,
    @SerializedName("tags") var tags: List<ArticleTagsDate>? = null,
    @SerializedName("userId") var userId: Int = 0,
    @SerializedName("originId") var originId: Int = 0
) : Serializable

data class ArticleTagsDate(
    @SerializedName("name") var name: String? = null,
    @SerializedName("url") var url: String? = null
) : Serializable