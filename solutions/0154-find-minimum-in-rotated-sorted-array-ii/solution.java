class Solution {
    public int findMin(int[] nums) {

        int low = 0;
        int high = nums.length - 1;

        while (low < high) {

            int mid = low + (high - low) / 2;

            // Minimum is in right half
            if (nums[mid] > nums[high]) {
                low = mid + 1;
            }

            // Minimum is in left half including mid
            else if (nums[mid] < nums[high]) {
                high = mid;
            }

            // Duplicate case
            else {
                high--;
            }
        }

        return nums[low];
    }
}
