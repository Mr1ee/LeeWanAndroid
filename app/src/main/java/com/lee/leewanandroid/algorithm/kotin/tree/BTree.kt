package com.lee.leewanandroid.algorithm.kotin.tree

import com.lee.leewanandroid.algorithm.kotin.tree.node.Node
import com.lee.leewanandroid.algorithm.kotin.tree.node.RBNode
import java.util.*
import kotlin.collections.ArrayList

/**
 *
 * @Description:    BTree 二叉树 binary tree
 * @Author:         lihuayong
 * @CreateDate:     2019-06-17 10:46
 * @UpdateUser:
 * @UpdateDate:     2019-06-17 10:46
 * @UpdateRemark:
 * @Version:        1.0
 */
@Suppress("unused")
open class BTree<T> : ITreeAction<T> {

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

    /**
     * clear tree
     */
    override fun clear() {
        root = null
        println("clear tree.")
    }

    /**
     * insert node to tree, return true if insert success!
     */
    override fun insert(value: T): Boolean {
        //need to be override by subclass
        return false
    }

    /**
     * remove node equals value, return true if remove success!
     */
    override fun remove(value: T): Boolean {
        //need to be override by subclass
        return false
    }

    /**
     * find node equals value, return null if not find!
     */
    override fun find(value: T): Node<T>? {
        //need to be override by subclass
        return null
    }

    open fun createTree(data: ArrayList<T>) {
        val stack = Stack<T>()
        for (i in data.size - 1 downTo 0) {
            stack.push(data[i])
        }
        println(stack)

//        root = createTreeByLevelOrder(data)
        root = createTreeByPreOrder(stack)
    }

    fun printTreeSize() {
        println("tree size is ${root.size()}")
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
            val parent: Node<T> =
                Node(value)
            parent.left = createTreeByPreOrder(data)
            parent.right = createTreeByPreOrder(data)
            parent
        } else {
            null
        }
    }

    @Suppress("UNCHECKED_CAST")
    fun buildTree(preOrder: String?, inOrder: String?) {
        root = buildTreeInner(preOrder, inOrder) as Node<T>
    }

    @Suppress("UNCHECKED_CAST")
    fun buildTree2(postOrder: String?, inOrder: String?) {
        root = buildTreeInner2(postOrder, inOrder) as Node<T>
    }

    /**
     * 用前序遍历串和后序遍历串重建二叉树
     * @param preOrder 前序遍历串
     * @param inOrder  中序遍历串
     */
    private fun buildTreeInner(preOrder: String?, inOrder: String?): Node<Char>? {
        if (preOrder.isNullOrEmpty() || inOrder.isNullOrEmpty() || preOrder.length != inOrder.length) return null
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
        if (postOrder.isNullOrEmpty() || inOrder.isNullOrEmpty() || postOrder.length != inOrder.length) return null
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
            root.print()
            parent.left?.let { queue.add(it) }
            parent.right?.let { queue.add(it) }
        }
    }

    /**
     * 前序遍历（中-->左->右） 递归实现
     */
    fun preOrderTraverseR(root: Node<T>?) {
        root?.let {
            root.print()

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

            root.print()

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
            root.print()
        }
    }

    /**
     * 前序遍历（中-->左->右） 非递归实现
     */
    fun preOrderTraverse(root: Node<T>) {
        val stack: Stack<Pair<Boolean, Node<T>>> = Stack()
        stack.push(Pair(false, root))
        while (stack.isNotEmpty()) {
            val pair = stack.pop()
            val visited = pair.first
            val parent = pair.second
            if (visited) {
                parent.print()
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

    /**
     * 中序遍历（左-->中->右） 非递归实现
     */
    fun inOrderTraverse(root: Node<T>) {
        val stack: Stack<Pair<Boolean, Node<T>>> = Stack()
        stack.push(Pair(false, root))
        while (stack.isNotEmpty()) {
            val pair = stack.pop()
            val visited = pair.first
            val parent = pair.second
            if (visited) {
                parent.print()
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

    /**
     * 后序遍历（左-->右->中） 非递归实现
     */
    fun postOrderTraverse(root: Node<T>) {
        val stack: Stack<Pair<Boolean, Node<T>>> = Stack()
        stack.push(Pair(false, root))
        while (stack.isNotEmpty()) {
            val pair = stack.pop()
            val visited = pair.first
            val parent = pair.second
            if (visited) {
                parent.print()
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

    /**
     * calculate all node's height
     */
    protected fun calculateAllNodesHeight(node: Node<T>? = root): Int {
        if (node == null) {
            return 0
        }
        var l: Int
        var r: Int
        node.left.let {
            l = calculateAllNodesHeight(it)
        }

        node.right.let {
            r = calculateAllNodesHeight(it)
        }

        node.height = if (l > r) {
            l + 1
        } else {
            r + 1
        }

        return if (l > r) {
            l + 1
        } else {
            r + 1
        }
    }

    /**
     *  print tree
     *               ┌────────────── #
     *       ┌────── A ──────┐
     *   ┌── B ──┐       ┌── C ──┐
     * ┌ D ┐   ┌ E ┐   ┌ F ┐   ┌ G ┐
     * H   I   #   J   #   #   #   #
     */
    fun printTree(r: Node<T>? = this.root) {
        calculateAllNodesHeight()
        if (r == null) {
            println("tree has empty")
            return
        }
        val height = r.height()
        val fullNodesList = ArrayList<ArrayList<Node<T>>>()
        fillTree(fullNodesList, r, height)

        val maxWidth = Math.pow(2.0, height.toDouble())
        fullNodesList.forEachIndexed { index, it ->
            if (index != fullNodesList.size - 1) {
                val size = it.size
                for (j in 0 until it.size) {
                    printChar(" ", (maxWidth / size / 2 - 2).toInt())
                    if (it[j].left!!.value != "#") {
                        print("┌")
                        printChar("─", (maxWidth / size / 2 - 3).toInt())
                    } else {
                        printChar(" ", (maxWidth / size / 2 - 2).toInt())
                    }
                    val t = it[j].value!!.toString()
                    when {
                        t == "#" -> print("   ")
                        t.length == 1 -> if (it[j] is RBNode && (it[j] as RBNode).color == RBNode.RED) {
                            print("[$t]")
                        } else print(" $t ")
                        t.length == 2 -> if (it[j] is RBNode && (it[j] as RBNode).color == RBNode.RED) {
                            print("[$t]")
                        } else print(" $t")
                        else -> if (it[j] is RBNode && (it[j] as RBNode).color == RBNode.RED) {
                            print("[$t]")
                        } else print(t)
                    }

                    if (it[j].right!!.value != "#") {
                        printChar("─", (maxWidth / size / 2 - 3).toInt())
                        print("┐")
                    } else {
                        printChar(" ", (maxWidth / size / 2 - 2).toInt())
                    }
                    printChar(" ", (maxWidth / size / 2 - 1).toInt())
                }
                println()
            } else {
                for (j in 0 until it.size) {
                    val t = it[j].value!!.toString()
                    when {
                        t == "#" -> print("   ")
                        t.length == 1 -> if (it[j] is RBNode && (it[j] as RBNode).color == RBNode.RED) {
                            print("[$t]")
                        } else print(" $t ")
                        t.length == 2 -> if (it[j] is RBNode && (it[j] as RBNode).color == RBNode.RED) {
                            print("[$t]")
                        } else print(" $t")
                        else -> if (it[j] is RBNode && (it[j] as RBNode).color == RBNode.RED) {
                            print("[$t]")
                        } else print(t)
                    }
                    print(" ")
                }
                println()
            }
        }

        deleteNull(r)
    }

    /**
     * full fill tree, replace null node with "#" node
     *
     *   ┌── B ──┐       ┌── C ──┐
     * ┌ D ┐   ┌ E ┐   ┌ F ┐   ┌ G ┐
     * H   I   #   J   #   #   #   #
     */
    @Suppress("UNCHECKED_CAST")
    private fun fillTree(
        fullNodesList: ArrayList<ArrayList<Node<T>>>,
        r: Node<T>,
        height: Int
    ) {
        fullNodesList.add(arrayListOf(r))
        var i = 0
        while (i++ < height - 1) {
            val preNodes = fullNodesList[fullNodesList.size - 1]
            val newRowNodes = ArrayList<Node<T>>()
            preNodes.forEach {
                if (it.value == "#") {
                    it.left = Node("#") as Node<T>
                    it.right = Node("#") as Node<T>
                    newRowNodes.add(Node("#") as Node<T>)
                    newRowNodes.add(Node("#") as Node<T>)
                } else {
                    if (it.left == null) {
                        it.left = Node("#") as Node<T>
                        newRowNodes.add(Node("#") as Node<T>)
                    } else {
                        newRowNodes.add(it.left!!)
                    }

                    if (it.right == null) {
                        it.right = Node("#") as Node<T>
                        newRowNodes.add(Node("#") as Node<T>)
                    } else {
                        newRowNodes.add(it.right!!)
                    }
                }
            }
            fullNodesList.add(newRowNodes)
        }
    }

    /**
     * delete all the "#" node
     */
    private fun deleteNull(node: Node<T>?) {
        node?.let {
            if (it.left != null) {
                if (it.left?.value == "#") {
                    it.left = null
                } else {
                    deleteNull(it.left)
                }
            }

            if (it.right != null) {
                if (it.right?.value == "#") {
                    it.right = null
                } else {
                    deleteNull(it.right)
                }
            }
        }
    }

    /**
     * print [s] for [n] times
     */
    private fun printChar(s: String, n: Int) {
        for (i in 0..n) {
            print(s)
        }
    }
}