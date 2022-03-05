package edu.kit.informatik.graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import edu.kit.informatik.model.Edge;
import edu.kit.informatik.model.IP;

/**
 * Alle Funktionen die benötigt werden für einen Graph
 * Erbt von der Klasse die den Graphen erzeugt
 * 
 * @author uwhlp
 * @version 1.0
 */

public class GraphFunktion extends GraphParser {

    private Map<IP, Integer> distance;
    private List<Edge> edges;
    private List<IP> nodes;

    public int countGraph;

    /**
     * Konstruktor der die Map erzeugt.
     * 
     */

    public GraphFunktion() {
        distance = new HashMap<>();
        countGraph = 1;
    }

    /**
     * Setzt die Listen für die Funktionen
     * 
     * @param edges Die Liste mit den Kanten
     * @param nodes Die Liste mit den Knoten
     */

    public void init(List<Edge> edges, List<IP> nodes) {
        this.edges = edges;
        this.nodes = nodes;
    }

    /**
     * Wandelt den Graph in die Bracketnotation um
     * 
     * @param root   die Wurzel
     * @param prevIP Objekt für die Rekursion
     * @return den Graphen in der Bracketnotation
     */

    public String toBracketNotation(IP root, IP prevIP) {

        if (super.getIPFromNode(nodes, root) == null) {
            return "";
        }

        List<Edge> children = getChildren(root, prevIP);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("(");
        stringBuilder.append(root.toString());

        for (Edge edge : children) {
            List<Edge> grandkids = getChildren(edge.getDestination(), root);
            if (!grandkids.isEmpty()) {
                stringBuilder.append(" ");
                stringBuilder.append(toBracketNotation(edge.getDestination(), root));
            } else {
                stringBuilder.append(" ");
                stringBuilder.append(edge.getDestination().toString());
            }

        }
        stringBuilder.append(")");
        return stringBuilder.toString();
    }

    /**
     * Gibt die Höhe des Baumes an
     * 
     * @param root Die Wurzel
     * @return die Höhe des Baumes
     */

    public int getHeight(IP root) {
        IP nodeIP = getIPFromNode(this.nodes, root);
        if (nodeIP == null) {
            this.distance = new HashMap<>();
            return -1;
        }
        this.setDistanceMap(root, null, 0);
        int max = 0;

        for (Map.Entry<IP, Integer> entry : distance.entrySet()) {
            if (entry.getValue() > max) {
                max = entry.getValue();
            }
        }
        return max;
    }

    /**
     * Übergibt eine Liste mit den IPs in den verschiedenen Leveln
     * 
     * @param root Die Wurzel
     * @return die Liste mit dem leveln und den Adressen
     */

    public List<List<IP>> getLevels(IP root) {
        List<List<IP>> levels = new ArrayList<>();
        int numberOfLevels = getHeight(root) + 1;
        for (int i = 0; i < numberOfLevels; i++) {
            List<IP> level = new ArrayList<>();
            levels.add(level);
        }
        for (Map.Entry<IP, Integer> entry : distance.entrySet()) {
            levels.get(entry.getValue()).add(entry.getKey());
            Collections.sort(levels.get(entry.getValue()));
        }
        return levels;
    }

    /**
     * Checkt ob die Adresse ein Knoten im Grapgen ist
     * 
     * @param ip Adresse die überprüft werden sollen
     * @return true wenn sie vorhanden ist
     */

    public boolean checkIP(IP ip) {
        return this.nodes.contains(ip);
    }

    /**
     * Die Route von einer Adresse zur anderen Adresse
     * 
     * @param start Startadresse
     * @param end   Endadresse
     * @param prev  Objekt für die Rekursion
     * @return Die Liste mit den Knoten
     */

    public List<IP> getRoute(final IP start, final IP end, IP prev) {
        List<IP> route = new ArrayList<>();
        List<Edge> children = getChildren(start, prev);
        Iterator<Edge> iter = children.iterator();
        while (iter.hasNext() && route.isEmpty()) {
            Edge edge = iter.next();

            if (edge.doDestinationContain(end)) {
                route.add(end);
                route.add(edge.getSource());
                return route;
            }

            route.addAll(getRoute(edge.getDestination(), end, edge.getSource()));

            if (!route.isEmpty()) {
                route.add(edge.getSource());
                return route;
            }
        }
        return route;
    }

    /**
     * Entfernt eine Kante im Graphen
     * 
     * @param ip1 erster Knoten
     * @param ip2 zweiter Knoten
     * @return ob der Knoten entfernt wurde
     */

    public boolean removeEdge(final IP ip1, final IP ip2) {

        Edge one = getEdgeFromList(ip1, ip2);
        Edge reversed = getEdgeFromList(ip2, ip1);
        if ((one == null && reversed == null) || nodes.size() < 3) {
            return false;
        }
        edges.remove(one);
        edges.remove(reversed);

        boolean removeGraph = false;
        if (getChildren(super.getIPFromNode(this.nodes, ip1), null).isEmpty()) {
            nodes.remove(super.getIPFromNode(this.nodes, ip1));
            removeGraph = true;

        }
        if (getChildren(super.getIPFromNode(this.nodes, ip2), null).isEmpty()) {
            nodes.remove(super.getIPFromNode(this.nodes, ip2));
            removeGraph = true;
        }
        if (!removeGraph) {
            countGraph++;
        }
        return true;

    }

    /**
     * Fügt eine Kante hinzu
     * 
     * @param ip1 erster Knoten
     * @param ip2 zweiter Knoten
     * @return gibt zurück ob die Kante gesetzt wurde
     */

    public boolean addEdge(IP ip1, IP ip2) {

        IP newIP1 = getIPFromNode(this.nodes, ip1);
        IP newIP2 = getIPFromNode(this.nodes, ip2);

        if (newIP1 == null || newIP2 == null) {
            return false;
        } else if (newIP1.equals(newIP2)) {
            return false;
        } else if (countGraph < 2) {
            return false;
        }

        Edge one = getEdgeFromList(newIP1, newIP2);
        Edge reversed = getEdgeFromList(newIP2, newIP1);

        if (one != null || reversed != null) {
            return false;
        }

        one = new Edge(newIP1, newIP2);
        reversed = new Edge(newIP2, newIP1);
        this.edges.add(one);
        this.edges.add(reversed);
        countGraph--;

        if (this.isCircular()) {
            this.edges.remove(one);
            this.edges.remove(reversed);
            countGraph++;
            return false;
        }

        return true;
    }

    /**
     * Gibt die Kante aus dem Graphen aus
     * 
     * @param ip1 erster Knoten
     * @param ip2 zweiter Knoten
     * @return gibt die Kante zurück, wenn nicht vorhanden null
     */

    public Edge getEdgeFromList(IP ip1, IP ip2) {

        // Kinder von ip1
        List<Edge> child = getChildren(ip1, null);
        IP realIP1 = super.getIPFromNode(this.nodes, ip1);
        IP realIP2 = super.getIPFromNode(this.nodes, ip2);

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

    /**
     * Checkt ob der Graph ein Kreis ist
     * 
     * @return true, wennn es ein Kreis ist
     */

    public boolean isCircular() {
        return (((this.edges.size() / 2) + countGraph) != (this.nodes.size()));
    }

    /**
     * Erzeugt eine Map um die Distanze zur Wurzel zu hinterlegen
     * 
     * @param root         Die Wurzel
     * @param prevIP       Das Übergabeobjekt für die Rekursion
     * @param currentLevel Das Level wo sich das Objekt befindet
     */

    private void setDistanceMap(IP root, IP prevIP, int currentLevel) {
        List<Edge> children = getChildren(root, prevIP);
        if (prevIP == null) {
            distance = new HashMap<>();
        }
        distance.put(root, currentLevel);
        for (Edge edge : children) {
            setDistanceMap(edge.getDestination(), edge.getSource(), currentLevel + 1);
        }
    }

    /**
     * Hilfsmethode um die Kanten zufinden die mit der IP verbunden sind
     * 
     * @param parent Adresse von den die Kante gesucht werden sollen
     * @param prevIP die Adresse die Ignoriert werden soll
     * @return die Liste mit den Kanten
     */

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

}
