/*
 * // Definition for a Node.
 * class Node {
 *     public int val;
 *     public Node left;
 *     public Node right;
 *     public Node next;
 *
 *     public Node() {}
 *
 *     public Node(int _val) {
 *         val = _val;
 *     }
 *
 *     public Node(int _val, Node _left, Node _right, Node _next) {
 *         val = _val;
 *         left = _left;
 *         right = _right;
 *         next = _next;
 *     }
 * }
 */

class Solution {

    public Node connect(Node root) {

        Node current = root;

        while (current != null) {

            // Dummy node for building the next level
            Node dummy = new Node(0);
            Node tail = dummy;

            // Traverse current level using next pointers
            while (current != null) {

                if (current.left != null) {
                    tail.next = current.left;
                    tail = tail.next;
                }

                if (current.right != null) {
                    tail.next = current.right;
                    tail = tail.next;
                }

                current = current.next;
            }

            // Move to the first node of next level
            current = dummy.next;
        }

        return root;
    }
}
