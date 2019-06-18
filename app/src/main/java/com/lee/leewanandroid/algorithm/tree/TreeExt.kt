package com.lee.leewanandroid.algorithm.tree

fun <T> Node<T>.isLeaf(): Boolean {
    return this.left == null && this.right == null
}

fun <T> Node<T>.print() {
    print(" " + this.value)
}