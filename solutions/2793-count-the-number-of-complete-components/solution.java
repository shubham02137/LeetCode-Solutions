import java.util.*;

class Solution {
    public int countCompleteComponents(int n, int[][] edges) {

        List<Integer>[] graph = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }

        for (int[] edge : edges) {
            graph[edge[0]].add(edge[1]);
            graph[edge[1]].add(edge[0]);
        }

        boolean[] visited = new boolean[n];
        int complete = 0;

        for (int i = 0; i < n; i++) {
            if (!visited[i]) {

                int[] info = dfs(i, graph, visited);

                int vertices = info[0];
                int degreeSum = info[1];

                if (degreeSum == vertices * (vertices - 1)) {
                    complete++;
                }
            }
        }

        return complete;
    }

    private int[] dfs(int node, List<Integer>[] graph, boolean[] visited) {
        visited[node] = true;

        int vertices = 1;
        int degreeSum = graph[node].size();

        for (int nei : graph[node]) {
            if (!visited[nei]) {
                int[] res = dfs(nei, graph, visited);
                vertices += res[0];
                degreeSum += res[1];
            }
        }

        return new int[]{vertices, degreeSum};
    }
}
