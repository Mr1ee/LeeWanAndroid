package com.lee.leewanandroid.entities.article

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class TopSearchData(
    @SerializedName("id") var id: Int = 0,
    @SerializedName("link") var link: String? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("order") var order: Int = 0,
    @SerializedName("visible") var visible: Int = 0
) : Serializable