public class CriticalTemperature {
    public static void main(String[] args) {
        int k = 2, n = 6; // Example values for k (samples) and n (temperature levels)
        int dp[][] = new int[k+1][n+1]; 

        // Fix the loop logic
        for(int m = 1; m <= n; m++) {
            for(int i = 1; i <= k; i++) {
                // Fix the dp array index usage by checking bounds
                if (m - 1 >= 0) {
                    dp[i][m] = dp[i - 1][m - 1] + dp[i][m - 1] + 1;
                }
            }
        }

        System.out.println("Minimum tests required: " + dp[k][n]);
    }
}
