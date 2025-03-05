public class CriticalTemperature{

    // Function to find the minimum trials to find the critical temperature
    public static int findCriticalThreshold(int k, int n) {
        // Create a DP table with k+1 rows and n+1 columns
        int[][] dp = new int[k + 1][n + 1];

        // Base case initialization: If we have only 1 material, we need to try all temperatures
        for (int i = 1; i <= n; i++) {
            dp[1][i] = i;  // With 1 material, we need i trials for i temperatures
        }

        // Fill the DP table for more materials
        for (int i = 2; i <= k; i++) {
            for (int j = 1; j <= n; j++) {
                int minTrials = Integer.MAX_VALUE;
                
                // Test all possible temperature levels for the current material
                for (int x = 1; x <= j; x++) {
                    // Scenario 1: Material does not change properties
                    int value1 = dp[i][j - x];  // Same number of materials, reduced temperatures
                    // Scenario 2: Material changes properties (loses 1 sample)
                    int value2 = dp[i - 1][x - 1];  // One less material, reduced temperatures
                    
                    // Calculate the worst-case scenario (take the maximum of the two)
                    int worstCase = Math.max(value1, value2);

                    // Take the minimum of all possible trials (to minimize the number of trials)
                    minTrials = Math.min(minTrials, worstCase);
                }

                // Update the DP table with the worst-case trials plus the current test
                dp[i][j] = minTrials + 1;
            }
        }

        // The answer is the value at dp[k][n], which is the minimum trials required
        return dp[k][n];
    }

    public static void main(String[] args) {
        // Example: 3 materials and 5 temperature levels
        int k = 3;  // Number of materials
        int n = 5;  // Number of temperature levels

        int result = findCriticalThreshold(k, n);
        System.out.println("Minimum number of trials required: " + result);
    }
}
