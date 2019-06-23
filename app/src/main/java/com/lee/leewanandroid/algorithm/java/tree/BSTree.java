package com.lee.leewanandroid.algorithm.java.tree;

import com.lee.leewanandroid.algorithm.java.tree.node.Node;

public class BSTree<K extends Comparable<K>, V> extends BTree<K, V> {

    @Override
    public boolean insert(K key, V value) {
        return super.insert(key, value);
    }

    @Override
    public boolean remove(K key) {
        return super.remove(key);
    }

    @Override
    public Node<K, V> find(K key) {
        return super.find(key);
    }

    @Override
    public boolean amend(K key, V value) {
        return super.amend(key, value);
    }

}
