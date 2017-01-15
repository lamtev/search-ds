package ru.mail.polis.search_ds;

import java.util.List;

public interface ISortedSet<E extends Comparable<E>> extends ISet<E> {

    /**
     * @return the first (lowest) element currently in this set
     * @throws java.util.NoSuchElementException if this set is empty
     */
    E first();

    /**
     * @return the last (highest) element currently in this set
     * @throws java.util.NoSuchElementException if this set is empty
     */
    E last();

    /**
     * @return list of element in sorting ordering
     */
    List<E> inorderTraverse();
}
