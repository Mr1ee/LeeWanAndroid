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
class AVLNode<T>(var key: T, var h: Int = 1) : Node<T>(key)