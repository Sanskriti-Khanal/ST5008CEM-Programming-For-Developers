import java.util.*;

class Edge implements Comparable<Edge> {
    int u, v, cost;

    public Edge(int u, int v, int cost) {
        this.u = u;
        this.v = v;
        this.cost = cost;
    }

    @Override
    public int compareTo(Edge other) {
        return this.cost - other.cost;
    }
}

class DSU {
    int[] parent;
    int[] rank;

    public DSU(int n) {
        parent = new int[n];
        rank = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
            rank[i] = 1;
        }
    }

    public int find(int x) {
        if (parent[x] != x) {
            parent[x] = find(parent[x]);
        }
        return parent[x];
    }

    public boolean union(int x, int y) {
        int rootX = find(x);
        int rootY = find(y);
        if (rootX == rootY) {
            return false; // Already connected
        }
        if (rank[rootX] > rank[rootY]) {
            parent[rootY] = rootX;
        } else if (rank[rootX] < rank[rootY]) {
            parent[rootX] = rootY;
        } else {
            parent[rootY] = rootX;
            rank[rootX]++;
        }
        return true;
    }
}

public class MinCostNetwork {
    public static int minCostToConnect(int n, int[] modules, int[][] connections) {
        List<Edge> edges = new ArrayList<>();

        // Add edges between virtual node (0) and each device
        for (int i = 1; i <= n; i++) {
            edges.add(new Edge(0, i, modules[i - 1]));
        }

        // Add edges for connections between devices
        for (int[] connection : connections) {
            int u = connection[0];
            int v = connection[1];
            int cost = connection[2];
            edges.add(new Edge(u, v, cost));
        }

        // Sort edges by cost in ascending order
        Collections.sort(edges);

        // Initialize DSU
        DSU dsu = new DSU(n + 1); // n devices + virtual node (0)

        int totalCost = 0;
        int edgesAdded = 0;

        // Build MST using Kruskal's algorithm
        for (Edge edge : edges) {
            if (dsu.union(edge.u, edge.v)) {
                totalCost += edge.cost;
                edgesAdded++;
                if (edgesAdded == n) { // All devices are connected
                    break;
                }
            }
        }

        return totalCost;
    }

    public static void main(String[] args) {
        int n = 4;
        int[] modules = {2, 3, 1, 4};
        int[][] connections = {
            {1, 2, 1},
            {2, 3, 2},
            {3, 4, 3},
            {1, 4, 4}
        };

        int result = minCostToConnect(n, modules, connections);
        System.out.println("Minimum total cost to connect all devices: " + result);
    }
}