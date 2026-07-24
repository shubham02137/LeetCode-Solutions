class Solution {

    public int uniqueXorTriplets(int[] nums) {

        // nums[i] <= 1500 < 2048
        int MAX = 2048;

        boolean[] pairXor = new boolean[MAX];
        boolean[] tripletXor = new boolean[MAX];

        // Find all XOR values using two elements
        for (int a : nums) {
            for (int b : nums) {
                pairXor[a ^ b] = true;
            }
        }

        // Add the third element
        for (int x = 0; x < MAX; x++) {

            if (!pairXor[x]) {
                continue;
            }

            for (int num : nums) {
                tripletXor[x ^ num] = true;
            }
        }

        int count = 0;

        for (boolean possible : tripletXor) {
            if (possible) {
                count++;
            }
        }

        return count;
    }
}
