package com.lee.leewanandroid.algorithm;

import com.lee.leewanandroid.algorithm.sort.CountingSort;
import com.lee.leewanandroid.algorithm.sort.HeapSort;
import com.lee.leewanandroid.algorithm.sort.MergeSort;
import com.lee.leewanandroid.algorithm.sort.QuickSort;
import com.lee.leewanandroid.algorithm.sort.RadixSort;
import com.lee.leewanandroid.algorithm.sort.SelectionSort;
import com.lee.leewanandroid.algorithm.sort.interfaces.ISort;
import com.lee.leewanandroid.algorithm.sort.ShellSort;
import com.lee.leewanandroid.algorithm.sort.StraightInsertionSort;

import java.util.ArrayList;
import java.util.Arrays;

public class AlgoMain {

    public static ArrayList<Integer> data = new ArrayList<>(Arrays.asList(2, 9, 4, 7, 5, 8, 1, 3, 6, 10));
    public static ArrayList<Integer> data1 = new ArrayList<>(Arrays.asList(542, 3521, 13459, 852, 742, 46, 2, 1, 633, 32));

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

        ISort<Integer> heapSort = new HeapSort();
        System.out.println(heapSort.sort((ArrayList<Integer>) data.clone()) + "\n\n");
    }
}
