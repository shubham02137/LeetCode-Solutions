import java.util.*;

class Solution {
    public int minimumEffort(int[][] tasks) {

        // Sort by (minimum - actual) descending
        Arrays.sort(tasks, (a, b) ->
            (b[1] - b[0]) - (a[1] - a[0])
        );

        int initialEnergy = 0;
        int currentEnergy = 0;

        for (int[] task : tasks) {

            int actual = task[0];
            int minimum = task[1];

            // If current energy is insufficient,
            // increase initial energy
            if (currentEnergy < minimum) {
                initialEnergy += (minimum - currentEnergy);
                currentEnergy = minimum;
            }

            // Complete task
            currentEnergy -= actual;
        }

        return initialEnergy;
    }
}
