class Solution {
    public int rob(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        if (nums.length == 1) {
            return nums[0];
        }

        // Case 1: Rob houses from index 0 to n - 2 (exclude the last house)
        // Case 2: Rob houses from index 1 to n - 1 (exclude the first house)
        return Math.max(robRange(nums, 0, nums.length - 2), 
                        robRange(nums, 1, nums.length - 1));
    }

    private int robRange(int[] nums, int start, int end) {
        int prevMax = 0;
        int currMax = 0;

        for (int i = start; i <= end; i++) {
            int temp = Math.max(currMax, prevMax + nums[i]);
            prevMax = currMax;
            currMax = temp;
        }

        return currMax;
    }
}