/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) {
 *         val = x;
 *         next = null;
 *     }
 * }
 */

public class Solution {
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {

        if (headA == null || headB == null) {
            return null;
        }

        ListNode a = headA;
        ListNode b = headB;

        while (a != b) {
            // After reaching end of A, start from B
            a = (a == null) ? headB : a.next;

            // After reaching end of B, start from A
            b = (b == null) ? headA : b.next;
        }

        return a;
    }
}