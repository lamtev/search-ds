package ru.mail.polis.search_ds;

import java.util.Comparator;
import java.util.List;

//TODO: write code here
public class RedBlackTree<E extends Comparable<E>> implements ISortedSet<E> {

    private int size;
    private final Comparator<E> comparator;

    public RedBlackTree() {
        this.comparator = null;
    }

    public RedBlackTree(Comparator<E> comparator) {
        this.comparator = comparator;
    }

    @Override
    public E first() {
        return null;
    }

    @Override
    public E last() {
        return null;
    }

    @Override
    public List<E> inorderTraverse() {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean contains(E value) {
        return false;
    }

    @Override
    public boolean add(E value) {
        return false;
    }

    @Override
    public boolean remove(E value) {
        return false;
    }

    private int compare(E v1, E v2) {
        return comparator == null ? v1.compareTo(v2) : comparator.compare(v1, v2);
    }
}
