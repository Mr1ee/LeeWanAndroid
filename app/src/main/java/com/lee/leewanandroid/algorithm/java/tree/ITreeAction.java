package com.lee.leewanandroid.algorithm.java.tree;

import com.lee.leewanandroid.algorithm.java.tree.PrintStrategy.IPrintStrategy;
import com.lee.leewanandroid.algorithm.java.tree.node.Node;

/**
 * 树的一些基本操作，增、删、查、改、打印树
 */
public interface ITreeAction<K, V> {
    boolean insert(K key, V value);

    boolean remove(K key);

    Node<K, V> find(K key);

    boolean amend(K key, V value);

    void print(IPrintStrategy strategy);
}
