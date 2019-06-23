package com.lee.leewanandroid.algorithm.kotin.sort

import com.lee.leewanandroid.algorithm.kotin.sort.interfaces.ISort
import java.util.ArrayList

/**
 * 冒泡排序
 */
class BubbleSort : ISort<Int> {
    override fun sort(data: ArrayList<Int>): MutableList<Int> {
        println("algorithm " + this::class.java.simpleName + " start!!!")

        for (i in 0 until data.size) {
            var exchange = false
            for (j in 0 until data.size - 1 - i) {
                if (data[j] > data[j + 1]) {
                    data.swapByBit(j, j + 1)
                    exchange = true
                }
            }
            println("the $i time(s) sort, result : $data")

            if (!exchange) {
                println("array already in sequence! break!")
                break
            }
        }

        return data
    }
}