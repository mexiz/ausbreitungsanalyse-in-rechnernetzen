
public class Edge{

    private IP x;
    private IP y;

    public Edge(IP root, IP y) {
        this.x = root;
        this.y = y;
    }

    public IP getY() {
        return y;
    }
    public IP getX() {
        return x;
    }

    public boolean xContains(IP ip) {
        if(ip == null) {
            return false;
        }
        return x.compareTo(ip) == 0;
    }    
    public boolean yContains(IP ip) {
        if(ip == null) {
            return false;
        }
        return y.compareTo(ip) == 0;
    }    

    public String toString(){
        String t = "(" + x.toString();
        t += " | " + y.toString() + ")";
        return t;
    }
}
