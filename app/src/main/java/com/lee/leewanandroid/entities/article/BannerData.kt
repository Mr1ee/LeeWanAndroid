package com.lee.leewanandroid.entities.article

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class BannerData(
    @SerializedName("id") var id: Int = 0,
    @SerializedName("url") var url: String? = null,
    @SerializedName("imagePath") var imagePath: String? = null,
    @SerializedName("title") var title: String? = null,
    @SerializedName("desc") var desc: String? = null,
    @SerializedName("isVisible") var isVisible: Int = 0,
    @SerializedName("order") var order: Int = 0,
    @SerializedName("type") var type: Int = 0
) : Serializable