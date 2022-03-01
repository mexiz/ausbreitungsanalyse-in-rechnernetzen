import java.util.List;

public class Test {

    public static void main(String[] args) throws ParseException {

        String bracketNotation = "(1.0.0.0 (2.0.0.0 (4.0.0.0 3.0.0.0) 9.0.0.0) 8.0.0.0)";
        TupleParser parser = new TupleParser();
        List<Edge> e = parser.getTuple(bracketNotation);
        for (Edge es : e) {
            System.out.println(es.toString());
        }

        Graph table = new Graph(bracketNotation);

        System.out.println(table.getHeight(new IP("1.0.0.0"), null));
        table.getTable();

        table.getLevels(new IP("1.0.0.0"));

    }

}
