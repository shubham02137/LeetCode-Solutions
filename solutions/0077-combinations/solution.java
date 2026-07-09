class Solution {

    List<List<Integer>> ans = new ArrayList<>();

    public List<List<Integer>> combine(int n, int k) {
        backtrack(1, n, k, new ArrayList<>());
        return ans;
    }

    private void backtrack(int start, int n, int k, List<Integer> curr) {

        // Base case
        if (curr.size() == k) {
            ans.add(new ArrayList<>(curr));
            return;
        }

        // Try every possible number
        for (int i = start; i <= n; i++) {
            curr.add(i);                  // Choose
            backtrack(i + 1, n, k, curr); // Explore
            curr.remove(curr.size() - 1); // Backtrack
        }
    }
}
