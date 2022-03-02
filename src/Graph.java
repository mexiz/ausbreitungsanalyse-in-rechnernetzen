import java.util.ArrayList;
import java.util.List;

public class Graph extends GraphFunktion {

    List<IP> nodes;
    List<Edge> edges;

    public Graph(List<IP> nodes, List<Edge> edges) {
        this.nodes = nodes;
        this.edges = edges;
        super.n = nodes;
        super.e = edges;
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
        super.n = nodes;
        super.e = edges;
    }

    public Graph(String bracketnotation) throws ParseException {
        this.nodes = getNodesFromBracketNotation(bracketnotation);
        this.edges = getEdgesFromBracketNotation(bracketnotation);
        super.e = edges;
    }

}
