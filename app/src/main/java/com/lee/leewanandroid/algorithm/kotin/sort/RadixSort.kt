package com.lee.leewanandroid.algorithm.kotin.sort

import com.lee.leewanandroid.algorithm.kotin.sort.interfaces.ISort
import java.util.*

/**
 * RadixSort 基数排序
 */
class RadixSort : ISort<Int> {
    override fun sort(data: ArrayList<Int>): MutableList<Int> {
        var max = Int.MIN_VALUE
        data.forEach {
            max = kotlin.math.max(max, it)
        }
        var times = 0
        while (max > 0) {
            max /= 10
            times++
        }

        sort(data, times)
        return data
    }

    fun sort(number: ArrayList<Int>, maxDigit: Int) {
        var mod = 1
        var bit = 1 //控制键值排序依据在哪一位
        val temp = Array(10) { IntArray(number.size) } //数组的第一维表示可能的余数0-9
        val order = IntArray(10) //数组order[i]用来表示该位是i的数的个数
        while (bit <= maxDigit) {
            for (i in number.indices) {
                val lsd = number[i] / mod % 10
                temp[lsd][order[lsd]++] = number[i]
            }
            var k = 0
            for (i in 0..9) {
                if (order[i] != 0) {
                    for (j in 0 until order[i]) {
                        number[k++] = temp[i][j]
                    }
                }
                order[i] = 0
            }
            mod *= 10
            bit++
            println("the $bit time(s) sort, result : $number")
        }
    }
}