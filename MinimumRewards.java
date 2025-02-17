import java.util.Arrays;

public class MinimumRewards {
    public static void main(String[] args) {
        // Example 1
        int[] ratings1 = {1, 0, 2};
        int totalRewards1 = calculateMinimumRewards(ratings1);
        System.out.println("Input: ratings = [1, 0, 2]");
        System.out.println("Output: Minimum rewards needed: " + totalRewards1);

        // Example 2
        int[] ratings2 = {1, 2, 2};
        int totalRewards2 = calculateMinimumRewards(ratings2);
        System.out.println("Input: ratings = [1, 2, 2]");
        System.out.println("Output: Minimum rewards needed: " + totalRewards2);
    }

    public static int calculateMinimumRewards(int[] ratings) {
        int[] rewards = new int[ratings.length];
        Arrays.fill(rewards, 1);

        // Left to Right pass
        for (int i = 1; i < ratings.length; i++) {
            if (ratings[i] > ratings[i - 1]) {
                rewards[i] = rewards[i - 1] + 1;
            }
        }

        // Right to Left pass
        for (int i = ratings.length - 2; i >= 0; i--) {
            if (ratings[i] > ratings[i + 1]) {
                rewards[i] = Math.max(rewards[i], rewards[i + 1] + 1);
            }
        }

        // Sum total rewards
        return Arrays.stream(rewards).sum();
    }
}
