package com.lee.leewanandroid.algorithm.kotin.tree

import com.lee.leewanandroid.algorithm.kotin.tree.node.Node

/**
 *
 * @Description:    BSTree 递归实现的二叉搜索树
 * @Author:         lihuayong
 * @CreateDate:     2019-06-20 14:48
 * @UpdateUser:
 * @UpdateDate:     2019-06-20 14:48
 * @UpdateRemark:
 * @Version:        1.0
 */
open class BSTree<T : Comparable<T>> : BTree<T>() {
    override fun insert(value: T): Boolean {
        println("\n\ninsert [$value]")
        root = insertInternal(root, value)
        println("after insert [$value], root value = [${root?.value}]")
        printTree()
        return find(value) != null
    }

    override fun remove(value: T): Boolean {
        println("\n\nremove [$value]")
        if (find(value) != null) {
            root = removeInternal(root, value)
            println("after remove [$value], root value = [${root?.value}]")
            printTree()
            return true
        }
        return false
    }

    override fun find(value: T): Node<T>? {
        var parent = root
        while (parent != null) {
            when {
                value == parent.value -> {
                    return parent
                }
                value > parent.value -> {
                    if (parent.right != null) {
                        parent = parent.right
                    } else {
                        return null
                    }
                }
                else -> {
                    if (parent.left != null) {
                        parent = parent.left
                    } else {
                        return null
                    }
                }
            }
        }

        return null
    }

    private fun insertInternal(node: Node<T>?, key: T): Node<T> {
        /* 1.  Perform the normal BST insertion */
        if (node == null) {
            return Node(key)
        }

        //BST insert
        when {
            key < node.value -> node.left = insertInternal(node.left, key)
            key > node.value -> node.right = insertInternal(node.right, key)
            else -> return node // Duplicate keys not allowed
        }

        //update node height
        node.height = 1 + max(node.left.height(), node.right.height())
        return rebuildAfterTreeChanged(node)
    }

    private fun removeInternal(node: Node<T>?, key: T): Node<T>? {
        if (node == null) {
            return null
        }

        //BST remove
        when {
            key < node.value -> node.left = removeInternal(node.left, key)
            key > node.value -> node.right = removeInternal(node.right, key)
            else -> when {
                node.isLeaf() -> {
                    return null
                }
                node.noLeftChild() -> {
                    return node.right
                }
                node.noRightChild() -> {
                    return node.left
                }
                else -> {
                    //待删除结点左右孩子结点均不为null，这个时候可以去找待删除结点的后继节点，或者前驱结点，
                    //根据二叉排序树的定义后继节点就是左子树的最大节点，前驱结点就是右子树的最小节点
                    //找到前驱结点, 交换前驱结点与node的值,然后删除前驱结点
                    val predecessorNode = predecessor(node)
                    //将前驱结点的值交给node
                    node.value = predecessorNode.value
                    //删除 前驱节点
                    node.right = removeInternal(node.right, predecessorNode.value)
                }
            }
        }

        //update node height
        node.height = 1 + max(node.left.height(), node.right.height())
        return rebuildAfterTreeChanged(node)
    }

    /**
     * override this method if subclass want to rotation tree after insert
     * or remove node. e.g. AVL tree must override, and handle rotation in
     * this method.
     */
    protected open fun rebuildAfterTreeChanged(node: Node<T>): Node<T> {
        return node
    }

    // A utility function to get maximum of two integers
    protected fun max(a: Int, b: Int): Int {
        return if (a > b) a else b
    }

    /**
     * find 后继节点
     */
    @Suppress("unused")
    fun successor(node: Node<T>): Node<T> {
        var p = node
        var child = node.left!!
        while (child.right != null) {
            p = child
            child = child.right!!
        }
        //删除前驱结点
        if (p == node) {
            node.left = child.left
        } else {
            p.right = child.left
        }
        return child
    }

    /**
     * find 前驱节点
     */
    @Suppress("unused")
    fun predecessor(node: Node<T>): Node<T> {
        var p = node
        var child = node.right!!
        while (child.left != null) {
            p = child
            child = child.left!!
        }
        //删除前驱结点
        if (p == node) {
            node.right = child.right
        } else {
            p.left = child.right
        }
        return child
    }
}