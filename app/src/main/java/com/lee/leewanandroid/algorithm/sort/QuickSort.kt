package com.lee.leewanandroid.algorithm.sort

import com.lee.leewanandroid.algorithm.sort.interfaces.ISort
import java.util.*

/**
 * 快排 核心：partition 将给定的数组分成两部分，对每一个部分再次执行相同过程
 * partition函数的目的是选取一个数，将给定的数组，分成大于该数和小于该数的两部分。
 * 一般取数组的第一个数或者最后一个数，但是最好这个数应该随机从数组取。
 */
class QuickSort : ISort<Int> {
    override fun sort(data: ArrayList<Int>): MutableList<Int> {
        println("algorithm " + this::class.java.simpleName + " start!!!")
        quickSort(data, 0, data.size - 1)
        return data
    }

    private fun quickSort(data: ArrayList<Int>, start: Int, end: Int) {
        if (start >= end) {
            return
        }
        val index: Int = partition(data, start, end)
        quickSort(data, start, index - 1)
        quickSort(data, index + 1, end)
    }

    private fun partition(data: ArrayList<Int>, start: Int, end: Int): Int {
        val key = data[start]
        var low = start
        var high = end
        println("quick sort, partition start : $data")
        while (low < high) {
            while (low < high && data[high] >= key) {
                high--
            }
            while (low < high && data[low] <= key) {
                low++
            }
            data.swap(low, high)
        }
        data.swap(start, high)
        println("quick sort, partition end : $data\n\n")
        return high
    }
}