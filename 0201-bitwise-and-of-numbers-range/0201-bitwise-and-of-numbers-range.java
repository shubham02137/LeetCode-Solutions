class Solution {
    public int rangeBitwiseAnd(int left, int right) {
        int shift = 0;
        // Keep shifting right until both numbers match (finding common prefix)
        while (left < right) {
            left >>= 1;
            right >>= 1;
            shift++;
        }
        // Shift left back to pad trailing zeros
        return left << shift;
    }
}