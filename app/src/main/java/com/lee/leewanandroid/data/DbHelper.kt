package com.lee.leewanandroid.data

import com.lee.leewanandroid.entities.greedao.HistoryData

interface DbHelper {

    /**
     * Add search history data
     *
     * @param data  added string
     * @return  List<HistoryData>
    </HistoryData> */
    fun addHistoryData(data: String): List<HistoryData>

    /**
     * Clear all search history data
     */
    fun clearAllHistoryData()

    /**
     * Clear all search history data
     */
    fun deleteHistoryDataById(id: Long)

    /**
     * Load all history data
     *
     * @return List<HistoryData>
    </HistoryData> */
    fun loadAllHistoryData(): List<HistoryData>

}