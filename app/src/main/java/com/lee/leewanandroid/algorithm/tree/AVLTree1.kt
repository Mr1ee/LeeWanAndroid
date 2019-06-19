package com.lee.leewanandroid.algorithm.tree

/**
 *
 * @Description:    AVLTree1 插入删除的递归实现版本
 * @Author:         lihuayong
 * @CreateDate:     2019-06-19 15:00
 * @UpdateUser:
 * @UpdateDate:     2019-06-19 15:00
 * @UpdateRemark:
 * @Version:        1.0
 */
class AVLTree1 : BinarySearchTree<Int>() {

    // A utility function to get maximum of two integers
    private fun max(a: Int, b: Int): Int {
        return if (a > b) a else b
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

        println("\n\nafter right rotation, node value = [${nodeY.value}]")
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

        println("\n\nafter left rotation, node value = [${nodeY.value}]")
        printTree(nodeY)
        // Return new root
        return nodeY
    }

    override fun insert(value: Int): Boolean {
        root = insertInternal(root, value)
        println("\n\nafter insert [$value], root value = [${root?.value}]")
        printTree()
        return find(value) == null
    }

    private fun insertInternal(node: Node<Int>?, key: Int): Node<Int> {
        /* 1.  Perform the normal BST insertion */
        if (node == null)
            return Node(key)

        //BST insert
        when {
            key < node.value -> node.left = insertInternal(node.left, key)
            key > node.value -> node.right = insertInternal(node.right, key)
            else // Duplicate keys not allowed
            -> return node
        }

        return rebuildTree(node)
    }

    override fun remove(value: Int): Boolean {
        if (find(value) != null) {
            root = removeInternal(root, value)
            println("\n\nafter remove [$value], root value = [${root?.value}]")
            printTree()
            return true
        }
        return false
    }

    private fun removeInternal(node: Node<Int>?, key: Int): Node<Int>? {
        if (node == null) {
            return null
        }

        //BST remove
        when {
            key < node.value -> node.left = removeInternal(node.left, key)
            key > node.value -> node.right = removeInternal(node.right, key)
            else -> when {
                node.isLeaf() -> {
                    return null
                }
                node.noLeftChild() -> {
                    return node.right
                }
                node.noRightChild() -> {
                    return node.left
                }
                else -> {
                    //待删除结点左右孩子结点均不为null，这个时候可以去找待删除结点的后继节点，或者前驱结点，
                    //根据二叉排序树的定义后继节点就是左子树的最大节点，前驱结点就是右子树的最小节点
                    //找到前驱结点, 交换前驱结点与node的值,然后删除前驱结点
                    val predecessorNode = predecessor(node)
                    removeInternal(node, predecessorNode.value)
                    //将前驱结点的值交给node
                    node.value = predecessorNode.value
                    return node
                }
            }
        }

        return rebuildTree(node)
    }

    private fun rebuildTree(node: Node<Int>): Node<Int> {
        /* 2. Update height of this ancestor node */
        node.height = 1 + max(node.left.height(), node.right.height())

        /* 3. Get the balance factor of this ancestor
              node to check whether this node became
              unbalanced */
        val balance = node.balance()

        // If this node becomes unbalanced, then there  are 4 cases
        // Left Left Case
        node.left?.let {
            if (balance > 1 && it.left != null)
                return rightRotate(node)
        }

        // Right Right Case
        node.right?.let {
            if (balance < -1 && it.right != null)
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
            if (balance > 1 && it.right != null) {
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
            if (balance < -1 && it.left != null) {
                node.right = rightRotate(it)
                return leftRotate(node)
            }
        }

        /* return the (unchanged) node pointer */
        return node
    }
}