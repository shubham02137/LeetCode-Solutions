import java.util.ArrayDeque;
import java.util.Deque;

class Solution {
    public int kthSmallest(TreeNode root, int k) {
        Deque<TreeNode> stack = new ArrayDeque<>();
        TreeNode curr = root;
        
        while (curr != null || !stack.isEmpty()) {
            // Traverse to the leftmost node
            while (curr != null) {
                stack.push(curr);
                curr = curr.left;
            }
            
            curr = stack.pop();
            k--;
            
            // If k becomes 0, we found the kth smallest element
            if (k == 0) {
                return curr.val;
            }
            
            // Move to the right subtree
            curr = curr.right;
        }
        
        return -1;
    }
}