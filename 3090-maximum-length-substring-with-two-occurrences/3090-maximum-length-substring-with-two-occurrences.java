class Solution {
    public int maximumLengthSubstring(String s) {
        int[] freq = new int[26];
        int left = 0;
        int maxLen = 0;

        for (int right = 0; right < s.length(); right++) {
            // Expand window by adding character at 'right'
            freq[s.charAt(right) - 'a']++;

            // Shrink window from 'left' if any character count exceeds 2
            while (freq[s.charAt(right) - 'a'] > 2) {
                freq[s.charAt(left) - 'a']--;
                left++;
            }

            // Update maximum valid substring length found so far
            maxLen = Math.max(maxLen, right - left + 1);
        }

        return maxLen;
    }
}