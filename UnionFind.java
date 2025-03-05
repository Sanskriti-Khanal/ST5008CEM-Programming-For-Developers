import java.util.HashMap;
import java.util.Map;

public class UnionFind {
    private final Map<String, String> parent = new HashMap<>();

    public void add(String node) {
        parent.putIfAbsent(node, node);
    }

    public String find(String node) {
        if (!parent.containsKey(node)) return null;
        if (!parent.get(node).equals(node)) {
            parent.put(node, find(parent.get(node))); // Path compression
        }
        return parent.get(node);
    }

    public void union(String node1, String node2) {
        String root1 = find(node1);
        String root2 = find(node2);
        if (root1 != null && root2 != null && !root1.equals(root2)) {
            parent.put(root1, root2);
        }
    }
}
