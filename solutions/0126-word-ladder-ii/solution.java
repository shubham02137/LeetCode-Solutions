import java.util.*;

class Solution {

    public List<List<String>> findLadders(String beginWord, String endWord, List<String> wordList) {

        Set<String> dict = new HashSet<>(wordList);
        List<List<String>> result = new ArrayList<>();

        if (!dict.contains(endWord)) {
            return result;
        }

        Map<String, List<String>> parents = new HashMap<>();
        Map<String, Integer> level = new HashMap<>();

        Queue<String> queue = new LinkedList<>();
        queue.offer(beginWord);
        level.put(beginWord, 0);

        int wordLen = beginWord.length();

        while (!queue.isEmpty()) {

            String word = queue.poll();
            int currLevel = level.get(word);

            char[] chars = word.toCharArray();

            for (int i = 0; i < wordLen; i++) {

                char original = chars[i];

                for (char c = 'a'; c <= 'z'; c++) {

                    if (c == original) {
                        continue;
                    }

                    chars[i] = c;
                    String next = new String(chars);

                    if (!dict.contains(next)) {
                        continue;
                    }

                    if (!level.containsKey(next)) {

                        level.put(next, currLevel + 1);
                        queue.offer(next);

                        parents.put(next, new ArrayList<>());
                        parents.get(next).add(word);

                    } else if (level.get(next) == currLevel + 1) {

                        parents.get(next).add(word);
                    }
                }

                chars[i] = original;
            }
        }

        if (!level.containsKey(endWord)) {
            return result;
        }

        LinkedList<String> path = new LinkedList<>();
        path.add(endWord);

        dfs(endWord, beginWord, parents, path, result);

        return result;
    }

    private void dfs(String word,
                     String beginWord,
                     Map<String, List<String>> parents,
                     LinkedList<String> path,
                     List<List<String>> result) {

        if (word.equals(beginWord)) {

            List<String> sequence = new ArrayList<>(path);
            Collections.reverse(sequence);
            result.add(sequence);

            return;
        }

        if (!parents.containsKey(word)) {
            return;
        }

        for (String parent : parents.get(word)) {

            path.addLast(parent);

            dfs(parent, beginWord, parents, path, result);

            path.removeLast();
        }
    }
}
