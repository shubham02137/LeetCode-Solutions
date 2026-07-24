import java.util.*;

class Solution {

    private Set<String> dictionary;
    private Map<Integer, List<String>> memo;

    public List<String> wordBreak(String s, List<String> wordDict) {

        dictionary = new HashSet<>(wordDict);
        memo = new HashMap<>();

        return dfs(s, 0);
    }

    private List<String> dfs(String s, int start) {

        // Already computed
        if (memo.containsKey(start)) {
            return memo.get(start);
        }

        List<String> result = new ArrayList<>();

        // Reached end of string
        if (start == s.length()) {
            result.add("");
            return result;
        }

        for (int end = start + 1; end <= s.length(); end++) {

            String word = s.substring(start, end);

            // Current part must be a dictionary word
            if (!dictionary.contains(word)) {
                continue;
            }

            // Find all sentences from remaining string
            List<String> remaining = dfs(s, end);

            for (String sentence : remaining) {

                if (sentence.isEmpty()) {
                    result.add(word);
                } else {
                    result.add(word + " " + sentence);
                }
            }
        }

        memo.put(start, result);

        return result;
    }
}
