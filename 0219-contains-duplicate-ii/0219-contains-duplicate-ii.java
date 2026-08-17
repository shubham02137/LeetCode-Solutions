import java.util.HashSet;
import java.util.Set;

class Solution {
    public boolean containsNearbyDuplicate(int[] nums, int k) {
        Set<Integer> window = new HashSet<>();

        for (int i = 0; i < nums.length; i++) {
            // If the element already exists within the current window of size k
            if (!window.add(nums[i])) {
                return true;
            }

            // Maintain window size <= k by removing the element that fell out
            if (window.size() > k) {
                window.remove(nums[i - k]);
            }
        }

        return false;
    }
}