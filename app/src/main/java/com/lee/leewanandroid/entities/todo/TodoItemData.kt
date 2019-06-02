package com.lee.leewanandroid.entities.todo

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class TodoItemData(
    @SerializedName("completeDate") var completeDate: String? = null,
    @SerializedName("completeDateStr") var completeDateStr: String? = null,
    @SerializedName("content") var content: String? = null,
    @SerializedName("date") var date: Long = 0,
    @SerializedName("dateStr") var dateStr: String? = null,
    @SerializedName("id") var id: Int = 0,
    @SerializedName("priority") var priority: Int = 0,
    @SerializedName("status") var status: Int = 0,
    @SerializedName("title") var title: String? = null,
    @SerializedName("type") var type: Int = 0,
    @SerializedName("userId") var userId: Int = 0
) : Serializable