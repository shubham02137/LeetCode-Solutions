class Solution {
    public long findKthSmallest(int[] coins, int k) {
        int n = coins.length;
        int numSubsets = 1 << n;
        long[] subsetLcm = new long[numSubsets];
        int[] subsetBits = new int[numSubsets];

        // Precompute LCM and bit count for all non-empty subsets
        for (int mask = 1; mask < numSubsets; mask++) {
            subsetBits[mask] = Integer.bitCount(mask);
            long currentLcm = 1;
            for (int i = 0; i < n; i++) {
                if ((mask & (1 << i)) != 0) {
                    currentLcm = lcm(currentLcm, coins[i]);
                }
            }
            subsetLcm[mask] = currentLcm;
        }

        // Find the minimum coin to set an upper bound for binary search
        int minCoin = coins[0];
        for (int coin : coins) {
            minCoin = Math.min(minCoin, coin);
        }

        long left = 1;
        long right = (long) minCoin * k;
        long result = right;

        // Binary search on the answer
        while (left <= right) {
            long mid = left + (right - left) / 2;
            if (countMultiples(mid, numSubsets, subsetLcm, subsetBits) >= k) {
                result = mid;
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }

        return result;
    }

    // Inclusion-Exclusion Principle to count distinct multiples <= m
    private long countMultiples(long m, int numSubsets, long[] subsetLcm, int[] subsetBits) {
        long count = 0;
        for (int mask = 1; mask < numSubsets; mask++) {
            long multiples = m / subsetLcm[mask];
            if (subsetBits[mask] % 2 == 1) {
                count += multiples;
            } else {
                count -= multiples;
            }
        }
        return count;
    }

    private long gcd(long a, long b) {
        while (b != 0) {
            long temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    private long lcm(long a, long b) {
        return (a / gcd(a, b)) * b;
    }
}