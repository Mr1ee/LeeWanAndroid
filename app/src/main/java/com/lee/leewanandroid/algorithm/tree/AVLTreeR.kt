package com.lee.leewanandroid.algorithm.tree

import com.lee.leewanandroid.algorithm.tree.node.Node

/**
 *
 * @Description:    AVLTreeR 插入删除的递归实现版本,不要指向parent的指针
 * @Author:         lihuayong
 * @CreateDate:     2019-06-19 15:00
 * @UpdateUser:
 * @UpdateDate:     2019-06-19 15:00
 * @UpdateRemark:
 * @Version:        1.0
 */
class AVLTreeR : BSTree<Int>() {

    override fun rebuildAfterTreeChanged(node: Node<Int>): Node<Int> {
        /* 3. Get the balance factor of this ancestor
              node to check whether this node became
              unbalanced */
        val balance = node.balance()

        // If this node becomes unbalanced, then there  are 4 cases
        // Left Left Case
        node.left?.let {
            if (balance > 1 && it.balance() >= 0)
                return rightRotate(node)
        }

        // Right Right Case
        node.right?.let {
            if (balance < -1 && it.balance() <= 0)
                return leftRotate(node)
        }

        /**
         *  [L-R] Left Right Case
         *       z                              z                           x
         *      / \                            / \                         /  \
         *     y  T4    Left Rotate (y)       x  T4   Right Rotate(z)    y     z
         *    / \      - - - - - - - - ->    / \      - - - - - - - ->  / \   / \
         *   T1  x                          y   T3                    T1  T2 T3  T4
         *      / \                        / \
         *    T2  T3                     T1  T2
         */
        node.left?.let {
            if (balance > 1 && it.balance() < 0) {
                node.left = leftRotate(it)
                return rightRotate(node)
            }
        }

        /**
         *  [R-L] Right Left Case
         *      z                            z                            x
         *     / \                          / \                          /  \
         *   T1   y   Right Rotate (y)    T1   x      Left Rotate(z)   z     y
         *       / \  - - - - - - - - ->     /  \   - - - - - - - ->  / \   / \
         *      x  T4                       T2   y                  T1  T2 T3  T4
         *     / \                              / \
         *   T2   T3                           T3 T4
         */
        node.right?.let {
            if (balance < -1 && it.balance() > 0) {
                node.right = rightRotate(it)
                return leftRotate(node)
            }
        }

        /* return the (unchanged) node pointer */
        return node
    }

    /**
     *  [L-L] Left Left Case:
     *        z                                      y
     *       / \                                   /   \
     *      y  T4       Right Rotate (z)          x      z
     *     / \         - - - - - - - - ->       /  \   /  \
     *    x   T3                               T1  T2 T3  T4
     *   / \
     *  T1  T2
     *
     *   T1, T2, T3 and T4 are subtrees.
     *   A utility function to right rotate subtree rooted with nodeZ
     *   See the diagram given above.
     */
    private fun rightRotate(nodeZ: Node<Int>): Node<Int> {
        val nodeY = nodeZ.left as Node
        val t2 = nodeY.right

        // Perform rotation
        nodeY.right = nodeZ
        nodeZ.left = t2

        // Update heights
        nodeZ.height = max(nodeZ.left.height(), nodeZ.right.height()) + 1
        nodeY.height = max(nodeY.left.height(), nodeY.right.height()) + 1

        println("after right rotation, node value = [${nodeY.value}]")
        printTree(nodeY)
        // Return new root
        return nodeY
    }

    /**
     *  [R-R]: Right Right Case:
     *      z                               y
     *    /  \                            /  \
     *   T1   y     Left Rotate(z)       z    x
     *       / \   - - - - - - - ->    / \   / \
     *      T2  x                     T1 T2 T3 T4
     *         / \
     *       T3  T4
     *
     *   T1, T2, T3 and T4 are subtrees.
     *   A utility function to left rotate subtree rooted with nodeZ
     *   See the diagram given above.
     */
    private fun leftRotate(nodeZ: Node<Int>): Node<Int> {
        val nodeY = nodeZ.right as Node
        val t2 = nodeY.left

        // Perform rotation
        nodeY.left = nodeZ
        nodeZ.right = t2

        //  Update heights
        nodeZ.height = max(nodeZ.left.height(), nodeZ.right.height()) + 1
        nodeY.height = max(nodeY.left.height(), nodeY.right.height()) + 1

        println("after left rotation, node value = [${nodeY.value}]")
        printTree(nodeY)
        // Return new root
        return nodeY
    }
}