
public class Test {

    public static void main(String[] args) throws ParseException {

        String bracketNotation = "(1.0.0.0 (2.0.0.0 (4.0.0.0 3.0.0.0) 10.0.0.0) 8.0.0.0)";

        Graph table = new Graph(bracketNotation);
        System.out.println(table.getHeight(new IP("1.0.0.0")));
        table.getTable();
      
    }


}
