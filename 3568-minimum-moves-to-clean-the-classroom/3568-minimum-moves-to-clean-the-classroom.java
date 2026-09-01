import java.util.*;

class Solution {
    public int minMoves(String[] classroom, int energy) {
        int m = classroom.length;
        int n = classroom[0].length();
        
        int sr = -1, sc = -1;
        int litterCount = 0;
        int[][] litterId = new int[m][n];
        for (int[] row : litterId) {
            Arrays.fill(row, -1);
        }
        
        for (int r = 0; r < m; r++) {
            for (int c = 0; c < n; c++) {
                char ch = classroom[r].charAt(c);
                if (ch == 'S') {
                    sr = r;
                    sc = c;
                } else if (ch == 'L') {
                    litterId[r][c] = litterCount++;
                }
            }
        }
        
        // If there is no litter to collect
        if (litterCount == 0) {
            return 0;
        }
        
        int targetMask = (1 << litterCount) - 1;
        
        // bestEnergy[r][c][mask] stores the maximum remaining energy seen at state (r, c, mask)
        int[][][] bestEnergy = new int[m][n][1 << litterCount];
        for (int[][] row2D : bestEnergy) {
            for (int[] row1D : row2D) {
                Arrays.fill(row1D, -1);
            }
        }
        
        // Queue stores [r, c, mask, current_energy]
        ArrayDeque<int[]> queue = new ArrayDeque<>();
        queue.offer(new int[]{sr, sc, 0, energy});
        bestEnergy[sr][sc][0] = energy;
        
        int steps = 0;
        int[][] dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        
        while (!queue.isEmpty()) {
            int size = queue.size();
            steps++;
            
            for (int i = 0; i < size; i++) {
                int[] curr = queue.poll();
                int r = curr[0];
                int c = curr[1];
                int mask = curr[2];
                int e = curr[3];
                
                // If the state has already been reached with strictly better energy, skip
                if (e < bestEnergy[r][c][mask]) {
                    continue;
                }
                
                // If out of energy and not standing on a reset area, cannot move further
                if (e == 0) {
                    continue;
                }
                
                for (int[] d : dirs) {
                    int nr = r + d[0];
                    int nc = c + d[1];
                    
                    if (nr < 0 || nr >= m || nc < 0 || nc >= n) continue;
                    char cell = classroom[nr].charAt(nc);
                    if (cell == 'X') continue;
                    
                    int nextMask = mask;
                    if (cell == 'L') {
                        nextMask |= (1 << litterId[nr][nc]);
                    }
                    
                    // Reaching all litter items
                    if (nextMask == targetMask) {
                        return steps;
                    }
                    
                    int nextEnergy = (cell == 'R') ? energy : (e - 1);
                    
                    if (nextEnergy > bestEnergy[nr][nc][nextMask]) {
                        bestEnergy[nr][nc][nextMask] = nextEnergy;
                        queue.offer(new int[]{nr, nc, nextMask, nextEnergy});
                    }
                }
            }
        }
        
        return -1;
    }
}