package com.lee.leewanandroid.algorithm;

import com.lee.leewanandroid.algorithm.kotin.tree.BTree;
import com.lee.leewanandroid.algorithm.kotin.tree.RedBlackTree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class AlgoMain {

    public static ArrayList<Integer> data = new ArrayList<>(Arrays.asList(2, 9, 4, 7, 5, 8, 1, 3, 6, 10));
    public static ArrayList<Integer> data1 = new ArrayList<>(Arrays.asList(542, 3521, 13459, 852, 742, 46, 2, 1, 633, 32));

    public static ArrayList<String> treeData = new ArrayList<>(Arrays.asList("A", "B", "C", "D", "E", "F", "G", "#", "#", "H", "I", "#", "J"));
    public static ArrayList<String> treeData_pre = new ArrayList<>(Arrays.asList("A", "B", "D", "#", "#", "E", "H",
            "#", "#", "I", "#", "#", "C", "F", "#", "J", "#", "#", "G", "#", "#"));

    public static String preOrderStr = "ABDEHICFJG";
    public static String inOrderStr = "DBHEIAFJCG";
    public static String postOrderStr = "DHIEBJFGCA";

    public static String CMD_QUIT = "quit";
    public static String CMD_CLEAR = "clear";
    public static String CMD_PRINT = "print";
    public static String CMD_SIZE = "size";

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
         * 层序遍历构建串 [A B C D E F G # # H I # J]
         *
         * 先序遍历构建串 [A B D # # E H # # I # # C F # J # # G # #]
         *              A
         *            /   \
         *           B     C
         *         / \    / \
         *        D  E   F   G
         *          / \   \
         *         H  I    J
         */
        BTree<String> tree = new BTree<>();
//        tree.createTree(treeData_pre);
//        System.out.println("level order");
//        tree.levelOrderTraverse(tree.getRoot());
//        System.out.println("\n\npre order recursive");
//        tree.preOrderTraverseR(tree.getRoot());
//        System.out.println("\n\npre order");
//        tree.preOrderTraverse(tree.getRoot());
//        System.out.println("\n\nin order recursive");
//        tree.inOrderTraverseR(tree.getRoot());
//        System.out.println("\n\nin order");
//        tree.inOrderTraverse(tree.getRoot());
//        System.out.println("\n\npost order recursive");
//        tree.postOrderTraverseR(tree.getRoot());
//        System.out.println("\n\npost order");
//        tree.postOrderTraverse(tree.getRoot());
//
//        System.out.println("\n");
//        tree.buildTree(preOrderStr, inOrderStr);
//        System.out.println("\n\npre order");
//        tree.preOrderTraverse(tree.getRoot());
//        System.out.println("\n\nin order");
//        tree.inOrderTraverse(tree.getRoot());
//        System.out.println("\n\npost order");
//        tree.postOrderTraverse(tree.getRoot());

//        System.out.println("\n");
//        tree.buildTree2(postOrderStr, inOrderStr);
//        System.out.println("\n\npre order");
//        tree.preOrderTraverse(tree.getRoot());
//        System.out.println("\n\nin order");
//        tree.inOrderTraverse(tree.getRoot());
//        System.out.println("\n\npost order");
//        tree.postOrderTraverse(tree.getRoot());
//        tree.printTree();
//
//        BinarySearchTree<Integer> bst = new BinarySearchTree<>();
//        bst.insert(62);
//        bst.insert(58);
//        bst.insert(47);
//        bst.insert(35);
//        bst.insert(51);
//        bst.insert(37);
//        bst.insert(88);
//        bst.insert(73);
//        bst.insert(99);
//        bst.insert(93);
//        bst.insert(97);
//        bst.insert(95);
//        bst.insert(100);
//        System.out.println("\n\nbst pre order");
//        bst.preOrderTraverse(bst.getRoot());
//        System.out.println("\n\nbst in order");
//        bst.inOrderTraverse(bst.getRoot());
//        System.out.println("\n\nbst post order");
//        bst.postOrderTraverse(bst.getRoot());
//
//        bst.remove(99);
//        System.out.println("\n\nremove 99 pre order");
//        bst.preOrderTraverse(bst.getRoot());
//        System.out.println("\n\nremove 99 in order");
//        bst.inOrderTraverse(bst.getRoot());
//        System.out.println("\n\nremove 99 post order");
//        bst.postOrderTraverse(bst.getRoot());
//        bst.printTree();

        long sTime = System.currentTimeMillis();
//        AVLTreeR avlTree = new AVLTreeR();
        RedBlackTree avlTree = new RedBlackTree();
//        AVLTree avlTree = new AVLTree();
//        BSTree<Integer> avlTree = new BSTree<>();
//        avlTree.insert(63);
//        avlTree.insert(58);
//        avlTree.insert(88);
//        avlTree.insert(37);
//        avlTree.insert(73);
//        avlTree.insert(99);
//        avlTree.insert(93);
//        avlTree.insert(100);
//        avlTree.insert(51);
//        avlTree.insert(30);
//        avlTree.insert(27);
//        avlTree.insert(25);
//        avlTree.insert(23);
//        avlTree.insert(21);
//        avlTree.insert(19);
//        avlTree.insert(17);
//        avlTree.insert(18);
//        avlTree.insert(15);
//        avlTree.insert(16);
//        avlTree.insert(13);
//        avlTree.insert(11);
//        avlTree.insert(9);
//        avlTree.insert(75);
//        avlTree.insert(74);
//        avlTree.remove(21);
//        avlTree.remove(25);
//        avlTree.remove(27);
//        avlTree.remove(23);
//        avlTree.remove(30);
//        avlTree.remove(37);
//        avlTree.remove(51);
//        avlTree.remove(58);
//        avlTree.remove(63);
//        avlTree.remove(73);
        avlTree.insert(20);
        avlTree.insert(9);
        avlTree.insert(25);
        avlTree.insert(15);
        avlTree.insert(30);
        avlTree.insert(12);
        avlTree.insert(18);
        long endTime = System.currentTimeMillis();
        System.out.println("time cost = " + (endTime - sTime));

        Scanner input = new Scanner(System.in);
        String cmd;
        while (!CMD_QUIT.equalsIgnoreCase(cmd = input.nextLine())) {
            String[] key = cmd.split(" ");
            if ("i".equalsIgnoreCase(key[0])) {
                if (key.length > 1) {
                    avlTree.insert(Integer.parseInt(key[1]));
                }
            } else if ("r".equalsIgnoreCase(key[0])) {
                if (key.length > 1) {
                    avlTree.remove(Integer.parseInt(key[1]));
                }
            } else if (CMD_CLEAR.equalsIgnoreCase(key[0])) {
                avlTree.clear();
            } else if (CMD_PRINT.equalsIgnoreCase(key[0])) {
                avlTree.printTree(avlTree.getRoot());
            }else if (CMD_SIZE.equalsIgnoreCase(key[0])) {
                avlTree.printTreeSize();
            }
        }
    }
}
