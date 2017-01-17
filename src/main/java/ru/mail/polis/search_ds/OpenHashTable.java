package ru.mail.polis.search_ds;

import java.util.Comparator;

//FIXME
/*
*
* 105106105108115115103113108104
*   346 <--- OK | real ---> 345
*
* 9912011710311210010697115121
*   256 <--- OK | real ---> 255
*
* */
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
        int i = -1;
        for (Node<E> x : table) {
            int hash = hash(value, ++i);
            if (table[hash] != null && compare(table[hash].value, value) == 0 && !table[hash].deleted) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean add(E value) {
        validateValueIsNull(value);
        int i = -1;
        for (Node<E> x : table) {
            int hash = hash(value, ++i);
            if (table[hash] == null || table[hash].deleted) {
                table[hash] = new Node<>(value);
                ++size;
                resize();
                return true;
            } else if (compare(table[hash].value, value) == 0) {
                return false;
            }
        }
        return false;
    }

    @Override
    public boolean remove(E value) {
        validateValueIsNull(value);
        int i = -1;
        for (Node<E> x : table) {
            int hash = hash(value, ++i);
            if (table[hash] != null) {
                if (table[hash].deleted) {
                    return false;
                }
                if (compare(table[hash].value, value) == 0) {
                    table[hash].deleted = true;
                    --size;
                    return true;
                }
            }
        }
        return false;
    }

    private void validateValueIsNull(E value) {
        if (value == null) {
            throw new NullPointerException("Method parameter is null");
        }
    }

    private int hash(E value, int i) {
        return (hash1(value) + i * hash2(value)) % table.length;
    }

    private int hash1(E value) {
        String s = (String) value;
        int h = 0;
        int a = table.length - 1;
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
        return h;
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
