import java.util.*;

public class PackageCollection {
    public static int minRoadTraversals(int n, int[] packages, int[][] roads) {
        // Step 1: Identify package nodes
        List<Integer> packageNodes = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if (packages[i] == 1) {
                packageNodes.add(i);
            }
        }
        int p = packageNodes.size(); // Number of package nodes

        // Step 2: Build adjacency list
        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            adj.add(new ArrayList<>());
        }
        for (int[] road : roads) {
            adj.get(road[0]).add(road[1]);
            adj.get(road[1]).add(road[0]);
        }

        // Step 3: Precompute collection masks for each node
        int[] collectMask = new int[n];
        for (int i = 0; i < n; i++) {
            int mask = 0;
            // Collect packages from the current node
            if (packages[i] == 1) {
                int idx = packageNodes.indexOf(i);
                mask |= (1 << idx);
            }
            // Collect packages from neighbors (distance 1)
            for (int neighbor : adj.get(i)) {
                if (packages[neighbor] == 1) {
                    int idx = packageNodes.indexOf(neighbor);
                    mask |= (1 << idx);
                }
                // Collect packages from neighbors-of-neighbors (distance 2)
                for (int neighbor2 : adj.get(neighbor)) {
                    if (packages[neighbor2] == 1) {
                        int idx = packageNodes.indexOf(neighbor2);
                        mask |= (1 << idx);
                    }
                }
            }
            collectMask[i] = mask;
        }

        // Step 4: Perform BFS for each possible start node
        int minCost = Integer.MAX_VALUE;
        for (int start = 0; start < n; start++) {
            // Initialize BFS
            Queue<int[]> queue = new LinkedList<>(); // [node, collectedMask, cost]
            Map<String, Integer> dist = new HashMap<>(); // State -> cost
            String initialState = start + "," + collectMask[start];
            queue.offer(new int[]{start, collectMask[start], 0});
            dist.put(initialState, 0);

            while (!queue.isEmpty()) {
                int[] current = queue.poll();
                int u = current[0], mask = current[1], costSoFar = current[2];

                // Step 6: Collect packages from radius-2 neighborhood
                int newMask = mask | collectMask[u];

                // Check if all packages are collected and we are back at the start
                if (newMask == (1 << p) - 1 && u == start) {
                    minCost = Math.min(minCost, costSoFar);
                    break;
                }

                // Explore neighbors
                for (int v : adj.get(u)) {
                    String nextState = v + "," + newMask;
                    int nextCost = costSoFar + 1;
                    if (!dist.containsKey(nextState) || dist.get(nextState) > nextCost) {
                        dist.put(nextState, nextCost);
                        queue.offer(new int[]{v, newMask, nextCost});
                    }
                }
            }
        }

        // Step 7: Return the minimum cost
        return minCost == Integer.MAX_VALUE ? -1 : minCost;
    }

    public static void main(String[] args) {
        int n = 5;
        int[] packages = {1, 0, 1, 0, 1}; // Example package nodes
        int[][] roads = {{0, 1}, {1, 2}, {2, 3}, {3, 4}, {4, 0}}; // Example roads
        int result = minRoadTraversals(n, packages, roads);
        System.out.println("Minimum road traversals: " + result);
    }
}