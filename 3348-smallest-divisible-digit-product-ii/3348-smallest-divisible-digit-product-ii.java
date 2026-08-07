import java.util.*;

class Solution {
    public String smallestNumber(String num, long t) {
        // Step 1: Factorize t into prime factors 2, 3, 5, 7
        long tempT = t;
        int e2 = 0, e3 = 0, e5 = 0, e7 = 0;
        
        while (tempT % 2 == 0) { e2++; tempT /= 2; }
        while (tempT % 3 == 0) { e3++; tempT /= 3; }
        while (tempT % 5 == 0) { e5++; tempT /= 5; }
        while (tempT % 7 == 0) { e7++; tempT /= 7; }
        
        // If t has prime factors other than 2, 3, 5, 7, it's impossible
        if (tempT > 1) return "-1";

        int n = num.length();

        // Check if num itself is valid
        int zeroIdx = num.indexOf('0');
        if (zeroIdx == -1) {
            int c2 = 0, c3 = 0, c5 = 0, c7 = 0;
            for (int i = 0; i < n; i++) {
                int d = num.charAt(i) - '0';
                c2 += countFactor(d, 2);
                c3 += countFactor(d, 3);
                c5 += countFactor(d, 5);
                c7 += countFactor(d, 7);
            }
            if (c2 >= e2 && c3 >= e3 && c5 >= e5 && c7 >= e7) {
                return num;
            }
        }

        int maxPrefix = (zeroIdx == -1) ? n - 1 : zeroIdx;

        // Compute prefix prime counts up to maxPrefix
        int[] pref2 = new int[n + 1];
        int[] pref3 = new int[n + 1];
        int[] pref5 = new int[n + 1];
        int[] pref7 = new int[n + 1];

        for (int i = 0; i <= maxPrefix; i++) {
            int d = num.charAt(i) - '0';
            pref2[i + 1] = pref2[i] + countFactor(d, 2);
            pref3[i + 1] = pref3[i] + countFactor(d, 3);
            pref5[i + 1] = pref5[i] + countFactor(d, 5);
            pref7[i + 1] = pref7[i] + countFactor(d, 7);
        }

        // Try prefixes of length i from maxPrefix down to 0
        for (int i = maxPrefix; i >= 0; i--) {
            int rem2 = Math.max(0, e2 - pref2[i]);
            int rem3 = Math.max(0, e3 - pref3[i]);
            int rem5 = Math.max(0, e5 - pref5[i]);
            int rem7 = Math.max(0, e7 - pref7[i]);

            int startDigit = num.charAt(i) - '0' + 1;
            for (int d = startDigit; d <= 9; d++) {
                int r2 = Math.max(0, rem2 - countFactor(d, 2));
                int r3 = Math.max(0, rem3 - countFactor(d, 3));
                int r5 = Math.max(0, rem5 - countFactor(d, 5));
                int r7 = Math.max(0, rem7 - countFactor(d, 7));

                String bestSuffix = getBestSuffix(r2, r3, r5, r7);
                int lenNeeded = bestSuffix.length();
                int lenAvailable = n - 1 - i;

                if (lenNeeded <= lenAvailable) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(num, 0, i);
                    sb.append(d);

                    int ones = lenAvailable - lenNeeded;
                    sb.append("1".repeat(ones));

                    sb.append(bestSuffix);
                    return sb.toString();
                }
            }
        }

        // If no number of length n works, construct smallest number of longer length
        String bestSuffix = getBestSuffix(e2, e3, e5, e7);
        int lenNeeded = bestSuffix.length();
        int totalLen = Math.max(n + 1, lenNeeded);

        StringBuilder sb = new StringBuilder();
        sb.append("1".repeat(totalLen - lenNeeded));
        sb.append(bestSuffix);
        return sb.toString();
    }

    private int countFactor(int n, int p) {
        if (n == 0) return 0;
        int count = 0;
        while (n % p == 0) {
            count++;
            n /= p;
        }
        return count;
    }

    // Finds the lexicographically smallest string of digits to fulfill r2, r3, r5, r7
    private String getBestSuffix(int r2, int r3, int r5, int r7) {
        String best = null;

        // Iterate over count of 9s and 8s to test all non-greedy combinations of 2s and 3s
        for (int c9 = r3 / 2; c9 >= 0; c9--) {
            int rem3 = r3 - c9 * 2;
            for (int c8 = r2 / 3; c8 >= 0; c8--) {
                int rem2 = r2 - c8 * 3;

                // For the remaining rem2 and rem3, determine digits 6, 4, 3, 2 needed
                int c6 = Math.min(rem2, rem3);
                int left2 = rem2 - c6;
                int left3 = rem3 - c6;

                int c4 = left2 / 2;
                int c2 = left2 % 2;
                int c3 = left3;

                StringBuilder sb = new StringBuilder();
                sb.append("2".repeat(c2));
                sb.append("3".repeat(c3));
                sb.append("4".repeat(c4));
                sb.append("5".repeat(r5));
                sb.append("6".repeat(c6));
                sb.append("7".repeat(r7));
                sb.append("8".repeat(c8));
                sb.append("9".repeat(c9));

                String candidate = sb.toString();
                if (best == null || candidate.length() < best.length() || 
                   (candidate.length() == best.length() && candidate.compareTo(best) < 0)) {
                    best = candidate;
                }
            }
        }

        return best;
    }
}