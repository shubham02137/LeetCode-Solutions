class Solution {

    List<List<Integer>> ans = new ArrayList<>();

    public List<List<Integer>> subsets(int[] nums) {
        backtrack(0, nums, new ArrayList<>());
        return ans;
    }

    private void backtrack(int start, int[] nums, List<Integer> curr) {

        // Every current subset is valid
        ans.add(new ArrayList<>(curr));

        for (int i = start; i < nums.length; i++) {
            curr.add(nums[i]);                 // Choose
            backtrack(i + 1, nums, curr);      // Explore
            curr.remove(curr.size() - 1);      // Backtrack
        }
    }
}
