class Solution {
    public String lexGreaterPermutation(String s, String target) {
        int n = s.length();
        int[] totalCount = new int[26];
        for (char c : s.toCharArray()) {
            totalCount[c - 'a']++;
        }

        // Determine the maximum length of prefix of `target` that can be formed by `s`
        int[] prefixCount = new int[26];
        int maxPrefix = 0;
        while (maxPrefix < n) {
            int idx = target.charAt(maxPrefix) - 'a';
            if (prefixCount[idx] + 1 <= totalCount[idx]) {
                prefixCount[idx]++;
                maxPrefix++;
            } else {
                break;
            }
        }

        // Try placing a character strictly greater than target[i] at position i,
        // starting from the longest possible prefix match down to 0.
        for (int i = maxPrefix; i >= 0; i--) {
            if (i == n) {
                // If the entire target is matched, we backtrack to find a bump
                prefixCount[target.charAt(n - 1) - 'a']--;
                continue;
            }

            int targetCharIdx = target.charAt(i) - 'a';
            int bumpCharIdx = -1;

            // Find the smallest available character strictly greater than target[i]
            for (int c = targetCharIdx + 1; c < 26; c++) {
                if (totalCount[c] - prefixCount[c] > 0) {
                    bumpCharIdx = c;
                    break;
                }
            }

            if (bumpCharIdx != -1) {
                StringBuilder result = new StringBuilder();
                result.append(target, 0, i);
                result.append((char) ('a' + bumpCharIdx));

                // Decrement counts for characters used so far
                int[] remaining = new int[26];
                for (int c = 0; c < 26; c++) {
                    remaining[c] = totalCount[c] - prefixCount[c];
                }
                remaining[bumpCharIdx]--;

                // Append the remaining characters in ascending lexicographical order
                for (int c = 0; c < 26; c++) {
                    while (remaining[c] > 0) {
                        result.append((char) ('a' + c));
                        remaining[c]--;
                    }
                }

                return result.toString();
            }

            // Backtrack one character from prefixCount for the next iteration
            if (i > 0) {
                prefixCount[target.charAt(i - 1) - 'a']--;
            }
        }

        return "";
    }
}