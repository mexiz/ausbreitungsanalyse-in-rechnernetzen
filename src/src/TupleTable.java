import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TupleTable {

    List<Tuple<IP>> table;
    List<IP> ipTable;

    public TupleTable(String bracketNotation) throws ParseException {
        TupleParser parser = new TupleParser();
        this.table = parser.getTuple(bracketNotation);
        this.ipTable = parser.getAdresses(bracketNotation);
        isCircle(ipTable);
    }

    private boolean isCircle(List<IP> ipTable){
        Set<String> duplicatTester = new HashSet<>(); 
        for (IP adress : ipTable) {
            if(!duplicatTester.add(adress.toString())){
                return true;
            }
        }
        return false;
    }

    public void mergeTupleTable(TupleTable mergeTable) {
        this.table.addAll(mergeTable.getTable());
    }

    public List<Tuple<IP>> getTable() {
        return table;
    }

}
