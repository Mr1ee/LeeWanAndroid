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
open class BinarySearchTree<T : Comparable<T>> : BTree<T>() {

    /**
     * 二叉搜索树删除
     */
    override fun remove(value: T): Boolean {
        return removeInternal(value) == null
    }

    override fun insert(value: T): Boolean {
        return insertInternal(value) == null
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

    fun removeInternal(value: T): TNode<T>? {
        val node = find(value) as TNode?
        node?.let {
            when {
                it.isLeaf() -> {
                    it.parent?.let { p ->
                        if (p.left == node) {
                            p.left = null
                        } else {
                            p.right = null
                        }
                    } ?: return run {
                        root = null
                        null
                    }

                    return node.parent
                }
                it.noLeftChild() -> {
                    val predecessorNode: TNode<T> = predecessor(it) as TNode<T>
                    it.value = predecessorNode.value
                    return predecessorNode.parent
                }
                it.noRightChild() -> {
                    val successorNode: TNode<T> = successor(it) as TNode<T>
                    it.value = successorNode.value
                    return successorNode.parent
                }
                else -> {
                    //找到左子树的最大（后继节点），或者右子树的最小节点(前驱节点)
                    val predecessorNode: TNode<T> = predecessor(it) as TNode<T>
                    //交换该节点与前驱节点的值，这个时候就相当于要删除 前驱节点。
                    it.value = predecessorNode.value
                    // 删除前驱节点，因为右子树的前驱节点的左孩子一定是null，
                    return predecessorNode.parent
                }
            }
        }
        return null
    }
}
