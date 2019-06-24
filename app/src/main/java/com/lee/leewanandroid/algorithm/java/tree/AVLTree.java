package com.lee.leewanandroid.algorithm.java.tree;

import com.lee.leewanandroid.algorithm.java.tree.node.Node;

/**
 * @Description: AVLTree
 * @Author: lihuayong
 * @CreateDate: 2019-06-24 14:27
 * @UpdateUser:
 * @UpdateDate: 2019-06-24 14:27
 * @UpdateRemark:
 * @Version: 1.0
 */
public class AVLTree<K extends Comparable<K>, V> extends BSTree<K, V> implements OnTreeChangedListener<K, V> {

    public AVLTree() {
        setOnTreeChangedListener(this);
    }

    @Override
    public void fixAfterInsertion(Node<K, V> node) {
        rebuildAfterTreeChanged(node);
    }

    @Override
    public void fixAfterDeletion(Node<K, V> node, boolean delete) {
        if (!delete) {
            //如果没有删除，因为平衡二叉树平衡需要计算节点高度，必须要把该节点删除了。
            //然后开始计算其父节点的平衡性
            if (node.parent != null) {
                if (node == node.parent.left)
                    node.parent.left = null;
                else if (node == node.parent.right)
                    node.parent.right = null;
            }
            rebuildAfterTreeChanged(node.parent);
            node.parent = null;
        } else {
            rebuildAfterTreeChanged(node);
        }
    }

    private void rebuildAfterTreeChanged(Node<K, V> node) {
        Node<K, V> p = node;
        while (p != null) {
            updateNodeHeight(p);
            int bf = p.balance();
            if (bf >= 2) {
                rotationAfterChanged(p, true);
            } else if (bf <= -2) {
                rotationAfterChanged(p, false);
            }
            p = p.parent;
        }
    }

    private void rotationAfterChanged(Node<K, V> p, boolean left) {
        if (left) {
            Node<K, V> lChild = p.left;
            if (lChild.balance() >= 0) { //右旋
                rotateRight(p);
            } else {
                rotateLeft(p.left);
                rotateRight(p);
            }
        } else {
            Node<K, V> rChild = p.right;
            if (rChild.balance() <= 0) { //左旋
                rotateLeft(p);
            } else {
                rotateRight(p.right);
                rotateLeft(p);
            }
        }
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
