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

    private final static String REGEX_BYTE = "(((2[0-5][0-5])|(2[0-4]\\d)|(1\\d\\d)|([1-9]\\d)|([0-9])))";
    private final static String REGEX_IP = "(" + REGEX_BYTE + "\\.){3}" + REGEX_BYTE;

    private final static String REGEX_BRACKET = "[(]" + REGEX_IP + "(" + "\\s" + REGEX_IP + ")+" + "[)]";
    private final static String REGEX_BRACKET_NODES = "(" + REGEX_IP + "\\s)*" + REGEX_IP;
    private final static String REGEX_START = "^([(]" + REGEX_IP + "\\s.+)";

    private final static String IP_SEPARATOR = " ";

    private final static int FIRST_ELEMENT = 0;

    private final static String ERROR_BRACKETNOTATION = "Error: Wrong bracketnotation";
    private final static String ERROR_TREE = "Error: Tree is circle";

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

        if (!notation.matches(REGEX_START)) {
            throw new ParseException(ERROR_BRACKETNOTATION);
        }

        Pattern pat = Pattern.compile(REGEX_BRACKET);

        while (!notation.matches(REGEX_IP)) {

            Matcher mat = pat.matcher(notation);
            if (mat.find()) {
                String innerBracket = mat.group();
                innerBracket = innerBracket.replace("(", "");
                innerBracket = innerBracket.replace(")", "");

                String[] splitted = innerBracket.split(IP_SEPARATOR);

                for (int i = 1; i < splitted.length; i++) {

                    IP one = getAdressFromList(node, new IP(splitted[FIRST_ELEMENT]));
                    IP two = getAdressFromList(node, new IP(splitted[i]));
                    edges.add(new Edge(one, two));
                    edges.add(new Edge(two, one));
                }
                notation = notation.replaceFirst(REGEX_BRACKET, splitted[FIRST_ELEMENT]);
            } else {

                throw new ParseException(ERROR_BRACKETNOTATION);
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
        if (!withoutBrackets.matches(REGEX_BRACKET_NODES)) {
            throw new ParseException(ERROR_BRACKETNOTATION);
        }
        String[] splitted = withoutBrackets.split(IP_SEPARATOR);
        List<IP> returnList = new ArrayList<>();
        Set<String> duplicatTester = new HashSet<>();
        for (String adress : splitted) {
            if (!duplicatTester.add(adress)) {
                throw new ParseException(ERROR_TREE);
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
