package edu.kit.informatik.graph;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.kit.informatik.ParseException;
import edu.kit.informatik.model.Edge;
import edu.kit.informatik.model.IP;

/**
 * Die Parser für die Umformung von der Bracketnotation in die Tupel Notation
 * und umgekehrt.
 * 
 * @author uwlhp
 * @version 1.0
 */

public class GraphParser {

    private static final String SEPARATOR_IP = " ";
    private String regexByte = "(((2[0-5][0-5])|(2[0-4]\\d)|(1\\d\\d)|([1-9]\\d)|([0-9])))";
    private String regexIP = "(" + regexByte + "\\.){3}" + regexByte;
    private String regexBracket = "[(]" + regexIP + "(\\s" + regexIP + ")" + "(\\s" + regexIP + ")*" + "[)]";
    private String regexStart = "^([(]" + regexIP + "\\s.+)";

    /**
     * Die Methode wandelt den Baum, der als Bracketnotation dargestellt wird,
     * in die Tupelnotation um.
     * 
     * @param node            die Liste mit Adressen
     * @param bracketNotation Der Baum als Bracketnotation
     * @return Gibt eine List von Kanten zurück
     * @throws ParseException Wenn die Bracketnotation- oder die Adressenform nicht
     *                        richtig ist
     */

    public List<Edge> getEdgesFromBracketNotation(List<IP> node, final String bracketNotation) throws ParseException {

        List<Edge> ed = new ArrayList<>();
        String test = bracketNotation;

        if (!test.matches(regexStart)) {
            throw new ParseException("message");
        }

        Pattern pat = Pattern.compile(regexBracket);

        while (!test.matches(regexIP)) {

            Matcher mat = pat.matcher(test);
            if (mat.find()) {
                String sub = mat.group();
                sub = sub.replace("(", "");
                sub = sub.replace(")", "");

                String[] splitted = sub.split(" ");

                for (int i = 1; i < splitted.length; i++) {

                    IP ip1 = getIPFromNode(node, new IP(splitted[0]));
                    IP ip2 = getIPFromNode(node, new IP(splitted[i]));

                    ed.add(new Edge(ip1, ip2));
                    ed.add(new Edge(ip2, ip1));
                }

                test = test.replaceFirst(regexBracket, splitted[0]);
            } else {

                throw new ParseException("Error: Wrong bracketnotation");
            }
        }
        return ed;
    }

    /**
     * Die Methode gibt alle Adressen die in der Bracketnotation drinnen sind aus
     * 
     * @param bracketNotation Der Baum als Bracketnotation
     * @return Gibt eine List von IPs zurück
     * @throws ParseException Wenn die Bracketnotation- oder die Adressenform nicht
     *                        richtig ist
     */

    public List<IP> getNodesFromBracketNotation(String bracketNotation) throws ParseException {
        String withoutBrackets = bracketNotation.replace("(", "");
        withoutBrackets = withoutBrackets.replace(")", "");
        if (!withoutBrackets.matches("(" + regexIP + "\\s)*" + regexIP)) {
            throw new ParseException("Error: Invalid Bracketnotation Form");
        }
        String[] splitted = withoutBrackets.split(" ");
        List<IP> returnList = new ArrayList<>();
        Set<String> duplicatTester = new HashSet<>();
        for (String adress : splitted) {
            if (!duplicatTester.add(adress)) {
                throw new ParseException("Error: Tree is circle");
            }
            returnList.add(new IP(adress));
        }
        return returnList;
    }

    /**
     * Gibt die Adresse aus die sich in der Liste befindet.
     * 
     * @param nodes  Die Liste mit den Knotenpunkten
     * @param adress Die Adresse die gesucht werden soll
     * @return null wenn die Adresse nicht vorhanden ist und die IP wenn sie
     *         vorhanden ist
     */

    public IP getIPFromNode(List<IP> nodes, IP adress) {
        if (adress == null) {
            return null;
        }
        for (IP ip : nodes) {
            if (ip.compareTo(adress) == 0) {
                return ip;
            }
        }
        return null;
    }

}
