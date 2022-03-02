import java.util.List;

public class Network {

    public Graph graph;

    /**
     * Konstruktor um ein Netzwerk mit nur einem Level zuerzeugen
     * 
     * @param root     Wurzel des Baumes
     * @param children Die Knoten
     */

    public Network(final IP root, final List<IP> children) {
        graph.setNodes(children);
        for (IP childrenAdress : children) {
            graph.addEdge(new Edge(root, childrenAdress));
        }
    }

    /**
     * Konstruktor um ein Netzwerk anhand der Bracketnotation zuerzeugen
     * 
     * @param bracketNotation Der Baum als Bracketnotation
     * @throws ParseException Wenn die Bracketnotation nicht in der richtigen Form
     *                        ist
     */

    public Network(final String bracketNotation) throws ParseException {
        GraphParser parser = new GraphParser();
        graph = new Graph();
        graph.setEdges(parser.getEdges(bracketNotation));
        graph.setNodes(parser.getNodes(bracketNotation));
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
        Algo algo = new Algo(graph);
        return algo.checkIP(ip);
    }

    /**
     * Gibt die Höhe des Baumes zurück
     * 
     * @param root Wuzel des Baumes
     * @return die Höhe
     */

    public int getHeight(final IP root) {
        return 0;
    }

    /**
     * Gibt die Level des Baumes zurück
     * 
     * @param root Wuzel des Baumes
     * @return die Level als Liste einer Liste
     */

    public List<List<IP>> getLevels(final IP root) {
        return null;
    }

    // WICHTIG

    public List<IP> getRoute(final IP start, final IP end) {
        return null;
    }

    public String toString(IP root) {
        return null;
    }

    public boolean add(final Network subnet) {
        return false;
    }

    public boolean connect(final IP ip1, final IP ip2) {
        return false;
    }

    public boolean disconnect(final IP ip1, final IP ip2) {
        return false;
    }

}
