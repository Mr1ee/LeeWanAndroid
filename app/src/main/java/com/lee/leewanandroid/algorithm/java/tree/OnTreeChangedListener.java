package com.lee.leewanandroid.algorithm.java.tree;

import com.lee.leewanandroid.algorithm.java.tree.node.Node;

/**
 * @Description: OnTreeChangedListener
 * @Author: lihuayong
 * @CreateDate: 2019-06-24 14:22
 * @UpdateUser:
 * @UpdateDate: 2019-06-24 14:22
 * @UpdateRemark:
 * @Version: 1.0
 */
public interface OnTreeChangedListener<K, V> {

    /**
     * 插入节点之后的调整，平衡二叉树（AVL-Tree）或者红黑树（RB-Tree）
     *
     * @param node 插入的节点
     */
    void fixAfterInsertion(Node<K, V> node);

    /**
     * 删除节点之后的调整，平衡二叉树（AVL-Tree）或者红黑树（RB-Tree）
     *
     * @param node   被删除的节点或者替代节点，取决于delete的值
     * @param delete 如果delete = true，则node是被删除节点的替代节点，
     *               如果delete = false，则node是被删除的节点。
     *               （ps：之所以这样做，只是我想让平衡二叉树和红黑树都继承自
     *               二叉搜索树，统一接口。其实就是为了懒省事，少些写代码\(- -)/）
     */
    void fixAfterDeletion(Node<K, V> node, boolean delete);
}
