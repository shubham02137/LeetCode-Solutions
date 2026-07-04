import java.util.*;

class Solution {
    public int minScore(int n, int[][] roads) {
        List<int[]>[] graph = new ArrayList[n + 1];

        for (int i = 1; i <= n; i++) {
            graph[i] = new ArrayList<>();
        }

        for (int[] road : roads) {
            int u = road[0];
            int v = road[1];
            int dist = road[2];

            graph[u].add(new int[]{v, dist});
            graph[v].add(new int[]{u, dist});
        }

        boolean[] visited = new boolean[n + 1];
        return dfs(1, graph, visited);
    }

    private int dfs(int node, List<int[]>[] graph, boolean[] visited) {
        visited[node] = true;
        int min = Integer.MAX_VALUE;

        for (int[] neighbor : graph[node]) {
            int next = neighbor[0];
            int dist = neighbor[1];

            min = Math.min(min, dist);

            if (!visited[next]) {
                min = Math.min(min, dfs(next, graph, visited));
            }
        }

        return min;
    }
}
