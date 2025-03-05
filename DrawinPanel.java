import java.awt.*;
import javax.swing.*;

class DrawingPanel extends JPanel {
    private WebCrawler graph;

    public DrawingPanel(WebCrawler graph) {
        this.graph = graph;
        setPreferredSize(new Dimension(500, 400));
        setBackground(Color.WHITE);
    }

    public void setGraph(WebCrawler graph) {
        this.graph = graph;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.BLUE);
        for (Edge edge : graph.getEdges()) {
            g.drawLine(edge.source.x, edge.source.y, edge.destination.x, edge.destination.y);
            int midX = (edge.source.x + edge.destination.x) / 2;
            int midY = (edge.source.y + edge.destination.y) / 2;
            g.drawString("Cost: " + edge.cost, midX, midY);
        }

        g.setColor(Color.RED);
        for (Node node : graph.getNodes()) {
            g.fillOval(node.x - 10, node.y - 10, 20, 20);
            g.setColor(Color.BLACK);
            g.drawString(node.name, node.x - 10, node.y - 15);
            g.setColor(Color.RED);
        }
    }
}
