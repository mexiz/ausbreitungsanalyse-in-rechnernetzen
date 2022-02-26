import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TupleParser {

    private String regexByte = "(((2[0-5][0-5])|(2[0-4]\\d)|(1\\d\\d)|([1-9]\\d)|([1-9])|(0)))";
    private String regexIP = "\\b(" + regexByte + "\\.){3}" + regexByte + "\\b";


    public List<Tuple<IP>> getTree(final String bracketNotation) throws ParseException {

        String bracket = bracketNotation.substring(1, bracketNotation.length() - 1);
        List<String> splitted = new ArrayList<>(Arrays.asList(bracket.split(" ")));
        List<Tuple<IP>> table = new ArrayList<>();
        String root = splitted.get(0);
        int i = 1;
        
        while (i < splitted.size()) {
            String ip = splitted.get(i);
            if (ip.charAt(0) == '(') {
                table.add(new Tuple<>(new IP(root), new IP(ip.substring(1))));
                int end = this.indexBracketClosed(splitted, i);
                List<Tuple<IP>> supTuples = this.getTree(this.toString(splitted, i, end));
                table.addAll(supTuples);
                splitted.subList(i, end).clear();
            } else if (ip.charAt(ip.length() - 1) == ')') {
                if (ip.charAt(ip.length() - 2) != ')') {
                    ip = ip.substring(0, ip.length() - 1);
                    table.add(new Tuple<>(new IP(root), new IP(ip)));
                    
                    splitted.remove(i);
                }
            } else {

                table.add(new Tuple<>(new IP(root), new IP(ip)));
                splitted.remove(i);
            }
        }
        return table;
    }

    private int indexBracketClosed(List<String> list, int start) {

        int openBracket = 0;
        int closedBracket = 0;

        int i = start;

        do {
            String ip = list.get(i);
            if (ip.charAt(0) == '(') {
                openBracket++;
                ip = ip.substring(1);
            }

            ip = ip.replaceAll(regexIP, "");
            closedBracket += ip.length();
            i++;
        } while (openBracket != closedBracket);

        return i;
    }

    private String toString(List<String> list, int start, int end) {

        List<String> l = list.subList(start, end);

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < l.size() - 1; i++) {
            sb.append(l.get(i));
            sb.append(" ");
        }

        sb.append(l.get(l.size() - 1));
        return sb.toString();
    }

}
