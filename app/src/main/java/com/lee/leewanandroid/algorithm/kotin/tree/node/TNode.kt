package com.lee.leewanandroid.algorithm.kotin.tree.node

/**
 * BST 为了解决kotlin没有指针不方便删除节点(非递归)，无奈引入parent
 */
open class TNode<T>(value: T, var parent: TNode<T>? = null) : Node<T>(value)