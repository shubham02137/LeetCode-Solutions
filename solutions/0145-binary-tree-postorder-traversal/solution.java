import java.util.*;

class Solution {
    public List<Integer> postorderTraversal(TreeNode root) {

        List<Integer> result = new ArrayList<>();

        postorder(root, result);

        return result;
    }

    private void postorder(TreeNode node, List<Integer> result) {

        if (node == null) {
            return;
        }

        // Left
        postorder(node.left, result);

        // Right
        postorder(node.right, result);

        // Root
        result.add(node.val);
    }
}
