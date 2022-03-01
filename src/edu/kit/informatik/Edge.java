
public class Edge{

    private IP root;
    private IP y;

    public Edge(IP root, IP y) {
        this.root = root;
        this.y = y;
    }

    public IP getY() {
        return y;
    }
    public IP getRoot() {
        return root;
    }

    public boolean contains(IP ip) {
        return root.compareTo(ip) == 0;
    }    
}
