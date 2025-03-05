public class ClosestPair {

    public static int[] findClosestPair(int[] x_coords, int[] y_coords) {
        // Initialize variables
        int minDistance = Integer.MAX_VALUE;
        int bestI = -1, bestJ = -1;
        int n = x_coords.length; // Number of points

        // Nested loops to iterate through all pairs (i, j) where i < j
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                // Calculate the distance using Manhattan distance formula
                int distance = Math.abs(x_coords[i] - x_coords[j]) + Math.abs(y_coords[i] - y_coords[j]);

                // Check if the current pair has a smaller distance
                if (distance < minDistance) {
                    minDistance = distance;
                    bestI = i;
                    bestJ = j;
                }
                // If distance is equal, check for lexicographical order
                else if (distance == minDistance) {
                    if (i < bestI || (i == bestI && j < bestJ)) {
                        bestI = i;
                        bestJ = j;
                    }
                }
            }
        }

        // Return the indices of the closest pair
        return new int[]{bestI, bestJ};
    }

    public static void main(String[] args) {
        // Example input
        int[] x_coords = {1, 2, 3, 4};
        int[] y_coords = {2, 3, 1, 5};

        // Find the closest pair
        int[] result = findClosestPair(x_coords, y_coords);

        // Output the result
        System.out.println("Closest Pair Indices: [" + result[0] + ", " + result[1] + "]");
    }
}