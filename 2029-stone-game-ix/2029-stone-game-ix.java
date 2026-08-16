class Solution {
    public boolean stoneGameIX(int[] stones) {
        int[] cnt = new int[3];
        for (int stone : stones) {
            cnt[stone % 3]++;
        }

        // If the number of stones with remainder 0 is even:
        // Alice can win if there is at least one stone with remainder 1 and at least one with remainder 2.
        if (cnt[0] % 2 == 0) {
            return cnt[1] >= 1 && cnt[2] >= 1;
        }

        // If the number of stones with remainder 0 is odd:
        // Alice can win if the absolute difference between count of 1s and 2s is greater than 2.
        return Math.abs(cnt[1] - cnt[2]) > 2;
    }
}