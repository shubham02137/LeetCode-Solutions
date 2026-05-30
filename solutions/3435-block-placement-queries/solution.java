import java.util.*;

class Solution {

    class Fenwick {
        int n;
        int[] bit;

        Fenwick(int n) {
            this.n = n;
            bit = new int[n + 2];
        }

        void update(int idx, int val) {
            for (int i = idx + 1; i <= n + 1; i += i & -i) {
                bit[i] = Math.max(bit[i], val);
            }
        }

        int query(int idx) {
            int res = 0;
            for (int i = idx + 1; i > 0; i -= i & -i) {
                res = Math.max(res, bit[i]);
            }
            return res;
        }
    }

    public List<Boolean> getResults(int[][] queries) {
        int maxX = 0;

        TreeSet<Integer> obstacles = new TreeSet<>();
        obstacles.add(0);

        for (int[] q : queries) {
            maxX = Math.max(maxX, q[1]);
            if (q[0] == 1) {
                obstacles.add(q[1]);
            }
        }

        Fenwick bit = new Fenwick(maxX + 2);

        int prev = 0;
        for (int pos : obstacles) {
            if (pos == 0) continue;
            bit.update(pos, pos - prev);
            prev = pos;
        }

        List<Boolean> ans = new ArrayList<>();

        for (int i = queries.length - 1; i >= 0; i--) {
            int[] q = queries[i];

            if (q[0] == 1) {
                int x = q[1];

                int left = obstacles.lower(x);
                Integer rightObj = obstacles.higher(x);

                if (rightObj != null) {
                    bit.update(rightObj, rightObj - left);
                }

                obstacles.remove(x);

            } else {
                int x = q[1];
                int sz = q[2];

                int left = obstacles.floor(x);

                int bestGap = Math.max(
                        bit.query(x),
                        x - left
                );

                ans.add(bestGap >= sz);
            }
        }

        Collections.reverse(ans);
        return ans;
    }
}
