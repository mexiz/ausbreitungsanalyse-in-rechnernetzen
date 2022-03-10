package edu.kit.informatik.model;

import java.util.Arrays;

import edu.kit.informatik.ParseException;

/**
 * Die IP-adresse
 * 
 * @author uwhlp
 * @version 1.0
 */

public class IP implements Comparable<IP> {

    private int[] adress;

    private final static  int MAX_ADDRESS_BYTES = 4;

    private final static  String REGEX_BYTE = "(((2[0-5][0-5])|(2[0-4]\\d)|(1\\d\\d)|([1-9]\\d)|([0-9])))";
    private final static  String REGEX_IP = "(" + REGEX_BYTE + "\\.){3}" + REGEX_BYTE;

    private final static  String ERROR_IP_INVALID = "Error: Invalid IP-Adress!";

    private final static  String BYTE_SEPARATOR = ".";

    private final static  int COMPARE_SMALLER = -1;
    private final static  int COMPARE_HIGHER = 1;
    private final static  int COMPARE_EQUAL = 0;

    /**
     * Konstruktor mit der Pointnotation
     * 
     * @param pointNotation die Adresse
     * @throws ParseException wenn die Adresse nicht der Form passt
     */
    public IP(final String pointNotation) throws ParseException {

        if (!pointNotation.matches(REGEX_IP)) {
            throw new ParseException(ERROR_IP_INVALID);
        }

        String[] adressString = pointNotation.split("\\.");

        if (adressString.length != MAX_ADDRESS_BYTES) {
            throw new ParseException(ERROR_IP_INVALID);
        }
        adress = new int[adressString.length];

        for (int i = 0; i < adressString.length; i++) {
            adress[i] = Integer.parseInt(adressString[i]);

        }

    }

    /**
     * Konstruktor für die copy()-Methide
     * 
     * @param adress die Adresse
     */

    public IP(int[] adress) {
        this.adress = adress;
    }

    @Override
    public String toString() {

        StringBuilder stringBuilder = new StringBuilder(String.valueOf(this.adress[0]));
        for (int i = 1; i < adress.length; i++) {
            stringBuilder.append(BYTE_SEPARATOR);
            stringBuilder.append(this.adress[i]);
        }
        return stringBuilder.toString();
    }

    @Override
    public int compareTo(IP o) {
        for (int i = 0; i < adress.length; i++) {
            if (o.adress[i] < this.adress[i]) {
                return COMPARE_HIGHER;
            }
            if (o.adress[i] > this.adress[i]) {
                return COMPARE_SMALLER;
            }
        }
        return COMPARE_EQUAL;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + MAX_ADDRESS_BYTES;
        result = prime * result + Arrays.hashCode(adress);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        IP other = (IP) obj;
        if (MAX_ADDRESS_BYTES != other.MAX_ADDRESS_BYTES) {
            return false;
        }
        if (!Arrays.equals(adress, other.adress)) {
            return false;
        }
        return true;
    }

    /**
     * Kopiert das IP-Objekt
     * 
     * @return das kopierte IP-Objekt
     */

    public IP copy() {
        return new IP(adress.clone());
    }
}
