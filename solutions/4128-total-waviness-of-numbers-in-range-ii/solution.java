import java.util.*;

class Solution {

    static class Pair {
        long count; // number of valid numbers
        long sum;   // total waviness

        Pair(long count, long sum) {
            this.count = count;
            this.sum = sum;
        }
    }

    private char[] digits;
    private Pair[][][][][] memo;

    public long totalWaviness(long num1, long num2) {
        return solve(num2) - solve(num1 - 1);
    }

    private long solve(long n) {
        if (n <= 0) return 0;

        digits = String.valueOf(n).toCharArray();

        int len = digits.length;

        // pos, started, stateLen(0,1,2), prev2(0..10), prev1(0..10)
        memo = new Pair[len + 1][2][3][11][11];

        return dfs(0, 1, 0, 10, 10).sum;
    }

    /**
     * stateLen:
     * 0 = no digit chosen yet
     * 1 = exactly one digit chosen
     * 2 = at least two digits chosen
     */
    private Pair dfs(int pos, int tight, int stateLen, int prev2, int prev1) {
        if (pos == digits.length) {
            return new Pair(1, 0);
        }

        if (tight == 0 && memo[pos][0][stateLen][prev2][prev1] != null) {
            return memo[pos][0][stateLen][prev2][prev1];
        }

        int limit = (tight == 1) ? digits[pos] - '0' : 9;

        long totalCount = 0;
        long totalSum = 0;

        for (int d = 0; d <= limit; d++) {
            int nextTight = (tight == 1 && d == limit) ? 1 : 0;

            if (stateLen == 0) {
                // still leading zeros
                if (d == 0) {
                    Pair nxt = dfs(pos + 1, nextTight, 0, 10, 10);
                    totalCount += nxt.count;
                    totalSum += nxt.sum;
                } else {
                    Pair nxt = dfs(pos + 1, nextTight, 1, 10, d);
                    totalCount += nxt.count;
                    totalSum += nxt.sum;
                }
            } else if (stateLen == 1) {
                Pair nxt = dfs(pos + 1, nextTight, 2, prev1, d);
                totalCount += nxt.count;
                totalSum += nxt.sum;
            } else {
                // We now know both neighbors of prev1:
                // prev2, prev1, d
                int add = 0;

                if (prev1 > prev2 && prev1 > d) add = 1;      // peak
                else if (prev1 < prev2 && prev1 < d) add = 1; // valley

                Pair nxt = dfs(pos + 1, nextTight, 2, prev1, d);

                totalCount += nxt.count;
                totalSum += nxt.sum + (long) add * nxt.count;
            }
        }

        Pair res = new Pair(totalCount, totalSum);

        if (tight == 0) {
            memo[pos][0][stateLen][prev2][prev1] = res;
        }

        return res;
    }
}
