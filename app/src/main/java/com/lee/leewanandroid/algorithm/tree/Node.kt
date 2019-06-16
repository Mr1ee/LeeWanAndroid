package com.lee.leewanandroid.algorithm.tree

abstract class Node<T> {
    var left: Node<T>? = null
    var right: Node<T>? = null
    abstract var value: T
}