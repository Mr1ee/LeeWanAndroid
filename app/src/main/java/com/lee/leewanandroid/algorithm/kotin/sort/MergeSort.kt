package com.lee.leewanandroid.algorithm.kotin.sort

import com.lee.leewanandroid.algorithm.kotin.sort.interfaces.ISort
import java.util.ArrayList

/**
 * 归并排序
 */
class MergeSort : ISort<Int> {
    override fun sort(data: ArrayList<Int>): MutableList<Int> {
        return part(data)
    }

    private fun part(data: ArrayList<Int>): ArrayList<Int> {
        println("merge sort, part  : $data")
        return if (data.size > 1) {
            merge(
                part(arrayListOf<Int>().apply {
                    addAll(data.subList(0, data.size / 2))
                }),
                part(arrayListOf<Int>().apply {
                    addAll(data.subList(data.size / 2, data.size))
                })
            )
        } else {
            data
        }

    }

    private fun merge(left: ArrayList<Int>, right: ArrayList<Int>): ArrayList<Int> {
        val list = arrayListOf<Int>()
        var i = 0
        var j = 0
        while (i < left.size && j < right.size) {
            if (left[i] < right[j]) {
                list.add(left[i])
                i++
            } else {
                list.add(right[j])
                j++
            }
        }
        if (i < left.size) {
            list.addAll(left.subList(i, left.size))
        }
        if (j < right.size) {
            list.addAll(right.subList(j, right.size))
        }
        println("merge sort, merge : $list")

        return list
    }
}