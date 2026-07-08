class Solution {

    static final int MOD = 1_000_000_007;

    class Node {
        long value;
        int count;

        Node(long value, int count) {
            this.value = value;
            this.count = count;
        }
    }

    Node[] tree;
    long[] pow10;
    String s;

    public int[] sumAndMultiply(String s, int[][] queries) {
        this.s = s;
        int n = s.length();

        // Precompute powers of 10 modulo MOD
        pow10 = new long[n + 1];
        pow10[0] = 1;
        for (int i = 1; i <= n; i++) {
            pow10[i] = (pow10[i - 1] * 10) % MOD;
        }

        // Prefix sum of non-zero digits
        int[] prefix = new int[n + 1];
        for (int i = 0; i < n; i++) {
            int d = s.charAt(i) - '0';
            prefix[i + 1] = prefix[i] + (d == 0 ? 0 : d);
        }

        tree = new Node[4 * n];
        build(1, 0, n - 1);

        int[] ans = new int[queries.length];

        for (int i = 0; i < queries.length; i++) {
            int l = queries[i][0];
            int r = queries[i][1];

            Node res = query(1, 0, n - 1, l, r);

            long sum = prefix[r + 1] - prefix[l];
            ans[i] = (int) ((res.value * sum) % MOD);
        }

        return ans;
    }

    private void build(int idx, int l, int r) {
        if (l == r) {
            int d = s.charAt(l) - '0';
            if (d == 0) {
                tree[idx] = new Node(0, 0);
            } else {
                tree[idx] = new Node(d, 1);
            }
            return;
        }

        int mid = (l + r) / 2;

        build(idx * 2, l, mid);
        build(idx * 2 + 1, mid + 1, r);

        tree[idx] = merge(tree[idx * 2], tree[idx * 2 + 1]);
    }

    private Node query(int idx, int l, int r, int ql, int qr) {

        if (ql <= l && r <= qr)
            return tree[idx];

        if (r < ql || l > qr)
            return new Node(0, 0);

        int mid = (l + r) / 2;

        Node left = query(idx * 2, l, mid, ql, qr);
        Node right = query(idx * 2 + 1, mid + 1, r, ql, qr);

        return merge(left, right);
    }

    private Node merge(Node left, Node right) {
        long value = (left.value * pow10[right.count] + right.value) % MOD;
        int count = left.count + right.count;
        return new Node(value, count);
    }
}
