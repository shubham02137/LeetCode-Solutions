class Solution {
    public boolean sumGame(String num) {
        int n = num.length();
        int sumDiff = 0;
        int qDiff = 0;

        for (int i = 0; i < n / 2; i++) {
            char c = num.charAt(i);
            if (c == '?') {
                qDiff++;
            } else {
                sumDiff += c - '0';
            }
        }

        for (int i = n / 2; i < n; i++) {
            char c = num.charAt(i);
            if (c == '?') {
                qDiff--;
            } else {
                sumDiff -= c - '0';
            }
        }

        // Alice wins if the number of '?' differences is odd,
        // or if Bob cannot neutralize the difference (each pair of '?' can contribute an average sum of 9).
        return (qDiff % 2 != 0) || (sumDiff + (qDiff / 2) * 9 != 0);
    }
}