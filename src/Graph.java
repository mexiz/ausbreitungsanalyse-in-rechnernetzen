import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Graph {

    private List<IP> nodes;
    private List<Edge> edges;


    public Graph(List<IP> nodes , List<Edge> edges){
        this.nodes = nodes;
        this.edges = edges;
    }

    public Graph(){
        this.nodes = new ArrayList<>();
        this.edges = new ArrayList<>();
    }

    public List<IP> getNodes() {
        return nodes;
    }

    public void setNodes(List<IP> nodes) {
        this.nodes = nodes;
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public void setEdges(List<Edge> edges) {
        this.edges = edges;
    }

    public void addEdge(Edge edge){
        edges.add(edge);
    }

    
}
