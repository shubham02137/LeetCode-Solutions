class Solution {
    private long limit;

    public String smallestPalindrome(String s, int k) {
        int n = s.length();
        int[] freq = new int[26];

        for (char c : s.toCharArray()) {
            freq[c - 'a']++;
        }

        // Character counts for the first half
        int[] cnt = new int[26];
        int halfLen = n / 2;
        char middle = 0;

        for (int i = 0; i < 26; i++) {
            cnt[i] = freq[i] / 2;

            if ((freq[i] & 1) == 1) {
                middle = (char) ('a' + i);
            }
        }

        limit = k;

        // Check whether at least k permutations exist
        if (countPermutations(cnt, halfLen) < k) {
            return "";
        }

        StringBuilder left = new StringBuilder();
        long rank = k;

        // Construct kth lexicographically smallest first half
        for (int pos = 0; pos < halfLen; pos++) {

            for (int c = 0; c < 26; c++) {
                if (cnt[c] == 0) {
                    continue;
                }

                // Try placing this character
                cnt[c]--;

                long ways = countPermutations(
                    cnt,
                    halfLen - pos - 1
                );

                if (rank > ways) {
                    // kth permutation is not in this group
                    rank -= ways;
                    cnt[c]++;
                } else {
                    // This character belongs here
                    left.append((char) ('a' + c));
                    break;
                }
            }
        }

        StringBuilder ans = new StringBuilder();

        ans.append(left);

        if ((n & 1) == 1) {
            ans.append(middle);
        }

        ans.append(new StringBuilder(left).reverse());

        return ans.toString();
    }

    // Number of distinct permutations, capped at limit
    private long countPermutations(int[] cnt, int total) {
        long ways = 1;
        int used = 0;

        for (int x : cnt) {
            if (x == 0) {
                continue;
            }

            for (int j = 1; j <= x; j++) {
                ways = multiplyCombination(ways, used + j, j);

                if (ways >= limit) {
                    return limit;
                }
            }

            used += x;
        }

        return Math.min(ways, limit);
    }

    private long multiplyCombination(long current, int numerator, int denominator) {
        long g = gcd(numerator, denominator);

        numerator /= g;
        denominator /= g;

        long g2 = gcd(current, denominator);

        current /= g2;
        denominator /= g2;

        if (current > limit / numerator) {
            return limit;
        }

        current *= numerator;

        if (denominator > 1) {
            current /= denominator;
        }

        return Math.min(current, limit);
    }

    private long gcd(long a, long b) {
        while (b != 0) {
            long temp = a % b;
            a = b;
            b = temp;
        }

        return a;
    }
}