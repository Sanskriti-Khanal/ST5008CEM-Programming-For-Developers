import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class NetworkTopology extends JFrame {
    private final WebCrawler graph = new WebCrawler();
    private final DrawingPanel drawingPanel = new DrawingPanel(graph);
    private final DrawingPanel optimizedPanel = new DrawingPanel(new WebCrawler());  // Panel for MST
    private final JComboBox<Node> srcNodeBox = new JComboBox<>();
    private final JComboBox<Node> destNodeBox = new JComboBox<>();

    public NetworkTopology() {
        setTitle("Network Topology Designer");
        setSize(1000, 500);  // Increased width to accommodate two panels
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new GridLayout(8, 2, 5, 5));

        JTextField nodeNameField = new JTextField();
        JButton addNodeBtn = new JButton("Add Node");
        JTextField costField = new JTextField();
        JTextField bandwidthField = new JTextField();
        JButton addEdgeBtn = new JButton("Add Edge");
        JButton shortestPathBtn = new JButton("Find Shortest Path");
        JButton optimizeBtn = new JButton("Optimize Network");

        leftPanel.add(new JLabel("Node Name:"));
        leftPanel.add(nodeNameField);
        leftPanel.add(addNodeBtn);
        leftPanel.add(new JLabel("Source:"));
        leftPanel.add(srcNodeBox);
        leftPanel.add(new JLabel("Destination:"));
        leftPanel.add(destNodeBox);
        leftPanel.add(new JLabel("Cost:"));
        leftPanel.add(costField);
        leftPanel.add(new JLabel("Bandwidth:"));
        leftPanel.add(bandwidthField);
        leftPanel.add(addEdgeBtn);
        leftPanel.add(shortestPathBtn);
        leftPanel.add(optimizeBtn);

        add(leftPanel, BorderLayout.WEST);
        JPanel centerPanel = new JPanel(new GridLayout(1, 2));  // Two panels side by side
        centerPanel.add(drawingPanel);  // Left panel (original network)
        centerPanel.add(optimizedPanel);  // Right panel (optimized network)
        add(centerPanel, BorderLayout.CENTER);

        addNodeBtn.addActionListener(e -> {
            String name = nodeNameField.getText().trim();
            if (!name.isEmpty()) {
                int x = new Random().nextInt(500) + 50;
                int y = new Random().nextInt(300) + 50;
                Node node = new Node(name, x, y);
                graph.addNode(node);
                srcNodeBox.addItem(node);
                destNodeBox.addItem(node);
                drawingPanel.repaint();
            }
        });

        addEdgeBtn.addActionListener(e -> {
            Node src = (Node) srcNodeBox.getSelectedItem();
            Node dest = (Node) destNodeBox.getSelectedItem();
            if (src != null && dest != null && !src.equals(dest)) {
                int cost = Integer.parseInt(costField.getText());
                int bandwidth = Integer.parseInt(bandwidthField.getText());
                graph.addEdge(src, dest, cost, bandwidth);
                drawingPanel.repaint();
            }
        });

        shortestPathBtn.addActionListener(e -> {
            Node src = (Node) srcNodeBox.getSelectedItem();
            Node dest = (Node) destNodeBox.getSelectedItem();
            if (src != null && dest != null) {
                List<Node> path = graph.shortestPath(src, dest);
                JOptionPane.showMessageDialog(this, "Shortest Path: " + path);
            }
        });

        optimizeBtn.addActionListener(e -> {
            Set<Edge> mst = graph.minimumSpanningTree();
            optimizedPanel.setGraph(createOptimizedGraph(mst));  // Update optimized panel with MST
            optimizedPanel.repaint();
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new NetworkTopology().setVisible(true));
    }

    private WebCrawler createOptimizedGraph(Set<Edge> mstEdges) {
        WebCrawler optimizedGraph = new WebCrawler();
        for (Edge edge : mstEdges) {
            optimizedGraph.addNode(edge.source);
            optimizedGraph.addNode(edge.destination);
            optimizedGraph.addEdge(edge.source, edge.destination, edge.cost, edge.bandwidth);
        }
        return optimizedGraph;
    }
}
