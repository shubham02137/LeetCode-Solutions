import java.util.*;

class Solution {

    private int[] seg;
    private int size;

    private int[] runStart;
    private int[] runEnd;
    private int[] runId;
    private char[] runChar;
    private int runCount;

    public List<Integer> maxActiveSectionsAfterTrade(
            String s, int[][] queries) {

        int n = s.length();

        // Total number of 1s in the ORIGINAL whole string.
        int totalOnes = 0;

        for (int i = 0; i < n; i++) {
            if (s.charAt(i) == '1') {
                totalOnes++;
            }
        }

        // ---------- Build Runs ----------

        runId = new int[n];

        ArrayList<Integer> starts = new ArrayList<>();
        ArrayList<Integer> ends = new ArrayList<>();
        ArrayList<Character> chars = new ArrayList<>();

        int id = 0;

        for (int i = 0; i < n; ) {

            int j = i;

            while (j + 1 < n &&
                   s.charAt(j + 1) == s.charAt(i)) {
                j++;
            }

            starts.add(i);
            ends.add(j);
            chars.add(s.charAt(i));

            for (int k = i; k <= j; k++) {
                runId[k] = id;
            }

            id++;
            i = j + 1;
        }

        runCount = id;

        runStart = new int[runCount];
        runEnd = new int[runCount];
        runChar = new char[runCount];

        for (int i = 0; i < runCount; i++) {
            runStart[i] = starts.get(i);
            runEnd[i] = ends.get(i);
            runChar[i] = chars.get(i);
        }

        /*
         * For a pattern:
         *
         * zero-run | one-run | zero-run
         *
         * trading the one-run gives:
         *
         * gain = leftZeroLength + rightZeroLength
         *
         * Store this gain at the ONE run.
         */
        int[] gain = new int[runCount];

        for (int i = 1; i + 1 < runCount; i++) {

            if (runChar[i] == '1' &&
                runChar[i - 1] == '0' &&
                runChar[i + 1] == '0') {

                int left =
                    runEnd[i - 1] - runStart[i - 1] + 1;

                int right =
                    runEnd[i + 1] - runStart[i + 1] + 1;

                gain[i] = left + right;
            }
        }

        build(gain);

        List<Integer> answer = new ArrayList<>();

        // ---------- Process Queries ----------

        for (int[] q : queries) {

            int l = q[0];
            int r = q[1];

            int leftRun = runId[l];
            int rightRun = runId[r];

            int bestGain = 0;

            /*
             * A valid candidate one-run must have:
             *
             * zero-run on left
             * zero-run on right
             *
             * Both zero parts must lie inside [l, r].
             */

            // Fully contained run triples.
            int firstFull = leftRun + 1;
            int lastFull = rightRun - 1;

            /*
             * If l itself is exactly the beginning of its run,
             * that run can be fully used.
             */
            if (runStart[leftRun] == l) {
                firstFull = leftRun;
            }

            /*
             * If r is exactly the end of its run,
             * that run can be fully used.
             */
            if (runEnd[rightRun] == r) {
                lastFull = rightRun;
            }

            /*
             * gain[i] needs BOTH neighboring zero runs fully
             * inside the query.
             *
             * Therefore i-1 and i+1 must be contained.
             */
            int from = firstFull + 1;
            int to = lastFull - 1;

            if (from <= to) {
                bestGain = Math.max(
                    bestGain,
                    rangeMax(from, to)
                );
            }

            // ---------- Left boundary truncated zero run ----------

            if (runChar[leftRun] == '0') {

                int oneRun = leftRun + 1;
                int rightZeroRun = oneRun + 1;

                if (rightZeroRun <= rightRun &&
                    oneRun < runCount &&
                    rightZeroRun < runCount &&
                    runChar[oneRun] == '1' &&
                    runChar[rightZeroRun] == '0') {

                    // One-run must be fully inside query
                    if (runEnd[oneRun] <= r) {

                        int leftZeros =
                            runEnd[leftRun] - l + 1;

                        int rightZeros;

                        if (rightZeroRun == rightRun) {
                            rightZeros =
                                r - runStart[rightZeroRun] + 1;
                        } else {
                            rightZeros =
                                runEnd[rightZeroRun]
                                - runStart[rightZeroRun] + 1;
                        }

                        if (leftZeros > 0 &&
                            rightZeros > 0) {

                            bestGain = Math.max(
                                bestGain,
                                leftZeros + rightZeros
                            );
                        }
                    }
                }
            }

            // ---------- Right boundary truncated zero run ----------

            if (runChar[rightRun] == '0') {

                int oneRun = rightRun - 1;
                int leftZeroRun = oneRun - 1;

                if (leftZeroRun >= leftRun &&
                    oneRun >= 0 &&
                    leftZeroRun >= 0 &&
                    runChar[oneRun] == '1' &&
                    runChar[leftZeroRun] == '0') {

                    // One-run must be fully inside query
                    if (runStart[oneRun] >= l) {

                        int rightZeros =
                            r - runStart[rightRun] + 1;

                        int leftZeros;

                        if (leftZeroRun == leftRun) {
                            leftZeros =
                                runEnd[leftZeroRun] - l + 1;
                        } else {
                            leftZeros =
                                runEnd[leftZeroRun]
                                - runStart[leftZeroRun] + 1;
                        }

                        if (leftZeros > 0 &&
                            rightZeros > 0) {

                            bestGain = Math.max(
                                bestGain,
                                leftZeros + rightZeros
                            );
                        }
                    }
                }
            }

            answer.add(totalOnes + bestGain);
        }

        return answer;
    }

    // ---------- Segment Tree ----------

    private void build(int[] arr) {

        size = 1;

        while (size < arr.length) {
            size <<= 1;
        }

        seg = new int[size * 2];

        for (int i = 0; i < arr.length; i++) {
            seg[size + i] = arr[i];
        }

        for (int i = size - 1; i >= 1; i--) {
            seg[i] = Math.max(
                seg[i * 2],
                seg[i * 2 + 1]
            );
        }
    }

    private int rangeMax(int left, int right) {

        if (left > right) {
            return 0;
        }

        left += size;
        right += size;

        int ans = 0;

        while (left <= right) {

            if ((left & 1) == 1) {
                ans = Math.max(ans, seg[left]);
                left++;
            }

            if ((right & 1) == 0) {
                ans = Math.max(ans, seg[right]);
                right--;
            }

            left >>= 1;
            right >>= 1;
        }

        return ans;
    }
}
