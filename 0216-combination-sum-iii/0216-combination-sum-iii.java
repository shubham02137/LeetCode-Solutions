import java.util.ArrayList;
import java.util.List;

class Solution {
    public List<List<Integer>> combinationSum3(int k, int n) {
        List<List<Integer>> result = new ArrayList<>();
        backtrack(1, k, n, new ArrayList<>(), result);
        return result;
    }

    private void backtrack(int start, int k, int remainSum, List<Integer> current, List<List<Integer>> result) {
        // Base case: combination is of size k and sums to n
        if (current.size() == k) {
            if (remainSum == 0) {
                result.add(new ArrayList<>(current));
            }
            return;
        }

        for (int i = start; i <= 9; i++) {
            // Prune: if current number exceeds the remaining sum, stop the loop
            if (i > remainSum) {
                break;
            }

            current.add(i);
            // Move to the next number (i + 1) since each number can only be used once
            backtrack(i + 1, k, remainSum - i, current, result);
            current.remove(current.size() - 1); // Backtrack
        }
    }
}