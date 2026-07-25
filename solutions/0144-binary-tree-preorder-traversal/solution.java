import java.util.*;

class Solution {
    public List<Integer> preorderTraversal(TreeNode root) {

        List<Integer> result = new ArrayList<>();

        preorder(root, result);

        return result;
    }

    private void preorder(TreeNode node, List<Integer> result) {

        if (node == null) {
            return;
        }

        // Root
        result.add(node.val);

        // Left
        preorder(node.left, result);

        // Right
        preorder(node.right, result);
    }
}
