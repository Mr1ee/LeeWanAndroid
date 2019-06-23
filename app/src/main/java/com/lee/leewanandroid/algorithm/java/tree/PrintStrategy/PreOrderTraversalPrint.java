package com.lee.leewanandroid.algorithm.java.tree.PrintStrategy;

import com.lee.leewanandroid.algorithm.java.tree.KeyValue;
import com.lee.leewanandroid.algorithm.java.tree.node.Node;

import java.util.Stack;

public class PreOrderTraversalPrint implements IPrintStrategy {
    @Override
    public void print(Node node) {
        Stack<KeyValue<Boolean, Node>> stack = new Stack<>();
        stack.push(new KeyValue<>(false, node));
        while (!stack.isEmpty()) {
            KeyValue<Boolean, Node> pair = stack.pop();
            boolean visited = pair.key;
            Node parent = pair.value;
            if (visited) {
                Node.printNode(parent);
            } else {
                Node right = parent.getRight();
                if (right != null) {
                    stack.push(new KeyValue<>(false, right));
                }

                Node left = parent.getLeft();
                if (left != null) {
                    stack.push(new KeyValue<>(false, left));
                }

                stack.push(new KeyValue<>(true, parent));
            }
        }
    }
}
