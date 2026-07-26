class Solution {
    public ListNode insertionSortList(ListNode head) {

        if (head == null || head.next == null) {
            return head;
        }

        ListNode dummy = new ListNode(0);
        ListNode current = head;

        while (current != null) {

            // Save next node before changing links
            ListNode next = current.next;

            // Find correct insertion position
            ListNode prev = dummy;

            while (prev.next != null &&
                   prev.next.val <= current.val) {
                prev = prev.next;
            }

            // Insert current node
            current.next = prev.next;
            prev.next = current;

            // Move to next unsorted node
            current = next;
        }

        return dummy.next;
    }
}
