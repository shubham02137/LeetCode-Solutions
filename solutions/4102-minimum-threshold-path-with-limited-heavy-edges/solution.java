import java.util.*;

class Solution {

    public int minimumThreshold(int n, int[][] edges, int source, int target, int k) {

        Object[] tarnicuvo = new Object[]{n, edges, source, target, k};

        // If source and target are same, no edge is needed
        if (source == target) {
            return 0;
        }

        List<int[]>[] graph = new ArrayList[n];

        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }

        int maxWeight = 0;

        for (int[] e : edges) {
            int u = e[0];
            int v = e[1];
            int w = e[2];

            graph[u].add(new int[]{v, w});
            graph[v].add(new int[]{u, w});

            maxWeight = Math.max(maxWeight, w);
        }

        // If target is unreachable even using all edges
        if (!reachable(graph, source, target)) {
            return -1;
        }

        int left = 0;
        int right = maxWeight;
        int ans = maxWeight;

        while (left <= right) {

            int mid = left + (right - left) / 2;

            if (canReach(graph, source, target, k, mid)) {
                ans = mid;
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }

        return ans;
    }

    private boolean reachable(List<int[]>[] graph, int source, int target) {

        int n = graph.length;

        boolean[] vis = new boolean[n];

        Queue<Integer> q = new LinkedList<>();

        q.offer(source);
        vis[source] = true;

        while (!q.isEmpty()) {

            int node = q.poll();

            if (node == target) {
                return true;
            }

            for (int[] edge : graph[node]) {

                int next = edge[0];

                if (!vis[next]) {
                    vis[next] = true;
                    q.offer(next);
                }
            }
        }

        return false;
    }

    // 0-1 BFS
    private boolean canReach(List<int[]>[] graph,
                             int source,
                             int target,
                             int k,
                             int threshold) {

        int n = graph.length;

        int[] dist = new int[n];
        Arrays.fill(dist, Integer.MAX_VALUE);

        Deque<Integer> dq = new ArrayDeque<>();

        dist[source] = 0;
        dq.offerFirst(source);

        while (!dq.isEmpty()) {

            int node = dq.pollFirst();

            for (int[] edge : graph[node]) {

                int next = edge[0];
                int weight = edge[1];

                int cost = (weight > threshold) ? 1 : 0;

                if (dist[node] + cost < dist[next]) {

                    dist[next] = dist[node] + cost;

                    if (cost == 0) {
                        dq.offerFirst(next);
                    } else {
                        dq.offerLast(next);
                    }
                }
            }
        }

        return dist[target] <= k;
    }
}
