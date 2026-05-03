import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class BST<K extends Comparable<K>, V> {

    private Node root;
    private int size;

    private class Node {
        private K key;
        private V val;
        private Node left, right;

        public Node(K key, V val) {
            this.key = key;
            this.val = val;
        }
    }


    public class Entry {
        private final K key;
        private final V value;

        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() { return key; }
        public V getValue() { return value; }

        @Override
        public String toString() {
            return "{" + key + " -> " + value + "}";
        }
    }


    public void put(K key, V val) {

        if (root == null) {
            root = new Node(key, val);
            size++;
            return;
        }

        Node current = root;
        while (true) {
            int cmp = key.compareTo(current.key);

            if (cmp < 0) {
                // идём влево
                if (current.left == null) {
                    current.left = new Node(key, val);
                    size++;
                    return;
                }
                current = current.left;

            } else if (cmp > 0) {
                // идём вправо
                if (current.right == null) {
                    current.right = new Node(key, val);
                    size++;
                    return;
                }
                current = current.right;

            } else {
                // ключ уже есть — обновляем значение
                current.val = val;
                return;
            }
        }
    }


    public V get(K key) {
        Node current = root;

        while (current != null) {
            int cmp = key.compareTo(current.key);

            if (cmp < 0) {
                current = current.left;
            } else if (cmp > 0) {
                current = current.right;
            } else {
                return current.val; // нашли!
            }
        }
        return null; // не нашли
    }


    public void delete(K key) {
        Node parent = null;
        Node current = root;
        boolean isLeftChild = false;


        while (current != null) {
            int cmp = key.compareTo(current.key);

            if (cmp < 0) {
                parent = current;
                isLeftChild = true;
                current = current.left;
            } else if (cmp > 0) {
                parent = current;
                isLeftChild = false;
                current = current.right;
            } else {
                break; // нашли узел
            }
        }

        if (current == null) return; // узел не найден
        size--;




        if (current.left == null && current.right == null) {
            if (parent == null) root = null;
            else if (isLeftChild) parent.left = null;
            else parent.right = null;


        } else if (current.left == null) {
            if (parent == null) root = current.right;
            else if (isLeftChild) parent.left = current.right;
            else parent.right = current.right;


        } else if (current.right == null) {
            if (parent == null) root = current.left;
            else if (isLeftChild) parent.left = current.left;
            else parent.right = current.left;


        } else {

            Node successorParent = current;
            Node successor = current.right;

            while (successor.left != null) {
                successorParent = successor;
                successor = successor.left;
            }


            current.key = successor.key;
            current.val = successor.val;


            if (successorParent == current) {
                successorParent.right = successor.right;
            } else {
                successorParent.left = successor.right;
            }
        }
    }


    public int size() {
        return size;
    }


    public Iterable<Entry> iterator() {
        List<Entry> list = new ArrayList<>();
        Stack<Node> stack = new Stack<>();
        Node current = root;

        while (current != null || !stack.isEmpty()) {

            while (current != null) {
                stack.push(current);
                current = current.left;
            }


            current = stack.pop();
            list.add(new Entry(current.key, current.val));


            current = current.right;
        }

        return list;
    }
}
