import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;



// Node class representing Servers and Clients
class Node {
    int id;
    int x, y;

    public Node(int id, int x, int y) {
        this.id = id;
        this.x = x;
        this.y = y;
    }
}

// Edge class representing connections
class Edge implements Comparable<Edge> {
    Node source, destination;
    int cost, bandwidth;

    public Edge(Node source, Node destination, int cost, int bandwidth) {
        this.source = source;
        this.destination = destination;
        this.cost = cost;
        this.bandwidth = bandwidth;
    }

    public int compareTo(Edge other) {
        return this.cost - other.cost;
    }
}

// Graph class
class NetworkGraph {
    ArrayList<Node> nodes;
    ArrayList<Edge> edges;
    HashMap<Node, Node> parent;

    public NetworkGraph() {
        nodes = new ArrayList<>();
        edges = new ArrayList<>();
        parent = new HashMap<>();
    }

    public void addNode(int id, int x, int y) {
        Node node = new Node(id, x, y);
        nodes.add(node);
        parent.put(node, node);
    }

    public void addEdge(Node src, Node dest, int cost, int bandwidth) {
        edges.add(new Edge(src, dest, cost, bandwidth));
    }

    public List<Edge> kruskalMST() {
        List<Edge> result = new ArrayList<>();
        Collections.sort(edges);

        for (Node node : nodes) {
            parent.put(node, node);
        }

        for (Edge edge : edges) {
            Node root1 = find(edge.source);
            Node root2 = find(edge.destination);
            if (root1 != root2) {
                result.add(edge);
                parent.put(root1, root2);
            }
        }
        System.out.println("Edges in MST: " + result.size());
        return result;
    }

    private Node find(Node node) {
        if (parent.get(node) != node) {
            parent.put(node, find(parent.get(node)));
        }
        return parent.get(node);
    }
}

// GUI Class
class NetworkOptimizerGUI extends JFrame {
    NetworkGraph graph;
    JPanel panel;

    public NetworkOptimizerGUI() {
        graph = new NetworkGraph();
        setTitle("Network Optimizer");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                for (Edge edge : graph.edges) {
                    g.drawLine(edge.source.x, edge.source.y, edge.destination.x, edge.destination.y);
                    g.drawString("$" + edge.cost + ", BW: " + edge.bandwidth, 
                        (edge.source.x + edge.destination.x) / 2, 
                        (edge.source.y + edge.destination.y) / 2);
                }
                for (Node node : graph.nodes) {
                    g.fillOval(node.x - 5, node.y - 5, 10, 10);
                    g.drawString("N" + node.id, node.x, node.y - 10);
                }
            }
        };

        panel.setBackground(Color.WHITE);
        panel.setVisible(true);

        panel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int id = graph.nodes.size() + 1;
                graph.addNode(id, evt.getX(), evt.getY());
                repaint();
            }
        });

        JButton addEdgeButton = new JButton("Add Connection");
        addEdgeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (graph.nodes.size() < 2) {
                    JOptionPane.showMessageDialog(null, "Need at least 2 nodes to add a connection");
                    return;
                }
                String input = JOptionPane.showInputDialog("Enter srcID destID cost bandwidth (e.g. 1 2 10 100)");
                if (input != null) {
                    String[] parts = input.split(" ");
                    int srcID = Integer.parseInt(parts[0]) - 1;
                    int destID = Integer.parseInt(parts[1]) - 1;
                    int cost = Integer.parseInt(parts[2]);
                    int bandwidth = Integer.parseInt(parts[3]);
                    graph.addEdge(graph.nodes.get(srcID), graph.nodes.get(destID), cost, bandwidth);
                    repaint();
                }
            }
        });

        JButton optimizeButton = new JButton("Optimize");
        optimizeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                List<Edge> optimalEdges = graph.kruskalMST();
                graph.edges = new ArrayList<>(optimalEdges);
                repaint();
            }
        });

        JPanel controls = new JPanel();
        controls.add(addEdgeButton);
        controls.add(optimizeButton);

        add(panel, BorderLayout.CENTER);
        add(controls, BorderLayout.SOUTH);
    }
}

// Main Class
public class NetworkOptimizer {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new NetworkOptimizerGUI().setVisible(true));
    }
}
