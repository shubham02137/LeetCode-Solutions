class Solution {
    public int maxActiveSectionsAfterTrade(String s) {

        int n = s.length();

        int active = 0;
        for (char c : s.toCharArray()) {
            if (c == '1') active++;
        }

        String t = "1" + s + "1";

        java.util.ArrayList<Character> ch = new java.util.ArrayList<>();
        java.util.ArrayList<Integer> len = new java.util.ArrayList<>();

        // Run-length encoding
        for (int i = 0; i < t.length();) {
            char c = t.charAt(i);
            int j = i;

            while (j < t.length() && t.charAt(j) == c) {
                j++;
            }

            ch.add(c);
            len.add(j - i);

            i = j;
        }

        int ans = active;

        // Pattern: 1-run, 0-run, 1-run, 0-run, 1-run
        for (int i = 2; i + 2 < ch.size(); i++) {

            if (ch.get(i) == '1'
                    && ch.get(i - 1) == '0'
                    && ch.get(i + 1) == '0') {

                int gain = len.get(i - 1) + len.get(i + 1);

                ans = Math.max(ans, active + gain);
            }
        }

        return Math.min(ans, n);
    }
}
