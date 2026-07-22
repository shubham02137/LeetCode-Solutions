class Solution {

    public int minCut(String s) {

        int n = s.length();

        // palindrome[i][j] = true if s[i...j] is palindrome
        boolean[][] palindrome = new boolean[n][n];

        // Precompute palindrome substrings
        for (int i = n - 1; i >= 0; i--) {

            for (int j = i; j < n; j++) {

                if (s.charAt(i) == s.charAt(j) &&
                    (j - i <= 1 || palindrome[i + 1][j - 1])) {

                    palindrome[i][j] = true;
                }
            }
        }

        // dp[i] = minimum cuts needed for s[0...i]
        int[] dp = new int[n];

        for (int i = 0; i < n; i++) {

            // Worst case:
            // s[0] | s[1] | ... | s[i]
            dp[i] = i;

            // Whole substring s[0...i] is palindrome
            if (palindrome[0][i]) {
                dp[i] = 0;
                continue;
            }

            // Try the last palindrome substring s[j...i]
            for (int j = 1; j <= i; j++) {

                if (palindrome[j][i]) {
                    dp[i] = Math.min(
                        dp[i],
                        dp[j - 1] + 1
                    );
                }
            }
        }

        return dp[n - 1];
    }
}
