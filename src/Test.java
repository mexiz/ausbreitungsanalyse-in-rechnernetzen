
public class Test {

    public static void main(String[] args) throws ParseException {

        Network net = new Network("(1.0.0.0 2.0.0.0 (3.0.0.0 (5.0.0.0 9.0.0.0 6.0.0.0) 4.0.0.0) 8.0.0.0 7.0.0.0)");
        Algo a = new Algo(net.graph);
        a.getLevels(new IP("1.0.0.0"));
    }

}
