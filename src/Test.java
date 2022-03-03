
public class Test {

    public static void main(String[] args) throws ParseException {
        Network net = new Network("(1.0.0.0 2.0.0.0 3.0.0.0)");
        net.getHeight(new IP("1.0.0.0"));
    }

}
