package com.lee.leewanandroid.algorithm.sort

import com.lee.leewanandroid.algorithm.sort.interfaces.IInsetSort
import java.util.*

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