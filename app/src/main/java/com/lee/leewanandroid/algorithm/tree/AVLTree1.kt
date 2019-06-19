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
     * T1, T2, T3 and T4 are subtrees.
     *       z                                      y
     *      / \                                   /   \
     *     y  T4      Right Rotate (z)          x      z
     *    / \          - - - - - - - - ->      /  \   /  \
     *   x   T3                               T1  T2 T3  T4
     *  / \
     * T1  T2
     *
     *
     **/
    // A utility function to right rotate subtree rooted with y
    // See the diagram given above.
    fun rightRotate(y: AVLNode<Int>): AVLNode<Int> {
        val x = y.left as AVLNode
        val t2 = x.right

        // Perform rotation
        x.right = y
        y.left = t2

        // Update heights
        y.h = max(heightAVL(y.left), heightAVL(y.right)) + 1
        x.h = max(heightAVL(x.left), heightAVL(x.right)) + 1

        println("\n\nafter right rotation, node value = [${x.value}]")
        printTree(x)
        // Return new root
        return x
    }

    // A utility function to left rotate subtree rooted with x
    // See the diagram given above.
    fun leftRotate(x: AVLNode<Int>): AVLNode<Int> {
        val y = x.right as AVLNode
        val t2 = y.left

        // Perform rotation
        y.left = x
        x.right = t2

        //  Update heights
        x.h = max(heightAVL(x.left), heightAVL(x.right)) + 1
        y.h = max(heightAVL(y.left), heightAVL(y.right)) + 1

        println("\n\nafter left rotation, node value = [${y.value}]")
        printTree(y)
        // Return new root
        return y
    }

    override fun insert(value: Int): Boolean {
        root = insert(root as AVLNode<Int>?, value)
        println("\n\nafter insert [$value], root value = [${root?.value}]")
        printTree()
        return find(value) == null
    }

    private fun insert(node: AVLNode<Int>?, key: Int): AVLNode<Int> {
        /* 1.  Perform the normal BST insertion */
        if (node == null)
            return AVLNode(key)

        when {
            key < node.key -> node.left = insert(node.left as AVLNode<Int>?, key)
            key > node.key -> node.right = insert(node.right as AVLNode<Int>?, key)
            else // Duplicate keys not allowed
            -> return node
        }

        /* 2. Update height of this ancestor node */
        node.h = 1 + max(heightAVL(node.left), heightAVL(node.right))

        /* 3. Get the balance factor of this ancestor
              node to check whether this node became
              unbalanced */
        val balance = getBalance(node)

        // If this node becomes unbalanced, then there
        // are 4 cases Left Left Case
        (node.left as AVLNode?)?.let {
            if (balance > 1 && key < it.key)
                return rightRotate(node)
        }

        // Right Right Case
        (node.right as AVLNode?)?.let {
            if (balance < -1 && key > it.key)
                return leftRotate(node)
        }

        // Left Right Case
        (node.left as AVLNode?)?.let {
            if (balance > 1 && key > it.key) {
                node.left = leftRotate(it)
                return rightRotate(node)
            }
        }

        // Right Left Case
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