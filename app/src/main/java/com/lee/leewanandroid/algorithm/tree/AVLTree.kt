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
class AVLTree : BinarySearchTree<Int>() {

    override fun insert(value: Int): Boolean {
        val insertP = insertInternal(value)
        println("after insert $value, parent's value = ${insertP?.value}")
        printTree()
        rebuild(insertP)
        println("after rebuild tree")
        printTree()
        return insertP == null
    }

    private fun rebuild(insertP: TNode<Int>?) {
        //https://www.cnblogs.com/qm-article/p/9349681.html
        var p = insertP
        while (p != null) {
            val lh = p.left.height()
            val rh = p.right.height()
            val bf = lh- rh
            if (bf == 2) {
                rebuildAfterInsert(p, true)
            } else if (bf == -2) {
                rebuildAfterInsert(p, false)
            }
            p = p.parent
        }
    }

    private fun rebuildAfterInsert(p: TNode<Int>, left: Boolean) {
        if (left) {
            val lChild = p.left
            if (lChild?.left != null) { //右旋
                rightRotation(p)
            } else if (lChild?.right != null) {
                leftRightRotation(p)
            }
        } else {
            val rChild = p.right
            if (rChild?.right != null) { //左旋
                leftRotation(p)
            } else if (rChild?.left != null) {
                rightLeftRotation(p)
            }
        }
    }

    override fun remove(value: Int): Boolean {
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
    private fun leftRotation(node: TNode<Int>): TNode<Int> {
        val rChild = node.right as TNode<Int>
        if (node.parent != null) {
            node.parent?.let { p ->
                p.right = rChild
                rChild.parent = p

                node.right = rChild.left
                (rChild.left as TNode?)?.parent = node

                rChild.left = node
                node.parent = rChild
            }
        } else {
            //node 是root节点
            node.right = rChild.left
            (rChild.left as TNode?)?.parent = node

            rChild.left = node
            node.parent = rChild
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
    private fun rightRotation(node: TNode<Int>): TNode<Int> {
        val lChild = node.left as TNode<Int>
        if (node.parent != null) {
            node.parent?.let { p ->
                p.left = lChild
                lChild.parent = p

                node.left = lChild.right
                (lChild.left as TNode?)?.parent = node

                lChild.right = node
                node.parent = lChild
            }
        } else {
            //node 是root节点
            node.left = lChild.right
            (lChild.left as TNode?)?.parent = node

            lChild.right = node
            node.parent = lChild
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
    private fun leftRightRotation(node: TNode<Int>): TNode<Int> {
        leftRotation(node.left as TNode<Int>)
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
    private fun rightLeftRotation(node: TNode<Int>): TNode<Int> {
        rightRotation(node.right as TNode<Int>)
        return leftRotation(node)
    }
}