class Solution {

    public Node copyRandomList(Node head) {

        if (head == null) {
            return null;
        }

        // Step 1:
        // Insert cloned node after every original node
        Node current = head;

        while (current != null) {

            Node copy = new Node(current.val);

            copy.next = current.next;
            current.next = copy;

            current = copy.next;
        }

        // Step 2:
        // Set random pointers of cloned nodes
        current = head;

        while (current != null) {

            if (current.random != null) {
                current.next.random = current.random.next;
            }

            current = current.next.next;
        }

        // Step 3:
        // Separate original and cloned lists
        Node copyHead = head.next;

        current = head;

        while (current != null) {

            Node copy = current.next;

            // Restore original list
            current.next = copy.next;

            // Connect cloned list
            if (copy.next != null) {
                copy.next = copy.next.next;
            }

            current = current.next;
        }

        return copyHead;
    }
}
