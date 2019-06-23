package com.lee.leewanandroid.algorithm.tree

import com.lee.leewanandroid.algorithm.tree.node.RBNode

/**
 *
 * @Description:    RedBlackTree, Red Black Tree 红黑树
 * @Author:         lihuayong
 * @CreateDate:     2019-06-21 15:35
 * @UpdateUser:
 * @UpdateDate:     2019-06-21 15:35
 * @UpdateRemark:
 * @Version:        1.0
 *
 * 1、结点是红色或黑色
 * 2、根结点始终是黑色
 * 3、叶子结点（NIL 结点）都是黑色
 * 4、红色结点的两个直接孩子结点都是黑色（即从叶子到根的所有路径上不存在两个连续的红色结点）
 * 5、从任一结点到每个叶子的所有简单路径都包含相同数目的黑色结点
 *
 */
@Suppress("unused", "KDocUnresolvedReference")
class RedBlackTree : BinarySearchTree<Int>() {
    companion object {
        const val RED = RBNode.RED
        const val BLACK = RBNode.BLACK
    }

    override fun insert(value: Int): Boolean {
        // binary search tree insert.
        var child = root as RBNode?
        if (child == null) {
            root = RBNode(value, null)
            return true
        }

        var parent: RBNode? = null
        while (child != null) {
            parent = child
            child = when {
                value > child.value -> child.right as RBNode?
                value < child.value -> child.left as RBNode?
                else -> return false //same value not allowed

            }
        }
        child = RBNode(value, parent, RED)

        parent?.let {
            if (parent.value > value) {
                parent.left = child
            } else {
                parent.right = child
            }
        }

        println("after insert $value")
        printTree()

        fixAfterInsertion(child)

        println("after fixed ")
        printTree()
        println()
        return true
    }

    override fun remove(value: Int): Boolean {
        val x = find(value) as RBNode?
        return if (x == null) {
            false
        } else {
            deleteNode(x)
            println("after delete $value")
            printTree()
            true
        }
    }

    /**
     * 1 如果待删除结点 X 是一个红色结点，则直接删除即可，不会违反定义。
     * 2 如果待删除结点 X 是一个黑色结点，且其孩子结点 C 是红色的，那么只需要将 X 替换成 C，同时将 C 由红变黑即可。
     * 3 如果需要删除的结点 X 是黑色的，同时它的孩子结点 C 也是黑色的，这种情况需要进一步分场景讨论。
     */
    private fun deleteNode(node: RBNode) {
        var x = node

        // binary search tree delete.
        // if delete node x has two children. then find its successor or predecessor,
        // then set exchange x and s value, and now we actually delete s node.
        if (x.left != null && x.right != null) {
            val s = x.predecessor1() ?: return
            x.value = s.value
            x = s
        }

        val replacement = (x.left?.run { this } ?: x.right) as RBNode?
        if (replacement != null) {
            replacement.parent = x.parent
            when {
                x.parent == null -> root = replacement
                x == x.parent?.left -> x.parent?.left = replacement
                else -> x.parent?.right = replacement
            }
            x.parent = null
            x.right = null
            x.left = null
            if (x.isBlack()) {
                fixAfterDeletion(replacement)
            }
        } else if (x.parent == null) {
            // if only one node, set root null
            root = null
        } else {
            //  No children. Use self as phantom replacement and unlink.
            if (x.isBlack()) {
                fixAfterDeletion(x)
            }

            if (x.parent != null) {
                if (x === x.parent?.left)
                    x.parent?.left = null
                else if (x === x.parent?.right)
                    x.parent?.right = null
                x.parent = null
            }
        }
    }

    private fun fixAfterDeletion(node: RBNode) {
        var x: RBNode? = node
        // 3
        while (x != root && x.isBlack()) {
            /**
             * x is real delete node's child node.(x has replaced the real delete node)
             *  因为此时p-x，即p的左子树相比于p的右子树，少了一个黑色节点（被删除了），因此必须调整树，
             *  么使p的右子树p-[b]的黑色节点也减少一个，要么使p的左子树p-x的黑色节点增加1个。
             * if x color is BLACK, then there are six situations
             * SOME STATEMENT:
             * x = real delete node's child node.
             * p = x's parent node
             * b = x's brother node
             * bl = brother node's left child  br = brother node's right child
             *
             * [p] means p's color is RED.
             * p? means p's color can be RED or BLACK, we need't care about it.
             *-------------------------------------------------------------------------------
             * (1)
             *  x color is BLACK, b is RED, according DEFINITION 4, p、bl and br color must be RED
             *  left rotation b, set b color BLACK, and set p color RED.
             *  [Tips] Notice that [b] is already RED, so we can't balance p-tree by reducing p's
             *  right subtree's BLACK node, then we need to add p's left subtree BLACK node.
             *  So we need left rotate p, and adjust color. This step ensure brother color is BLACK,
             *  so we are ready for next step!
             *  因为[b]已经是红色的，所以不可能去减少p的右子树的黑色节点，因此增加p的左子树的黑色节点数，
             *  所以需要围绕p左旋，并且调整颜色。
             *      p                       [b]                 b
             *     / \        b left        / \     color      / \
             *    x  [b]     rotation      p  br  transform  [p] br
             *       / \   - - - - - ->   / \    - - - - ->  / \
             *     bl  br                x  bl              x  bl
             * ------------------------------------------------------------------------------
             * (1)-i
             *  x, br and br color is all BLACK.
             *  set b color RED, and set p as x to continue.
             *  [Tips] In this situation, we can easily balance p-tree by reduce p's right subtree,
             *        just change b's color from BLACK to RED!!!
             *      p?                       p?
             *     / \        color         / \
             *    x   b     transform      x  [b]
             *       / \   - - - - - ->      /  \
             *      bl br                   bl  br
             *-------------------------------------------------------------------------------
             * (1)-ii
             *  x, b color is BLACK, but br and br color not all BLACK
             *  A: if br is RED,  left rotation p and set bl color p's color, set p、br color BLACK.
             *  [Tips] In this situation, we add p's left subtree's BLACK node by left rotating p, and
             *          don't forget to adjust nodes color
             *
             *  B: if bl is RED and br is BLACK, right rotation b and set b color RED, set bl color BLACK,
             *  then we just do step A.
             *  [Tips] this step ensure brother is BLACK and brother's right is RED, so we can change this
             *  to another situation---br is RED.
             *  B:                                     = A:
             *    p?                           p?      =   p?                            b?
             *   / \      b right rotation    / \      =  / \      p left rotation      / \
             *  x   b     color transform    x  bl     = x   b     color transform     p   br
             *     / \   - - - - - - - - ->      \     =    / \   - - - - - - - - ->  / \
             *   [bl] br                         [b]   =  bl? [br]                   x  bl?
             *                                     \   =
             *                                      br =
             *-------------------------------------------------------------------------------
             * (2) and (2)-i and (2)-ii is symmetric above
             *
             */
            val p = x!!.parent as RBNode
            if (x == p.left) {
                var b = x.parent?.right as RBNode?
                if (b?.color == RED) {
                    b.color = BLACK
                    p.color = RED
                    leftRotation(p)
                    b = p.right as RBNode?
                }

                val blc: RBNode? = b?.left as RBNode?
                val brc: RBNode? = b?.right as RBNode?
                if (blc.isBlack() && brc.isBlack()) {
                    b?.color = RED
                    x = p
                } else {
                    // brother right child is BLACK, so brother left child node must RED
                    if (brc.isBlack()) {
                        blc?.color = BLACK
                        b?.color = RED
                        rightRotation(b!!)
                        b = p.right as RBNode?
                    }
                    b?.color = p.color
                    p.color = BLACK
                    (b?.right as RBNode?)?.color = BLACK
                    leftRotation(p)
                    x = root as RBNode?
                }
            } else { //symmetric
                var b = p.left as RBNode?
                if (b?.color == RED) {
                    b.color = BLACK
                    p.color = RED
                    rightRotation(p)
                    b = p.left as RBNode?
                }

                if ((b?.right as RBNode?).isBlack() && (b?.left as RBNode?).isBlack()) {
                    b?.color = RED
                    x = p
                } else {
                    if ((b?.left as RBNode?).isBlack()) {
                        (b?.right as RBNode?)?.color = BLACK
                        b?.color = RED
                        leftRotation(b!!)
                        b = p.left as RBNode?
                    }

                    b?.color = p.color
                    p.color = BLACK
                    (b?.left as RBNode?)?.color = BLACK
                    rightRotation(p)
                    x = root as RBNode?
                }
            }
        }

        x?.color = BLACK
    }

    private fun fixAfterInsertion(node: RBNode) {
        var x: RBNode? = node
        x?.color = RED
        while (x != null && x != root) {
            var p = x.parent as RBNode?
            if (p.isBlack()) {
                // if parent color is black, insert a RED child has no influence to the tree, so just break.
                break
            }

            if (p?.color == RED) {
                // if parent color is RED, according Red Black Tree definition, it must has grandparent
                val g = x.parent?.parent as RBNode
                var u: RBNode?
                /**
                 * if parent color is RED, then there are four situations
                 * SOME STATEMENT:
                 * x = insert node              p = x's parent node
                 * g = x's grandparent node     u = x's uncle node
                 * [p] means p's color is RED.
                 * ----------------------------------------------------------
                 * (1)  x's parent node is x's grandparent node's left child
                 *     parent and uncle color both are RED, then update color as below
                 *        g                                [g]
                 *       / \                              /   \
                 *     [p] [u]          color            p     u
                 *     / \         - - - - - - - ->     / \
                 *   [x]  T1                          [x]  T1
                 * ----------------------------------------------------------
                 * (2) x's parent node is x's grandparent node's left child
                 *     parent color id RED, uncle color is BLACK,
                 *     if x is parent's right child, then left rotation parent,
                 *     then update color as below, and right rotation grandparent
                 *      g                  g              [g]                 x
                 *     / \   [p] left     / \             / \   [g] right    / \
                 *   [p]  u  rotation   [x]  u  color    x   u  rotation   [p] [g]
                 *   / \    - - - - -> /       - - - -> /       - - - - ->       \
                 *  T1 [x]            [p]              [p]                        u
                 *
                 * ----------------------------------------------------------
                 * (3) see (1) symmetric
                 *
                 *        g                           [g]
                 *       / \                          / \
                 *     [u]  [p]        color         u   p
                 *         / \     - - - - - - ->       / \
                 *       T1  [x]                      T1  [x]
                 * ----------------------------------------------------------
                 * (4) see (2) symmetric
                 *    g                  g               [g]                    x
                 *   / \    [p] right   / \              / \     [g] left      / \
                 *  u  [p]   rotation  u  [x]   color   u   x    rotation    [g]  [p]
                 *     / \  - - - - ->       \  - - - ->     \  - - - - ->   /
                 *   [x]  T1                 [p]             [p]            u
                 *
                 * ----------------------------------------------------------
                 */
                if (p == g.left) {
                    // if x's parent node is x's grandparent node's left child; see Chart above (1)(2)
                    u = g.right as RBNode?
                    if (u?.color == RED) { // (1) if parent and uncle color both are RED,
                        p.color = BLACK   // step 1 set parent color BLACK
                        u.color = BLACK   // step 2 set uncle color BLACK
                        g.color = RED     // step 3 set grandparent color BLACK
                        x = g        // step 4 set grandparent node as new 'insert' node, continue
                    } else {               // (2) if uncle color is BLACK
                        // then if x is parent right child, left rotation parent
                        // exchange x and parent, set parent node as new 'insert' node 'x'
                        if (x == p.right) {
                            x = p
                            p = leftRotation(x)
                        }
                        p.color = BLACK      // step 1 set x's parent color BLACK
                        g.color = RED        // step 2 set grandparent color RED
                        rightRotation(g)     // step 3 right rotation grandparent
                    }
                } else {
                    // if x's parent node is x's grandparent node's right child; see Chart above (3)(4)
                    // this is symmetric of above
                    u = g.left as RBNode?
                    if (u?.color == RED) { // (3) if parent and uncle color both are RED,
                        p.color = BLACK   // step 1 set parent color BLACK
                        u.color = BLACK   // step 2 set uncle color BLACK
                        g.color = RED     // step 3 set grandparent color BLACK
                        x = g      // step 4 set grandparent node as new 'insert' node, continue
                    } else {               // (4) if uncle color is BLACK
                        if (x == p.left) {  // then if x is parent left child, right rotation parent
                            x = p
                            p = rightRotation(x)
                        }
                        p.color = BLACK     // step 1 set x's parent color BLACK
                        g.color = RED       // step 2 set grandparent color RED
                        leftRotation(g)     // step 3 left rotation grandparent
                    }
                }
            }
        }

        // finally set root color BLACK
        (root as RBNode?)?.color = BLACK
    }

    /**
     *  \           \
     *   [A]         B
     *     \   =>   / \
     *      B      A   C
     *     / \      \
     *    T1  C     T1
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

        println("\n\nafter right rotation, nodeA value = [${nodeA.value}]")
        printTree()
        return nodeB
    }
}