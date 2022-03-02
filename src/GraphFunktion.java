import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GraphFunktion extends GraphParser {

    private Map<IP, Integer> distance;
    private List<Edge> edges;
    private List<IP> nodes;

    public GraphFunktion() {
        distance = new HashMap<>();
        
    }

    public void init(List<Edge> edges, List<IP> nodes) {
        this.edges = edges;
        this.nodes = nodes;
    }

    public void setDistanceMap(IP root, IP prevIP, int currentLevel) {
        List<Edge> children = getChildren(root, prevIP);
        distance.put(root, currentLevel);
        for (Edge edge : children) {
            setDistanceMap(edge.getY(), edge.getX(), currentLevel + 1);
        }
    }

    public int getHeight(IP root) {
        this.setDistanceMap(root, null, 0);
        int max = 0;

        for (Map.Entry<IP, Integer> entry : distance.entrySet()) {
            if (entry.getValue() > max) {
                max = entry.getValue();
            }
        }
        return max;
    }

    public List<List<IP>> getLevels(IP root) {
        List<List<IP>> levels = new ArrayList<>();
        int numberOfLevels = getHeight(root) + 1;
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

        for (Edge edge : this.edges) {
            if (edge.xContains(parent) && !edge.yContains(prevIP)) {
                children.add(edge);
            }
        }
        return children;
    }

    public boolean checkIP(IP ip) {
        return this.nodes.contains(ip);
    }

}
