package eda.ds;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Stack;

public class BST<K extends Comparable<K>> implements Iterable<K> {
    private BSTNodeRec root;

    private class BSTNodeRec {
        K key;
        BSTNodeRec left, right;

        BSTNodeRec(K key) {
            this.key = key;
        }
    }

    public BST() {
        root = null;
    }

    public void add(K key) {
        BSTNodeRec newNode = new BSTNodeRec(key);
        if (root == null) {
            root = newNode;
            return;
        }

        BSTNodeRec current = root;
        BSTNodeRec parent = null;

        while (current != null) {
            parent = current;
            if (key.compareTo(current.key) < 0) {
                current = current.left;
            } else if (key.compareTo(current.key) > 0) {
                current = current.right;
            } else {
                return; // duplicado
            }
        }

        if (key.compareTo(parent.key) < 0) {
            parent.left = newNode;
        } else {
            parent.right = newNode;
        }
    }

    public K search(K key) {
        BSTNodeRec current = root;

        while (current != null) {
            int cmp = key.compareTo(current.key);
            if (cmp == 0) return current.key;
            if (cmp < 0) current = current.left;
            else current = current.right;
        }

        return null;
    }

    public K min() {
        if (root == null) return null;
        BSTNodeRec current = root;
        while (current.left != null) current = current.left;
        return current.key;
    }

    public K max() {
        if (root == null) return null;
        BSTNodeRec current = root;
        while (current.right != null) current = current.right;
        return current.key;
    }

    public void delete(K key) {
        root = deleteRec(root, key);
    }

    private BSTNodeRec deleteRec(BSTNodeRec node, K key) {
        if (node == null) return null;

        int cmp = key.compareTo(node.key);
        if (cmp < 0) {
            node.left = deleteRec(node.left, key);
        } else if (cmp > 0) {
            node.right = deleteRec(node.right, key);
        } else {
            if (node.left == null) return node.right;
            if (node.right == null) return node.left;

            BSTNodeRec minNode = findMin(node.right);
            node.key = minNode.key;
            node.right = deleteRec(node.right, minNode.key);
        }

        return node;
    }

    private BSTNodeRec findMin(BSTNodeRec node) {
        while (node.left != null) node = node.left;
        return node;
    }

    @Override
    public Iterator<K> iterator() {
        return new Iterator<>() {
            private final Stack<BSTNodeRec> stack = new Stack<>();
            private BSTNodeRec current = root;

            {
                while (current != null) {
                    stack.push(current);
                    current = current.left;
                }
            }

            @Override
            public boolean hasNext() {
                return !stack.isEmpty();
            }

            @Override
            public K next() {
                if (!hasNext()) throw new NoSuchElementException();
                BSTNodeRec node = stack.pop();
                K result = node.key;
                BSTNodeRec temp = node.right;
                while (temp != null) {
                    stack.push(temp);
                    temp = temp.left;
                }
                return result;
            }
        };
    }
}
