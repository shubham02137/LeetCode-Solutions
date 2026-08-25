class Solution {
    public int stoneGameVIII(int[] stones) {
        int n = stones.length;
        
        // Compute prefix sums in-place
        for (int i = 1; i < n; i++) {
            stones[i] += stones[i - 1];
        }
        
        // dp represents the maximum score difference from index i to n - 1
        // Base case: at index n - 1, the player must take all remaining stones
        int dp = stones[n - 1];
        
        // Iterate backwards from n - 2 down to 1 (Alice must take at least 2 stones)
        for (int i = n - 2; i >= 1; i--) {
            dp = Math.max(dp, stones[i] - dp);
        }
        
        return dp;
    }
}