class Solution {
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        
        ListNode dummy = new ListNode(0);
        ListNode curr = dummy;
        
        int carry = 0;
        
        while (l1 != null || l2 != null || carry != 0) {
            
            int sum = carry;
            
            // Add l1 value
            if (l1 != null) {
                sum += l1.val;
                l1 = l1.next;
            }
            
            // Add l2 value
            if (l2 != null) {
                sum += l2.val;
                l2 = l2.next;
            }
            
            // New digit
            curr.next = new ListNode(sum % 10);
            
            // Update carry
            carry = sum / 10;
            
            // Move pointer
            curr = curr.next;
        }
        
        return dummy.next;
    }
}
