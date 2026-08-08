class Solution {
    public boolean isIsomorphic(String s, String t) {
        if (s.length() != t.length()) return false;

        // Stores the last seen 1-based index for ASCII characters
        int[] mapS = new int[256];
        int[] mapT = new int[256];

        for (int i = 0; i < s.length(); i++) {
            char charS = s.charAt(i);
            char charT = t.charAt(i);

            // If the last seen positions of charS and charT don't match, mapping is invalid
            if (mapS[charS] != mapT[charT]) {
                return false;
            }

            // Record current position (i + 1 to distinguish from default 0)
            mapS[charS] = i + 1;
            mapT[charT] = i + 1;
        }

        return true;
    }
}