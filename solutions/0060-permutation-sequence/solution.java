import java.util.*;

class Solution {
    public String getPermutation(int n, int k) {

        List<Integer> numbers = new ArrayList<>();
        int factorial = 1;

        // Build list of numbers and compute (n-1)!
        for (int i = 1; i < n; i++) {
            factorial *= i;
            numbers.add(i);
        }
        numbers.add(n);

        k--; // Convert to 0-based index

        StringBuilder ans = new StringBuilder();

        while (true) {

            int index = k / factorial;
            ans.append(numbers.get(index));

            numbers.remove(index);

            if (numbers.isEmpty())
                break;

            k %= factorial;
            factorial /= numbers.size();
        }

        return ans.toString();
    }
}
