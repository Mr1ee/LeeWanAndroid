package com.lee.leewanandroid.algorithm.tree

/**
 *
 * @Description:    BinarySearchTree 二叉搜索树
 * @Author:         lihuayong
 * @CreateDate:     2019-06-17 18:19
 * @UpdateUser:
 * @UpdateDate:     2019-06-17 18:19
 * @UpdateRemark:
 * @Version:        1.0
 *
 *  in order [35 37 47 58 62 73 88 93 95 97 99 100]
 *              62
 *            /   \
 *          58     88
 *         /      /  \
 *        47     73  99
 *       /  \        / \
 *      35  51      93 100
 *       \           \
 *       37           97
 *                   /
 *                  95
 */
open class BinarySearchTree<T : Comparable<T>> : Tree<T>(), ITreeAction<T> {

    /**
     * 二叉搜索树删除
     */
    override fun remove(value: T): Boolean {
        val node = find(value)
        node?.let {
            when {
                it.isLeaf() -> {
                    it.parent?.let { p ->
                        if (p.left == node) {
                            p.left = null
                        } else {
                            p.right = null
                        }
                    } ?: this.root.apply { root = null }

                    return true
                }
                it.left == null && it.right != null -> {
                    deleteNode(delNode = node, child = it.right as TNode<T>)
                    return true
                }
                it.right == null && it.left != null -> {
                    deleteNode(delNode = node, child = it.left as TNode<T>)
                    return true
                }
                else -> {
                    //找到左子树的最大（后继节点），或者右子树的最小节点(前驱节点)
                    val lMax: TNode<T> = maximum(it.left as TNode<T>)
                    //交换该节点与后继节点的值，这个时候就相当于要删除 后继节点。
                    it.value = lMax.value
                    // 删除后继节点，因为左子树的后继节点的右孩子一定是null，
                    // 所以只需要考虑将后继节点的parent的右孩子节点指向改后继节点的左孩子即可
                    lMax.parent?.right = lMax.left
                    return true
                }
            }
        }

        return false
    }

    /**
     * 删除[delNode]节点
     */
    private fun deleteNode(delNode: TNode<T>, child: TNode<T>) {
        delNode.parent?.let { p ->
            if (p.value > delNode.value) {
                p.left = child
            } else {
                p.right = child
            }
        } ?: root.apply { root = child }
    }

    @Suppress("unused")
    fun minimum(p: TNode<T>): TNode<T> {
        var parent: Node<T> = p
        while (parent.left != null) {
            parent.left?.let {
                parent = it
            }
        }

        return parent as TNode<T>
    }

    fun maximum(p: TNode<T>): TNode<T> {
        var parent: Node<T> = p
        while (parent.right != null) {
            parent.right?.let {
                parent = it
            }
        }

        return parent as TNode<T>
    }

    override fun find(value: T): TNode<T>? {
        var parent = root
        while (parent != null) {
            when {
                value == parent.value -> {
                    return parent as TNode<T>
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

    override fun insert(value: T): Boolean {
        val node = insertInternal(value)
        return node == null
    }

    fun insertInternal(value: T): TNode<T>? {
        if (root == null) {
            root = TNode(value, null)
            return root as TNode<T>
        } else {
            var parent = root
            while (parent != null) {
                when {
                    parent.value == value -> {
                        return null
                    }
                    value > parent.value -> {
                        if (parent.right == null) {
                            parent.right = TNode(value, parent as TNode<T>?)
                            return parent
                        } else {
                            parent = parent.right
                        }
                    }
                    else -> {
                        if (parent.left == null) {
                            parent.left = TNode(value, parent as TNode<T>?)
                            return parent
                        } else {
                            parent = parent.left
                        }
                    }
                }
            }
        }
        return null
    }
}
