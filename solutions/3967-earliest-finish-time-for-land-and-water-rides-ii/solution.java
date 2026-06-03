class Solution {

    private static final int MAX = 200000;
    private static final int INF = 1_000_000_000;

    private long query(int t, int[] prefMinDur, int[] suffMinOpenDur) {
        long ans = Long.MAX_VALUE;

        if (prefMinDur[t] != INF) {
            ans = Math.min(ans, (long) t + prefMinDur[t]);
        }

        if (t + 1 <= MAX && suffMinOpenDur[t + 1] != INF) {
            ans = Math.min(ans, (long) suffMinOpenDur[t + 1]);
        }

        return ans;
    }

    public int earliestFinishTime(int[] landStartTime, int[] landDuration,
                                  int[] waterStartTime, int[] waterDuration) {

        // Water preprocessing
        int[] waterDurAtStart = new int[MAX + 2];
        int[] waterOpenDurAtStart = new int[MAX + 2];

        java.util.Arrays.fill(waterDurAtStart, INF);
        java.util.Arrays.fill(waterOpenDurAtStart, INF);

        for (int i = 0; i < waterStartTime.length; i++) {
            int s = waterStartTime[i];
            int d = waterDuration[i];

            waterDurAtStart[s] =
                    Math.min(waterDurAtStart[s], d);

            waterOpenDurAtStart[s] =
                    Math.min(waterOpenDurAtStart[s], s + d);
        }

        int[] waterPrefMinDur = new int[MAX + 2];
        int[] waterSuffMinOpenDur = new int[MAX + 3];

        waterPrefMinDur[0] = INF;
        for (int t = 1; t <= MAX; t++) {
            waterPrefMinDur[t] =
                    Math.min(waterPrefMinDur[t - 1],
                             waterDurAtStart[t]);
        }

        waterSuffMinOpenDur[MAX + 1] = INF;
        for (int t = MAX; t >= 1; t--) {
            waterSuffMinOpenDur[t] =
                    Math.min(waterSuffMinOpenDur[t + 1],
                             waterOpenDurAtStart[t]);
        }

        // Land preprocessing
        int[] landDurAtStart = new int[MAX + 2];
        int[] landOpenDurAtStart = new int[MAX + 2];

        java.util.Arrays.fill(landDurAtStart, INF);
        java.util.Arrays.fill(landOpenDurAtStart, INF);

        for (int i = 0; i < landStartTime.length; i++) {
            int s = landStartTime[i];
            int d = landDuration[i];

            landDurAtStart[s] =
                    Math.min(landDurAtStart[s], d);

            landOpenDurAtStart[s] =
                    Math.min(landOpenDurAtStart[s], s + d);
        }

        int[] landPrefMinDur = new int[MAX + 2];
        int[] landSuffMinOpenDur = new int[MAX + 3];

        landPrefMinDur[0] = INF;
        for (int t = 1; t <= MAX; t++) {
            landPrefMinDur[t] =
                    Math.min(landPrefMinDur[t - 1],
                             landDurAtStart[t]);
        }

        landSuffMinOpenDur[MAX + 1] = INF;
        for (int t = MAX; t >= 1; t--) {
            landSuffMinOpenDur[t] =
                    Math.min(landSuffMinOpenDur[t + 1],
                             landOpenDurAtStart[t]);
        }

        long answer = Long.MAX_VALUE;

        // Land -> Water
        for (int i = 0; i < landStartTime.length; i++) {
            int finishLand = landStartTime[i] + landDuration[i];

            answer = Math.min(
                    answer,
                    query(finishLand,
                          waterPrefMinDur,
                          waterSuffMinOpenDur)
            );
        }

        // Water -> Land
        for (int j = 0; j < waterStartTime.length; j++) {
            int finishWater = waterStartTime[j] + waterDuration[j];

            answer = Math.min(
                    answer,
                    query(finishWater,
                          landPrefMinDur,
                          landSuffMinOpenDur)
            );
        }

        return (int) answer;
    }
}
