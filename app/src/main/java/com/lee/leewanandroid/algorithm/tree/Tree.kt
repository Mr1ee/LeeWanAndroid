package com.lee.leewanandroid.algorithm.tree

import java.util.*

/**
 *
 * @Description:    Tree
 * @Author:         lihuayong
 * @CreateDate:     2019-06-17 10:46
 * @UpdateUser:
 * @UpdateDate:     2019-06-17 10:46
 * @UpdateRemark:
 * @Version:        1.0
 */
open class Tree<T> {
    /**
     * 层序遍历构建串 [A B C D E F G # # H I # J]
     *
     * 先序遍历构建串 [A B D # # E H # # I # # C F # J # # G # #]
     *
     * 中序遍历构建串 [D B H E I A F J C G]
     *
     * 后序遍历构建串 [D H I E B J F G C A]
     *
     *              A
     *            /   \
     *           B     C
     *         / \    / \
     *        D  E   F   G
     *          / \   \
     *         H  I    J
     */

    var root: Node<T>? = null

    open fun createTree(data: ArrayList<T>) {
        val stack = Stack<T>()
        for (i in data.size - 1 downTo 0) {
            stack.push(data[i])
        }
        println(stack)

//        root = createTreeByLevelOrder(data)
        root = createTreeByPreOrder(stack)
    }

    /**
     * @param data 用来构建树的数据,'#'用来标记为null
     */
    fun createTreeByLevelOrder(data: ArrayList<T>): Node<T>? {
        if (data.isEmpty()) return null
        val queue: Queue<Pair<Int, Node<T>>> = ArrayDeque(data.size)
        val root = Node(data[0])
        queue.add(Pair(0, root))
        while (queue.isNotEmpty()) {
            val position = queue.peek().first
            val parent = queue.poll().second
            val leftChild = position * 2 + 1
            if (leftChild < data.size && data[leftChild] != "#") {
                val left = Node(data[leftChild])
                parent.left = left
                queue.add(Pair(leftChild, left))
            }

            val rightChild = position * 2 + 2
            if (rightChild < data.size && data[rightChild] != "#") {
                val right = Node(data[rightChild])
                parent.right = right
                queue.add(Pair(rightChild, right))
            }
        }

        return root
    }

    fun createTreeByPreOrder(data: Stack<T>): Node<T>? {
        if (data.isEmpty()) return null
        val value = data.pop()
        return if (value != "#") {
            val parent: Node<T> = Node(value)
            parent.left = createTreeByPreOrder(data)
            parent.right = createTreeByPreOrder(data)
            parent
        } else {
            null
        }
    }

    fun buildTree(preOrder: String?, inOrder: String?) {
        root = buildTreeInner(preOrder, inOrder) as Node<T>
    }

    fun buildTree2(postOrder: String?, inOrder: String?) {
        root = buildTreeInner2(postOrder, inOrder) as Node<T>
    }

    /**
     * 用前序遍历串和后序遍历串重建二叉树
     * @param preOrder 前序遍历串
     * @param inOrder  中序遍历串
     */
    private fun buildTreeInner(preOrder: String?, inOrder: String?): Node<Char>? {
        if (preOrder.isNullOrEmpty() || inOrder.isNullOrEmpty()) return null
        val parent = Node(preOrder.first())
        val length = inOrder.indexOf(preOrder.first())
        parent.left =
            buildTreeInner(preOrder.substring(1, length + 1), inOrder.substring(0, length))
        parent.right = buildTreeInner(preOrder.substring(length + 1), inOrder.substring(length + 1))
        return parent
    }

    /**
     * 用后序遍历串和后序遍历串重建二叉树
     * @param postOrder 后序遍历串
     * @param inOrder   中序遍历串
     */
    private fun buildTreeInner2(postOrder: String?, inOrder: String?): Node<Char>? {
        if (postOrder.isNullOrEmpty() || inOrder.isNullOrEmpty()) return null
        val parent = Node(postOrder.last())
        val length = inOrder.indexOf(postOrder.last())
        parent.left =
            buildTreeInner2(postOrder.substring(0, length), inOrder.substring(0, length))
        parent.right =
            buildTreeInner2(
                postOrder.substring(length, postOrder.lastIndex),
                inOrder.substring(length + 1)
            )
        return parent
    }

    fun levelOrderTraverse(root: Node<T>) {
        val queue: Queue<Node<T>> = ArrayDeque()
        queue.add(root)
        while (queue.isNotEmpty()) {
            val parent = queue.poll()
            print(" " + parent.value)
            parent.left?.let { queue.add(it) }
            parent.right?.let { queue.add(it) }
        }
    }

    /**
     * 前序遍历（中-->左->右） 递归实现
     */
    fun preOrderTraverseR(root: Node<T>?) {
        root?.let {
            print(" " + root.value)

            root.left?.let {
                preOrderTraverseR(it)
            }

            root.right?.let {
                preOrderTraverseR(it)
            }
        }
    }

    /**
     * 中序遍历（左-->中->右） 递归实现
     */
    fun inOrderTraverseR(root: Node<T>?) {
        root?.let {
            root.left?.let {
                inOrderTraverseR(it)
            }

            print(" " + root.value)

            root.right?.let {
                inOrderTraverseR(it)
            }
        }
    }

    /**
     * 后序遍历（左-->右->中） 递归实现
     */
    fun postOrderTraverseR(root: Node<T>?) {
        root?.let {
            root.left?.let {
                postOrderTraverseR(it)
            }

            root.right?.let {
                postOrderTraverseR(it)
            }

            print(" " + root.value)
        }
    }

    fun preOrderTraverse(root: Node<T>) {
        val stack: Stack<Pair<Boolean, Node<T>>> = Stack()
        stack.push(Pair(false, root))
        while (stack.isNotEmpty()) {
            val pair = stack.pop()
            val visited = pair.first
            val parent = pair.second
            if (visited) {
                print(" " + parent.value)
            } else {
                parent.right?.let {
                    stack.push(Pair(false, it))
                }
                parent.left?.let {
                    stack.push(Pair(false, it))
                }
                stack.push(Pair(true, parent))
            }
        }
    }

    fun inOrderTraverse(root: Node<T>) {
        val stack: Stack<Pair<Boolean, Node<T>>> = Stack()
        stack.push(Pair(false, root))
        while (stack.isNotEmpty()) {
            val pair = stack.pop()
            val visited = pair.first
            val parent = pair.second
            if (visited) {
                print(" " + parent.value)
            } else {
                parent.right?.let {
                    stack.push(Pair(false, it))
                }
                stack.push(Pair(true, parent))
                parent.left?.let {
                    stack.push(Pair(false, it))
                }
            }
        }
    }

    fun postOrderTraverse(root: Node<T>) {
        val stack: Stack<Pair<Boolean, Node<T>>> = Stack()
        stack.push(Pair(false, root))
        while (stack.isNotEmpty()) {
            val pair = stack.pop()
            val visited = pair.first
            val parent = pair.second
            if (visited) {
                print(" " + parent.value)
            } else {
                stack.push(Pair(true, parent))
                parent.right?.let {
                    stack.push(Pair(false, it))
                }
                parent.left?.let {
                    stack.push(Pair(false, it))
                }
            }
        }
    }
}