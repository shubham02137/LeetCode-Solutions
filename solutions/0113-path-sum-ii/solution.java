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

    public List<List<Integer>> pathSum(TreeNode root, int targetSum) {

        List<List<Integer>> result = new ArrayList<>();
        List<Integer> path = new ArrayList<>();

        dfs(root, targetSum, path, result);

        return result;
    }

    private void dfs(TreeNode node, int remainingSum,
                     List<Integer> path,
                     List<List<Integer>> result) {

        if (node == null) {
            return;
        }

        // Add current node
        path.add(node.val);

        remainingSum -= node.val;

        // Check if current node is a leaf
        if (node.left == null && node.right == null) {

            if (remainingSum == 0) {
                result.add(new ArrayList<>(path));
            }

        } else {

            dfs(node.left, remainingSum, path, result);
            dfs(node.right, remainingSum, path, result);
        }

        // Backtrack
        path.remove(path.size() - 1);
    }
}
