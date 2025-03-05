import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

// Panel for Graph Visualization
class GraphPanel extends JPanel {
    List<GraphNode> nodes = new ArrayList<>();
    List<GraphEdge> edges = new ArrayList<>();

    GraphPanel() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                nodes.add(new GraphNode(nodes.size(), e.getX(), e.getY()));
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);

        for (GraphEdge edge : edges) {
            g.drawLine(edge.src.x, edge.src.y, edge.dest.x, edge.dest.y);
        }

        for (GraphNode node : nodes) {
            g.setColor(Color.BLUE);
            g.fillOval(node.x - 10, node.y - 10, 20, 20);
            g.setColor(Color.WHITE);
            g.drawString("" + node.id, node.x - 5, node.y + 5);
        }
    }
}

// Main Frame
public class GraphNetwork extends JFrame {
    GraphPanel graphPanel = new GraphPanel();

    public GraphNetwork() {
        setTitle("Graph Network Visualization");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        add(graphPanel, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GraphNetwork().setVisible(true));
    }
}
