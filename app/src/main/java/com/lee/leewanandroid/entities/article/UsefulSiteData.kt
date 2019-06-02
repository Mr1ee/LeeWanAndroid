package com.lee.leewanandroid.entities.article

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class UsefulSiteData(
    @SerializedName("id") var id: Int = 0,
    @SerializedName("name") var name: String? = null,
    @SerializedName("link") var link: String? = null,
    @SerializedName("visible") var visible: Int = 0,
    @SerializedName("order") var order: Int = 0,
    @SerializedName("icon") var icon: String? = null
) : Serializable