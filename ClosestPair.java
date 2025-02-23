import java.util.Arrays;

public class ClosestPair {
    public static void main(String[] args) {
        int[] x = {1, 2, 3, 2, 4}; 
        int[] y = {2, 3, 1, 2, 3};
        int[] ans = {0, 1};  // Initial closest pair indices
        
        // Initializing the best distance with the distance between the first two points
        int best = Math.abs(x[0] - x[1]) + Math.abs(y[0] - y[1]);
        
        // Loop to find the closest pair
        for (int i = 0; i < x.length; i++) {
            for (int j = i + 1; j < x.length; j++) {
                int d = Math.abs(x[i] - x[j]) + Math.abs(y[i] - y[j]); // Calculate the Manhattan distance
                // Check if we have found a better (or lexicographically smaller) pair
                if (d < best || (d == best && (i < ans[0] || (i == ans[0] && j < ans[1])))) {
                    best = d;
                    ans = new int[]{i, j};  // Update the answer with the new pair
                }
            }
        }
        
        // Output the closest pair indices
        System.out.println(Arrays.toString(ans));
    }
}
