 class Solution {
    public String shortestBeautifulSubstring(String s, int k) {
        int n = s.length();
        String ans = "";
        int left = 0;
        int count1 = 0;

        for (int right = 0; right < n; right++) {
            if (s.charAt(right) == '1') {
                count1++;
            }

            // Shrink from the left if we have excess 1's or leading 0's
            while (count1 > k || (left <= right && count1 == k && s.charAt(left) == '0')) {
                if (s.charAt(left) == '1') {
                    count1--;
                }
                left++;
            }

            // Valid candidate found
            if (count1 == k) {
                String candidate = s.substring(left, right + 1);
                if (ans.isEmpty() 
                    || candidate.length() < ans.length() 
                    || (candidate.length() == ans.length() && candidate.compareTo(ans) < 0)) {
                    ans = candidate;
                }
            }
        }

        return ans;
    }
}