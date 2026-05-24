class Solution {

    int[] dp;

    public int dfs(int[] arr, int d, int i) {

        // already calculated
        if (dp[i] != -1)
            return dp[i];

        int n = arr.length;
        int ans = 1;

        // Move Right
        for (int j = i + 1; j <= Math.min(n - 1, i + d); j++) {

            // stop if greater or equal element found
            if (arr[j] >= arr[i])
                break;

            ans = Math.max(ans, 1 + dfs(arr, d, j));
        }

        // Move Left
        for (int j = i - 1; j >= Math.max(0, i - d); j--) {

            // stop if greater or equal element found
            if (arr[j] >= arr[i])
                break;

            ans = Math.max(ans, 1 + dfs(arr, d, j));
        }

        return dp[i] = ans;
    }

    public int maxJumps(int[] arr, int d) {

        int n = arr.length;

        dp = new int[n];

        // initialize with -1
        Arrays.fill(dp, -1);

        int res = 1;

        for (int i = 0; i < n; i++) {
            res = Math.max(res, dfs(arr, d, i));
        }

        return res;
    }
}
