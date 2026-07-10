import java.util.*;

class Solution {
    public int[] pathExistenceQueries(int n, int[] nums, int maxDiff, int[][] queries) {
        int[][] arr = new int[n][2];
        for (int i = 0; i < n; i++) {
            arr[i][0] = nums[i];
            arr[i][1] = i;
        }

        Arrays.sort(arr, (a, b) -> {
            if (a[0] != b[0]) return Integer.compare(a[0], b[0]);
            return Integer.compare(a[1], b[1]);
        });

        int[] pos = new int[n];
        int[] comp = new int[n];

        int cid = 0;
        for (int i = 0; i < n; i++) {
            pos[arr[i][1]] = i;
            if (i > 0 && arr[i][0] - arr[i - 1][0] > maxDiff) {
                cid++;
            }
            comp[i] = cid;
        }

        int[] next = new int[n];
        int r = 0;
        for (int i = 0; i < n; i++) {
            while (r + 1 < n && arr[r + 1][0] - arr[i][0] <= maxDiff) {
                r++;
            }
            next[i] = r;
        }

        int LOG = 18;
        int[][] up = new int[LOG][n];

        for (int i = 0; i < n; i++) {
            up[0][i] = next[i];
        }

        for (int k = 1; k < LOG; k++) {
            for (int i = 0; i < n; i++) {
                up[k][i] = up[k - 1][up[k - 1][i]];
            }
        }

        int[] ans = new int[queries.length];

        for (int q = 0; q < queries.length; q++) {
            int u = queries[q][0];
            int v = queries[q][1];

            if (u == v) {
                ans[q] = 0;
                continue;
            }

            int a = pos[u];
            int b = pos[v];

            if (a > b) {
                int temp = a;
                a = b;
                b = temp;
            }

            if (comp[a] != comp[b]) {
                ans[q] = -1;
                continue;
            }

            int cur = a;
            int steps = 0;

            for (int k = LOG - 1; k >= 0; k--) {
                int nxt = up[k][cur];
                if (nxt < b) {
                    cur = nxt;
                    steps += 1 << k;
                }
            }

            ans[q] = steps + 1;
        }

        return ans;
    }
}
