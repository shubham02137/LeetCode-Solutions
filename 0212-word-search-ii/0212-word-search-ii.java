import java.util.ArrayList;
import java.util.List;

class Solution {

    private static class TrieNode {
        TrieNode[] children = new TrieNode[26];
        String word = null;
    }

    public List<String> findWords(char[][] board, String[] words) {
        List<String> result = new ArrayList<>();
        TrieNode root = buildTrie(words);

        int rows = board.length;
        int cols = board[0].length;

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                dfs(board, r, c, root, result);
            }
        }

        return result;
    }

    private void dfs(char[][] board, int r, int c, TrieNode node, List<String> result) {
        char ch = board[r][c];

        // If visited ('#') or character path doesn't exist in Trie, return
        if (ch == '#' || node.children[ch - 'a'] == null) {
            return;
        }

        node = node.children[ch - 'a'];

        // If we found a valid word, add to result and set to null to avoid duplicates
        if (node.word != null) {
            result.add(node.word);
            node.word = null;
        }

        // Mark cell as visited
        board[r][c] = '#';

        // Explore adjacent neighbors (Up, Down, Left, Right)
        if (r > 0) dfs(board, r - 1, c, node, result);
        if (r < board.length - 1) dfs(board, r + 1, c, node, result);
        if (c > 0) dfs(board, r, c - 1, node, result);
        if (c < board[0].length - 1) dfs(board, r, c + 1, node, result);

        // Backtrack: restore cell
        board[r][c] = ch;
    }

    private TrieNode buildTrie(String[] words) {
        TrieNode root = new TrieNode();
        for (String w : words) {
            TrieNode current = root;
            for (char ch : w.toCharArray()) {
                int index = ch - 'a';
                if (current.children[index] == null) {
                    current.children[index] = new TrieNode();
                }
                current = current.children[index];
            }
            current.word = w;
        }
        return root;
    }
}