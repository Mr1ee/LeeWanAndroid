package com.lee.leewanandroid.entities.article

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class WxChapterData(
    @SerializedName("courseId") var courseId: Int = 0,
    @SerializedName("id") var id: Int = 0,
    @SerializedName("name") var name: String? = null,
    @SerializedName("order") var order: Int = 0,
    @SerializedName("parentChapterId") var parentChapterId: Int = 0,
    @SerializedName("userControlSetTop") var userControlSetTop: Boolean = false,
    @SerializedName("visible") var visible: Int = 0
) : Serializable