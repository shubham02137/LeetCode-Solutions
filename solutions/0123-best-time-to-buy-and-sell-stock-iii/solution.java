class Solution {
    public int maxProfit(int[] prices) {

        int firstBuy = Integer.MIN_VALUE;
        int firstSell = 0;

        int secondBuy = Integer.MIN_VALUE;
        int secondSell = 0;

        for (int price : prices) {

            // Best balance after first buy
            firstBuy = Math.max(firstBuy, -price);

            // Best profit after first sell
            firstSell = Math.max(firstSell, firstBuy + price);

            // Best balance after second buy
            secondBuy = Math.max(secondBuy, firstSell - price);

            // Best profit after second sell
            secondSell = Math.max(secondSell, secondBuy + price);
        }

        return secondSell;
    }
}
