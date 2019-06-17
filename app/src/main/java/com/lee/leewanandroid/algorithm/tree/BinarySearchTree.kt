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
 */
class BinarySearchTree<T> : Tree<T>(), ITreeAction<T> {

    override fun remove(value: T): Boolean {
        return false
    }

    override fun find(value: T): Boolean {
        var parent = root
        while (parent != null) {
            when {
                value == parent.value -> {
                    return true
                }
                value > parent.value -> {
                    if (parent.right != null) {
                        parent = parent.right
                    } else {
                        return false
                    }
                }
                else -> {
                    if (parent.left != null) {
                        parent = parent.left
                    } else {
                        return false
                    }
                }
            }
        }

        return false
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
                    value > parent.value -> {
                        if (parent.right != null) {
                            parent.right = Node(value)
                            return true
                        } else {
                            parent = parent.right
                        }
                    }
                    else -> {
                        if (parent.left != null) {
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

private operator fun <T> T.compareTo(value: T?): Int {
    return when (value) {
        is Int -> (this as Int).compareTo(value)
        is Long -> (this as Long).compareTo(value)
        is Double -> (this as Double).compareTo(value)
        is Float -> (this as Float).compareTo(value)
        is Char -> (this as Char).compareTo(value)
        else -> (this as Int).compareTo(value)
    }
}
