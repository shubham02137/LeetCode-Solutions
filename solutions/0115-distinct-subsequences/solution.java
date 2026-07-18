class Solution {

    public int numDistinct(String s, String t) {

        int m = s.length();
        int n = t.length();

        // Impossible if t is longer than s
        if (n > m) {
            return 0;
        }

        long[] dp = new long[n + 1];

        // Empty string t can always be formed in one way
        dp[0] = 1;

        for (int i = 0; i < m; i++) {

            // Traverse backwards to avoid using
            // the same character more than once
            for (int j = n; j >= 1; j--) {

                if (s.charAt(i) == t.charAt(j - 1)) {
                    dp[j] += dp[j - 1];
                }
            }
        }

        return (int) dp[n];
    }
}
