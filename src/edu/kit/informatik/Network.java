package edu.kit.informatik;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import edu.kit.informatik.graph.Graph;
import edu.kit.informatik.model.IP;

/**
 * Die Netzwerklasse
 * 
 * @author uwlho
 * @version 1.0
 */

public class Network {

    private final static String ERROR_INPUT_NULL = "Error: Input is null";
    private final static int MIN_HEIGHT = 0;

    private Graph graph;

    /**
     * Konstruktor um ein Netzwerk mit nur einem Level zuerzeugen
     * 
     * @param root     Wurzel des Baumes
     * @param children Die Knoten
     */

    public Network(final IP root, final List<IP> children) {
        graph = new Graph(root, children);
    }

    /**
     * Konstruktor um ein Netzwerk anhand der Bracketnotation zuerzeugen
     * 
     * @param bracketNotation Der Baum als Bracketnotation
     * @throws ParseException Wenn die Bracketnotation nicht in der richtigen Form
     *                        ist
     */

    public Network(final String bracketNotation) throws ParseException {
        if (bracketNotation == null) {
            throw new ParseException(ERROR_INPUT_NULL);
        }
        graph = new Graph(bracketNotation);
    }

    /**
     * Gibt eine Liste mit den IP-Adressen aus
     * 
     * @return eine Liste mit den Adressen im Netzwerk
     */

    public List<IP> list() {
        List<IP> node = new ArrayList<>();
        for (IP ip : graph.getNodes()) {
            node.add(ip.copy());
        }
        return node;
    }

    /**
     * Überprüft ob die Adresse im Netzwerk ist
     * 
     * @param ip Die zu überprüfunende Adresse
     * @return ob die Adresse im Netzwerk ist
     */

    public boolean contains(final IP ip) {
        return graph.checkIP(ip);
    }

    /**
     * Gibt die Höhe des Baumes zurück
     * 
     * @param root Wuzel des Baumes
     * @return die Höhe
     */

    public int getHeight(final IP root) {
        int height = graph.getHeight(root);
        if (height < MIN_HEIGHT) {
            return MIN_HEIGHT;
        }
        return graph.getHeight(root);
    }

    /**
     * Gibt die Level des Baumes zurück
     * 
     * @param root Wuzel des Baumes
     * @return die Level als Liste einer Liste
     */

    public List<List<IP>> getLevels(final IP root) {
        if (root == null) {
            return new ArrayList<>();
        }
        return graph.getLevels(root);
    }

    /**
     * Gibt eine Liste mit den Knoten aus, die auf der Route liegen
     * 
     * @param start Startwert der Route
     * @param end   Endwert der Route
     * @return Die Liste mit den Knoten
     */

    public List<IP> getRoute(final IP start, final IP end) {

        List<IP> route = graph.getRoute(start, end, null);
        Collections.reverse(route);
        return route;
    }

    /**
     * Trennt die Verbindung zwischen zwei Knoten und entfernt sie wenn nötig
     * 
     * 
     * @param ip1 Erste Adresse
     * @param ip2 Zweite Adresse
     * @return true wenn zwei Konten getrennt wurden
     */

    public boolean disconnect(final IP ip1, final IP ip2) {
        if (ip1 == null || ip2 == null) {
            return false;
        }
        return graph.removeEdge(ip1, ip2);
    }

    /**
     * Verbindet zwei Knoten wenn möglich
     * 
     * @param ip1 Erste Adresse
     * @param ip2 Zweite Adresse
     * @return true wenn zwei Konten verbunden wurden
     */

    public boolean connect(final IP ip1, final IP ip2) {
        if (ip1 == null || ip2 == null) {
            return false;
        }
        return graph.createEdge(ip1, ip2);
    }

    /**
     * Verbindet zwei Netwerke
     * 
     * @param subnet Das zu verbindende Netzwerk
     * @return Ob die zusammenführung gelungen ists
     */
    public boolean add(final Network subnet) {
        Graph first = this.getGraph().copy();
        if (subnet == null) {
            return false;
        }
        Graph two = subnet.getGraph().copy();

        if (first.mergeGraph(two) && !first.isCircular()) {
            this.graph = first;
            return true;
        }

        return false;
    }

    /**
     * Gibt den Graphen des Netzwerkes zurück
     * 
     * @return den Graphen des Netzwerkes
     */
    public Graph getGraph() {
        return graph;
    }

    /**
     * Wandelt den Baum in die Bracketnotation um.
     * 
     * @param root Die Wurzel des Baumes
     * @return Den String mit der Bracketnotation
     */

    public String toString(IP root) {
        return graph.toBracketNotation(root, null);
    }

}
