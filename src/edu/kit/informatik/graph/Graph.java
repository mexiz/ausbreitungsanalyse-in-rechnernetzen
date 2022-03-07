package edu.kit.informatik.graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import edu.kit.informatik.ParseException;
import edu.kit.informatik.model.Edge;
import edu.kit.informatik.model.IP;

/**
 * Der Graph mit den Knoten und Kanten und den Funktionen
 * 
 * @author uwhlp
 * @version 1.0
 */

public class Graph extends GraphFunction {

    private List<IP> nodes;
    private List<Edge> edges;

    /**
     * Konstruktor für die Eingabe mit der Bracketnotation
     * 
     * @param bracketnotation die Bracketnotation
     * @throws ParseException Wenn die Bracketnotation fehlerhaft ist
     */

    public Graph(String bracketnotation) throws ParseException {

        this.nodes = getNodesFromBracketNotation(bracketnotation);
        this.edges = getEdgesFromBracketNotation(this.nodes, bracketnotation);
        super.init(edges, nodes);

    }

    /**
     * Konstruktor für die Eingabe eines Graphes mit der Höhe 1
     * 
     * @param root     die Wurzel
     * @param children die Kinder von der Wurzel
     */

    public Graph(IP root, List<IP> children) {

        if (root == null || children == null) {
            throw new RuntimeException("Error: Network creation null");
        }
        nodes = new ArrayList<>();
        edges = new ArrayList<>();

        Set<IP> duplicate = new HashSet<>();

        nodes.add(root);
        for (IP ip : children) {
            if(root.equals(ip)|| !duplicate.add(ip)){
                throw new RuntimeException("Error: Network creation null");
            }
            nodes.add(ip);
            edges.add(new Edge(root, ip));
            edges.add(new Edge(ip, root));
        }
        super.init(edges, nodes);
    }

    /**
     * Konstruktor für die copy() methode
     * 
     * @param nodes Liste mit Knoten
     * @param edges Liste mit Kanten
     */

    public Graph(List<IP> nodes, List<Edge> edges) {
        this.edges = edges;
        this.nodes = nodes;
        super.init(edges, nodes);
    }

    /**
     * Getter für die Knoten
     * 
     * @return sortierte Liste an Knoten
     */

    public List<IP> getNodes() {
        Collections.sort(nodes);
        return nodes;
    }

    /**
     * Getter für die Kanten
     * 
     * @return gibt eine Liste an Kanten zurück
     */

    public List<Edge> getEdges() {
        return edges;
    }

    /**
     * Kopiert den Graphen und gibt ihn aus
     * 
     * @return den kopierten Graphen
     */

    public Graph copy() {
        List<Edge> newEdge = new ArrayList<>();
        List<IP> newIP = new ArrayList<>();
        for (Edge edge : this.edges) {
            newEdge.add(edge.copy());
        }
        for (IP ip : nodes) {
            newIP.add(ip.copy());
        }
        return new Graph(newIP, newEdge);
    }

    /**
     * Vebindet den Graphen mit einem anderen
     * 
     * @param merge der Graph zum verbinden
     * @return ob der Graph verbunden wurde
     */

    public boolean mergeGraph(Graph merge) {

        // Verbindet die Knoten
        Set<IP> setNodes = new HashSet<>(nodes);
        setNodes.addAll(merge.getNodes());

        this.nodes = new ArrayList<>(setNodes);

        super.init(this.edges, this.nodes);

        boolean sameGraph = false;

        Iterator<Edge> iter = merge.getEdges().iterator();
        while (iter.hasNext()) {
            Edge edge = iter.next();
            IP source = edge.getSource();
            IP des = edge.getDestination();

            if (getEdgeFromList(source, des) == null && getEdgeFromList(des, source) == null) {

                this.edges.add(new Edge(source, des));
                this.edges.add(new Edge(des, source));

                sameGraph = true;
            }

        }

        super.init(this.edges, this.nodes);
        return sameGraph;
    }

}
