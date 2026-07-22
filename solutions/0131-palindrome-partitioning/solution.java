import java.util.*;

class Solution {

    public List<List<String>> partition(String s) {

        List<List<String>> result = new ArrayList<>();
        List<String> current = new ArrayList<>();

        int n = s.length();

        // palindrome[i][j] = true if s[i...j] is palindrome
        boolean[][] palindrome = new boolean[n][n];

        // Precompute all palindrome substrings
        for (int i = n - 1; i >= 0; i--) {

            for (int j = i; j < n; j++) {

                if (s.charAt(i) == s.charAt(j) &&
                    (j - i <= 2 || palindrome[i + 1][j - 1])) {

                    palindrome[i][j] = true;
                }
            }
        }

        backtrack(0, s, palindrome, current, result);

        return result;
    }

    private void backtrack(
            int start,
            String s,
            boolean[][] palindrome,
            List<String> current,
            List<List<String>> result) {

        // Entire string has been partitioned
        if (start == s.length()) {
            result.add(new ArrayList<>(current));
            return;
        }

        for (int end = start; end < s.length(); end++) {

            // Only choose palindrome substrings
            if (palindrome[start][end]) {

                current.add(s.substring(start, end + 1));

                backtrack(
                    end + 1,
                    s,
                    palindrome,
                    current,
                    result
                );

                // Backtrack
                current.remove(current.size() - 1);
            }
        }
    }
}
