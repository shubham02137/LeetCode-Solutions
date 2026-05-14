import java.util.*;

class Solution {
    public boolean isGood(int[] nums) {

        Arrays.sort(nums);

        int n = nums.length - 1;

        // Last two elements must be n
        if (nums[n] != n || nums[n - 1] != n) {
            return false;
        }

        // Check numbers from 1 to n-1
        for (int i = 0; i < n - 1; i++) {
            if (nums[i] != i + 1) {
                return false;
            }
        }

        return true;
    }
}
