package com.lee.leewanandroid.algorithm.sort

import com.lee.leewanandroid.algorithm.sort.interfaces.IInsetSort
import java.util.*

class ShellSort : IInsetSort<Int> {
    override var gab: Int = 1

    override fun sort(data: ArrayList<Int>): MutableList<Int> {
        println("algorithm " + this@ShellSort::class.java.simpleName + " start!!!")
        var gab = data.size
        while (gab > 1) {
            gab = gab / 3 + 1
            for (i in gab until data.size) {
                val t = data[i]
                var j = i
                while (j >= gab && data[j - gab] > t) {
                    data[j] = data[j - gab]
                    j -= gab
                }
                data[j] = t
            }
            println("gab = $gab,  result : $data")
        }

        return data
    }

}