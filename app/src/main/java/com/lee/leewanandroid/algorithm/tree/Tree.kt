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
class Tree<T> {
    /**
     * [A B C D E F G H I # # # J]
     * 层序遍历
     *              A
     *            /   \
     *           B     C
     *         / \    / \
     *        D  E   F   G
     *       /\       \
     *      H  I       J
     */

    var root: Node<T>? = null

    fun createTree(data: ArrayList<T>) {
        root = createTreeByLevelOrder(data)
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