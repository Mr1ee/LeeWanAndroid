package com.lee.leewanandroid.net

import java.io.Serializable

data class BaseResponse<T>(
    val errorCode: Int = 0,
    val errorMsg: String? = null,
    val data: T? = null
) : Serializable {
    companion object {
        const val RESULT_CODE_SUCCESS = 0
        const val RESULT_CODE_FAILED = 1
    }
}