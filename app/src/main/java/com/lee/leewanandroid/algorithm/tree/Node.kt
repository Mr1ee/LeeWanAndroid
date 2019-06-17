package com.lee.leewanandroid.algorithm.tree

open class Node<T>(t: T) {
    var left: Node<T>? = null
    var right: Node<T>? = null
    open var value: T? = t
}