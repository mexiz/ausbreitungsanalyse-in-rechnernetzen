import java.util.ArrayList;
import java.util.List;

public class Test {

    public static void main(String[] args) throws ParseException {

        Network net = new Network("(1.0.0.0 2.0.0.0 (3.0.0.0 5.0.0.0 9.0.0.0 6.0.0.0))");
        Network asd = new Network("(1.0.0.0 2.0.0.0 3.0.0.0)");
        System.out.println( net.add(asd));

        Graph o = new Graph("(1.0.0.0 2.0.0.0 (3.0.0.0 5.0.0.0))");
        Graph t = new Graph("(2.0.0.0 4.0.0.0)");

        Boolean a = o.mergeGraph(t);
        o = new Graph("(1.0.0.0 2.0.0.0 (3.0.0.0 4.0.0.0))");
        t = new Graph("(2.0.0.0 4.0.0.0)");

        a = o.mergeGraph(t);

        System.out.println("done");
    }

}
