package ru.mail.polis.search_ds;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

public class AVLTree<E extends Comparable<E>> implements ISortedSet<E> {

    private Node root;
    private int size;
    private final Comparator<E> comparator;
    private boolean successfullyDeleted;
    private boolean alreadyInserted;

    public AVLTree() {
        this.comparator = null;
    }

    public AVLTree(Comparator<E> comparator) {
        this.comparator = comparator;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return root == null;
    }

    @Override
    public boolean contains(E value) {
        validateValueIsNull(value);
        if (root != null) {
            Node current = root;
            while (current != null) {
                int cmp = compare(current.value, value);
                if (cmp == 0) {
                    return true;
                } else if (cmp > 0) {
                    current = current.left;
                } else {
                    current = current.right;
                }
            }
        }
        return false;
    }

    @Override
    public boolean add(E value) {
        validateValueIsNull(value);
        alreadyInserted = true;
        root = insert(root, value);
        if (!alreadyInserted) {
            ++size;
        }
        return !alreadyInserted;
    }

    @Override
    public boolean remove(E value) {
        validateValueIsNull(value);
        successfullyDeleted = false;
        root = remove(root, value);
        if (successfullyDeleted) {
            --size;
        }
        return successfullyDeleted;
    }

    @Override
    public E first() {
        validateTreeIsEmpty();
        return minimum(root).value;
    }

    @Override
    public E last() {
        validateTreeIsEmpty();
        return maximum(root).value;
    }

    @Override
    public List<E> inorderTraverse() {
        List<E> list = new ArrayList<>();
        Node minimum = minimum(root);
        list = orderedList(minimum, list);
        return list;
    }

    private void validateTreeIsEmpty() {
        if (isEmpty()) {
            throw new NoSuchElementException("Tree is empty");
        }
    }

    private void validateValueIsNull(E value) {
        if (value == null) {
            throw new NullPointerException("Method parameter is null");
        }
    }

    private Node next(Node x) {
        Node current = root;
        Node successor = null;
        while (current != null) {
            if (compare(current.value, x.value) > 0) {
                successor = current;
                current = current.left;
            } else {
                current = current.right;
            }
        }
        return successor;
    }

    private Node minimum(Node current) {
        if (current.left == null) {
            return current;
        }
        return minimum(current.left);
    }

    private Node maximum(Node current) {
        if (current.right == null) {
            return current;
        }
        return maximum(current.right);
    }


    private List<E> orderedList(Node current, List<E> list) {
        if (current == null) {
            return list;
        }
        list.add(current.value);
        return orderedList(next(current), list);
    }

    private int compare(E value1, E value2) {
        return comparator == null ? value1.compareTo(value2) : comparator.compare(value1, value2);
    }

    private int height(Node node) {
        if (node != null) {
            return node.height;
        }
        return 0;
    }

    private int balanceFactor(Node current) {
        return height(current.right) - height(current.left);
    }

    private void fixHeight(Node current) {
        int leftHeight = height(current.left);
        int rightHeight = height(current.right);
        current.height = Math.max(leftHeight, rightHeight) + 1;
    }

    private Node rotateRight(Node current) {
        Node left = current.left;
        current.left = left.right;
        left.right = current;
        fixHeight(current);
        fixHeight(left);
        return left;
    }

    private Node rotateLeft(Node current) {
        Node right = current.right;
        current.right = right.left;
        right.left = current;
        fixHeight(current);
        fixHeight(right);
        return right;
    }

    private Node balance(Node current) {
        fixHeight(current);
        if (balanceFactor(current) == 2) {
            if (balanceFactor(current.right) < 0) {
                current.right = rotateRight(current.right);
            }
            return rotateLeft(current);
        }
        if (balanceFactor(current) == -2) {
            if (balanceFactor(current.left) > 0) {
                current.left = rotateLeft(current.left);
            }
            return rotateRight(current);
        }
        return current;
    }

    private Node insert(Node current, E value) {
        if (current == null) {
            alreadyInserted = false;
            return new Node(value);
        }
        int cmp = compare(value, current.value);
        if (cmp == 0) {
            alreadyInserted = true;
        } else if (cmp < 0) {
            current.left = insert(current.left, value);
        } else {
            current.right = insert(current.right, value);
        }
        return balance(current);
    }

    private Node removeMinimum(Node current) {
        if (current.left == null) {
            return current.right;
        }
        current.left = removeMinimum(current.left);
        return balance(current);
    }

    private Node remove(Node current, E value) {
        if (current == null) {
            successfullyDeleted = false;
            return null;
        }
        int cmp = compare(value, current.value);
        if (cmp < 0)
            current.left = remove(current.left, value);
        else if (cmp > 0) {
            current.right = remove(current.right, value);
        } else {
            successfullyDeleted = true;
            Node left = current.left;
            Node right = current.right;
            if (right == null) {
                return left;
            }

            Node min = minimum(right);
            min.right = removeMinimum(right);
            min.left = left;
            return balance(min);
        }
        return balance(current);
    }

    private class Node {

        private E value;
        private int height;
        private Node left;
        private Node right;

        private Node(E value) {
            this.value = value;
            height = 1;
            left = null;
            right = null;
        }
    }

}
