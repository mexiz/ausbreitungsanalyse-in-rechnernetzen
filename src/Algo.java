import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Algo {

    Graph graph;
    List<IP> nodes;
    List<Edge> edges;
    Map<IP, Integer> distance;

    public Algo(Graph graph) {
        this.graph = graph;
        this.nodes = graph.getNodes();
        this.edges = graph.getEdges();
        distance = new HashMap<>();
    }

    public void setDistanceMap(IP root, IP prevIP, int currentLevel) {
        List<Edge> children = getChildren(root, prevIP);
        distance.put(root, currentLevel);
        for (Edge e : children) {
            setDistanceMap(e.getY(), e.getX(), currentLevel + 1);
        }
    }

    public int getHeight(IP root) {
        this.setDistanceMap(root, null, 0);
        int max = 0;

        for (Map.Entry<IP, Integer> entry : distance.entrySet()) {
            if (entry.getValue() + 1 > max) {
                max = entry.getValue() + 1;
            }
        }
        return max;
    }

    public List<List<IP>> getLevels(IP root) {
        List<List<IP>> levels = new ArrayList<>();
        int numberOfLevels = getHeight(root);
        for (int i = 0; i < numberOfLevels; i++) {
            List<IP> level = new ArrayList<>();
            levels.add(level);
        }
        for (Map.Entry<IP, Integer> entry : distance.entrySet()) {
            levels.get(entry.getValue()).add(entry.getKey());
        }
        return levels;
    }

    private List<Edge> getChildren(IP parent, IP prevIP) {
        List<Edge> children = new ArrayList<>();

        for (Edge e : graph.getEdges()) {
            if (e.xContains(parent) && !e.yContains(prevIP)) {
                children.add(e);
            }
        }
        return children;
    }

    public boolean checkIP(IP ip) {
        return graph.getNodes().contains(ip);
    }

}
