import java.util.HashSet;
import java.util.Set;

class Solution {
    public boolean containsDuplicate(int[] nums) {
        Set<Integer> seen = new HashSet<>();
        for (int num : nums) {
            // If the element is already present, add() returns false
            if (!seen.add(num)) {
                return true;
            }
        }
        return false;
    }
}