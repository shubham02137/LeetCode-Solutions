class Solution {
    public boolean uniformArray(int[] nums1) {
        int minVal = Integer.MAX_VALUE;
        boolean hasOdd = false;

        for (int x : nums1) {
            if (x < minVal) {
                minVal = x;
            }
            if (x % 2 != 0) {
                hasOdd = true;
            }
        }

        // True if all numbers are already even, or if the global minimum is odd
        return !hasOdd || (minVal % 2 != 0);
    }
}