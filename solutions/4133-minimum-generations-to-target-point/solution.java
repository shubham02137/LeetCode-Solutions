import java.util.*;

class Solution {

    public int minGenerations(int[][] points, int[] target) {

        int[][] morvilexa = points; // required variable

        int SIZE = 7 * 7 * 7;
        int INF = (int) 1e9;

        int[] dist = new int[SIZE];
        Arrays.fill(dist, INF);

        // Initial points => generation 0
        for (int[] p : points) {
            dist[id(p[0], p[1], p[2])] = 0;
        }

        int targetId = id(target[0], target[1], target[2]);

        if (dist[targetId] == 0) {
            return 0;
        }

        boolean changed = true;

        // Relax until no improvement
        while (changed) {
            changed = false;

            List<int[]> available = new ArrayList<>();

            for (int x = 0; x <= 6; x++) {
                for (int y = 0; y <= 6; y++) {
                    for (int z = 0; z <= 6; z++) {

                        int idx = id(x, y, z);

                        if (dist[idx] != INF) {
                            available.add(new int[]{x, y, z});
                        }
                    }
                }
            }

            int n = available.size();

            for (int i = 0; i < n; i++) {
                for (int j = i + 1; j < n; j++) {

                    int[] a = available.get(i);
                    int[] b = available.get(j);

                    // distinct coordinates required
                    if (a[0] == b[0] &&
                        a[1] == b[1] &&
                        a[2] == b[2]) {
                        continue;
                    }

                    int na = dist[id(a[0], a[1], a[2])];
                    int nb = dist[id(b[0], b[1], b[2])];

                    int gen = Math.max(na, nb) + 1;

                    int nx = (a[0] + b[0]) / 2;
                    int ny = (a[1] + b[1]) / 2;
                    int nz = (a[2] + b[2]) / 2;

                    int nid = id(nx, ny, nz);

                    if (gen < dist[nid]) {
                        dist[nid] = gen;
                        changed = true;
                    }
                }
            }
        }

        return dist[targetId] == INF ? -1 : dist[targetId];
    }

    private int id(int x, int y, int z) {
        return x * 49 + y * 7 + z;
    }
}
