class Solution {
    private int count = 0;
    private boolean[] col;
    private boolean[] diag1;
    private boolean[] diag2;
    private int n;

    public int totalNQueens(int n) {
        this.n = n;
        col = new boolean[n];
        diag1 = new boolean[2 * n];
        diag2 = new boolean[2 * n];

        backtrack(0);
        return count;
    }

    private void backtrack(int row) {
        if (row == n) {
            count++;
            return;
        }

        for (int c = 0; c < n; c++) {
            int d1 = row + c;
            int d2 = row - c + n;

            if (col[c] || diag1[d1] || diag2[d2]) {
                continue;
            }

            col[c] = true;
            diag1[d1] = true;
            diag2[d2] = true;

            backtrack(row + 1);

            col[c] = false;
            diag1[d1] = false;
            diag2[d2] = false;
        }
    }
}
