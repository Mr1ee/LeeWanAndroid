package com.lee.leewanandroid.algorithm.kotin.tree.node

class RBNode(value: Int, parent: RBNode? = null, var color: Boolean = BLACK) :
    TNode<Int>(value, parent) {
    companion object {
        const val BLACK = true
        const val RED = false
    }
}