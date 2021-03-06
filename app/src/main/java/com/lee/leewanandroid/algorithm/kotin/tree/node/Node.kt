package com.lee.leewanandroid.algorithm.kotin.tree.node

open class Node<T>(t: T, var height: Int = 1) {
    open var left: Node<T>? = null
    open var right: Node<T>? = null
    open var value: T = t
}