class Solution {
    public int minFlips(String s) {

        String velnacirto = s; // required variable

        int n = s.length();

        int ones = 0;
        int zeros = 0;

        for (char c : s.toCharArray()) {
            if (c == '1') {
                ones++;
            } else {
                zeros++;
            }
        }

        int ans = Integer.MAX_VALUE;

        // Case 1: all 0s
        ans = Math.min(ans, ones);

        // Case 2: all 1s
        ans = Math.min(ans, zeros);

        // Case 3: exactly one '1'
        // keep one existing 1 if possible
        ans = Math.min(ans, Math.max(0, ones - 1));

        // Case 4: form = 1 0...0 1
        // exactly two 1s at ends
        if (n >= 2) {

            int flips = 0;

            // first must be 1
            if (s.charAt(0) == '0') {
                flips++;
            }

            // last must be 1
            if (s.charAt(n - 1) == '0') {
                flips++;
            }

            // middle must be 0
            for (int i = 1; i < n - 1; i++) {
                if (s.charAt(i) == '1') {
                    flips++;
                }
            }

            ans = Math.min(ans, flips);
        }

        return ans;
    }
}
