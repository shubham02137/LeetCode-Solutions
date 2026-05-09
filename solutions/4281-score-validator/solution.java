class Solution {
    public int[] scoreValidator(String[] events) {
        int score = 0;
        int counter = 0;

        for (String event : events) {
            // Stop if counter becomes 10
            if (counter == 10) {
                break;
            }

            switch (event) {
                case "W":
                    counter++;
                    break;

                case "WD":
                case "NB":
                    score += 1;
                    break;

                default:
                    // "0", "1", "2", "3", "4", "6"
                    score += Integer.parseInt(event);
            }
        }

        return new int[]{score, counter};
    }
}
