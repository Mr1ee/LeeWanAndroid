package com.lee.leewanandroid.algorithm.tree

/**
 *
 * @Description:    RedBlackTree
 * @Author:         lihuayong
 * @CreateDate:     2019-06-21 15:35
 * @UpdateUser:
 * @UpdateDate:     2019-06-21 15:35
 * @UpdateRemark:
 * @Version:        1.0
 */
class RedBlackTree : BinarySearchTree<Int>() {

    override fun insert(value: Int): Boolean {
        var ch = root as RBNode?
        if (ch == null) {
            root = TNode(value, null)
            return true
        }

        var parent: RBNode? = null
        while (ch != null) {
            parent = ch
            ch = when {
                value > ch.value -> ch.right as RBNode?
                value < ch.value -> ch.left as RBNode?
                else -> return false //same value not allowed

            }
        }
        val newNode = RBNode(value, parent, RBNode.RED)

        parent?.let {
            if (parent.value > value) {
                parent.left = newNode
            } else {
                parent.right = newNode
            }
        }

        fixAfterInsertion(newNode)
        return true
    }

    override fun remove(value: Int): Boolean {

        return false
    }

    private fun fixAfterInsertion(node: RBNode) {

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
    private fun leftRotation(nodeA: RBNode): RBNode {
        val nodeB = nodeA.right as RBNode
        val nodeT = nodeB.left as RBNode?
        nodeB.parent = nodeA.parent
        nodeA.parent?.let { p ->
            if (p.left == nodeA) {
                p.left = nodeB
            } else {
                p.right = nodeB
            }
        } ?: run {
            //nodeA 是root节点
            root = nodeB
        }

        nodeB.left = nodeA
        nodeA.parent = nodeB

        nodeA.right = nodeT
        nodeT?.parent = nodeA

        calculateAllNodesHeight(root)
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
    private fun rightRotation(nodeA: RBNode): RBNode {
        val nodeB = nodeA.left as RBNode
        val nodeT = nodeB.right as RBNode?
        nodeB.parent = nodeA.parent
        nodeA.parent?.let { p ->
            if (p.left == nodeA) {
                p.left = nodeB
            } else {
                p.right = nodeB
            }
        } ?: run {
            //nodeA 是root节点
            root = nodeB
        }

        nodeB.right = nodeA
        nodeA.parent = nodeB

        nodeA.left = nodeT
        nodeT?.parent = nodeA

        calculateAllNodesHeight(root)
        println("\n\nafter right rotation, nodeA value = [${nodeA.value}]")
        printTree()
        return nodeB
    }


    class RBNode(value: Int, parent: RBNode? = null, color: Boolean = BLACK) :
        TNode<Int>(value, parent) {
        companion object {
            const val BLACK = true
            const val RED = !BLACK
        }
    }
}