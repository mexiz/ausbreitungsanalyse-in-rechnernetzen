package edu.kit.informatik.graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import edu.kit.informatik.ParseException;
import edu.kit.informatik.model.Edge;
import edu.kit.informatik.model.IP;

public class Graph extends GraphFunktion {

    private List<IP> nodes;
    private List<Edge> edges;

    public Graph(String bracketnotation) throws ParseException {

        this.nodes = getNodesFromBracketNotation(bracketnotation);
        this.edges = getEdgesFromBracketNotation(this.nodes, bracketnotation);
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

    public Graph(List<IP> nodes, List<Edge> edges) {
        this.edges = edges;
        this.nodes = nodes;
        super.init(edges, nodes);
    }

    public List<IP> getNodes() {
        Collections.sort(nodes);
        return nodes;
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public Graph copy() {
        List<Edge> newEdge = new ArrayList<>();
        List<IP> newIP = new ArrayList<>();
        for (Edge edge : edges) {
            newEdge.add(edge.copy());
        }
        for (IP ip : nodes) {
            newIP.add(ip.copy());
        }
        return new Graph(newIP, newEdge);
    }

    public boolean mergeGraph(Graph merge) {
        Set<IP> nodedd = new HashSet<>(nodes);
        nodedd.addAll(merge.getNodes());
        this.nodes = new ArrayList<>(nodedd);
        super.init(this.edges, this.nodes);

        boolean sameGraph = false;

        for (Edge edge : merge.getEdges()) {
            IP source = edge.getSource();
            IP des = edge.getDestination();

            if (getEdge(source, des) == null) {
                this.edges.add(new Edge(source, des));
                sameGraph = true;
            }

        }
        super.init(this.edges, this.nodes);
        return sameGraph;
    }

}
