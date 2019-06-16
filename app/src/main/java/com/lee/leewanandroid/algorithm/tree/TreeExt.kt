package com.lee.leewanandroid.algorithm.tree

fun <T> addNode(parent: Node<T>, child: Node<T>) {
    parent.left = child
}