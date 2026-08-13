class Solution {
    static class Node {
        int mx;   // Maximum length of repeating character substring in this segment
        int pre;  // Length of longest valid prefix in this segment
        int suf;  // Length of longest valid suffix in this segment
        char lc;  // Character at left endpoint
        char rc;  // Character at right endpoint
    }

    private Node[] tree;
    private char[] chars;

    public int[] longestRepeating(String s, String queryCharacters, int[] queryIndices) {
        int n = s.length();
        chars = s.toCharArray();
        tree = new Node[4 * n];
        
        // Build the Segment Tree
        build(1, 0, n - 1);

        int k = queryIndices.length;
        int[] ans = new int[k];

        for (int i = 0; i < k; i++) {
            int idx = queryIndices[i];
            char c = queryCharacters.charAt(i);
            
            // Perform point update
            update(1, 0, n - 1, idx, c);
            
            // The root node (1) holds the max length for the full range [0, n - 1]
            ans[i] = tree[1].mx;
        }

        return ans;
    }

    private void build(int node, int l, int r) {
        tree[node] = new Node();
        if (l == r) {
            tree[node].mx = 1;
            tree[node].pre = 1;
            tree[node].suf = 1;
            tree[node].lc = chars[l];
            tree[node].rc = chars[l];
            return;
        }
        int mid = l + (r - l) / 2;
        build(node * 2, l, mid);
        build(node * 2 + 1, mid + 1, r);
        merge(node, l, r, mid);
    }

    private void update(int node, int l, int r, int idx, char c) {
        if (l == r) {
            chars[l] = c;
            tree[node].lc = c;
            tree[node].rc = c;
            return;
        }
        int mid = l + (r - l) / 2;
        if (idx <= mid) {
            update(node * 2, l, mid, idx, c);
        } else {
            update(node * 2 + 1, mid + 1, r, idx, c);
        }
        merge(node, l, r, mid);
    }

    private void merge(int node, int l, int r, int mid) {
        Node left = tree[node * 2];
        Node right = tree[node * 2 + 1];
        
        int lenLeft = mid - l + 1;
        int lenRight = r - mid;

        tree[node].lc = left.lc;
        tree[node].rc = right.rc;

        // Base prefix and suffix values from child nodes
        tree[node].pre = left.pre;
        if (left.pre == lenLeft && left.rc == right.lc) {
            tree[node].pre = lenLeft + right.pre;
        }

        tree[node].suf = right.suf;
        if (right.suf == lenRight && right.lc == left.rc) {
            tree[node].suf = lenRight + left.suf;
        }

        // Maximum repeating length within the merged segment
        tree[node].mx = Math.max(left.mx, right.mx);
        if (left.rc == right.lc) {
            tree[node].mx = Math.max(tree[node].mx, left.suf + right.pre);
        }
    }
}