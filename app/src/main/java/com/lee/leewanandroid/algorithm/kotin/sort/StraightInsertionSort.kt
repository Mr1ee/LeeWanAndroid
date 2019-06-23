package com.lee.leewanandroid.algorithm.kotin.sort

import com.lee.leewanandroid.algorithm.kotin.sort.interfaces.IInsetSort
import java.util.*

/**
 * 直接插入排序
 */
class StraightInsertionSort : IInsetSort<Int> {
    override var gab: Int = 1

    override fun sort(data: ArrayList<Int>): MutableList<Int> {
        println("algorithm " + this::class.java.simpleName + " start!!!")

        for (i in gab until data.size) {
            val t = data[i]
            var j = i
            while (j >= gab && data[j - gab] > t) {
                data[j] = data[j - gab]
                j -= gab
            }
            data[j] = t

            println("the $i time(s) sort, result : $data")
        }
        return data
    }
}