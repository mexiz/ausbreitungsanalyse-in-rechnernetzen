
public class Test {

    public static void main(String[] args) throws ParseException {

        String bracketNotation = "(1.0.0.0 2.0.0.0 (3.0.0.0 (1.0.0.0 4.0.0.0)))";

        TupleTable table = new TupleTable(bracketNotation);
        table.getTable();
      
    }


}
