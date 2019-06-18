package com.lee.leewanandroid.algorithm.tree

/**
 *
 * @Description:    AVLNode
 * @Author:         lihuayong
 * @CreateDate:     2019-06-18 16:18
 * @UpdateUser:
 * @UpdateDate:     2019-06-18 16:18
 * @UpdateRemark:
 * @Version:        1.0
 */
class AVLNode<T>(value: T, parent: TNode<T>?) : TNode<T>(value, parent) {
    var height: Int = 0
}