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

import java.util.*;

class Solution {

    public List<TreeNode> generateTrees(int n) {
        return build(1, n);
    }

    private List<TreeNode> build(int start, int end) {

        List<TreeNode> trees = new ArrayList<>();

        // Empty subtree
        if (start > end) {
            trees.add(null);
            return trees;
        }

        // Try every value as root
        for (int root = start; root <= end; root++) {

            List<TreeNode> leftTrees = build(start, root - 1);
            List<TreeNode> rightTrees = build(root + 1, end);

            // Combine left and right subtrees
            for (TreeNode left : leftTrees) {
                for (TreeNode right : rightTrees) {

                    TreeNode node = new TreeNode(root);
                    node.left = left;
                    node.right = right;

                    trees.add(node);
                }
            }
        }

        return trees;
    }
}
