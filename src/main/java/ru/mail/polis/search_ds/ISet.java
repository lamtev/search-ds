package ru.mail.polis.search_ds;

public interface ISet<E extends Comparable<E>> {

    /**
     * @return the number of elements in this set (its cardinality)
     */
    int size();

    /**
     * @return true if this set contains no elements
     */
    boolean isEmpty();

    /**
     * @param value element whose presence in this set is to be tested
     * @return true if this set contains the specified element
     * @throws NullPointerException if the specified element is null
     */
    boolean contains(E value);

    /**
     * @param value element to be added to this set
     * @return true if this set did not already contain the specified element
     * @throws NullPointerException if the specified element is null
     */
    boolean add(E value);

    /**
     * @param value object to be removed from this set, if present
     * @return true if this set contained the specified element
     * @throws NullPointerException if the specified element is null
     */
    boolean remove(E value);
}
