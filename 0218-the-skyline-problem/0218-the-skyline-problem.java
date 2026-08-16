import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.TreeMap;

class Solution {
    public List<List<Integer>> getSkyline(int[][] buildings) {
        List<int[]> events = new ArrayList<>();
        
        // Break buildings into left (start) and right (end) events
        for (int[] b : buildings) {
            // Use negative height for start event to sort taller buildings first
            events.add(new int[]{b[0], -b[2]});
            // Positive height for end event
            events.add(new int[]{b[1], b[2]});
        }
        
        // Sort events:
        // 1. By x-coordinate ascending
        // 2. If x is same:
        //    - Start vs Start: taller first (-height ascending)
        //    - End vs End: shorter first (height ascending)
        //    - Start vs End: start comes before end
        Collections.sort(events, (a, b) -> {
            if (a[0] != b[0]) {
                return Integer.compare(a[0], b[0]);
            }
            return Integer.compare(a[1], b[1]);
        });
        
        List<List<Integer>> result = new ArrayList<>();
        // TreeMap to track active heights and their frequencies
        TreeMap<Integer, Integer> heightMap = new TreeMap<>();
        heightMap.put(0, 1); // Ground level
        
        int prevMaxHeight = 0;
        
        for (int[] event : events) {
            int x = event[0];
            int h = event[1];
            
            if (h < 0) {
                // Building start: add height to map
                heightMap.put(-h, heightMap.getOrDefault(-h, 0) + 1);
            } else {
                // Building end: remove height from map
                int count = heightMap.get(h);
                if (count == 1) {
                    heightMap.remove(h);
                } else {
                    heightMap.put(h, count - 1);
                }
            }
            
            int currentMaxHeight = heightMap.lastKey();
            // If the maximum height changed, record a key point
            if (currentMaxHeight != prevMaxHeight) {
                result.add(Arrays.asList(x, currentMaxHeight));
                prevMaxHeight = currentMaxHeight;
            }
        }
        
        return result;
    }
}