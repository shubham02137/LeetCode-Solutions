class Solution {
    public int numberOfSubstrings(String s) {
        int[] last = {-1, -1, -1}; // a, b, c
        int count = 0;

        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);

            last[ch - 'a'] = i;

            int minIndex = Math.min(last[0], Math.min(last[1], last[2]));

            if (minIndex != -1) {
                count += minIndex + 1;
            }
        }

        return count;
    }
}
