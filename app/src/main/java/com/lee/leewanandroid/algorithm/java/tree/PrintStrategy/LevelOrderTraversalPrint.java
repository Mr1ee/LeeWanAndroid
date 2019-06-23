package com.lee.leewanandroid.algorithm.java.tree.PrintStrategy;

import com.lee.leewanandroid.algorithm.java.tree.node.Node;

import java.util.ArrayDeque;
import java.util.Queue;

public class LevelOrderTraversalPrint implements IPrintStrategy {
    @Override
    public void print(Node node) {
        Queue<Node> queue = new ArrayDeque<>();
        queue.add(node);
        while (!queue.isEmpty()) {
            Node parent = queue.poll();
            Node.printNode(parent);

            Node left = parent.getLeft();
            if (left != null) {
                queue.add(left);
            }

            Node right = parent.getRight();
            if (right != null) {
                queue.add(right);
            }
        }
    }
}
