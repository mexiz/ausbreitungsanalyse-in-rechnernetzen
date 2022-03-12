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

public class GraphFunction extends GraphParser {

    private static final String IP_SEPARATOR = " ";

    private static final int FIRST_ELEMENT = 0;
    private static final int MIN_NODES_DISCONNECT = 3;
    private static final int WRONG_ROOT_HEIGHT = -1;
    private static final int START_VALUE = 0;
    private static final int START_VALUE_VISITED = 0;
    private static final int ADD_ONE = 1;

    private Map<IP, Integer> distance;
    private List<Edge> edges;
    private List<IP> nodes;

    /**
     * Konstruktor der die Map erzeugt.
     * 
     */

    public GraphFunction() {
        distance = new HashMap<>();
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

        if (super.getAdressFromList(nodes, root) == null) {
            return "";
        }

        List<Edge> children = getChildren(this.edges, root, prevIP);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("(");
        stringBuilder.append(root.toString());

        for (Edge edge : children) {
            List<Edge> grandkids = getChildren(this.edges, edge.getDestination(), root);
            if (!grandkids.isEmpty()) {
                stringBuilder.append(IP_SEPARATOR);
                stringBuilder.append(toBracketNotation(edge.getDestination(), root));
            } else {
                stringBuilder.append(IP_SEPARATOR);
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
        IP nodeIP = getAdressFromList(this.nodes, root);
        if (nodeIP == null) {
            this.distance = new HashMap<>();
            return WRONG_ROOT_HEIGHT;
        }
        this.setDistanceMap(root, null, START_VALUE);
        int max = START_VALUE;

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
        int numberOfLevels = getHeight(root) + ADD_ONE;
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
        List<Edge> children = getChildren(this.edges, start, prev);
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

        IP firstIP = ip1;
        IP secondIP = ip2;

        Edge one = getEdgeFromList(firstIP, secondIP);
        Edge reversed = getEdgeFromList(secondIP, firstIP);
        if ((one == null && reversed == null) || nodes.size() < MIN_NODES_DISCONNECT) {
            return false;
        }
        edges.remove(one);
        edges.remove(reversed);

        if (getChildren(this.edges, super.getAdressFromList(this.nodes, ip1), null).isEmpty()) {
            nodes.remove(super.getAdressFromList(this.nodes, ip1));

        }
        if (getChildren(this.edges, super.getAdressFromList(this.nodes, ip2), null).isEmpty()) {
            nodes.remove(super.getAdressFromList(this.nodes, ip2));
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

    public boolean createEdge(IP ip1, IP ip2) {

        IP newIP1 = getAdressFromList(this.nodes, ip1);
        IP newIP2 = getAdressFromList(this.nodes, ip2);

        if (newIP1 == null || newIP2 == null) {
            return false;
        } else if (newIP1.equals(newIP2)) {
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

        if (this.isCircular()) {
            this.edges.remove(one);
            this.edges.remove(reversed);
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
        List<Edge> child = getChildren(this.edges, ip1, null);
        IP realIP1 = super.getAdressFromList(this.nodes, ip1);
        IP realIP2 = super.getAdressFromList(this.nodes, ip2);

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
        Map<IP, Integer> viseted = new HashMap<>();

        List<Edge> copy = new ArrayList<>();
        for (Edge edge : edges) {
            copy.add(edge.copy());
        }
        while (!copy.isEmpty()) {
            if (checkVisitedTwice(copy, viseted, copy.get(FIRST_ELEMENT).getSource(), null)) {
                return true;
            }

        }

        return false;
    }

    /**
     * Die Hilfsmethode überprüft ob ein Knoten zweimal besucht wurde (Kreis)
     * 
     * 
     * @param copy    Die Liste mit den Edges
     * @param viseted Die Map in der gespeichert wird wie oft ein Knoten besucht
     *                wurde
     * @param root    die Wurzel
     * @param prevIP  die vorherige Adresse für die Rekursion
     * @return {@code true} wenn ein Knoten zweimal besucht wurde
     */

    private boolean checkVisitedTwice(List<Edge> copyOfEdges, Map<IP, Integer> visetedMap, IP root, IP prevIP) {

        List<Edge> children = getChildren(copyOfEdges, root, prevIP);

        int countVisited = visetedMap.containsKey(root) ? visetedMap.get(root) : START_VALUE_VISITED;
        if (countVisited > START_VALUE_VISITED) {
            return true;
        }
        visetedMap.put(root, countVisited + ADD_ONE);

        for (Edge edge : children) {
            if (checkVisitedTwice(copyOfEdges, visetedMap, edge.getDestination(), root)) {
                return true;
            }

            copyOfEdges.remove(edge);
            copyOfEdges.remove(getEdgeFromList(edge.getDestination(), edge.getSource()));
        }

        return false;
    }

    /**
     * Erzeugt eine Map um die Distanze zur Wurzel zu hinterlegen
     * 
     * @param root         Die Wurzel
     * @param prevIP       Das Übergabeobjekt für die Rekursion
     * @param currentLevel Das Level wo sich das Objekt befindet
     */

    private void setDistanceMap(IP root, IP prevIP, int currentLevel) {
        List<Edge> children = getChildren(this.edges, root, prevIP);
        if (prevIP == null) {
            distance = new HashMap<>();
        }
        distance.put(root, currentLevel);
        for (Edge edge : children) {
            setDistanceMap(edge.getDestination(), edge.getSource(), currentLevel + ADD_ONE);
        }
    }

    /**
     * Hilfsmethode um die Kanten zufinden die mit der IP verbunden sind
     * 
     * @param parent Adresse von den die Kante gesucht werden sollen
     * @param prevIP die Adresse die Ignoriert werden soll
     * @return die Liste mit den Kanten
     */

    private List<Edge> getChildren(List<Edge> checkList, IP parent, IP prevIP) {
        List<Edge> children = new ArrayList<>();
        for (Edge edge : checkList) {
            if (edge.doSourceContain(parent) && !edge.doDestinationContain(prevIP)) {
                children.add(edge);
            }
        }
        Collections.sort(children);
        return children;
    }

    /**
     * Gibt eine Liste mit allen Graden (Ausgangsgrad und Eingangsgrad) der Knoten aus
     * 
     * @return Liste mit den Graden
     */

    public List<Integer> getDegree() {
        Map<IP, Integer> degreeMap = new HashMap<>();

        for (Edge edge : edges) {
            IP sourceIP = edge.getSource();
            int degree = degreeMap.containsKey(sourceIP) ? degreeMap.get(sourceIP) + 1 : 1;
            degreeMap.put(sourceIP, degree);

        }
        List<Integer> allDegrees = new ArrayList<>(degreeMap.values());
        Collections.sort(allDegrees);
        return allDegrees;
    }

}
