import java.util.*;

class Solution {

    public List<String> restoreIpAddresses(String s) {
        List<String> ans = new ArrayList<>();
        backtrack(s, 0, 0, new ArrayList<>(), ans);
        return ans;
    }

    private void backtrack(String s, int index, int parts,
                           List<String> path, List<String> ans) {

        // If 4 parts are formed
        if (parts == 4) {
            if (index == s.length()) {
                ans.add(String.join(".", path));
            }
            return;
        }

        // Try segment lengths: 1, 2, or 3
        for (int len = 1; len <= 3; len++) {

            if (index + len > s.length())
                break;

            String segment = s.substring(index, index + len);

            // Leading zero check
            if (segment.length() > 1 && segment.charAt(0) == '0')
                continue;

            int value = Integer.parseInt(segment);

            // Value must be <= 255
            if (value > 255)
                continue;

            path.add(segment);
            backtrack(s, index + len, parts + 1, path, ans);
            path.remove(path.size() - 1);
        }
    }
}
