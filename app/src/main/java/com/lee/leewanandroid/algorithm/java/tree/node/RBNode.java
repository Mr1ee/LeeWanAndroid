package com.lee.leewanandroid.algorithm.java.tree.node;

public class RBNode<K, V> extends Node<K, V> {
    private static final boolean RED = false;
    private static final boolean BLACK = !RED;

    protected boolean color;

    public RBNode(K key, V value) {
        this(key, value, null);
    }

    public RBNode(K key, V value, Node<K, V> parent) {
        this(key, value, parent, RED);
    }

    public RBNode(K key, V value, Node<K, V> parent, boolean color) {
        super(key, value, parent);
        this.color = color;
    }

    public boolean isColor() {
        return color;
    }

    public void setColor(boolean color) {
        this.color = color;
    }

}
