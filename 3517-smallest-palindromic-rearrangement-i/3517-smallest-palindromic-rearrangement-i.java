class Solution {
    public String smallestPalindrome(String s) {
        int[] freq = new int[26];

        // Count frequency of each character
        for (char ch : s.toCharArray()) {
            freq[ch - 'a']++;
        }

        StringBuilder firstHalf = new StringBuilder();
        char middle = 0;

        // Build lexicographically smallest first half
        for (int i = 0; i < 26; i++) {
            int half = freq[i] / 2;

            for (int j = 0; j < half; j++) {
                firstHalf.append((char) ('a' + i));
            }

            // A palindrome can have at most one odd frequency
            if (freq[i] % 2 == 1) {
                middle = (char) ('a' + i);
            }
        }

        StringBuilder result = new StringBuilder();

        result.append(firstHalf);

        if (middle != 0) {
            result.append(middle);
        }

        result.append(new StringBuilder(firstHalf).reverse());

        return result.toString();
    }
}