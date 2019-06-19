package com.lee.leewanandroid.algorithm.tree

/**
 *
 * @Description:    AVLTree1
 * @Author:         lihuayong
 * @CreateDate:     2019-06-19 15:00
 * @UpdateUser:
 * @UpdateDate:     2019-06-19 15:00
 * @UpdateRemark:
 * @Version:        1.0
 */
class AVLTree1 : BinarySearchTree<Int>() {

    // A utility function to get the height of the tree
    private fun heightAVL(node: Node<Int>?): Int {
        return (node as AVLNode?)?.h ?: 0
    }

    // A utility function to get maximum of two integers
    fun max(a: Int, b: Int): Int {
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
    private fun rightRotate(nodeZ: AVLNode<Int>): AVLNode<Int> {
        val nodeY = nodeZ.left as AVLNode
        val t2 = nodeY.right

        // Perform rotation
        nodeY.right = nodeZ
        nodeZ.left = t2

        // Update heights
        nodeZ.h = max(heightAVL(nodeZ.left), heightAVL(nodeZ.right)) + 1
        nodeY.h = max(heightAVL(nodeY.left), heightAVL(nodeY.right)) + 1

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
    private fun leftRotate(nodeZ: AVLNode<Int>): AVLNode<Int> {
        val nodeY = nodeZ.right as AVLNode
        val t2 = nodeY.left

        // Perform rotation
        nodeY.left = nodeZ
        nodeZ.right = t2

        //  Update heights
        nodeZ.h = max(heightAVL(nodeZ.left), heightAVL(nodeZ.right)) + 1
        nodeY.h = max(heightAVL(nodeY.left), heightAVL(nodeY.right)) + 1

        println("\n\nafter left rotation, node value = [${nodeY.value}]")
        printTree(nodeY)
        // Return new root
        return nodeY
    }

    override fun insert(value: Int): Boolean {
        root = insertInternal(root as AVLNode<Int>?, value)
        println("\n\nafter insert [$value], root value = [${root?.value}]")
        printTree()
        return find(value) == null
    }

    private fun insertInternal(node: AVLNode<Int>?, key: Int): AVLNode<Int> {
        /* 1.  Perform the normal BST insertion */
        if (node == null)
            return AVLNode(key)

        when {
            key < node.key -> node.left = insertInternal(node.left as AVLNode<Int>?, key)
            key > node.key -> node.right = insertInternal(node.right as AVLNode<Int>?, key)
            else // Duplicate keys not allowed
            -> return node
        }

        /* 2. Update height of this ancestor node */
        node.h = 1 + max(heightAVL(node.left), heightAVL(node.right))

        /* 3. Get the balance factor of this ancestor
              node to check whether this node became
              unbalanced */
        val balance = getBalance(node)

        // If this node becomes unbalanced, then there  are 4 cases
        // Left Left Case
        (node.left as AVLNode?)?.let {
            if (balance > 1 && key < it.key)
                return rightRotate(node)
        }

        // Right Right Case
        (node.right as AVLNode?)?.let {
            if (balance < -1 && key > it.key)
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
        (node.left as AVLNode?)?.let {
            if (balance > 1 && key > it.key) {
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
        (node.right as AVLNode?)?.let {
            if (balance < -1 && key < it.key) {
                node.right = rightRotate(it)
                return leftRotate(node)
            }
        }

        /* return the (unchanged) node pointer */
        return node
    }

    // Get Balance factor of node node
    private fun getBalance(node: AVLNode<Int>?): Int {
        return if (node == null) 0 else heightAVL(node.left) - heightAVL(node.right)
    }
}