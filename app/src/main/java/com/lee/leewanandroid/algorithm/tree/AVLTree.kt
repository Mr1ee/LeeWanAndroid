package com.lee.leewanandroid.algorithm.tree

/**
 *
 * @Description:    AVLTree 平衡二叉树 插入删除的非递归实现版本
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
 */
@Suppress("KDocUnresolvedReference")
class AVLTree : BinarySearchTree<Int>() {

    override fun insert(value: Int): Boolean {
        val insertP = insertInternal(value)
        heightR(root)
        println("after insert $value, parent's value = ${insertP?.value}")
        printTree()
        rebuild(insertP)
        return insertP == null
    }

    private fun rebuild(insertP: TNode<Int>?) {
        //https://www.cnblogs.com/qm-article/p/9349681.html
        var p = insertP
        while (p != null) {
            val lh = p.left.height()
            val rh = p.right.height()
            val bf = lh - rh
            if (bf >= 2) {
                rebuildAfterInsert(p, true)
            } else if (bf <= -2) {
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
        val removeP = removeInternal(value)
        heightR(root)
        println("after remove $value, parent's value = ${removeP?.value}")
        printTree()
        rebuild(removeP)
        return removeP == null
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
    private fun leftRotation(nodeA: TNode<Int>): TNode<Int> {
        val nodeB = nodeA.right as TNode<Int>
        val nodeT = nodeB.left as TNode<Int>?
        if (nodeA.parent != null) {
            nodeA.parent?.let { p ->
                if (p.left == nodeA) {
                    p.left = nodeB
                } else {
                    p.right = nodeB
                }
                nodeB.parent = p
            }
        } else {
            //nodeA 是root节点
            nodeB.parent = null
            root = nodeB
        }

        nodeB.left = nodeA
        nodeA.parent = nodeB

        nodeA.right = nodeT
        nodeT?.parent = nodeA

        heightR(root)
        println("\n\nafter left rotation, nodeA value = [${nodeA.value}]")
        printTree()
        return nodeB
    }

    /**
     *
     *     [A]        /
     *     /         B
     *    B    =>   / \
     *   / \       C  A
     *  C  t         /
     *              t
     * 右旋
     */
    private fun rightRotation(nodeA: TNode<Int>): TNode<Int> {
        val nodeB = nodeA.left as TNode<Int>
        val nodeT = nodeB.right as TNode<Int>?
        if (nodeA.parent != null) {
            nodeA.parent?.let { p ->
                if (p.left == nodeA) {
                    p.left = nodeB
                } else {
                    p.right = nodeB
                }
                nodeB.parent = p
            }
        } else {
            //nodeA 是root节点
            nodeB.parent = null
            root = nodeB
        }
        nodeB.right = nodeA
        nodeA.parent = nodeB

        nodeA.left = nodeT
        nodeT?.parent = nodeA

        heightR(root)
        println("\n\nafter right rotation, nodeA value = [${nodeA.value}]")
        printTree()
        return nodeB
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
    private fun leftRightRotation(nodeA: TNode<Int>): TNode<Int> {
        val nodeB = nodeA.left
        leftRotation(nodeB as TNode<Int>)
        return rightRotation(nodeA)
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
    private fun rightLeftRotation(nodeA: TNode<Int>): TNode<Int> {
        val nodeB = nodeA.right
        rightRotation(nodeB as TNode<Int>)
        return leftRotation(nodeA)
    }
}