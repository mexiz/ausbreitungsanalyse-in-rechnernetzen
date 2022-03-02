
public class Edge{

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
        if(ip == null) {
            return false;
        }
        return source.compareTo(ip) == 0;
    }    
    public boolean doDestinationContain(IP ip) {
        if(ip == null) {
            return false;
        }
        return destination.compareTo(ip) == 0;
    }    

    public String toString(){
        String t = "(" + source.toString();
        t += " | " + destination.toString() + ")";
        return t;
    }
}
