import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Algo {

    Graph graph;

    public Algo(Graph graph){
        this.graph = graph;
    }

    private boolean isCircle(List<IP> ipTable) {
        Set<String> duplicatTester = new HashSet<>();
        for (IP adress : ipTable) {
            if (!duplicatTester.add(adress.toString())) {
                return true;
            }
        }
        return false;
    }

    public int getHeight(IP root, IP parentIp) {
        int maxHeight = 0;
        List<Edge> children = getChildren(root, parentIp);

        if (children.isEmpty()) {
            return 1;
        } else {
            int prevEdge = 0;
            for (Edge e : children) {
                int edgeHeight = getHeight(e.getY(), e.getX());
                edgeHeight += maxHeight;
                if (edgeHeight >= prevEdge) {
                    prevEdge = edgeHeight;
                }
            }
            return prevEdge + 1;
        }
    }

    public List<List<IP>> getLevels(IP root) {
        List<List<IP>> all = new ArrayList<>();
        List<Edge> children = getChildren(root, null);
        List<Edge> grandkids = new ArrayList<>();
        List<IP> currentLevel = new ArrayList<>();
        currentLevel.add(root);
        all.add(new ArrayList<>(currentLevel));
        while (!children.isEmpty()) {
            currentLevel.clear();
            for (Edge child : children) {
                currentLevel.add(child.getY());
                for (Edge grandChild : getChildren(child.getY(), child.getX())) {
                    grandkids.add(grandChild);
                }
            }

            children = new ArrayList<>(grandkids);
            grandkids.clear();
            all.add(new ArrayList<>(currentLevel));
        }
        return all;
    }

    public List<Edge> getChildren(IP parent, IP prevIP) {
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
