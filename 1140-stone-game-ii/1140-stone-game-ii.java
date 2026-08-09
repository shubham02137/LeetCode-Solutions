import java.util.Arrays;

class Solution {
    private int[][] memo;
    private int[] suffixSum;
    private int n;

    public int stoneGameII(int[] piles) {
        n = piles.length;
        
        // Memoization table: max M value needed is n
        memo = new int[n][n + 1];
        for (int[] row : memo) {
            Arrays.fill(row, -1);
        }

        // Compute suffix sums
        suffixSum = new int[n];
        suffixSum[n - 1] = piles[n - 1];
        for (int i = n - 2; i >= 0; i--) {
            suffixSum[i] = suffixSum[i + 1] + piles[i];
        }

        return dfs(0, 1);
    }

    private int dfs(int i, int M) {
        // Base Case: If player can take all remaining piles
        if (i + 2 * M >= n) {
            return suffixSum[i];
        }

        // Return cached result if available
        if (memo[i][M] != -1) {
            return memo[i][M];
        }

        int maxStones = 0;

        // Try taking X piles where 1 <= X <= 2 * M
        for (int X = 1; X <= 2 * M; X++) {
            int nextM = Math.max(M, X);
            int opponentScore = dfs(i + X, nextM);
            // My score = Total remaining stones - Opponent's optimal score
            int currentScore = suffixSum[i] - opponentScore;
            maxStones = Math.max(maxStones, currentScore);
        }

        return memo[i][M] = maxStones;
    }
}