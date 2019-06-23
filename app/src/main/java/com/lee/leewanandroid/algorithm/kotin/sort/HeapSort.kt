package com.lee.leewanandroid.algorithm.kotin.sort

import com.lee.leewanandroid.algorithm.kotin.sort.interfaces.ISort
import java.util.ArrayList

/**
 * 堆排序
 */
class HeapSort : ISort<Int> {
    override fun sort(data: ArrayList<Int>): MutableList<Int> {
        initHeap(data, data.size)
        for (i in data.size - 1 downTo 0) {
            data.swap(0, i)
            adjustHeap(data, 0, i)
        }

        return data
    }

    /**
     * 初始化大顶堆
     */
    private fun initHeap(heap: ArrayList<Int>, length: Int) {
        //从第一个非叶子结点从下至上，从右至左调整结构
        for (i in heap.size / 2 - 1 downTo 0) {
            adjustHeap(heap, i, length)
        }
    }

    /**
     * @param heap 待调整的堆
     * @param position 父节点位置
     * @param length 剩余堆大小
     */
    private fun adjustHeap(heap: ArrayList<Int>, position: Int, length: Int) {
        var parent = position
        var child = parent * 2 + 1
        while (child < length) {
            //取左、右孩子节点较大者
            if (child + 1 < length && heap[child + 1] > heap[child]) {
                child++
            }

            //如果child值大于parent，则交换，并且评估交换过后的以child为父节点的堆的情况
            if (heap[child] > heap[parent]) {
                heap.swap(child, parent)
                parent = child
                child = parent * 2 + 1
            } else {
                break
            }
        }
        println("adjust : $heap")
    }

    /**
     * @param heap 待调整的堆
     * @param parent 父节点位置
     * @param length 剩余堆大小
     */
    private fun adjustHeapR(heap: ArrayList<Int>, parent: Int, length: Int) {
        var child = parent * 2 + 1
        if (child < length) {
            //取左、右孩子节点较大者
            if (child + 1 < length && heap[child + 1] > heap[child]) {
                child++
            }

            //如果child值大于parent，则交换，并且评估交换过后的以child为父节点的堆的情况
            if (heap[child] > heap[parent]) {
                heap.swap(child, parent)
                adjustHeapR(heap, child, length)
            }
        }
        println("adjust : $heap")
    }
}