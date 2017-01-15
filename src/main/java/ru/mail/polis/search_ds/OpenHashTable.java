package ru.mail.polis.search_ds;

import java.util.Comparator;

//TODO: write code here
public class OpenHashTable<E extends Comparable<E>> implements ISet<E> {

    private Comparator<E> comparator;

    public OpenHashTable() {
        this(null);
    }

    public OpenHashTable(Comparator<E> comparator) {
        this.comparator = comparator;
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
}
