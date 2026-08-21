class Solution {
    public int countDigitOne(int n) {
        int count = 0;
        
        // Iterate through each digit place (1s, 10s, 100s, ...)
        for (long i = 1; i <= n; i *= 10) {
            long divider = i * 10;
            // Full sets of numbers contributing to the current position
            count += (n / divider) * i;
            // Partial set contributing to the current position
            count += Math.min(Math.max(n % divider - i + 1, 0L), i);
        }
        
        return count;
    }
}