class Solution {
    public long sumAndMultiply(int n) {

        if (n == 0) return 0;

        StringBuilder sb = new StringBuilder();

        while (n > 0) {
            int digit = n % 10;
            if (digit != 0) {
                sb.append(digit);
            }
            n /= 10;
        }

        sb.reverse();

        long x = 0;
        long sum = 0;

        for (int i = 0; i < sb.length(); i++) {
            int digit = sb.charAt(i) - '0';
            x = x * 10 + digit;
            sum += digit;
        }

        return x * sum;
    }
}
