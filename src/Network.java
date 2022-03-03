
import java.util.List;

/**
 * 
 */

public class Network {

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
        graph = new Graph(bracketNotation);
    }

    /**
     * Gibt eine Liste mit den IP-Adressen aus
     * 
     * @return eine Liste mit den Adressen im Netzwerk
     */

    public List<IP> list() {
        return graph.getNodes();
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
        return graph.getHeight(root);
    }

    /**
     * Gibt die Level des Baumes zurück
     * 
     * @param root Wuzel des Baumes
     * @return die Level als Liste einer Liste
     */

    public List<List<IP>> getLevels(final IP root) {
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
        return graph.getRoute(start, end, null);
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
        return graph.addEdge(ip1, ip2);
    }

    /**
     * Verbindet zwei Netwerke
     * 
     * @param subnet Das zu verbindende Netzwerk
     * @return  Ob die zusammenführung gelungen ists
     */
    public boolean add(final Network subnet) {
        Graph first = this.getGraph().copy();
        Graph two = subnet.getGraph().copy();

        if (!first.mergeGraph(two)) {
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
