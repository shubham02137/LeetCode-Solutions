import java.util.*;

class Solution {
    public int lengthOfLongestSubstring(String s) {
        
        HashSet<Character> set = new HashSet<>();
        
        int left = 0;
        int maxLen = 0;
        
        for (int right = 0; right < s.length(); right++) {
            
            char ch = s.charAt(right);
            
            // Remove duplicates
            while (set.contains(ch)) {
                set.remove(s.charAt(left));
                left++;
            }
            
            // Add current character
            set.add(ch);
            
            // Update answer
            maxLen = Math.max(maxLen, right - left + 1);
        }
        
        return maxLen;
    }
}
