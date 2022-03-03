
public class Edge implements Comparable<Edge> {

    private IP source;
    private IP destination;

    public Edge(IP root, IP y) {
        this.source = root;
        this.destination = y;
    }

    public IP getDestination() {
        return destination;
    }

    public IP getSource() {
        return source;
    }

    public boolean doSourceContain(IP ip) {
        if (ip == null) {
            return false;
        }
        return source.compareTo(ip) == 0;
    }

    public boolean doDestinationContain(IP ip) {
        if (ip == null) {
            return false;
        }
        return destination.compareTo(ip) == 0;
    }

    public boolean contains(IP ip) {
        return doSourceContain(ip) || doDestinationContain(ip);
    }

    public String toString() {
        String t = "(" + source.toString();
        t += " | " + destination.toString() + ")";
        return t;
    }

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
