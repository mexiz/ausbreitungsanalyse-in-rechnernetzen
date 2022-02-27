import java.util.List;

public class TupleTable {

    List<Tuple<IP>> table;
    List<IP> ipTable;
    TupleParser parser;

    public TupleTable(String bracketNotation) throws ParseException {
        parser = new TupleParser();
        this.table = parser.getTuple(bracketNotation);
    }

    public void mergeTupleTable(TupleTable mergeTable) {
        this.table.addAll(mergeTable.getTable());
    }

    public List<Tuple<IP>> getTable() {
        return table;
    }

}
