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
            setDistanceMap(edge.getDestination(), edge.getSource(), currentLevel + 1);
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
            if (edge.doSourceContain(parent) && !edge.doDestinationContain(prevIP)) {
                children.add(edge);
            }
        }
        return children;
    }

    public boolean checkIP(IP ip) {
        return this.nodes.contains(ip);
    }

    public List<IP> getRoute(final IP start, final IP end, IP prev) {
        this.setDistanceMap(start, null, 0);
        List<IP> route = new ArrayList<>();
        List<Edge> children = getChildren(start, prev);
        for (Edge edge : children) {
            if (!route.isEmpty()) {
                route.add(edge.getSource());
                return route;
            }
            if (edge.doDestinationContain(end)) {
                route.add(edge.getSource());
                route.add(end);
                return route;
            }
            route.addAll(getRoute(edge.getDestination(), end, edge.getSource()));
        }

        return route;
    }

}
