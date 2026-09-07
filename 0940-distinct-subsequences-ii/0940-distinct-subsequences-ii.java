class Solution {
    public int distinctSubseqII(String s) {
        int MOD = 1_000_000_007;
        // endWith[i] stores the number of distinct subsequences ending with char ('a' + i)
        long[] endWith = new long[26];
        long total = 0; // Total count of distinct subsequences formed so far

        for (char c : s.toCharArray()) {
            int idx = c - 'a';
            
            // New distinct subsequences ending with current char c:
            // Every existing subsequence can have 'c' appended, plus the subsequence "c" itself (+1).
            // That equals (total + 1) % MOD.
            long newEndingWithC = (total + 1) % MOD;
            
            // Update total:
            // Add new subsequences and remove previous ones ending with the same character to prevent duplicates.
            total = (total + newEndingWithC - endWith[idx] + MOD) % MOD;
            
            // Update the count of subsequences ending with character c
            endWith[idx] = newEndingWithC;
        }

        return (int) total;
    }
}