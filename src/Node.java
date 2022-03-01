import java.util.List;

public class Node {

    private IP parent;
    private IP adress;
    private List<IP> children;

    public Node(IP adress) {
        this.adress = adress;
    }

    public IP getNodeAdress(){
        return adress;
    }

    public int getOutDeg(){
        return children.size();
    }

    public void addNode(IP children){
        this.children.add(children);
    }

}   
