import java.util.Arrays;

public class MinimumRewards {
    public static void main(String[] args) {
        // Define ratings array
        int[] ratings = {1, 0, 2}; // Example input

        // Step 1: Initialize rewards array
        int[] rewards = new int[ratings.length];
        Arrays.fill(rewards, 1);

        // Step 2: Left to Right pass
        for (int i = 1; i < ratings.length; i++) {
            if (ratings[i] > ratings[i - 1]) {
                rewards[i] = rewards[i - 1] + 1;
            }
        }

        // Step 3: Right to Left pass
        for (int i = ratings.length - 2; i >= 0; i--) {
            if (ratings[i] > ratings[i + 1]) {
                rewards[i] = Math.max(rewards[i], rewards[i + 1] + 1);
            }
        }

        // Step 4: Calculate total rewards
        int totalRewards = Arrays.stream(rewards).sum();

        // Output the result
        System.out.println("Minimum rewards needed: " + totalRewards);
    }
}
