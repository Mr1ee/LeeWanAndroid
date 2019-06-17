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
 *  in order [35 37 47 58 62 73 88 93 99]
 *              62
 *            /   \
 *          58     88
 *         /      /  \
 *        47     73  99
 *       /  \        /
 *      35  51      93
 *       \
 *       37
 */
class BinarySearchTree<T : Comparable<T>> : Tree<T>(), ITreeAction<T> {

    /**
     * 二叉搜索树删除
     */
    override fun remove(value: T): Boolean {
        val node = find(value)
        node?.let {
            if (it.left == null && it.right == null) {
                it.parent?.right = null
                return true
            } else if (it.left == null) {
                if (it.parent?.value!! > it.value) {
                    it.parent?.left = it.right as TNode<T>?
                } else {
                    it.parent?.right = it.right as TNode<T>?
                }
            } else if (it.right == null) {
                if (it.parent?.value!! > it.value) {
                    it.parent?.left = it.left as TNode<T>?
                } else {
                    it.parent?.right = it.left as TNode<T>?
                }
            } else {
                //找到左子树的最大，或者右子树的最小节点
                val rMin: TNode<T> = findMin(checkNotNull(it.right as TNode<T>))
                it.value = rMin.value
                //kotlin 和java中没有指针，所以删除rMin就很操蛋了。
                rMin.parent?.left = null
            }
        }

        return false
    }

    fun findMin(p: TNode<T>): TNode<T> {
        var parent: Node<T> = p
        while (parent.left != null) {
            parent.left?.let {
                parent = it
            }
        }

        return parent as TNode<T>
    }

    fun findMax(p: TNode<T>): TNode<T> {
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
        if (root == null) {
            root = TNode(value, null)
        } else {
            var parent = root
            while (parent != null) {
                when {
                    parent.value == value -> {
                        return false
                    }
                    value > parent.value -> {
                        if (parent.right == null) {
                            parent.right = TNode(value, parent)
                            return true
                        } else {
                            parent = parent.right
                        }
                    }
                    else -> {
                        if (parent.left == null) {
                            parent.left = TNode(value, parent)
                            return true
                        } else {
                            parent = parent.left
                        }
                    }
                }
            }
        }
        return false
    }
}
