package com.lee.leewanandroid.algorithm.tree

open class Node<T>(t: T) {
    open var left: Node<T>? = null
    open var right: Node<T>? = null
    open var value: T = t
}