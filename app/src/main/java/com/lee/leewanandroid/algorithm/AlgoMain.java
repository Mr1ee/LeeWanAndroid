package com.lee.leewanandroid.algorithm;

import com.lee.leewanandroid.algorithm.sort.QuickSort;
import com.lee.leewanandroid.algorithm.sort.interfaces.ISort;
import com.lee.leewanandroid.algorithm.sort.ShellSort;
import com.lee.leewanandroid.algorithm.sort.StraightInsertionSort;

import java.util.ArrayList;
import java.util.Arrays;

public class AlgoMain {

    public static ArrayList<Integer> data = new ArrayList<>(Arrays.asList(2, 9, 4, 7, 5, 8, 1, 3, 6, 10));

    public static void main(String[] args) {

//        ISort<Integer> algo = new BubbleSort();
//        System.out.println(algo.sort(data));

        ISort<Integer> shellSort = new ShellSort();
        System.out.println(shellSort.sort((ArrayList<Integer>) data.clone()) + "\n\n");

        ISort<Integer> insertionSort = new StraightInsertionSort();
        System.out.println(insertionSort.sort((ArrayList<Integer>) data.clone()) + "\n\n");

        ISort<Integer> quickSort = new QuickSort();
        System.out.println(quickSort.sort((ArrayList<Integer>) data.clone()) + "\n\n");
    }
}
