import java.util.Arrays;

class Solution {
    public int[] validSequence(String word1, String word2) {
        int n = word1.length();
        int m = word2.length();

        // lastExact[j] = maximum index in word1 to match word2[j...m-1] with 0 mismatches
        int[] lastExact = new int[m + 1];
        // lastOne[j] = maximum index in word1 to match word2[j...m-1] with AT MOST 1 mismatch
        int[] lastOne = new int[m + 1];

        Arrays.fill(lastExact, -1);
        Arrays.fill(lastOne, -1);

        lastExact[m] = n;
        lastOne[m] = n;

        // Step 1: Compute lastExact from right to left
        int idx = n - 1;
        for (int j = m - 1; j >= 0; j--) {
            while (idx >= 0 && word1.charAt(idx) != word2.charAt(j)) {
                idx--;
            }
            if (idx >= 0) {
                lastExact[j] = idx;
                idx--;
            }
        }

        // Step 2: Compute lastOne from right to left
        for (int j = m - 1; j >= 0; j--) {
            // Option 1: Use mismatch at position j
            int posMismatch = lastExact[j + 1] - 1;

            // Option 2: Match word2[j] exactly at the rightmost valid index < lastOne[j + 1]
            int posExact = -1;
            int limit = lastOne[j + 1] - 1;
            if (limit >= 0) {
                // Find the largest index <= limit where word1[index] == word2[j]
                for (int k = limit; k >= 0; k--) {
                    if (word1.charAt(k) == word2.charAt(j)) {
                        posExact = k;
                        break;
                    }
                }
            }

            lastOne[j] = Math.max(posMismatch, posExact);
        }

        // Step 3: Build the lexicographically smallest index sequence greedily
        int[] result = new int[m];
        boolean usedMismatch = false;
        int i = 0; // Pointer in word1

        for (int j = 0; j < m; j++) {
            boolean matched = false;

            while (i < n) {
                boolean isMatch = (word1.charAt(i) == word2.charAt(j));

                if (usedMismatch) {
                    // If mismatch was already used, must match exactly and finish with 0 mismatches
                    if (isMatch && i + 1 <= lastExact[j + 1]) {
                        result[j] = i;
                        i++;
                        matched = true;
                        break;
                    }
                } else {
                    if (isMatch) {
                        // Option A: Exact match. Check if remaining word2[j+1...m-1] can be matched with <= 1 mismatch
                        if (i + 1 <= lastOne[j + 1]) {
                            result[j] = i;
                            i++;
                            matched = true;
                            break;
                        }
                    } else {
                        // Option B: Use mismatch here. Remaining word2[j+1...m-1] MUST be matched with 0 mismatches
                        if (i + 1 <= lastExact[j + 1]) {
                            result[j] = i;
                            usedMismatch = true;
                            i++;
                            matched = true;
                            break;
                        }
                    }
                }
                i++;
            }

            if (!matched) {
                return new int[0];
            }
        }

        return result;
    }
}