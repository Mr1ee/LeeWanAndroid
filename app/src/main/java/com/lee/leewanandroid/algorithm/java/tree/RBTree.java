package com.lee.leewanandroid.algorithm.java.tree;

import com.lee.leewanandroid.algorithm.java.tree.node.Node;

/**
 * @Description: RBTree
 * @Author: lihuayong
 * @CreateDate: 2019-06-24 16:26
 * @UpdateUser:
 * @UpdateDate: 2019-06-24 16:26
 * @UpdateRemark:
 * @Version: 1.0
 */
@SuppressWarnings("unused")
public class RBTree<K extends Comparable<K>, V> extends BSTree<K, V> implements OnTreeChangedListener<K, V> {

    private static final boolean BLACK = Node.BLACK;
    private static final boolean RED = !BLACK;

    public RBTree() {
        setOnTreeChangedListener(this);
    }

    /**
     * 插入节点之后的调整，平衡二叉树（AVL-Tree）或者红黑树（RB-Tree）
     *
     * 1 如果待删除结点 X 是一个红色结点，则直接删除即可，不会违反定义。
     * 2 如果待删除结点 X 是一个黑色结点，且其孩子结点 C 是红色的，那么只需要将 X 替换成 C，同时将 C 由红变黑即可。
     * 3 如果需要删除的结点 X 是黑色的，同时它的孩子结点 C 也是黑色的，这种情况需要进一步分场景讨论。
     *
     *
     * @param x 插入的节点
     */
    @Override
    public void fixAfterInsertion(Node<K, V> x) {
        x.color = RED;

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
        while (x != null && x != root && x.parent.color == RED) {
            if (parentOf(x) == leftOf(parentOf(parentOf(x)))) {
                Node<K, V> y = rightOf(parentOf(parentOf(x)));
                if (colorOf(y) == RED) {
                    setColor(parentOf(x), BLACK);
                    setColor(y, BLACK);
                    setColor(parentOf(parentOf(x)), RED);
                    x = parentOf(parentOf(x));
                } else {
                    if (x == rightOf(parentOf(x))) {
                        x = parentOf(x);
                        rotateLeft(x);
                    }
                    setColor(parentOf(x), BLACK);
                    setColor(parentOf(parentOf(x)), RED);
                    rotateRight(parentOf(parentOf(x)));
                }
            } else {
                Node<K, V> y = leftOf(parentOf(parentOf(x)));
                if (colorOf(y) == RED) {
                    setColor(parentOf(x), BLACK);
                    setColor(y, BLACK);
                    setColor(parentOf(parentOf(x)), RED);
                    x = parentOf(parentOf(x));
                } else {
                    if (x == leftOf(parentOf(x))) {
                        x = parentOf(x);
                        rotateRight(x);
                    }
                    setColor(parentOf(x), BLACK);
                    setColor(parentOf(parentOf(x)), RED);
                    rotateLeft(parentOf(parentOf(x)));
                }
            }
        }
        root.color = BLACK;
    }

    /**
     * 删除节点之后的调整，平衡二叉树（AVL-Tree）或者红黑树（RB-Tree）
     */
    @Override
    public void fixAfterDeletion(Node<K, V> x, boolean delete) {

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
         *        just change b's color from BLACK to RED. but p's parent(if has) will be out-off-balance
         *        so we just set p as x to continue.
         *      p?                       p
         *     / \        color         / \
         *    x   b     transform      x  [b]
         *       / \   - - - - - ->      /  \
         *      bl br                   bl  br
         *-------------------------------------------------------------------------------
         * (1)-ii
         *  x, b color is BLACK, but br and br color not all BLACK
         *  A: if br is RED,  left rotation p and set bl color p's color, set p、br color BLACK.
         *  [Tips] In this situation, we add p's left subtree's BLACK node by left rotating p, and
         *          don't forget to adjust nodes color. and NOW we have balanced all tree, just break.
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
        while (x != root && colorOf(x) == BLACK) {
            if (x == leftOf(parentOf(x))) {
                Node<K, V> p = parentOf(x);
                Node<K, V> b = rightOf(p);

                if (colorOf(b) == RED) {
                    setColor(b, BLACK);
                    setColor(p, RED);
                    rotateLeft(p);
                    b = rightOf(p);
                }

                Node<K, V> bl = leftOf(b);
                Node<K, V> br = rightOf(b);
                if (colorOf(bl) == BLACK &&
                        colorOf(br) == BLACK) {
                    setColor(b, RED);
                    x = p;
                } else {
                    if (colorOf(br) == BLACK) {
                        setColor(bl, BLACK);
                        setColor(b, RED);
                        rotateRight(b);
                        b = rightOf(p);
                    }
                    setColor(b, colorOf(p));
                    setColor(p, BLACK);
                    setColor(rightOf(b), BLACK);
                    rotateLeft(p);
                    x = root;
                }
            } else { // symmetric
                Node<K, V> p = parentOf(x);
                Node<K, V> b = leftOf(p);

                if (colorOf(b) == RED) {
                    setColor(b, BLACK);
                    setColor(p, RED);
                    rotateRight(p);
                    b = leftOf(p);
                }

                Node<K, V> bl = leftOf(b);
                Node<K, V> br = rightOf(b);
                if (colorOf(bl) == BLACK &&
                        colorOf(br) == BLACK) {
                    setColor(b, RED);
                    x = p;
                } else {
                    if (colorOf(bl) == BLACK) {
                        setColor(br, BLACK);
                        setColor(b, RED);
                        rotateLeft(b);
                        b = leftOf(p);
                    }
                    setColor(b, colorOf(p));
                    setColor(p, BLACK);
                    setColor(leftOf(b), BLACK);
                    rotateRight(p);
                    x = root;
                }
            }
        }

        setColor(x, BLACK);
    }

    private static <K, V> boolean colorOf(Node<K, V> p) {
        return (p == null ? BLACK : p.color);
    }

    private static <K, V> Node<K, V> parentOf(Node<K, V> p) {
        return (p == null ? null : p.parent);
    }

    private static <K, V> void setColor(Node<K, V> p, boolean c) {
        if (p != null)
            p.color = c;
    }

    private static <K, V> Node<K, V> leftOf(Node<K, V> p) {
        return (p == null) ? null : p.left;
    }

    private static <K, V> Node<K, V> rightOf(Node<K, V> p) {
        return (p == null) ? null : p.right;
    }

    private void rotateLeft(Node<K, V> p) {
        if (p != null) {
            Node<K, V> r = p.right;
            p.right = r.left;
            if (r.left != null)
                r.left.parent = p;
            r.parent = p.parent;
            if (p.parent == null)
                root = r;
            else if (p.parent.left == p)
                p.parent.left = r;
            else
                p.parent.right = r;
            r.left = p;
            p.parent = r;

            // Update heights
            updateNodeHeight(p);
            updateNodeHeight(r);

            println("after rotateLeft " + p.key);
            printTree();
        }
    }

    private void rotateRight(Node<K, V> p) {
        if (p != null) {
            Node<K, V> l = p.left;
            p.left = l.right;
            if (l.right != null) l.right.parent = p;
            l.parent = p.parent;
            if (p.parent == null)
                root = l;
            else if (p.parent.right == p)
                p.parent.right = l;
            else p.parent.left = l;
            l.right = p;
            p.parent = l;

            // Update heights
            updateNodeHeight(p);
            updateNodeHeight(l);

            println("after rotateRight " + p.key);
            printTree();
        }
    }
}
