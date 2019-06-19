package com.lee.leewanandroid.algorithm.tree

/**
 * if node is a leaf node
 */
fun <T> Node<T>.isLeaf(): Boolean {
    return this.left == null && this.right == null
}

/**
 * if node has no left children
 */
fun <T> Node<T>.noLeftChild(): Boolean {
    return this.left == null && this.right != null
}

/**
 * if node has no right children
 */
fun <T> Node<T>.noRightChild(): Boolean {
    return this.left != null && this.right == null
}

/**
 * print node value
 */
fun <T> Node<T>.print() {
    print(" " + this.value)
}

/**
 * node height
 */
fun <T> Node<T>?.height(): Int {
    return this?.height ?: 0
}

/**
 * Get Balance factor of node
 */
fun Node<Int>?.balance(): Int {
    return if (this == null) 0 else this.left.height() - this.right.height()
}