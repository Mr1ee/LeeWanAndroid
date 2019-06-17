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
     * 没有指针的二叉树删除太操蛋了，不干了！！~！~
     */
    override fun remove(value: T): Boolean {
        var node = find(value)
        node?.let {
            if (it.left == null && it.right == null) {
                node = null
                return true
            } else if (it.left == null) {
                node = it.right
            } else if (it.right == null) {
                node = it.left
            } else {
                //找到左子树的最大，或者右子树的最小节点
                val rMin: Node<T> = findMin(checkNotNull(it.right))
                it.value = rMin.value
                //kotlin 和java中没有指针，所以删除rMin就很操蛋了。
            }
        }
        return false
    }

    fun findMin(p: Node<T>): Node<T> {
        var parent: Node<T> = p
        while (parent.left != null) {
            parent.left?.let {
                parent = it
            }
        }

        return parent
    }

    fun findMax(p: Node<T>): Node<T> {
        var parent: Node<T> = p
        while (parent.right != null) {
            parent.right?.let {
                parent = it
            }
        }

        return parent
    }

    override fun find(value: T): Node<T>? {
        var parent = root
        while (parent != null) {
            when {
                value == parent.value -> {
                    return parent
                }
                value > parent.value!! -> {
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
            root = Node(value)
        } else {
            var parent = root
            while (parent != null) {
                when {
                    parent.value == value -> {
                        return false
                    }
                    value > parent.value!! -> {
                        if (parent.right == null) {
                            parent.right = Node(value)
                            return true
                        } else {
                            parent = parent.right
                        }
                    }
                    else -> {
                        if (parent.left == null) {
                            parent.left = Node(value)
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
