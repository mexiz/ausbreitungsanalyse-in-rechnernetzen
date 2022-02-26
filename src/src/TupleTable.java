
import java.util.ArrayList;
import java.util.List;

public class TupleTable {

    List<Tuple<String>> table = new ArrayList<>();
    TupleParser parser = new TupleParser();
    String root;

    public void add(String ip1, String ip2) {
        table.add(new Tuple<>(ip1, ip2));
    }

    // TEST

    public void test() {
        for (Tuple<String> con : table) {
            System.out.println(con.connection());
        }
    }
}
