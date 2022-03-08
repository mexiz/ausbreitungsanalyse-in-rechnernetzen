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

    public List<Edge> setEdgesFromNotation(List<IP> node, final String bracketNotation) throws ParseException {

        List<Edge> edges = new ArrayList<>();
        String notation = bracketNotation;

        if (!notation.matches(regexStart)) {
            throw new ParseException("Error: Wrong bracketnotation");
        }

        Pattern pat = Pattern.compile(regexBracket);

        while (!notation.matches(regexIP)) {

            Matcher mat = pat.matcher(notation);
            if (mat.find()) {
                String innerBracket = mat.group();
                innerBracket = innerBracket.replace("(", "");
                innerBracket = innerBracket.replace(")", "");

                String[] splitted = innerBracket.split(" ");

                for (int i = 1; i < splitted.length; i++) {

                    IP one = getAdressFromList(node, new IP(splitted[0]));
                    IP two = getAdressFromList(node, new IP(splitted[i]));
                    edges.add(new Edge(one, two));
                    edges.add(new Edge(two, one));
                }
                notation = notation.replaceFirst(regexBracket, splitted[0]);
            } else {

                throw new ParseException("Error: Wrong bracketnotation");
            }
        }
        return edges;
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

    public IP getAdressFromList(List<IP> nodes, IP adress) {
        return nodes.indexOf(adress) < 0 ? null : nodes.get(nodes.indexOf(adress));
    }

}
