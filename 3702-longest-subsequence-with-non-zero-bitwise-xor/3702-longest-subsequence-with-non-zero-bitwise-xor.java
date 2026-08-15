class Solution {
    public int longestSubsequence(int[] nums) {
        int totalXor = 0;
        boolean hasNonZero = false;

        for (int num : nums) {
            totalXor ^= num;
            if (num != 0) {
                hasNonZero = true;
            }
        }

        // If the XOR of all elements is already non-zero, take the whole array
        if (totalXor != 0) {
            return nums.length;
        }

        // If total XOR is 0:
        // - If there is at least one non-zero element, removing it leaves XOR != 0 (length n - 1)
        // - If all elements are 0, no non-zero XOR subsequence is possible (length 0)
        return hasNonZero ? nums.length - 1 : 0;
    }
}

