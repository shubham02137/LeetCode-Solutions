import java.util.*;

class Solution {
    
    // Check prime
    private boolean isPrime(int x) {
        if (x < 2) return false;
        if (x == 2) return true;
        if (x % 2 == 0) return false;
        
        for (int i = 3; i * i <= x; i += 2) {
            if (x % i == 0) return false;
        }
        
        return true;
    }

    public int minJumps(int[] nums) {
        int n = nums.length;
        
        if (n == 1) return 0;

        // Map prime -> indices divisible by prime
        Map<Integer, List<Integer>> divisibleMap = new HashMap<>();

        // Build divisibility map
        for (int i = 0; i < n; i++) {
            int val = nums[i];
            int temp = val;

            for (int p = 2; p * p <= temp; p++) {
                if (temp % p == 0) {
                    divisibleMap
                        .computeIfAbsent(p, k -> new ArrayList<>())
                        .add(i);

                    while (temp % p == 0) {
                        temp /= p;
                    }
                }
            }

            if (temp > 1) {
                divisibleMap
                    .computeIfAbsent(temp, k -> new ArrayList<>())
                    .add(i);
            }
        }

        // BFS
        Queue<Integer> q = new LinkedList<>();
        boolean[] visited = new boolean[n];

        q.offer(0);
        visited[0] = true;

        int steps = 0;

        // To avoid reusing same prime teleport many times
        Set<Integer> usedPrime = new HashSet<>();

        while (!q.isEmpty()) {
            int size = q.size();

            for (int s = 0; s < size; s++) {
                int i = q.poll();

                if (i == n - 1) return steps;

                // Adjacent left
                if (i - 1 >= 0 && !visited[i - 1]) {
                    visited[i - 1] = true;
                    q.offer(i - 1);
                }

                // Adjacent right
                if (i + 1 < n && !visited[i + 1]) {
                    visited[i + 1] = true;
                    q.offer(i + 1);
                }

                // Prime teleportation
                int val = nums[i];

                if (isPrime(val) && !usedPrime.contains(val)) {
                    usedPrime.add(val);

                    List<Integer> next = divisibleMap.get(val);

                    if (next != null) {
                        for (int idx : next) {
                            if (!visited[idx]) {
                                visited[idx] = true;
                                q.offer(idx);
                            }
                        }
                    }
                }
            }

            steps++;
        }

        return -1;
    }
}
