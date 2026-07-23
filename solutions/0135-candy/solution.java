class Solution {

    public int candy(int[] ratings) {

        int n = ratings.length;

        int[] candies = new int[n];

        // Every child gets at least 1 candy
        for (int i = 0; i < n; i++) {
            candies[i] = 1;
        }

        // Left -> Right
        // Higher rating than left neighbor
        for (int i = 1; i < n; i++) {

            if (ratings[i] > ratings[i - 1]) {
                candies[i] = candies[i - 1] + 1;
            }
        }

        // Right -> Left
        // Higher rating than right neighbor
        for (int i = n - 2; i >= 0; i--) {

            if (ratings[i] > ratings[i + 1]) {

                candies[i] = Math.max(
                    candies[i],
                    candies[i + 1] + 1
                );
            }
        }

        int total = 0;

        for (int candy : candies) {
            total += candy;
        }

        return total;
    }
}
