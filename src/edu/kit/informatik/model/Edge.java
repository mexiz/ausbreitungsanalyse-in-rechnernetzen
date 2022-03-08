package edu.kit.informatik.model;

/**
 * Die Kanten in Form von Tuple(source, destination)
 * 
 * @author uwhlp
 * @version 1.0
 */

public class Edge implements Comparable<Edge> {

    private IP source;
    private IP destination;

    /**
     * Konstruktor
     * 
     * @param root die Wurzel
     * @param y    das Ziel
     */

    public Edge(IP root, IP y) {
        this.source = root;
        this.destination = y;
    }

    /**
     * Getter für das Ziel
     * 
     * @return gibt das Ziel zurück
     */

    public IP getDestination() {
        return destination;
    }

    /**
     * Getter für die Wurzel
     * 
     * @return gibt die Wurzel zurück
     */
    public IP getSource() {
        return source;
    }

    /**
     * Checkt ob die Adresse zur Wurzel der Kante passt
     * 
     * @param ip die Adresse
     * @return true, wenn die Adresse passt
     */

    public boolean doSourceContain(IP ip) {
        if (ip == null) {
            return false;
        }
        return source.compareTo(ip) == 0;
    }

    /**
     * Checkt ob die Adresse zum Ziel der Kante passt
     * 
     * @param ip die Adresse
     * @return true, wenn die Adresse passt
     */

    public boolean doDestinationContain(IP ip) {
        if (ip == null) {
            return false;
        }
        return destination.compareTo(ip) == 0;
    }

    /**
     * Kopiert das Edge-Objekt
     * 
     * @return gibt das kopierte Edgeobjekt zurück
     */

    public Edge copy() {
        return new Edge(source.copy(), destination.copy());
    }

    @Override
    public int compareTo(Edge o) {
        return destination.compareTo(o.getDestination());
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((destination == null) ? 0 : destination.hashCode());
        result = prime * result + ((source == null) ? 0 : source.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Edge other = (Edge) obj;
        if (destination == null) {
            if (other.destination != null)
                return false;
        } else if (!destination.equals(other.destination))
            return false;
        if (source == null) {
            if (other.source != null)
                return false;
        } else if (!source.equals(other.source))
            return false;
        return true;
    }


}
