package com.lee.leewanandroid.algorithm.kotin.sort

import com.lee.leewanandroid.algorithm.kotin.sort.interfaces.ISort
import java.util.ArrayList


/**
 * 计数排序
 */
class CountingSort : ISort<Int> {
    override fun sort(data: ArrayList<Int>): MutableList<Int> {
        println("algorithm " + this::class.java.simpleName + " start!!!")
        var min = Int.MAX_VALUE
        var max = Int.MIN_VALUE
        for (i in data.indices) {
            min = kotlin.math.min(min, data[i])
            max = kotlin.math.max(max, data[i])
        }
        val R = max - min + 1 // 最大最小元素之间范围[min, max]的长度
        val count = IntArray(R + 1)
        for (i in data.indices) {
            count[data[i] - min + 1]++
        }
        for (r in 0 until R) {
            count[r + 1] += count[r]
        }
        val result: ArrayList<Int> = ArrayList(data)
        result.forEachIndexed { index, _ ->
            result[index] = 0
        }

        for (i in 0 until data.size) {
            result[--count[data[i] - min + 1]] = data[i]
            println("the $i time(s) sort, result : $result")
        }
        return result
    }
}