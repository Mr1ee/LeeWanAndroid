package com.lee.leewanandroid.algorithm.kotin.tree

import com.lee.leewanandroid.algorithm.kotin.tree.node.Node
import com.lee.leewanandroid.algorithm.kotin.tree.node.RBNode
import com.lee.leewanandroid.algorithm.kotin.tree.node.TNode

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
 * node size. include itself and all its children node
 */
fun <T> Node<T>?.size(): Int {
    return this?.run { left.size() + right.size() + 1 } ?: 0
}

/**
 * Get Balance factor of node
 */
fun Node<Int>?.balance(): Int {
    return this?.run { left.height() - right.height() } ?: 0
}

/**
 * find and return successor node
 */
@Suppress("UNCHECKED_CAST")
fun <T, N : Node<T>> N.successor(): N {
    var p = this
    var child: Node<T>? = this.left ?: return this
    while (child?.right != null) {
        p = child as N
        child = child.right
    }
    //删除后继结点
    if (p == this) {
        this.left = child?.left
    } else {
        p.right = child?.left
    }
    return child as N
}

/**
 * find and return predecessor node, and reconnect tree
 */
@Suppress("UNCHECKED_CAST")
fun <T, N : Node<T>> N.predecessor(): N {
    var p = this
    var child: Node<T>? = this.right ?: return this
    while (child?.left != null) {
        p = child as N
        child = child.left
    }
    //删除前驱结点
    if (p == this) {
        this.right = child?.right
    } else {
        p.left = child?.right
    }
    return child as N
}

/**
 * find and return predecessor node, but not reconnect tree
 */
@Suppress("UNCHECKED_CAST")
fun <T, N : TNode<T>> N.predecessor1(): N? {
    return if (this.right != null) {
        var p = this.right as N
        while (p.left != null)
            p = p.left as N
        p
    } else {
        var p = this.parent as N?
        var ch = this
        while (p != null && ch === p.right) {
            ch = p
            p = p.parent as N?
        }
        p
    }
}

/**
 * return red black node is black or not
 */
fun RBNode?.isBlack(): Boolean {
    return this?.run { color == RBNode.BLACK } ?: RBNode.BLACK
}

/**
 * return red black node is red or not
 */
fun RBNode?.isRed(): Boolean {
    return this?.color == RBNode.RED
}