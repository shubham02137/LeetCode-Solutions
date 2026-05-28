class Solution {

    class TrieNode {
        TrieNode[] child = new TrieNode[26];

        int idx = -1;
        int len = Integer.MAX_VALUE;
    }

    TrieNode root = new TrieNode();

    // update best answer at node
    void update(TrieNode node, int index, int length) {

        if (length < node.len ||
           (length == node.len && index < node.idx)) {

            node.len = length;
            node.idx = index;
        }
    }

    // insert reversed word
    void insert(String word, int index) {

        TrieNode curr = root;

        update(curr, index, word.length());

        for (int i = word.length() - 1; i >= 0; i--) {

            int c = word.charAt(i) - 'a';

            if (curr.child[c] == null) {
                curr.child[c] = new TrieNode();
            }

            curr = curr.child[c];

            update(curr, index, word.length());
        }
    }

    // query answer
    int search(String word) {

        TrieNode curr = root;

        for (int i = word.length() - 1; i >= 0; i--) {

            int c = word.charAt(i) - 'a';

            if (curr.child[c] == null)
                break;

            curr = curr.child[c];
        }

        return curr.idx;
    }

    public int[] stringIndices(String[] wordsContainer,
                               String[] wordsQuery) {

        int n = wordsQuery.length;

        // build trie
        for (int i = 0; i < wordsContainer.length; i++) {
            insert(wordsContainer[i], i);
        }

        int[] ans = new int[n];

        for (int i = 0; i < n; i++) {
            ans[i] = search(wordsQuery[i]);
        }

        return ans;
    }
}
