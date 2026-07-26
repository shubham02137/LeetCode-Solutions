import java.util.*;

class Solution {

    public int maxPoints(int[][] points) {

        int n = points.length;

        if (n <= 2) {
            return n;
        }

        int answer = 2;

        for (int i = 0; i < n; i++) {

            HashMap<String, Integer> map = new HashMap<>();
            int currentMax = 0;

            for (int j = i + 1; j < n; j++) {

                int dx = points[j][0] - points[i][0];
                int dy = points[j][1] - points[i][1];

                int gcd = gcd(Math.abs(dx), Math.abs(dy));

                dx /= gcd;
                dy /= gcd;

                // Normalize direction
                if (dx < 0) {
                    dx = -dx;
                    dy = -dy;
                }

                // Vertical line
                if (dx == 0) {
                    dy = 1;
                }

                // Horizontal line
                if (dy == 0) {
                    dx = 1;
                }

                String slope = dy + "/" + dx;

                int count = map.getOrDefault(slope, 0) + 1;
                map.put(slope, count);

                currentMax = Math.max(currentMax, count);
            }

            // +1 for points[i] itself
            answer = Math.max(answer, currentMax + 1);
        }

        return answer;
    }

    private int gcd(int a, int b) {

        while (b != 0) {
            int temp = a % b;
            a = b;
            b = temp;
        }

        return a;
    }
}