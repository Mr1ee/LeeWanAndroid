package com.lee.leewanandroid.algorithm.java.tree;

import com.lee.leewanandroid.algorithm.java.tree.node.Node;

@SuppressWarnings("unused")
public class BSTree<K extends Comparable<K>, V> extends BTree<K, V> {

    private OnTreeChangedListener<K,V> listener = null;

    void setOnTreeChangedListener(OnTreeChangedListener<K,V> listener) {
        this.listener = listener;
    }

    @Override
    public boolean insert(K key, V value) {
//        Node<K, V> node = find(key);
//        if (node == null) {
//            root = insertInternalR(root, null, key, value);
//            println("after insert <" + key + "," + value + ">");
//            printTree();
//            return true;
//        } else {
//            return false;
//        }
        boolean inserted = insertInternal(key, value);
        println("after insert <" + key + "," + value + ">");
        printTree();
        return inserted;
    }

    @Override
    public boolean remove(K key) {
        Node<K, V> node = find(key);
        if (node == null) {
            return false;
        } else {
//            root = removeInternalR(root, key);
            removeInternal(node);
            println("after remove " + key);
            printTree();
            return true;
        }
    }

    @Override
    public Node<K, V> find(K key) {
        return findInternalR(root, key);
    }

    @Override
    public boolean amend(K key, V value) {
        Node<K, V> node = find(key);
        if (node == null) {
            return false;
        } else {
            node.value = value;
            return true;
        }
    }

    private boolean insertInternal(K key, V value) {
        if (key == null || value == null) {
            return false;
        }
        Node<K, V> t = root;
        if (t == null) {
            root = new Node<>(key, value, null);
            return true;
        }
        Node<K, V> parent = null;
        int cpr = 0;
        while (t != null) {
            parent = t;
            cpr = key.compareTo(t.key);
            if (cpr < 0) {
                t = t.left;
            } else if (cpr > 0) {
                t = t.right;
            } else {
                System.out.println("tree has the same key,can not insert again.");
                return false;
            }
        }
        Node<K, V> e = new Node<>(key, value, parent);
        if (cpr < 0)
            parent.left = e;
        else
            parent.right = e;

        if (listener != null) {
            listener.fixAfterInsertion(e);
        }
        return true;
    }

    private Node<K, V> insertInternalR(Node<K, V> node, Node<K, V> parent, K key, V value) {
        if (node == null) {
            return new Node<>(key, value, parent);
        }
        int cpr = key.compareTo(node.key);
        if (cpr > 0) {
            node.right = insertInternalR(node.right, node, key, value);
        } else if (cpr < 0) {
            node.left = insertInternalR(node.left, node, key, value);
        } else {
            //tree has the same key,can not insert again.
            System.out.println("tree has the same key,can not insert again.");
            return null;
        }
        updateNodeHeight(node);

        return node;
    }

    void updateNodeHeight(Node<K, V> node) {
        int lh = getNodeHeight(node.left);
        int rh = getNodeHeight(node.right);
        node.height = (lh > rh ? lh + 1 : rh + 1);
    }

    private Node<K, V> removeInternalR(Node<K, V> node, K key) {
        if (node == null) {
            return null;
        }
        int cpr = key.compareTo(node.getKey());
        if (cpr > 0) {
            node.right = removeInternalR(node.right, key);
        } else if (cpr < 0) {
            node.left = removeInternalR(node.left, key);
        } else {
            if (node.isLeaf()) {
                return null;
            } else if (node.noLeftChild()) {
                return node.right;
            } else if (node.noRightChild()) {
                return node.left;
            } else {
                //待删除结点左右孩子结点均不为null，这个时候可以去找待删除结点的后继节点，或者前驱结点，
                //根据二叉排序树的定义后继节点就是左子树的最大节点，前驱结点就是右子树的最小节点
                //找到前驱结点, 交换前驱结点与node的值,然后删除前驱结点
                Node<K, V> predecessorNode = node.predecessor();
                //将前驱结点的值交给node
                node.value = predecessorNode.value;
                node.key = predecessorNode.key;
                //删除 前驱节点
                node.right = removeInternalR(node.right, predecessorNode.key);
            }
        }

        updateNodeHeight(node);
        return node;
    }

    private void removeInternal(Node<K, V> node) {
        if (node.left != null && node.right != null) {
            Node<K, V> s = node.predecessor();
            node.key = s.key;
            node.value = s.value;
            node = s;
        } // p has 2 children

        // Start fix up at replacement node, if it exists.
        Node<K, V> replacement = (node.left != null ? node.left : node.right);
        if (replacement != null) {
            // Link replacement to parent
            replacement.parent = node.parent;
            if (node.parent == null)
                root = replacement;
            else if (node == node.parent.left)
                node.parent.left = replacement;
            else
                node.parent.right = replacement;

            // Null out links so they are OK to use by fixAfterDeletion.
            node.left = node.right = node.parent = null;

            //fixAfterDeletion(node)
            if (listener != null) {
                if (this instanceof RBTree ) {
                    if (node.isBLACK()) {
                        listener.fixAfterDeletion(replacement, true);
                    }
                } else {
                    listener.fixAfterDeletion(node, true);
                }
            }
        } else if (node.parent == null) { // return if we are the only node.
            root = null;
        } else { //  No children. Use self as phantom replacement and unlink.
            if (listener != null) {
                if (this instanceof RBTree ) {
                    if (node.isBLACK()) {
                        listener.fixAfterDeletion(node, true);
                    }
                } else {
                    listener.fixAfterDeletion(node, true);
                }
            }
            if (node.parent != null) {
                if (node == node.parent.left)
                    node.parent.left = null;
                else if (node == node.parent.right)
                    node.parent.right = null;
                node.parent = null;
            }
        }
    }


    private Node<K, V> findInternalR(Node<K, V> node, K key) {
        if (node == null) {
            return null;
        }
        int cpr = key.compareTo(node.key);
        if (cpr == 0) {
            return node;
        } else if (cpr > 0) {
            return findInternalR(node.right, key);
        } else {
            return findInternalR(node.left, key);
        }
    }

    private int getNodeHeight(Node<K, V> node) {
        return node == null ? 0 : node.height;
    }
}
