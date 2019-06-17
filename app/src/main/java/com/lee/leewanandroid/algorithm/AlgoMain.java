package com.lee.leewanandroid.algorithm;

import com.lee.leewanandroid.algorithm.tree.Tree;

import java.util.ArrayList;
import java.util.Arrays;

public class AlgoMain {

    public static ArrayList<Integer> data = new ArrayList<>(Arrays.asList(2, 9, 4, 7, 5, 8, 1, 3, 6, 10));
    public static ArrayList<Integer> data1 = new ArrayList<>(Arrays.asList(542, 3521, 13459, 852, 742, 46, 2, 1, 633, 32));

    public static ArrayList<String> treeData = new ArrayList<>(Arrays.asList("A", "B", "C", "D", "E", "F", "G", "H", "I", "#", "#", "#", "J"));

    public static void main(String[] args) {

//        ISort<Integer> algo = new BubbleSort();
//        System.out.println(algo.sort(data));
//
//        ISort<Integer> shellSort = new ShellSort();
//        System.out.println(shellSort.sort((ArrayList<Integer>) data.clone()) + "\n\n");
//
//        ISort<Integer> insertionSort = new StraightInsertionSort();
//        System.out.println(insertionSort.sort((ArrayList<Integer>) data.clone()) + "\n\n");
//
//        ISort<Integer> quickSort = new QuickSort();
//        System.out.println(quickSort.sort((ArrayList<Integer>) data.clone()) + "\n\n");

//        ISort<Integer> selectionSort = new SelectionSort();
//        System.out.println(selectionSort.sort((ArrayList<Integer>) data.clone()) + "\n\n");

//        ISort<Integer> mergeSort = new MergeSort();
//        System.out.println(mergeSort.sort((ArrayList<Integer>) data.clone()) + "\n\n");

//        ISort<Integer> countingSort = new CountingSort();
//        System.out.println(countingSort.sort((ArrayList<Integer>) data.clone()) + "\n\n");

//        ISort<Integer> radixSort = new RadixSort();
//        System.out.println(radixSort.sort((ArrayList<Integer>) data1.clone()) + "\n\n");

//        ISort<Integer> heapSort = new HeapSort();
//        System.out.println(heapSort.sort((ArrayList<Integer>) data.clone()) + "\n\n");


        /**
         * [A B C D E F G H I # # # J]
         *              A
         *            /   \
         *           B     C
         *         / \    / \
         *        D  E   F   G
         *       /\       \
         *      H  I       J
         */
        Tree<String> tree = new Tree<>();
        tree.createTree(treeData);
        System.out.println("level order");
        tree.levelOrderTraverse(tree.getRoot());
        System.out.println("\n\npre order recursive");
        tree.preOrderTraverseR(tree.getRoot());
        System.out.println("\n\npre order");
        tree.preOrderTraverse(tree.getRoot());
        System.out.println("\n\nin order recursive");
        tree.inOrderTraverseR(tree.getRoot());
        System.out.println("\n\nin order");
        tree.inOrderTraverse(tree.getRoot());
        System.out.println("\n\npost order recursive");
        tree.postOrderTraverseR(tree.getRoot());
        System.out.println("\n\npost order");
        tree.postOrderTraverse(tree.getRoot());

    }
}