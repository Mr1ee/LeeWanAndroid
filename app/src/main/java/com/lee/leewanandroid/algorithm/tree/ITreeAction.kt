package com.lee.leewanandroid.algorithm.tree

/**
 *
 * @Description:    ITreeAction
 * @Author:         lihuayong
 * @CreateDate:     2019-06-17 18:23
 * @UpdateUser:
 * @UpdateDate:     2019-06-17 18:23
 * @UpdateRemark:
 * @Version:        1.0
 */
interface ITreeAction<T> {

    fun insert(value: T): Boolean

    fun remove(value: T): Boolean

    fun find(value: T): Boolean
}