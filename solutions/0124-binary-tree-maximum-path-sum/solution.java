/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */

class Solution {

    private int maxSum = Integer.MIN_VALUE;

    public int maxPathSum(TreeNode root) {
        dfs(root);
        return maxSum;
    }

    private int dfs(TreeNode node) {

        if (node == null) {
            return 0;
        }

        // Ignore negative contributions
        int leftGain = Math.max(0, dfs(node.left));
        int rightGain = Math.max(0, dfs(node.right));

        // Best path passing through current node
        int currentPathSum =
                node.val + leftGain + rightGain;

        // Update global maximum
        maxSum = Math.max(maxSum, currentPathSum);

        // Return only one branch to parent
        return node.val + Math.max(leftGain, rightGain);
    }
}
