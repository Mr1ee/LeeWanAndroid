package com.lee.leewanandroid.algorithm.java;


import com.lee.leewanandroid.algorithm.java.tree.AVLTree;
import com.lee.leewanandroid.algorithm.java.tree.BSTree;
import com.lee.leewanandroid.algorithm.java.tree.BTree;
import com.lee.leewanandroid.algorithm.java.tree.KeyValue;
import com.lee.leewanandroid.algorithm.java.tree.PrintStrategy.PreOrderTraversalPrint;
import com.lee.leewanandroid.algorithm.java.tree.RBTree;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import java.util.Scanner;

@SuppressWarnings("unused")
public class Main {
    public static ArrayList<Integer> data = new ArrayList<>(Arrays.asList(2, 9, 4, 7, 5, 8, 1, 3, 6, 10));
    public static ArrayList<Integer> data1 = new ArrayList<>(Arrays.asList(542, 3521, 13459, 852, 742, 46, 2, 1, 633, 32));

    public static ArrayList<String> treeData = new ArrayList<>(Arrays.asList("A", "B", "C", "D", "E", "F", "G", "#", "#", "H", "I", "#", "J"));
    public static ArrayList<String> treeData_pre = new ArrayList<>(Arrays.asList("A", "B", "D", "#", "#", "E", "H",
            "#", "#", "I", "#", "#", "C", "F", "#", "J", "#", "#", "G", "#", "#"));

    private static String preOrderStr = "ABDEHICFJG";
    private static String preOrderVStr = "0123456789";
    private static String inOrderKStr = "DBHEIAFJCG";
    private static int[] inOrderV = new int[]{2, 1, 4, 3, 5, 0, 7, 8, 6, 9};

    private static String postOrderKStr = "DHIEBJFGCA";
    private static int[] postOrderV = new int[]{2, 4, 5, 3, 1, 8, 7, 9, 6, 0};


    private static ArrayList<KeyValue<Integer, Character>> preOrder = new ArrayList<>();
    private static ArrayList<KeyValue<Integer, Character>> inOrder = new ArrayList<>();
    private static ArrayList<KeyValue<Integer, Character>> postOrder = new ArrayList<>();

    public static String CMD_QUIT = "quit";
    public static String CMD_CLEAR = "clear";
    public static String CMD_PRINT = "print";
    public static String CMD_SIZE = "size";

    public static void main(String[] args) {
        /*
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
        for (int i = 0; i < preOrderStr.length(); i++) {
            preOrder.add(new KeyValue<>(i, preOrderStr.charAt(i)));
        }

        for (int i = 0; i < inOrderKStr.length(); i++) {
            inOrder.add(new KeyValue<>(inOrderV[i], inOrderKStr.charAt(i)));
        }

        for (int i = 0; i < postOrderKStr.length(); i++) {
            postOrder.add(new KeyValue<>(postOrderV[i], postOrderKStr.charAt(i)));
        }

//        BSTree<Character, Integer> tree = new BSTree<>();
//        BSTree<Character, Integer> tree = new AVLTree<>();
        BSTree<Character, Integer> tree = new RBTree<>();
//        tree.buildTreeWithPreAndInSequence(preOrder, inOrder);
//        tree.print(new PreOrderTraversalPrint());
//        System.out.println();
//
//        tree.buildTreeWithPostAndInSequence(postOrder, inOrder);
//        tree.print(new PreOrderTraversalPrint());
//        System.out.println();
        tree.insert('D', 70);
        tree.insert('E', 17);
        tree.insert('B', 15);
        tree.insert('A', 30);
        tree.insert('F', 55);
        tree.insert('C', 40);


        tree.printTree();

        Scanner input = new Scanner(System.in);
        String cmd;
        while (!CMD_QUIT.equalsIgnoreCase(cmd = input.nextLine())) {
            String[] key = cmd.split(" ");
            if ("i".equalsIgnoreCase(key[0])) {
                if (key.length > 2) {
                    tree.insert(key[1].charAt(0), Integer.parseInt(key[2]));
                }
            } else if ("r".equalsIgnoreCase(key[0])) {
                if (key.length > 1) {
                    tree.remove(key[1].charAt(0));
                }
            } else if (CMD_CLEAR.equalsIgnoreCase(key[0])) {
//                tree.clear();
            } else if (CMD_PRINT.equalsIgnoreCase(key[0])) {
                tree.printTree();
            } else if (CMD_SIZE.equalsIgnoreCase(key[0])) {
//                tree.printTreeSize();
            }
        }
    }

    public static int findSingle(int[] array) {
        int mask1 = 0;
        int mask2 = 0;
        for (int i : array) {
            mask1 = (mask1 ^ i) & (~mask2);
            mask2 = (mask2 ^ i) & (~mask1);
            System.out.println("i = " + i + ", mask1 = " + mask1 + "; mask2 = " + mask2);
        }
        return mask1 ^ mask2;
    }
}
