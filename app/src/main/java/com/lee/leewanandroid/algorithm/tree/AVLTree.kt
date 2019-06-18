package com.lee.leewanandroid.algorithm.tree

/**
 *
 * @Description:    AVLTree 平衡二叉树
 * @Author:         lihuayong
 * @CreateDate:     2019-06-18 14:23
 * @UpdateUser:
 * @UpdateDate:     2019-06-18 14:23
 * @UpdateRemark:
 * @Version:        1.0
 *
 *  in order [47 58 62 73 88 93 99 100] -> insert [51]
 *              62
 *            /   \
 *          58     88
 *         /      /  \
 *        47     73  99
 *          \        / \
 *         51      93 100
 *
 *
 *
 *
 */
class AVLTree<T : Comparable<T>> : BinarySearchTree<T>() {

    override fun insert(value: T): Boolean {
        val insertP = insertInternal(value)

        rebuild(insertP)
        return insertP == null
    }

    private fun rebuild(insertP: TNode<T>?) {
        //https://www.cnblogs.com/qm-article/p/9349681.html
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun remove(value: T): Boolean {
        return super.remove(value)
    }

    /**
     *  \           \
     *   [A]         B
     *     \   =>   / \
     *      B      A   C
     *     / \      \
     *        C
     * 左旋
     */
    private fun leftRotation(node: AVLNode<T>): AVLNode<T> {
        val rChild = node.right as AVLNode<T>
        if (node.parent != null) {
            node.parent?.let { p ->
                p.right = rChild
                node.right = rChild.left
                rChild.left = node
            }
        } else {
            //node 是root节点
            node.right = rChild.left
            rChild.left = node
        }

        return rChild
    }

    /**
     *
     *     [A]        /
     *     /         B
     *    B    =>   / \
     *   / \       C  A
     *  C            /
     *
     * 右旋
     */
    private fun rightRotation(node: AVLNode<T>): AVLNode<T> {
        val lChild = node.left as AVLNode<T>
        if (node.parent != null) {
            node.parent?.let { p ->
                p.left = lChild
                node.left = lChild.right
                lChild.right = node
            }
        } else {
            //node 是root节点
            node.left = lChild.right
            lChild.right = node
        }

        return lChild
    }

    /**
     *     /             /
     *    A            [A]        /
     *   /             /         C
     * [B]      =>    C    =>   / \
     *   \           /         B  A
     *    C         B
     * 先左旋，再右旋
     */
    private fun leftRightRotation(node: AVLNode<T>): AVLNode<T> {
        leftRotation(node.left as AVLNode<T>)
        return rightRotation(node)
    }

    /**
     *  \         \          \
     *   A        [A]         C
     *    \    =>   \   =>   / \
     *    [B]        C      A   B
     *    /           \
     *  C              B
     * 先右旋，再左旋
     */
    private fun rightLeftRotation(node: AVLNode<T>): AVLNode<T> {
        rightRotation(node.right as AVLNode<T>)
        return leftRotation(node)
    }
}