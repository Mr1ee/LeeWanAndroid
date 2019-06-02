package com.lee.leewanandroid.entities.article

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class LoginData(
    @SerializedName("username") var username: String? = null,
    @SerializedName("password") var password: String? = null,
    @SerializedName("icon") var icon: String? = null,
    @SerializedName("type") var type: Int = 0,
    @SerializedName("collectIds") var collectIds: List<Int>? = null
) : Serializable