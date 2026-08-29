import java.util.*;

class Solution {
    public int[] lexicographicallySmallestArray(int[] nums, int limit) {
        int n = nums.length;
        
        // 1. Create a sorted copy of the array
        int[] sorted = nums.clone();
        Arrays.sort(sorted);
        
        // 2. Group adjacent elements whose difference is <= limit
        Map<Integer, Integer> numToGroup = new HashMap<>();
        List<Queue<Integer>> groupToList = new ArrayList<>();
        
        int groupIndex = 0;
        groupToList.add(new ArrayDeque<>());
        groupToList.get(0).offer(sorted[0]);
        numToGroup.put(sorted[0], 0);
        
        for (int i = 1; i < n; i++) {
            if (sorted[i] - sorted[i - 1] > limit) {
                groupIndex++;
                groupToList.add(new ArrayDeque<>());
            }
            groupToList.get(groupIndex).offer(sorted[i]);
            numToGroup.put(sorted[i], groupIndex);
        }
        
        // 3. Reconstruct the array by picking the smallest available element for each group
        int[] result = new int[n];
        for (int i = 0; i < n; i++) {
            int group = numToGroup.get(nums[i]);
            result[i] = groupToList.get(group).poll();
        }
        
        return result;
    }
}