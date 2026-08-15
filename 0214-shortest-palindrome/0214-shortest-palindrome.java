class Solution {
    public String shortestPalindrome(String s) {
        if (s == null || s.length() <= 1) {
            return s;
        }

        String rev = new StringBuilder(s).reverse().toString();
        String combined = s + "#" + rev;

        // Compute the KMP prefix table (LPS array)
        int[] lps = new int[combined.length()];
        for (int i = 1; i < combined.length(); i++) {
            int j = lps[i - 1];
            while (j > 0 && combined.charAt(i) != combined.charAt(j)) {
                j = lps[j - 1];
            }
            if (combined.charAt(i) == combined.charAt(j)) {
                j++;
            }
            lps[i] = j;
        }

        // Length of the longest palindromic prefix in s
        int longestPalPrefixLen = lps[combined.length() - 1];

        // Suffix that needs to be reversed and added to the front
        String suffixToAdd = s.substring(longestPalPrefixLen);
        String prefix = new StringBuilder(suffixToAdd).reverse().toString();

        return prefix + s;
    }
}
