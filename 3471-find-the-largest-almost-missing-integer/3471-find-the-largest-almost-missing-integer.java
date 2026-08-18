import java.util.HashSet;
import java.util.Set;

class Solution {
    public int largestInteger(int[] nums, int k) {
        int n = nums.length;
        int[] subarrayCount = new int[51];

        // Iterate through all subarrays of size k
        for (int i = 0; i <= n - k; i++) {
            Set<Integer> seen = new HashSet<>();
            for (int j = i; j < i + k; j++) {
                seen.add(nums[j]);
            }
            // Count unique occurrences within each subarray
            for (int val : seen) {
                subarrayCount[val]++;
            }
        }

        int maxAlmostMissing = -1;
        for (int val = 0; val <= 50; val++) {
            if (subarrayCount[val] == 1) {
                maxAlmostMissing = Math.max(maxAlmostMissing, val);
            }
        }

        return maxAlmostMissing;
    }
}