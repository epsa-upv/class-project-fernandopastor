package eda.ds;

import java.util.Iterator;
import java.util.Stack;

public class AVL<K extends Comparable<K>> implements Iterable<K> {

    private static class AVLNode<K> {
        K key;
        int height;
        AVLNode<K> left, right;

        AVLNode(K key) {
            this.key = key;
            this.height = 1;
        }
    }

    private AVLNode<K> root;

    public void add(K key) {
        if (key == null) return;
        root = insertIterative(root, key);
    }

    private AVLNode<K> insertIterative(AVLNode<K> root, K key) {
        Stack<AVLNode<K>> path = new Stack<>();
        AVLNode<K> current = root;
        AVLNode<K> parent = null;

        while (current != null) {
            path.push(current);
            parent = current;
            int cmp = key.compareTo(current.key);
            if (cmp < 0) current = current.left;
            else if (cmp > 0) current = current.right;
            else return root; // Duplicate, do nothing
        }

        AVLNode<K> newNode = new AVLNode<>(key);
        if (parent == null) return newNode;
        if (key.compareTo(parent.key) < 0) parent.left = newNode;
        else parent.right = newNode;

        while (!path.isEmpty()) {
            AVLNode<K> node = path.pop();
            updateHeight(node);
            node = rebalance(node);
            if (!path.isEmpty()) {
                AVLNode<K> top = path.peek();
                if (node.key.compareTo(top.key) < 0) top.left = node;
                else top.right = node;
            } else {
                root = node;
            }
        }

        return root;
    }

    public AVLNode<K> search(K key) {
        AVLNode<K> current = root;
        while (current != null) {
            int cmp = key.compareTo(current.key);
            if (cmp == 0) return current;
            else if (cmp < 0) current = current.left;
            else current = current.right;
        }
        return null;
    }

    public void delete(K key) {
        root = deleteIterative(root, key);
    }

    private AVLNode<K> deleteIterative(AVLNode<K> root, K key) {
        Stack<AVLNode<K>> path = new Stack<>();
        AVLNode<K> current = root;
        AVLNode<K> parent = null;

        while (current != null && !current.key.equals(key)) {
            path.push(current);
            parent = current;
            if (key.compareTo(current.key) < 0) current = current.left;
            else current = current.right;
        }

        if (current == null) return root;

        if (current.left != null && current.right != null) {
            AVLNode<K> successor = current.right;
            Stack<AVLNode<K>> subPath = new Stack<>();
            while (successor.left != null) {
                subPath.push(successor);
                successor = successor.left;
            }
            current.key = successor.key;
            key = successor.key;
            path.addAll(subPath);
            current = current.right;
            parent = path.peek();
        }

        AVLNode<K> child = (current.left != null) ? current.left : current.right;
        if (parent == null) return child;
        if (key.compareTo(parent.key) < 0) parent.left = child;
        else parent.right = child;

        while (!path.isEmpty()) {
            AVLNode<K> node = path.pop();
            updateHeight(node);
            node = rebalance(node);
            if (!path.isEmpty()) {
                AVLNode<K> top = path.peek();
                if (node.key.compareTo(top.key) < 0) top.left = node;
                else top.right = node;
            } else {
                root = node;
            }
        }

        return root;
    }

    public K min() {
        if (root == null) return null;
        AVLNode<K> current = root;
        while (current.left != null) current = current.left;
        return current.key;
    }

    public K max() {
        if (root == null) return null;
        AVLNode<K> current = root;
        while (current.right != null) current = current.right;
        return current.key;
    }

    private void updateHeight(AVLNode<K> node) {
        int leftHeight = (node.left != null) ? node.left.height : 0;
        int rightHeight = (node.right != null) ? node.right.height : 0;
        node.height = 1 + Math.max(leftHeight, rightHeight);
    }

    private int getBalance(AVLNode<K> node) {
        if (node == null) return 0;
        int leftHeight = (node.left != null) ? node.left.height : 0;
        int rightHeight = (node.right != null) ? node.right.height : 0;
        return leftHeight - rightHeight;
    }

    private AVLNode<K> rebalance(AVLNode<K> node) {
        int balance = getBalance(node);
        if (balance > 1) {
            if (getBalance(node.left) < 0)
                node.left = rotateLeft(node.left);
            return rotateRight(node);
        } else if (balance < -1) {
            if (getBalance(node.right) > 0)
                node.right = rotateRight(node.right);
            return rotateLeft(node);
        }
        return node;
    }

    private AVLNode<K> rotateLeft(AVLNode<K> node) {
        AVLNode<K> newRoot = node.right;
        node.right = newRoot.left;
        newRoot.left = node;
        updateHeight(node);
        updateHeight(newRoot);
        return newRoot;
    }

    private AVLNode<K> rotateRight(AVLNode<K> node) {
        AVLNode<K> newRoot = node.left;
        node.left = newRoot.right;
        newRoot.right = node;
        updateHeight(node);
        updateHeight(newRoot);
        return newRoot;
    }

    @Override
    public Iterator<K> iterator() {
        return new PostOrderIterator();
    }

    private class PostOrderIterator implements Iterator<K> {
        private final Stack<AVLNode<K>> stack = new Stack<>();
        private AVLNode<K> lastVisited = null;
        private AVLNode<K> current = root;

        @Override
        public boolean hasNext() {
            return !stack.isEmpty() || current != null;
        }

        @Override
        public K next() {
            while (current != null) {
                stack.push(current);
                current = current.left;
            }

            AVLNode<K> peek = stack.peek();
            if (peek.right != null && lastVisited != peek.right) {
                current = peek.right;
                return next();
            }

            stack.pop();
            lastVisited = peek;
            return peek.key;
        }
    }
}
