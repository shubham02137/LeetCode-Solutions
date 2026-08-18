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
    public int countNodes(TreeNode root) {
        if (root == null) {
            return 0;
        }

        int leftDepth = getLeftDepth(root);
        int rightDepth = getRightDepth(root);

        // If left and right depths are equal, it's a full binary tree
        if (leftDepth == rightDepth) {
            return (1 << leftDepth) - 1;
        }

        // Otherwise, recursively count left and right subtrees
        return 1 + countNodes(root.left) + countNodes(root.right);
    }

    private int getLeftDepth(TreeNode node) {
        int depth = 0;
        while (node != null) {
            depth++;
            node = node.left;
        }
        return depth;
    }

    private int getRightDepth(TreeNode node) {
        int depth = 0;
        while (node != null) {
            depth++;
            node = node.right;
        }
        return depth;
    }
}