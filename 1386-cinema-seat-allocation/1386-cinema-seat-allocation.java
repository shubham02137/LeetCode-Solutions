class Solution {
    public int maxNumberOfFamilies(int n, int[][] reservedSeats) {
        Map<Integer, Integer> reservedRows = new HashMap<>();

        // Group reserved seats per row using bitmasks
        for (int[] seat : reservedSeats) {
            int row = seat[0];
            int col = seat[1];
            // Seats 1 and 10 do not affect 4-person group allocations
            if (col >= 2 && col <= 9) {
                reservedRows.put(row, reservedRows.getOrDefault(row, 0) | (1 << col));
            }
        }

        // Each completely empty row can accommodate 2 four-person groups
        int ans = (n - reservedRows.size()) * 2;

        // Bitmasks for valid 4-seat blocks:
        // Left block (seats 2, 3, 4, 5): (1<<2) | (1<<3) | (1<<4) | (1<<5) = 60
        // Right block (seats 6, 7, 8, 9): (1<<6) | (1<<7) | (1<<8) | (1<<9) = 960
        // Middle block (seats 4, 5, 6, 7): (1<<4) | (1<<5) | (1<<6) | (1<<7) = 240
        int leftMask = 60;
        int rightMask = 960;
        int middleMask = 240;

        for (int mask : reservedRows.values()) {
            boolean left = (mask & leftMask) == 0;
            boolean right = (mask & rightMask) == 0;
            boolean middle = (mask & middleMask) == 0;

            if (left && right) {
                ans += 2;
            } else if (left || right || middle) {
                ans += 1;
            }
        }

        return ans;
    }
}