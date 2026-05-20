class Solution {
    public int[] findThePrefixCommonArray(int[] A, int[] B) {
        int n = A.length;
        int[] result = new int[n];
        
        boolean[] seenA = new boolean[n + 1];
        boolean[] seenB = new boolean[n + 1];
        
        int common = 0;
        
        for (int i = 0; i < n; i++) {
            
            // Mark current elements as seen
            seenA[A[i]] = true;
            seenB[B[i]] = true;
            
            // If A[i] already appeared in B
            if (seenB[A[i]]) {
                common++;
            }
            
            // If B[i] already appeared in A
            // Avoid double count when A[i] == B[i]
            if (A[i] != B[i] && seenA[B[i]]) {
                common++;
            }
            
            result[i] = common;
        }
        
        return result;
    }
}
