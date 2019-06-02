package com.lee.leewanandroid.entities.article

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class KnowledgeTreeData(
    @SerializedName("children") var children: List<KnowledgeTreeData>? = null,
    @SerializedName("courseId") var courseId: Int = 0,
    @SerializedName("id") var id: Int = 0,
    @SerializedName("name") var name: String? = null,
    @SerializedName("order") var order: Int = 0,
    @SerializedName("parentChapterId") var parentChapterId: Int = 0,
    @SerializedName("visible") var visible: Int = 0
) : Serializable