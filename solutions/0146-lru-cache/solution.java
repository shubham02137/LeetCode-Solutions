import java.util.*;

class LRUCache {

    class Node {
        int key;
        int value;
        Node prev;
        Node next;

        Node(int key, int value) {
            this.key = key;
            this.value = value;
        }
    }

    private int capacity;
    private HashMap<Integer, Node> map;

    private Node head;
    private Node tail;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        map = new HashMap<>();

        // Dummy nodes
        head = new Node(0, 0);
        tail = new Node(0, 0);

        head.next = tail;
        tail.prev = head;
    }

    public int get(int key) {

        if (!map.containsKey(key)) {
            return -1;
        }

        Node node = map.get(key);

        // Recently accessed -> move to front
        remove(node);
        addFirst(node);

        return node.value;
    }

    public void put(int key, int value) {

        // Key already exists
        if (map.containsKey(key)) {

            Node node = map.get(key);

            node.value = value;

            remove(node);
            addFirst(node);

            return;
        }

        // Create new node
        Node newNode = new Node(key, value);

        map.put(key, newNode);
        addFirst(newNode);

        // Capacity exceeded
        if (map.size() > capacity) {

            // Node before tail is LRU
            Node lru = tail.prev;

            remove(lru);
            map.remove(lru.key);
        }
    }

    // Remove node from linked list
    private void remove(Node node) {

        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    // Add node immediately after head
    private void addFirst(Node node) {

        node.next = head.next;
        node.prev = head;

        head.next.prev = node;
        head.next = node;
    }
}
