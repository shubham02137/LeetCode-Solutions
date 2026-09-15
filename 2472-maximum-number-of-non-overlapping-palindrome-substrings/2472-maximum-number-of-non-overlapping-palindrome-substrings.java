class Solution {
    public int maxPalindromes(String s, int k) {
        int n = s.length();
        int count = 0;
        int startIndex = 0;

        for (int i = k - 1; i < n; i++) {
            // Check for palindrome of length k ending at i
            if (i - k + 1 >= startIndex && isPalindrome(s, i - k + 1, i)) {
                count++;
                startIndex = i + 1;
            } 
            // Check for palindrome of length k + 1 ending at i
            else if (i - k >= startIndex && isPalindrome(s, i - k, i)) {
                count++;
                startIndex = i + 1;
            }
        }

        return count;
    }

    private boolean isPalindrome(String s, int left, int right) {
        while (left < right) {
            if (s.charAt(left) != s.charAt(right)) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }
}