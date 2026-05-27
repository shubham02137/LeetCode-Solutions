class Solution {
    public int numberOfSpecialChars(String word) {
        int[] lastLower = new int[26];
        int[] firstUpper = new int[26];
        
        // Initialize
        for (int i = 0; i < 26; i++) {
            lastLower[i] = -1;
            firstUpper[i] = Integer.MAX_VALUE;
        }

        // Store last position of lowercase letters
        // and first position of uppercase letters
        for (int i = 0; i < word.length(); i++) {
            char ch = word.charAt(i);

            if (Character.isLowerCase(ch)) {
                lastLower[ch - 'a'] = i;
            } else {
                firstUpper[ch - 'A'] = Math.min(firstUpper[ch - 'A'], i);
            }
        }

        int count = 0;

        // Check special characters
        for (int i = 0; i < 26; i++) {
            if (lastLower[i] != -1 &&
                firstUpper[i] != Integer.MAX_VALUE &&
                lastLower[i] < firstUpper[i]) {
                count++;
            }
        }

        return count;
    }
}
