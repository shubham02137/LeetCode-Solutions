class Solution {
    public boolean isPalindrome(int x) {

        // Negative numbers are not palindrome
        // Numbers ending with 0 are also not palindrome
        // except 0 itself
        if (x < 0 || (x % 10 == 0 && x != 0)) {
            return false;
        }

        int reversed = 0;

        // Reverse only half of the number
        while (x > reversed) {

            int digit = x % 10;

            reversed = reversed * 10 + digit;

            x = x / 10;
        }

        // For even digits: x == reversed
        // For odd digits: x == reversed / 10
        return x == reversed || x == reversed / 10;
    }
}
