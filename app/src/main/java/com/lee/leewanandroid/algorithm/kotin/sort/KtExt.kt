package com.lee.leewanandroid.algorithm.kotin.sort


fun <T> ArrayList<T>.swap(p1: Int, p2: Int) {
    if (p1 == p2) return
    val t = this[p1]
    this[p1] = this[p2]
    this[p2] = t
}

fun ArrayList<Int>.swapByBit(p1: Int, p2: Int) {
    this[p1] = this[p1] xor this[p2]
    this[p2] = this[p1] xor this[p2]
    this[p1] = this[p1] xor this[p2]
}
