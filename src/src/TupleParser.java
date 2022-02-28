import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Die Parser für die Umformung von der Bracketnotation in die Tupel Notation
 * und umgekehrt.
 * 
 * @author uwlhp
 * @version 1.0
 */

public class TupleParser {

    private String regexByte = "(((2[0-5][0-5])|(2[0-4]\\d)|(1\\d\\d)|([1-9]\\d)|([0-9])))";
    private String regexIP = "(" + regexByte + "\\.){3}" + regexByte;
    private String regexBracket = "[(]" + regexIP + "(\\s" + regexIP + ")+" + "[)]";

    private static final String SEPARATOR_IP = " ";

    /**
     * Die Methode wandelt den Baum, der als Bracketnotation dargestellt wird,
     * in die Tupelnotation um.
     * 
     * @param bracketNotation Der Baum als Bracketnotation
     * @return Gibt eine List von Tupeln zurück
     * @throws ParseException Wenn die Bracketnotation- oder die Adressenform nicht
     *                        richtig ist
     */

    public List<Tuple<IP>> getTuple(final String bracketNotation) throws ParseException {

        // Entfernt die äußerste Klammer
        String bracket = bracketNotation.substring(1, bracketNotation.length() - 1);
        List<String> splitted = new ArrayList<>(Arrays.asList(bracket.split(SEPARATOR_IP)));
        getAdresses(bracketNotation);
        List<Tuple<IP>> table = new ArrayList<>();

        String root = splitted.get(0);
        int i = 1;

        // Solange bis die Liste splitted nur noch den Root hat
        while (i < splitted.size()) {
            String ip = splitted.get(i);
            if (ip.charAt(0) == '(') {
                table.add(new Tuple<>(new IP(root), new IP(ip.substring(1))));
                int end = this.indexBracketClosed(splitted, i);

                // Rekursion um eine Ebene tiefer in den Graphen zu kommen
                List<Tuple<IP>> supTuples = this.getTuple(this.toString(splitted, i, end));
                table.addAll(supTuples);

                // Löscht die Subliste
                splitted.subList(i, end).clear();
            } else if (ip.charAt(ip.length() - 1) == ')') {
                ip = ip.substring(0, ip.length() - 1);
                table.add(new Tuple<>(new IP(root), new IP(ip)));
                splitted.remove(i);
            } else {
                table.add(new Tuple<>(new IP(root), new IP(ip)));
                splitted.remove(i);
            }
        }
        return table;
    }

    private List<IP> getAdresses(String bracketNotation) throws ParseException {
        String withoutBrackets = bracketNotation.replace("(", "");
        withoutBrackets = withoutBrackets.replace(")", "");
        if(!withoutBrackets.matches("(" + regexIP + "\\s)*" + regexIP)){
            throw new ParseException("Error: Invalid Bracketnotation Form");
        }
        String[] splitted = withoutBrackets.split(" ");
        List<IP> returnList = new ArrayList<>();
        for (String string : splitted) {
            returnList.add(new IP(string));
        }
        return returnList;
    }

    /**
     * Die Hilfsmethode gibt den Index zurück, wo sich der Substrang vom Baum
     * schließt.
     * 
     * @param list  Die Liste mit den Addressen von der Bracketnotation
     * @param start Der Startwert, wo der Substrang begonnen hat
     * @return git den Index zurück
     */

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

    /**
     * Die Hilfsmethode verbindet die durch den {@link TupleParser#SEPARATOR_IP}
     * getrennte Liste zusammen.
     * 
     * @param list  Die durch ein {@link TupleParser#SEPARATOR_IP} getrennte Liste
     * @param start Startwert der Liste
     * @param end   Endwert der Liste
     * @return Gibt den zusammengesetzten String zurück
     */

    private String toString(List<String> list, int start, int end) {

        List<String> l = list.subList(start, end);

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < l.size() - 1; i++) {
            sb.append(l.get(i));
            sb.append(SEPARATOR_IP);
        }

        sb.append(l.get(l.size() - 1));
        return sb.toString();
    }

}
