package com.lee.leewanandroid.algorithm.java.tree.node;

@SuppressWarnings("unused")
public class Node<K, V> {
    public static final boolean BLACK = true;
    public Node<K, V> left = null;
    public Node<K, V> right = null;
    public Node<K, V> parent;
    public int height = 1;
    public boolean color = BLACK;
    public K key;
    public V value;

    public Node(K key, V value) {
        this(key, value, null);
    }

    public Node(K key, V value, Node<K, V> parent) {
        this.key = key;
        this.value = value;
        this.parent = parent;
    }

    public Node<K, V> getLeft() {
        return left;
    }

    public void setLeft(Node<K, V> left) {
        this.left = left;
    }

    public Node<K, V> getRight() {
        return right;
    }

    public void setRight(Node<K, V> right) {
        this.right = right;
    }

    public Node<K, V> getParent() {
        return parent;
    }

    public void setParent(Node<K, V> parent) {
        this.parent = parent;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    public static <K, V> void printNode(Node<K, V> node) {
        System.out.print("<" + node.getKey() + "," + node.getValue() + "> ");
    }

    public boolean isLeaf() {
        return this.left == null && this.right == null;
    }

    public boolean noLeftChild() {
        return this.left == null;
    }

    public boolean noRightChild() {
        return this.right == null;
    }

    public boolean isRed() {
        return color != BLACK;
    }

    public int balance() {
        int lh = left == null ? 0 : left.height;
        int rh = right == null ? 0 : right.height;
        return lh - rh;
    }

    public int size() {
        int lSize = left == null ? 0 : left.size();
        int rlSize = right == null ? 0 : right.size();
        return lSize + rlSize + 1;
    }

    /**
     * @return 前驱结点
     */
    public Node<K, V> predecessor() {
        if (this.right != null) {
            Node<K, V> p = this.right;
            while (p.left != null)
                p = p.left;
            return p;
        } else {
            Node<K, V> p = this.parent;
            Node<K, V> ch = this;
            while (p != null && ch == p.right) {
                ch = p;
                p = p.parent;
            }
            return p;
        }
    }

    /**
     * @return 后继节点
     */
    public Node<K, V> successor() {
        if (this.left != null) {
            Node<K, V> p = this.left;
            while (p.right != null)
                p = p.right;
            return p;
        } else {
            Node<K, V> p = this.parent;
            Node<K, V> ch = this;
            while (p != null && ch == p.left) {
                ch = p;
                p = p.parent;
            }
            return p;
        }
    }
}
