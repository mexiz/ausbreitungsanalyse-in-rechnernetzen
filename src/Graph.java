import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Graph {

    List<Edge> table;
    List<IP> nodes;

    public Graph(String bracketNotation) throws ParseException {
        TupleParser parser = new TupleParser();
        this.table = parser.getTuple(bracketNotation);
        this.nodes = parser.getAdresses(bracketNotation);
        isCircle(nodes);
        Collections.sort(nodes);
    }

    public Graph(final IP root, final List<IP> children) {
        nodes.addAll(children);
        for (IP childrenAdress : children) {
            table.add(new Edge(root, childrenAdress));
        }
        isCircle(nodes);
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

    public void mergeTupleTable(Graph mergeTable) {
        this.table.addAll(mergeTable.getTable());
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


    public List<Edge> getChildren(IP parent, IP parentIp) {
        List<Edge> children = new ArrayList<>();

        for (Edge e : this.table) {
            if (e.xContains(parent) && !e.yContains(parentIp)) {
                children.add(e);
            }
        }
        return children;
    }



    public List<Edge> getTable() {
        return table;
    }

    public List<IP> getList() {
        return nodes;
    }

    public boolean checkIP(IP ip) {
        return nodes.contains(ip);
    }
}
