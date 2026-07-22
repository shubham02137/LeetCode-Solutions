class Solution {

    public int sumNumbers(TreeNode root) {
        return dfs(root, 0);
    }

    private int dfs(TreeNode node, int current) {

        if (node == null) {
            return 0;
        }

        // Add current digit to the number
        current = current * 10 + node.val;

        // Leaf node: complete root-to-leaf number
        if (node.left == null && node.right == null) {
            return current;
        }

        // Sum numbers from left and right subtrees
        return dfs(node.left, current)
             + dfs(node.right, current);
    }
}
