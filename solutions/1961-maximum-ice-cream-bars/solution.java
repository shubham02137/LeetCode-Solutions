class Solution {
    public int maxIceCream(int[] costs, int coins) {
        int max = 0;

        // Find maximum cost
        for (int cost : costs) {
            max = Math.max(max, cost);
        }

        // Frequency array (Counting Sort)
        int[] freq = new int[max + 1];

        for (int cost : costs) {
            freq[cost]++;
        }

        int count = 0;

        // Buy from cheapest to expensive
        for (int cost = 1; cost <= max; cost++) {
            if (freq[cost] == 0) continue;

            int canBuy = Math.min(freq[cost], coins / cost);

            count += canBuy;
            coins -= canBuy * cost;

            if (coins < cost) break;
        }

        return count;
    }
}
