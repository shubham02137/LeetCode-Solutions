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

    public void flatten(TreeNode root) {

        TreeNode current = root;

        while (current != null) {

            if (current.left != null) {

                // Find rightmost node in left subtree
                TreeNode predecessor = current.left;

                while (predecessor.right != null) {
                    predecessor = predecessor.right;
                }

                // Attach original right subtree
                predecessor.right = current.right;

                // Move left subtree to right
                current.right = current.left;
                current.left = null;
            }

            current = current.right;
        }
    }
}
