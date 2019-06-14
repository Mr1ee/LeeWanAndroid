package com.lee.leewanandroid.algorithm.sort

import com.lee.leewanandroid.algorithm.sort.interfaces.ISort
import java.util.*

/**
 * 直接选择排序
 */
class SelectionSort : ISort<Int> {

    override fun sort(data: ArrayList<Int>): MutableList<Int> {
        println("algorithm " + this::class.java.simpleName + " start!!!")
        for (i in 0 until data.size) {
            var minimum = Int.MAX_VALUE
            var pos = i
            for (j in i until data.size) {
                if (minimum > data[j]) {
                    minimum = data[j]
                    pos = j
                }
            }
            data.swap(i, pos)
            println("the $i time(s) sort, result : $data")
        }
        return data
    }

}