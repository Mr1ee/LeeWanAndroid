package com.lee.leewanandroid.algorithm.tree

open class Node<T>(t: T, var height: Int = 1) {
    open var left: Node<T>? = null
    open var right: Node<T>? = null
    open var value: T = t
}