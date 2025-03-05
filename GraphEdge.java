// Represents an edge between two nodes
public class GraphEdge implements Comparable<GraphEdge> {
    GraphNode src, dest;
    double cost, bandwidth;
    double weight;

    public GraphEdge(GraphNode src, GraphNode dest, double cost, double bandwidth) {
        this.src = src;
        this.dest = dest;
        this.cost = cost;
        this.bandwidth = bandwidth;
        updateWeight(1.0, 1.0); // Default α=1, β=1
    }

    public void updateWeight(double alpha, double beta) {
        final double LATENCY_FACTOR = 1000.0;
        this.weight = alpha * cost + beta * (LATENCY_FACTOR / bandwidth);
    }

    @Override
    public int compareTo(GraphEdge other) {
        return Double.compare(this.weight, other.weight);
    }
}
