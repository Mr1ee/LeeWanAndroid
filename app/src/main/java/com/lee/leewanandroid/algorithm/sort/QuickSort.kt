package com.lee.leewanandroid.algorithm.sort

import com.lee.leewanandroid.algorithm.sort.interfaces.ISort
import java.util.ArrayList
import kotlin.random.Random

/**
 * 快排
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
//            data[low] = data[high]
            while (low < high && data[low] <= key) {
                low++
            }
//            data[high] = data[low]
            data.swap(low, high)
        }
//        data[high] = key
        data.swap(start, high)
        println("quick sort, partition end : $data\n\n")
        return high
    }
}