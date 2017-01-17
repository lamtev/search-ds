package ru.mail.polis.search_ds;

import java.util.Comparator;

public class OpenHashTable<E extends Comparable<E>> implements ISet<E> {

    private static final int INITIAL_CAPACITY = 128;
    private static final double LOAD_FACTOR = 0.5d;
    private Comparator<E> comparator;
    private Node<E>[] table;
    private int size = 0;

    public OpenHashTable() {
        this(null);
    }

    @SuppressWarnings("unchecked")
    public OpenHashTable(Comparator<E> comparator) {
        this.comparator = comparator;
        this.table = new Node[INITIAL_CAPACITY];
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(E value) {
        validateValueIsNull(value);
        int hash1 = hash1(value);
        int hash2 = hash2(value);
        for (Node<E> x : table) {
            if (table[hash1] != null && compare(table[hash1].value, value) == 0 && !table[hash1].deleted) {
                return true;
            }
            hash1 = (hash1 + hash2) % table.length;
        }
        return false;
    }

    @Override
    public boolean add(E value) {
        validateValueIsNull(value);
        int hash1 = hash1(value);
        int hash2 = hash2(value);
        for (Node<E> x : table) {
            if (table[hash1] == null || table[hash1].deleted) {
                table[hash1] = new Node<>(value);
                ++size;
                resize();
                return true;
            } else if (compare(table[hash1].value, value) == 0) {
                return false;
            }
            hash1 = (hash1 + hash2) % table.length;
        }
        return false;
    }

    @Override
    public boolean remove(E value) {
        validateValueIsNull(value);
        int hash1 = hash1(value);
        int hash2 = hash2(value);
        for (Node<E> x : table) {
            if (table[hash1] != null) {
                if (table[hash1].deleted) {
                    return false;
                }
                if (compare(table[hash1].value, value) == 0) {
                    table[hash1].deleted = true;
                    --size;
                    return true;
                }
            }
            hash1 = (hash1 + hash2) % table.length;
        }
        return false;
    }

    private void validateValueIsNull(E value) {
        if (value == null) {
            throw new NullPointerException("Method parameter is null");
        }
    }

    private int hash1(E value) {
        String s = (String) value;
        int h = 0;
        int a = 31;
        for (char x : s.toCharArray()) {
            h = Math.abs(h * a + x) % table.length;
        }
        return h;
    }

    private int hash2(E value) {
        String s = (String) value;
        int h = 0;
        for (char x : s.toCharArray()) {
            h = Math.abs(h + x) % (table.length - 1) + 1;
        }
        return h % 2 == 0 ? Math.abs(h - 1) : h;
    }

    private int compare(E v1, E v2) {
        return comparator == null ? v1.compareTo(v2) : comparator.compare(v1, v2);
    }

    @SuppressWarnings("unchecked")
    private void resize() {
        if (size < table.length * LOAD_FACTOR) {
            return;
        }
        size = 0;
        Node<E>[] oldTable = table;
        table = new Node[table.length << 1];
        int i = 0;
        for (Node<E> x : oldTable) {
            if (x != null && !x.deleted) {
                add(x.value);
            }
            oldTable[i] = null;
            ++i;
        }
    }

    private class Node<T> {
        private T value;
        private boolean deleted = false;

        private Node(T value) {
            this.value = value;
        }
    }
}
