import java.util.ArrayList;
import java.util.Collections;
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
        Collections.sort(children);
        return children;
    }

    public boolean checkIP(IP ip) {
        return this.nodes.contains(ip);
    }

    public List<IP> getRoute(final IP start, final IP end, IP prev) {
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

    public boolean removeEdge(final IP ip1, final IP ip2) {

        Edge one = getEdge(ip1, ip2);
        Edge reversed = getEdge(ip2, ip1);
        if (one == null && reversed == null) {
            return false;
        }
        edges.remove(one);
        edges.remove(reversed);

        boolean remove = false;
        if (getChildren(getIPFromNode(this.nodes, ip1), null).isEmpty()) {
            nodes.remove(getIPFromNode(this.nodes, ip1));
            remove = true;
        }
        if (getChildren(getIPFromNode(this.nodes, ip2), null).isEmpty()) {
            nodes.remove(getIPFromNode(this.nodes, ip2));
            remove = true;
        }
        return remove;

    }

    public Edge getEdge(IP ip1, IP ip2) {
        List<Edge> child = getChildren(ip1, null);
        IP realIP1 = getIPFromNode(this.nodes, ip1);
        IP realIP2 = getIPFromNode(this.nodes, ip2);
        if (realIP1 == null || realIP2 == null) {
            return null;
        }
        for (Edge edge : child) {
            if (edge.getSource().equals(realIP1) && edge.getDestination().equals(realIP2)) {
                return edge;
            }
        }
        return null;
    }

    public boolean addEdge(IP ip1, IP ip2) {

        Edge one = getEdge(ip1, ip2);
        Edge reversed = getEdge(ip2, ip1);
        if (one != null || reversed != null) {
            return false;
        }
        this.edges.add(one);
        this.edges.add(reversed);

        return true;
    }

    public boolean isCircular() {
        return (((this.edges.size() / 2) + 1) != (this.nodes.size()));
    }

    public String toBracketNotation(IP root, IP prevIP) {

        List<Edge> children = getChildren(root, prevIP);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("(");
        stringBuilder.append(root.toString());

        for (Edge edge : children) {
            List<Edge> grandkids = getChildren(edge.getDestination(), root);
            if (!grandkids.isEmpty()) {
                stringBuilder.append(" ");
                stringBuilder.append(toBracketNotation(edge.getDestination(), root));
            }else {
                stringBuilder.append(" ");
                stringBuilder.append(edge.getDestination().toString());
            }
           
        }
        stringBuilder.append(")");
        return stringBuilder.toString();
    }

}
