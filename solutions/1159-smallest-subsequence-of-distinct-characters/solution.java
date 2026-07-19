class Solution {
    public String smallestSubsequence(String s) {

        int[] lastIndex = new int[26];
        boolean[] used = new boolean[26];

        // Store last occurrence of each character
        for (int i = 0; i < s.length(); i++) {
            lastIndex[s.charAt(i) - 'a'] = i;
        }

        StringBuilder stack = new StringBuilder();

        for (int i = 0; i < s.length(); i++) {

            char ch = s.charAt(i);
            int index = ch - 'a';

            // Character already included
            if (used[index]) {
                continue;
            }

            // Remove larger characters if they
            // appear again later
            while (stack.length() > 0) {

                char last = stack.charAt(stack.length() - 1);

                if (last > ch &&
                    lastIndex[last - 'a'] > i) {

                    used[last - 'a'] = false;
                    stack.deleteCharAt(stack.length() - 1);

                } else {
                    break;
                }
            }

            stack.append(ch);
            used[index] = true;
        }

        return stack.toString();
    }
}
