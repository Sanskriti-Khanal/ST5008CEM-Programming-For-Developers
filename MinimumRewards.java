public class MinimumRewards {

    public static int MinRewards(int[] ratings) {
        // Validate the ratings array
        if (ratings == null || ratings.length == 0) {
            return 0;
        }
        if (ratings.length == 1) {
            return 1;
        }

        int n = ratings.length;
        int[] rewards = new int[n];

        // Initialize all rewards to 1 (each employee gets at least 1 reward)
        for (int i = 0; i < n; i++) {
            rewards[i] = 1;
        }

        // Left-to-right traversal
        for (int i = 1; i < n; i++) {
            if (ratings[i] > ratings[i - 1]) {
                rewards[i] = rewards[i - 1] + 1;
            }
        }

        // Right-to-left traversal
        for (int i = n - 2; i >= 0; i--) {
            if (ratings[i] > ratings[i + 1]) {
                rewards[i] = Math.max(rewards[i], rewards[i + 1] + 1);
            }
        }

        // Calculate the total minimum rewards
        int totalRewards = 0;
        for (int reward : rewards) {
            totalRewards += reward;
        }

        return totalRewards;
    }

    public static void main(String[] args) {
        int[] ratings = {3, 6, 2, 1, 4, 5}; // Example input
        int result =  MinRewards(ratings);
        System.out.println("Total Minimum Rewards: " + result); // Output: 10
    }
}