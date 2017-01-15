package ru.mail.polis.search_ds;

import java.util.*;

public class BinarySearchTree<E extends Comparable<E>> implements ISortedSet<E> {

    class Node {

        Node(E value) {
            this.value = value;
        }

        E value;
        Node left;
        Node right;

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("N{");
            sb.append("d=").append(value);
            if (left != null) {
                sb.append(", l=").append(left);
            }
            if (right != null) {
                sb.append(", r=").append(right);
            }
            sb.append('}');
            return sb.toString();
        }
    }

    private Node root;
    private int size;
    private final Comparator<E> comparator;

    public BinarySearchTree() {
        this.comparator = null;
    }

    public BinarySearchTree(Comparator<E> comparator) {
        this.comparator = comparator;
    }

    @Override
    public E first() {
        if (isEmpty()) {
            throw new NoSuchElementException("set is empty, no first element");
        }
        Node curr = root;
        while (curr.left != null) {
            curr = curr.left;
        }
        return curr.value;
    }

    @Override
    public E last() {
        if (isEmpty()) {
            throw new NoSuchElementException("set is empty, no last element");
        }
        Node curr = root;
        while (curr.right != null) {
            curr = curr.right;
        }
        return curr.value;
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
        if (value == null) {
            throw new NullPointerException("value is null");
        }
        if (root != null) {
            Node curr = root;
            while (curr != null) {
                int cmp = compare(curr.value, value);
                if (cmp == 0) {
                    return true;
                } else if (cmp < 0) {
                    curr = curr.right;
                } else {
                    curr = curr.left;
                }
            }
        }
        return false;
    }

    @Override
    public boolean add(E value) {
        if (value == null) {
            throw new NullPointerException("value is null");
        }
        if (root == null) {
            root = new Node(value);
        } else {
            Node curr = root;
            while (true) {
                int cmp = compare(curr.value, value);
                if (cmp == 0) {
                    return false;
                } else if (cmp < 0) {
                    if (curr.right != null) {
                        curr = curr.right;
                    } else {
                        curr.right = new Node(value);
                        break;
                    }
                } else if (cmp > 0) {
                    if (curr.left != null) {
                        curr = curr.left;
                    } else {
                        curr.left = new Node(value);
                        break;
                    }
                }
            }
        }
        size++;
        return true;
    }

    @Override
    public boolean remove(E value) {
        if (value == null) {
            throw new NullPointerException("value is null");
        }
        if (root == null) {
            return false;
        }
        Node parent = root;
        Node curr = root;
        int cmp;
        while ((cmp = compare(curr.value, value)) != 0) {
            parent = curr;
            if (cmp > 0) {
                curr = curr.left;
            } else {
                curr = curr.right;
            }
            if (curr == null) {
                return false; // ничего не нашли
            }
        }
        if (curr.left != null && curr.right != null) {
            Node next = curr.right;
            Node pNext = curr;
            while (next.left != null) {
                pNext = next;
                next = next.left;
            } //next = наименьший из больших
            curr.value = next.value;
            next.value = null;
            //у правого поддерева нет левых потомков
            if (pNext == curr) {
                curr.right = next.right;
            } else {
                pNext.left = next.right;
            }
            next.right = null;
        } else {
            if (curr.left != null) {
                reLink(parent, curr, curr.left);
            } else if (curr.right != null) {
                reLink(parent, curr, curr.right);
            } else {
                reLink(parent, curr, null);
            }
        }
        size--;
        return true;
    }

    private void reLink(Node parent, Node curr, Node child) {
        if (parent == curr) {
            root = child;
        } else if (parent.left == curr) {
            parent.left = child;
        } else {
            parent.right = child;
        }
        curr.value = null;
    }

    private int compare(E v1, E v2) {
        return comparator == null ? v1.compareTo(v2) : comparator.compare(v1, v2);
    }

    @Override
    public List<E> inorderTraverse() {
        List<E> list = new ArrayList<E>(size);
        inorderTraverse(root, list);
        return list;
    }

    private void inorderTraverse(Node curr, List<E> list) {
        if (curr == null) {
            return;
        }
        inorderTraverse(curr.left, list);
        list.add(curr.value);
        inorderTraverse(curr.right, list);
    }

    @Override
    public String toString() {
        return "BST{" + root + "}";
    }

    public static void main(String[] args) {
        BinarySearchTree<Integer> tree = new BinarySearchTree<>();
        tree.add(10);
        tree.add(5);
        tree.add(15);
        System.out.println(tree.inorderTraverse());
        System.out.println(tree.size);
        System.out.println(tree);
        tree.remove(10);
        tree.remove(15);
        System.out.println(tree.size);
        System.out.println(tree);
        tree.remove(5);
        System.out.println(tree.size);
        System.out.println(tree);
        tree.add(15);
        System.out.println(tree.size);
        System.out.println(tree);

        System.out.println("------------");
        Random rnd = new Random();
        tree = new BinarySearchTree<>();
        for (int i = 0; i < 15; i++) {
            tree.add(rnd.nextInt(50));
        }
        System.out.println(tree.inorderTraverse());
        tree = new BinarySearchTree<>((v1, v2) -> {
            // Even first
            final int c = Integer.compare(v1 % 2, v2 % 2);
            return c != 0 ? c : Integer.compare(v1, v2);
        });
        for (int i = 0; i < 15; i++) {
            tree.add(rnd.nextInt(50));
        }
        System.out.println(tree.inorderTraverse());
    }
}
