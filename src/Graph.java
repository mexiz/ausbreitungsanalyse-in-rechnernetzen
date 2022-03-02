import java.util.ArrayList;
import java.util.List;

public class Graph extends GraphFunktion {

    private List<IP> nodes;
    private List<Edge> edges;

    public Graph(String bracketnotation) throws ParseException {
        this.nodes = getNodesFromBracketNotation(bracketnotation);
        this.edges = getEdgesFromBracketNotation(bracketnotation);
        super.init(edges, nodes);
    }
    
    public Graph(IP root, List<IP> children) {
        nodes = new ArrayList<>();
        edges = new ArrayList<>();
        nodes.add(root);
        for (IP ip : children) {
            nodes.add(ip);
            edges.add(new Edge(root, ip));
            edges.add(new Edge(ip, root));
        }
        super.init(edges, nodes);
    }

    public List<IP> getNodes() {
        return nodes;
    }

    public void setNodes(List<IP> nodes) {
        this.nodes = nodes;
    }

}
