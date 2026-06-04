class Solution {

    static class Node {
        long count;
        long sum;

        Node(long count, long sum) {
            this.count = count;
            this.sum = sum;
        }
    }

    private char[] digits;
    private Node[][][][] memo;

    public int totalWaviness(int num1, int num2) {
        return (int)(solve(num2) - solve(num1 - 1));
    }

    private long solve(long n) {
        if (n < 0) return 0;

        digits = String.valueOf(n).toCharArray();
        memo = new Node[20][11][11][4];

        return dfs(0, true, false, -1, -1, 0).sum;
    }

    private Node dfs(int pos, boolean tight, boolean started,
                     int prev2, int prev1, int lenState) {

        if (pos == digits.length) {
            return new Node(1, 0);
        }

        if (!tight) {
            Node cached = memo[pos][prev2 + 1][prev1 + 1][lenState];
            if (cached != null) return cached;
        }

        int limit = tight ? digits[pos] - '0' : 9;

        long totalCount = 0;
        long totalSum = 0;

        for (int d = 0; d <= limit; d++) {

            boolean nextTight = tight && (d == limit);

            if (!started && d == 0) {

                Node nxt = dfs(pos + 1, nextTight,
                               false, -1, -1, 0);

                totalCount += nxt.count;
                totalSum += nxt.sum;

            } else if (!started) {

                Node nxt = dfs(pos + 1, nextTight,
                               true, -1, d, 1);

                totalCount += nxt.count;
                totalSum += nxt.sum;

            } else {

                long add = 0;

                if (lenState >= 2) {
                    if ((prev1 > prev2 && prev1 > d) ||
                        (prev1 < prev2 && prev1 < d)) {
                        add = 1;
                    }
                }

                Node nxt = dfs(
                        pos + 1,
                        nextTight,
                        true,
                        prev1,
                        d,
                        Math.min(3, lenState + 1)
                );

                totalCount += nxt.count;
                totalSum += nxt.sum + add * nxt.count;
            }
        }

        Node ans = new Node(totalCount, totalSum);

        if (!tight) {
            memo[pos][prev2 + 1][prev1 + 1][lenState] = ans;
        }

        return ans;
    }
}
