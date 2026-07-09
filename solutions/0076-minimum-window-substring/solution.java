class Solution {
    public String minWindow(String s, String t) {

        if (s.length() < t.length()) return "";

        int[] need = new int[128];

        // Store frequency of characters in t
        for (char c : t.toCharArray()) {
            need[c]++;
        }

        int left = 0;
        int count = t.length();

        int minLen = Integer.MAX_VALUE;
        int start = 0;

        for (int right = 0; right < s.length(); right++) {

            char ch = s.charAt(right);

            if (need[ch] > 0) {
                count--;
            }

            need[ch]--;

            // Window contains all characters
            while (count == 0) {

                if (right - left + 1 < minLen) {
                    minLen = right - left + 1;
                    start = left;
                }

                char leftChar = s.charAt(left);

                need[leftChar]++;

                if (need[leftChar] > 0) {
                    count++;
                }

                left++;
            }
        }

        return minLen == Integer.MAX_VALUE
                ? ""
                : s.substring(start, start + minLen);
    }
}
