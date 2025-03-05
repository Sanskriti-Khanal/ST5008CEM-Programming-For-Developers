class Edge implements Comparable<Edge> {
    Node source, destination;
    int cost, bandwidth;

    public Edge(Node source, Node destination, int cost, int bandwidth) {
        this.source = source;
        this.destination = destination;
        this.cost = cost;
        this.bandwidth = bandwidth;
    }

    @Override
    public int compareTo(Edge other) {
        return Integer.compare(this.cost, other.cost);
    }
}
